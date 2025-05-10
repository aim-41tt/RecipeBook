package ru.example.RecipeBook.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false)
	private String userName;

	@Column(nullable = false, unique = true)
	private String login;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Recipe> recipes = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Favorite> favorites = new ArrayList<Favorite>();

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", login=" + login + ", email=" + email + "]";
	}
	/**
	 * @param userId
	 * @param userName
	 * @param login
	 * @param password
	 * @param email
	 * @param recipes
	 * @param favorites
	 */
	public User(Long userId, String userName, String login, String password, String email, List<Recipe> recipes,
			List<Favorite> favorites) {
		this.userId = userId;
		this.userName = userName;
		this.login = login;
		this.password = password;
		this.email = email;
		this.recipes = recipes;
		this.favorites = favorites;
	}

	/**
	 * @param userName
	 * @param login
	 * @param password
	 * @param email
	 */
	public User(String userName, String login, String password, String email) {
		this.userName = userName;
		this.login = login;
		this.password = password;
		this.email = email;
	}

	/**
	 * 
	 */
	public User() {
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the recipes
	 */
	public List<Recipe> getRecipes() {
		return recipes;
	}

	/**
	 * @param recipes the recipes to set
	 */
	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	/**
	 * @return the favorites
	 */
	public List<Favorite> getFavorites() {
		return favorites;
	}

	/**
	 * @param favorites the favorites to set
	 */
	public void setFavorites(List<Favorite> favorites) {
		this.favorites = favorites;
	}

}
