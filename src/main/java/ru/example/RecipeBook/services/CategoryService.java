package ru.example.RecipeBook.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.example.RecipeBook.models.Category;
import ru.example.RecipeBook.repository.CategoryRepo;

@Service
public class CategoryService {

	private CategoryRepo categoryRepo;

	public CategoryService(CategoryRepo categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	public List<Category> getAll() {
		return categoryRepo.findAll();
	}

}
