package ru.example.RecipeBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import dev.aim_41tt.sling.config.ConfigApp;

@SpringBootApplication
public class RecipeBookApplication {

	public static void main(String[] args) {
//		ConfigApp.configSpring();
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(RecipeBookApplication.class, args);
	}

}
