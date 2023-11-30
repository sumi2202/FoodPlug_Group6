package com.foodplug.FoodPlug_Group6;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class RestaurantServer {
    // Hardcoded restaurants
    private static final List<String> RESTAURANTS = Arrays.asList(
            "Restaurant0", "Restaurant1", "Restaurant2", "Restaurant3",
            "Restaurant4", "Restaurant5", "Restaurant6", "Restaurant7",
            "Restaurant8", "Restaurant9"
    );

    // Hardcoded menus for each restaurant
    private static final List<String> MENUS = Arrays.asList(
            "Restaurant0: Chicken Burger, Veggie Burger, Cheese Pizza",
            "Restaurant1: Pepperoni Pizza, Pasta Alfredo, Spaghetti Bolognese",
            "Restaurant2: Caesar Salad, Greek Salad, Vanilla Ice Cream",
            "Restaurant3: Chocolate Ice Cream, Chicken Burger, Veggie Burger",
            "Restaurant4: Cheese Pizza, Pepperoni Pizza, Pasta Alfredo",
            "Restaurant5: Spaghetti Bolognese, Caesar Salad, Greek Salad",
            "Restaurant6: Vanilla Ice Cream, Chocolate Ice Cream, Chicken Burger",
            "Restaurant7: Veggie Burger, Cheese Pizza, Pepperoni Pizza",
            "Restaurant8: Pasta Alfredo, Spaghetti Bolognese, Caesar Salad",
            "Restaurant9: Greek Salad, Vanilla Ice Cream, Chocolate Ice Cream"
    );

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // ROUTER socket to receive subscription preferences from clients
            ZMQ.Socket clientSubscriptions = context.createSocket(SocketType.ROUTER);
            clientSubscriptions.bind("tcp://*:5559");

            // PUSH socket that pushes notifications to the Client
            ZMQ.Socket pushUpdates = context.createSocket(SocketType.PUSH);
            pushUpdates.connect("tcp://*:5557");

            // PUB socket that publishes menus to the Client
            ZMQ.Socket publishMenus = context.createSocket(SocketType.PUB);
            publishMenus.bind("tcp://*:5558");

            // PULL socket that gets the orders from Updates Server
            ZMQ.Socket orderReceiver = context.createSocket(SocketType.PULL);
            orderReceiver.connect("tcp://*:5561");


            // List of restaurants that the client is subscribed to
            List<String> subscribedRestaurants = new ArrayList<>();

            while (!Thread.currentThread().isInterrupted()){
                //get subscription choice from the clients
                String clientMessage = clientSubscriptions.recvStr(0);
                subscribedRestaurants = Arrays.asList(clientMessage.split(","));

                // Send notifications and menus to the Client
                for (String restaurantName : RESTAURANTS) {
                    if (subscribedRestaurants.contains(restaurantName)) {
                        String notifications = "Notification: " + restaurantName + ": Notification ";
                        pushUpdates.send(notifications);

                        // Send hardcoded menu to the Client
                        String menu = "Menu: " + restaurantName + ": " +MENUS.get(RESTAURANTS.indexOf(restaurantName));
                        System.out.println(menu);
                        publishMenus.send(menu);

                        Thread.sleep(1000);

                        //Prints the received order from the updates server
                        String receivedOrder = orderReceiver.recvStr(0);
                        System.out.println("The received order: " + receivedOrder);

                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
