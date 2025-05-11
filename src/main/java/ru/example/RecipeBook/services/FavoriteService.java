package ru.example.RecipeBook.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.example.RecipeBook.models.Favorite;
import ru.example.RecipeBook.models.Recipe;
import ru.example.RecipeBook.models.User;
import ru.example.RecipeBook.models.DTO.FavoriteRecipeReportDTO;
import ru.example.RecipeBook.repository.FavoriteRepo;
import ru.example.RecipeBook.repository.RecipeRepo;

@Service
public class FavoriteService {

	private final FavoriteRepo favoriteRepository;
	private final RecipeRepo recipeRepository;
	private final AuthService userRepository;

	public FavoriteService(FavoriteRepo favoriteRepository, RecipeRepo recipeRepository, AuthService userRepository) {
		this.favoriteRepository = favoriteRepository;
		this.recipeRepository = recipeRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public boolean toggleFavorite(Long userId, Long recipeId) {
		User user = userRepository.getUserById(userId);

		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

		if (favoriteRepository.existsByUserAndRecipe(user, recipe)) {
			favoriteRepository.deleteByUserAndRecipe(user, recipe);
			return false; // удалено из избранного
		} else {
			Favorite favorite = new Favorite();
			favorite.setUser(user);
			favorite.setRecipe(recipe);
			favoriteRepository.save(favorite);
			return true; // добавлено в избранное
		}
	}

	public boolean isFavorite(Long userId, Long recipeId) {
		User user = userRepository.getUserById(userId);
		Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
		if (user == null || recipe == null)
			return false;
		return favoriteRepository.existsByUserAndRecipe(user, recipe);
	}
	@Transactional
	public List<FavoriteRecipeReportDTO> getFavoriteReportByUser(String input) {
	    List<Favorite> favorites = favoriteRepository.findFavoritesByUserLoginOrEmail(input);

	    return favorites.stream()
	        .map(fav -> {
	            Recipe recipe = fav.getRecipe();
	            User user = fav.getUser();
//String userName, String recipeName, String ingredients, String instructions
	            return new FavoriteRecipeReportDTO(
	                user.getUserName(),
	                recipe.getRecipeName(),
	                recipe.getIngredients(),
	                recipe.getInstructions()
	                
	            );
	        })
	        .collect(Collectors.toList());
	}


}
