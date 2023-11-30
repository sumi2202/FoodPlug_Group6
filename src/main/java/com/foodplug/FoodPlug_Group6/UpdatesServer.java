package com.foodplug.FoodPlug_Group6;//responsible for publish-subscribe mechanism , sends all updates to the restaurant
//responsible for publish-subscribe mechanism , sends all updates to the restaurant
//acts as middleman between the restaurant server and the client

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


public class UpdatesServer {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // PULL socket that receives notifications from RestaurantServer
            ZMQ.Socket restaurantNotification = context.createSocket(SocketType.PULL);
            restaurantNotification.bind("tcp://*:5557");

            // PUB socket that publishes messages to clients
            ZMQ.Socket clientMessenger = context.createSocket(SocketType.PUB);
            clientMessenger.bind("tcp://*:5556");
//
//            // PUSH socket to pushing menus to Restaurant Server
//            ZMQ.Socket restaurantMenu = context.createSocket(SocketType.PUSH);
//            restaurantMenu.bind("tcp://*:5558");

            // SUB socket that subscribes to menus from RestaurantServer
            ZMQ.Socket menuSubscriber = context.createSocket(SocketType.SUB);
            menuSubscriber.connect("tcp://localhost:5558");
            menuSubscriber.subscribe("");  // Subscribe to all messages

            //PULL socket that receives the orders from the client
            ZMQ.Socket orderReceiver = context.createSocket(SocketType.PULL);
            orderReceiver.connect("tcp://localhost:5560");

            //PUSH socket to forward the order to Restaurant Server
            ZMQ.Socket orderForwarder = context.createSocket(SocketType.PUSH);
            orderForwarder.connect("tcp://localhost:5561");


            while (true) {
                // send notifications from RestaurantServer to client
                String notification = restaurantNotification.recvStr(0);
                clientMessenger.send(notification);

//                // sends received menus from Restaurant Server to clients
//                String menu = restaurantMenu.recvStr(0);
//                clientMessenger.send(menu);

                // Receive menus from RestaurantServer and send them to clients
                String receivedMenus = menuSubscriber.recvStr(0);
                System.out.println(receivedMenus);
                clientMessenger.send(receivedMenus);

                //Receive orders from client and then send them to RestuarantServer
                String receivedOrders = orderReceiver.recvStr(0);
                System.out.println("Received orders: " + receivedOrders);
                orderForwarder.send(receivedOrders);

            }
        }
    }


}
