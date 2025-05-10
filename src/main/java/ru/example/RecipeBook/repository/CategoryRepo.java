package ru.example.RecipeBook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.example.RecipeBook.models.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
