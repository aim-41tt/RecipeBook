package ru.example.RecipeBook.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "favorites", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "recipe_id" }) })
public class Favorite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoriteId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = false)
	private Recipe recipe;

	public Favorite() {
		// TODO Auto-generated constructor stub
	}

	public Favorite(Long favoriteId, User user, Recipe recipe) {
		this.favoriteId = favoriteId;
		this.user = user;
		this.recipe = recipe;
	}

	/**
	 * @return the favoriteId
	 */
	public Long getFavoriteId() {
		return favoriteId;
	}

	/**
	 * @param favoriteId the favoriteId to set
	 */
	public void setFavoriteId(Long favoriteId) {
		this.favoriteId = favoriteId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the recipe
	 */
	public Recipe getRecipe() {
		return recipe;
	}

	/**
	 * @param recipe the recipe to set
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

}
