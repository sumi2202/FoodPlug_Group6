package com.foodplug.FoodPlug_Group6;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
public class loginController {

    @PostMapping("/myLogin")
    public String authenticate(HttpServletRequest request) {
        String username = request.getParameter("firstName");
        String password = request.getParameter("Password");

        // Performing authentication based on the database class
        Database myDatabase = new Database();
        boolean isAuthenticated = myDatabase.authenticate(username, password);

        if (isAuthenticated) {
            System.out.println("Authentication successful for user: " + username);
            return "redirect:/subscribe"; // Redirect to the subscribe page
        } else {
            System.out.println("Authentication failed for user: " + username);
            return "redirect:/login?error"; // Redirect with an error parameter
        }
    }

    @GetMapping("/subscribe")
    public String subscription() {
        return "subscribe";
    }
}