package com.foodplug.FoodPlug_Group6;
// UserRepository.java
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByFirstNameAndPassword(String firstName, String password);
}
