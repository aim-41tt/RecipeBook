package ru.example.RecipeBook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ru.example.RecipeBook.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	@Query("SELECT c FROM Category c LEFT JOIN FETCH c.recipes r LEFT JOIN FETCH r.user")
	List<Category> findAllWithRecipes();

}
