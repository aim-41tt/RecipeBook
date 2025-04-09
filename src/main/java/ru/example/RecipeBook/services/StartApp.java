package ru.example.RecipeBook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.aim_41tt.sling.core.App;
import jakarta.annotation.PostConstruct;

@Service
public class StartApp {

	@Autowired
	private App app;
	
	@PostConstruct
	public void start() {
		app.startSpring();
	}
	
}
