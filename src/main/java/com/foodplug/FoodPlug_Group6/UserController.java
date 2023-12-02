package com.foodplug.FoodPlug_Group6;

// UserController.java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/subscribe")
    public String subscribe(Model model) {
        // Retrieve and pass restaurant data to the model
        // You can use a service class to interact with the database
        // and retrieve the restaurant information based on the user's location.
        return "subscribe";
    }
}
