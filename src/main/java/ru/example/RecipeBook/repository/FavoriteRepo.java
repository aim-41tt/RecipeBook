package ru.example.RecipeBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.example.RecipeBook.models.Favorite;

public interface FavoriteRepo extends JpaRepository<Favorite, Long>{

}
