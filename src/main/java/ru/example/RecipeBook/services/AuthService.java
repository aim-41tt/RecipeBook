package ru.example.RecipeBook.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ru.example.RecipeBook.models.User;
import ru.example.RecipeBook.models.DTO.LoginUserDTO;
import ru.example.RecipeBook.models.DTO.RegUserDTO;
import ru.example.RecipeBook.repository.UserRepo;
import ru.example.RecipeBook.utils.Valider;

@Service
public class AuthService {

	private static User userAuth = null;

	private final UserRepo userRepo;

	public AuthService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	public void regUser(RegUserDTO regUser) throws IllegalArgumentException {
		if (isBlankRegUserDTO(regUser)) {
			throw new IllegalArgumentException("Не все поля заполнены.");
		}
		Optional<User> userOpt = userRepo.findByLogin(regUser.login());
		if (userOpt.isPresent()) {
			throw new IllegalArgumentException("Пользователь с логином " + regUser.login() + " уже существует.");
		}
		if (regUser.Email() != null) {
			String email = regUser.Email();
			if (!Valider.isValidEmail(email)) {
				throw new IllegalArgumentException("Некоректный Email " + regUser.Email());
			}
		}

		User user = new User(regUser.name(), regUser.login(), regUser.password(), regUser.Email());
		userRepo.save(user);
		userAuth = user;
	}

	private boolean isBlankRegUserDTO(RegUserDTO regUser) {
		return regUser.name().isBlank() || regUser.login().isBlank() || regUser.password().isBlank()
				|| regUser.Email().isBlank();
	}

	private boolean isBlankLoginUserDTO(LoginUserDTO loginUser) {
		return loginUser.login().isBlank() || loginUser.login().isBlank();
	}

	public void loginUser(LoginUserDTO loginUser) throws IllegalArgumentException {
		if (isBlankLoginUserDTO(loginUser)) {
			throw new IllegalArgumentException("Не все поля заполнены.");
		}
		Optional<User> userOpt = userRepo.findByLogin(loginUser.login());
		if (!userOpt.isPresent()) {
			throw new IllegalArgumentException("Пользователя с логином " + loginUser.login() + " не существует.");
		}
		User user = userOpt.get();
		if (!user.getPassword().equals(loginUser.password())) {
			throw new IllegalArgumentException("Неверный логин или пароль");
		}
		userAuth = user;
	}

	/**
	 * @return the userAuth
	 */
	public User getUserAuth() {
		return userAuth;
	}
	public static User getUserAuthStat() {
		return userAuth;
	}
	public User getUserById(Long id) {
		User user = userRepo.findById(id).get();
		return user;
	}

}
