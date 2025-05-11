package ru.example.RecipeBook.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.example.RecipeBook.models.Category;
import ru.example.RecipeBook.repository.CategoryRepo;

@Service
public class CategoryService {

	private CategoryRepo categoryRepo;

	public CategoryService(CategoryRepo categoryRepo) {
		this.categoryRepo = categoryRepo;
	}
	@Transactional
	public List<Category> getAll() {
		return categoryRepo.findAllWithRecipes();
	}
	@Transactional
	public void deleteCategory(Long categoryId) {
		categoryRepo.deleteById(categoryId);
	}
	@Transactional
	public void createCategory(Category category) {
		categoryRepo.save(category);
	}

}
