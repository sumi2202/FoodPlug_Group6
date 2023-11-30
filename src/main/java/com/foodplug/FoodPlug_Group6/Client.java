package com.foodplug.FoodPlug_Group6;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class Client {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {

            // Socket to send user subscription preferences to the Restaurant Server
            ZMQ.Socket subscriptionSender = context.createSocket(SocketType.DEALER);
            subscriptionSender.connect("tcp://localhost:5559");        //changed from connect to bind

            // SUB socket to subscribe to messages from the Restaurant Server
            ZMQ.Socket clientSubscriber = context.createSocket(SocketType.SUB);
            clientSubscriber.connect("tcp://localhost:5558");
            clientSubscriber.subscribe("".getBytes(ZMQ.CHARSET));  // Subscribe to all messages

            ZMQ.Socket orderSender = context.createSocket(SocketType.PUSH);
            orderSender.bind("tcp://localhost:5560");    //Changed from 5562 to 5560


            // Scanner for user input
            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    // Authenticate user
                    System.out.println("Hello, Welcome to FoodPlug, your number 1 food ordering service:");
                    System.out.println("Enter first name:");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();

                    // Authenticating the user based on the database
                    Database database = new Database();
                    boolean isAuthenticated = database.authenticate(firstName, password);

                    if (isAuthenticated) {
                        System.out.println("Authentication Successful, Welcome customer");

                        // Send subscription preferences to the Restaurant Server
                        System.out.println("Here are the restaurants: ");
                        System.out.println("\tRestaurant0,\tRestaurant1, \t Restaurant2, \tRestaurant3,\tRestaurant4, \n \tRestaurant5,\tRestaurant6, \tRestaurant7 \tRestaurant8, \tRestaurant9");

                        System.out.println("Enter restaurants to subscribe to (comma-separated):");
                        String[] restaurants = scanner.nextLine().split(",");
                        subscriptionSender.send(String.join(",", restaurants));

                        // Accessing messages from the Restaurant Server
                        while (true) {
                            String receivedUpdates = clientSubscriber.recvStr(0);
                            System.out.println(receivedUpdates);
                            if (receivedUpdates.startsWith("Notification")) {
                                System.out.println(receivedUpdates);
                            } else if (receivedUpdates.startsWith("Menu")) {
                                System.out.println(receivedUpdates.split(":")[2]);  // Print the menu content

                                System.out.println("Please enter your order: "); //Asks user to enter their order
                                String orderInput = scanner.nextLine();

                                orderSender.send(orderInput);        //Sends this order to the UpdatesServer
                                System.out.println("Sent order: " + orderInput);
                            } else {
                                System.out.println(receivedUpdates);
                            }
                        }
                    } else {
                        System.out.println("Authentication failed. Exiting...");
                        break;  // Exit the loop if authentication fails
                    }
                }
            }
        }
    }
}
