package ru.example.RecipeBook.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.aim_41tt.sling.core.App;
import ru.example.RecipeBook.frames.LoginUser;

@Configuration
public class ConfigApp {
	private App app = new App(350, 200);

	@Bean
	protected App appBean() {
		app.setMainScreen(LoginUser.class);
		return app;
	}

}
