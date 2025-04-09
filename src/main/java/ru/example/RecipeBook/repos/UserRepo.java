package ru.example.RecipeBook.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.example.RecipeBook.entitys.User;

public interface UserRepo extends JpaRepository<User, Long> {

}
