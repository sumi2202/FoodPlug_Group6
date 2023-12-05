package com.foodplug.FoodPlug_Group6;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EntityScan("com.foodplug.FoodPlug_Group6")
@SpringBootApplication(scanBasePackages = {"com.test", "com.bean"})

public class FoodPlugGroup6Application {

	public static void main(String[] args) {
		SpringApplication.run(FoodPlugGroup6Application.class, args);
	}

}
