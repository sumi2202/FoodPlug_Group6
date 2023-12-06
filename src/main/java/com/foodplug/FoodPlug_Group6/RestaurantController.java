package com.foodplug.FoodPlug_Group6;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.Arrays;
import java.util.List;

    //this controller will handle the subscription.html page
    @Controller
    public class RestaurantController {
        //will display all the restaurants available to the user
        private static final List<String> RESTAURANTS = Arrays.asList(
                "Restaurant0", "Restaurant1", "Restaurant2", "Restaurant3",
                "Restaurant4", "Restaurant5", "Restaurant6", "Restaurant7",
                "Restaurant8", "Restaurant9"
        );

        @GetMapping("/restaurants")
        public String showRestaurants(Model model) {
            model.addAttribute("restaurants", RESTAURANTS);
            return "subscribe";
        }

        //here the restaurant controller sends subscriptions to the RestauarantServer
        @PostMapping("/subscribe")
        public String subscribe(@RequestParam(name = "restaurants") String selectedRestaurants, Model model) {
            // subscription  based on selectedRestaurants
            List<String> subscribedRestaurants = Arrays.asList(selectedRestaurants.split(","));
            model.addAttribute("subscribedRestaurants", subscribedRestaurants);


            // Send the subscription to RestaurantServer using ZeroMQ
            try (ZContext context = new ZContext()) {
                ZMQ.Socket socket = context.createSocket(SocketType.REQ);
                socket.connect("tcp://localhost:5559");
                socket.send(selectedRestaurants);

                // Receive any response from the server if needed
                String response = socket.recvStr(0);
                System.out.println("Server response: " + response);
            }

            //  fetch menus for subscribed restaurants


            // For example, you may want to fetch notifications and menus from your backend service

            return "subscribe";  // Redirect back to the subscription page
        }
    }


