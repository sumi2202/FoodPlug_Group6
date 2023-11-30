//package com.foodplug.FoodPlug_Group6;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MyUserDetailsService implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Load the user from your database (or any other source).
//        // Throw a UsernameNotFoundException if the user could not be found.
//        // You'll need to adapt this to your requirements.
//
//        return User.withUsername(username)
//                .password("password")
//                .roles("USER")
//                .build();
//    }
//}
//
