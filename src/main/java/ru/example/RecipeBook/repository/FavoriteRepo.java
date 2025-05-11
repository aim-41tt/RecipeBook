package ru.example.RecipeBook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.example.RecipeBook.models.Favorite;
import ru.example.RecipeBook.models.Recipe;
import ru.example.RecipeBook.models.User;
import ru.example.RecipeBook.models.DTO.FavoriteRecipeReportDTO;

public interface FavoriteRepo extends JpaRepository<Favorite, Long> {

	Optional<Favorite> findByUserAndRecipe(User user, Recipe recipe);

	boolean existsByUserAndRecipe(User user, Recipe recipe);

	void deleteByUserAndRecipe(User user, Recipe recipe);

	@Query("SELECT f FROM Favorite f WHERE f.user.login = :value OR f.user.email = :value")
	List<Favorite> findFavoritesByUserLoginOrEmail(@Param("value") String value);


}
