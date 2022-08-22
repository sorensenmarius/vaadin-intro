package com.example.demo.data.service;

import com.example.demo.data.entity.Recipe;
import com.example.demo.data.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {
    private final RecipeRepository recipeRepository;

    public CrmService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public long countRecipes() {
        return recipeRepository.count();
    }

    public void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    public void saveRecipe(Recipe recipe) {
        if (recipe == null) {
            System.err.println("Recipe is null. Are you sure you have connected your form to the application?");
            return;
        }

        recipeRepository.save(recipe);
    }

    public List<Recipe> getRecipes() {
        return getRecipes("");
    }

    public List<Recipe> getRecipes(String searchString) {
        return recipeRepository.search(searchString);
    }
}
