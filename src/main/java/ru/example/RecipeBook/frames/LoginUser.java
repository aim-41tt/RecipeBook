package ru.example.RecipeBook.frames;

import org.springframework.stereotype.Component;

import dev.aim_41tt.sling.defaults.ui.AuthPanel;
import ru.example.RecipeBook.repos.UserRepo;

@Component
public class LoginUser extends AuthPanel {

	@Override
	protected void authUser(String userName, char[] password) {
		System.out.println(userName + "|||" + password.toString());
	}
}
