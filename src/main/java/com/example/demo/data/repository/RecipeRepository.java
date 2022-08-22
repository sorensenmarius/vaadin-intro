package com.example.demo.data.repository;

import com.example.demo.data.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    @Query("select r from Recipe r " + "where lower(r.name) like lower(concat('%', :searchTerm, '%'))")
    List<Recipe> search(@Param("searchTerm") String searchTerm);
}
