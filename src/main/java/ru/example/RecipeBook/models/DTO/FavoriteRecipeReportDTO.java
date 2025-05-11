package ru.example.RecipeBook.models.DTO;

public class FavoriteRecipeReportDTO {
	private String userName;
	private String recipeName;
	private String ingredients;
	private String instructions;

	public FavoriteRecipeReportDTO(String userName, String recipeName, String ingredients, String instructions) {
		this.userName = userName;
		this.recipeName = recipeName;
		this.ingredients = ingredients;
		this.instructions = instructions;
	}
	public FavoriteRecipeReportDTO() {
		// TODO Auto-generated constructor stub
	}
	
	
	// геттеры
	public String getUserName() {
		return userName;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public String getIngredients() {
		return ingredients;
	}

	public String getInstructions() {
		return instructions;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param recipeName the recipeName to set
	 */
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	/**
	 * @param ingredients the ingredients to set
	 */
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @param instructions the instructions to set
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
}
