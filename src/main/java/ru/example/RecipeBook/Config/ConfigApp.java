package ru.example.RecipeBook.Config;

import dev.aim_41tt.sling.core.App;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.example.RecipeBook.ui.MainPage;
import ru.example.RecipeBook.ui.auth.LoginPage;
import ru.example.RecipeBook.ui.auth.RegPage;
import ru.example.RecipeBook.ui.pages.CategoriePage;
import ru.example.RecipeBook.ui.pages.FavoritePage;
import ru.example.RecipeBook.ui.pages.MainMenuPage;
import ru.example.RecipeBook.ui.pages.SettingsPage;
import ru.example.RecipeBook.ui.pages.recipe.CreateRecipePage;
import ru.example.RecipeBook.ui.pages.recipe.EditRecipePage;
import ru.example.RecipeBook.ui.pages.recipe.ReceptsPage;
import ru.example.RecipeBook.ui.reports.ReportsPage;

@Configuration
public class ConfigApp {

    @Bean
    protected App app() {
        App app = new App("RecipeBook", 745, 700);
        app.getFrame().setResizable(false);
        app.setMainScreen(MainPage.class);
        return app;
    }
    
    @Bean
    protected ObjectMapper mapper() {
    	return new ObjectMapper();
    }

    @EventListener
    public void onContextInitialized(ContextRefreshedEvent event) {
    	
    	ClientSettings settings = event.getApplicationContext().getBean(ClientSettings.class);
    	settings.loadFile();
    	
        App app = event.getApplicationContext().getBean(App.class);
        app.addPage(RegPage.class);
        app.addPage(LoginPage.class); 
        app.addPage(MainMenuPage.class);
        
        app.addPage(ReceptsPage.class);
        app.addPage(FavoritePage.class);
        app.addPage(CategoriePage.class);
        app.addPage(ReportsPage.class);
        app.addPage(SettingsPage.class);
       
        app.addPage(CreateRecipePage.class);
        app.addPage(EditRecipePage.class);
        
        app.start();
    }
}
