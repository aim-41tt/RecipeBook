package ru.example.RecipeBook.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.example.RecipeBook.models.Recipe;

public interface RecipeRepo extends JpaRepository<Recipe, Long> {
	@Query("SELECT r FROM Recipe r JOIN FETCH r.category ORDER BY r.recipeId DESC")
	List<Recipe> findLatestWithCategory(PageRequest pageable);

    
}
