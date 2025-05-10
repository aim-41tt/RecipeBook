package ru.example.RecipeBook.utils;

public class Valider {

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
		return email != null && email.matches(emailRegex);
	}

}
