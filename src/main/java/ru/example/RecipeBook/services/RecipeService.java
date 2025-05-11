package ru.example.RecipeBook.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import jakarta.transaction.Transactional;
import ru.example.RecipeBook.models.Recipe;
import ru.example.RecipeBook.repository.RecipeRepo;

@Service
public class RecipeService {

    private final RecipeRepo recipeRepo;

    public RecipeService(RecipeRepo recipeRepo) {
		this.recipeRepo = recipeRepo;
	}
    
    @Transactional
    public List<Recipe> getLatestRecipes(int limit) {
        return recipeRepo.findLatestWithCategory(PageRequest.of(0, limit));
    }

	@Transactional
    public Optional<Recipe> getById(Long id) {
        return recipeRepo.findById(id);
    }
	@Transactional
    public Recipe create(Recipe recipe) {
        return recipeRepo.save(recipe);
    }
	@Transactional
    public void updateImage(Long recipeId, byte[] imageData) {
        Recipe recipe = recipeRepo.findById(recipeId).orElseThrow();
        recipe.setImage(imageData);
        recipeRepo.save(recipe);
    }
	@Transactional
	public void delId(Long id) {
		recipeRepo.deleteById(id);
	}

}
