package ru.example.RecipeBook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.example.RecipeBook.models.User;

public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByLogin(String login);
}
