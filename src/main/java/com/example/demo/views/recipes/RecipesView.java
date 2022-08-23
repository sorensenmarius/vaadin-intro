package com.example.demo.views.recipes;

import com.example.demo.data.entity.Recipe;
import com.example.demo.data.service.CrmService;
import com.example.demo.layouts.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "recipes", layout = MainLayout.class)
public class RecipesView extends VerticalLayout {
    Grid<Recipe> grid = new Grid<>(Recipe.class);
    RecipeForm form;
    CrmService service;

    public RecipesView(CrmService service) {
        this.service = service;

        setSizeFull();

        configureForm();
        configureGrid();

        add(getToolbar(), getContent());
        updateList();

        closeEditor();
    }

    private void configureForm() {
        form = new RecipeForm();
        form.setWidth("25em");

        form.addListener(RecipeForm.SaveEvent.class, this::saveRecipe);
        form.addListener(RecipeForm.DeleteEvent.class, this::deleteRecipe);
        form.addListener(RecipeForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("name", "description");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editRecipe(event.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private HorizontalLayout getToolbar() {
        Button addRecipeButton = new Button("Add recipe");
        addRecipeButton.addClickListener(click -> addRecipe());

        HorizontalLayout toolbar = new HorizontalLayout(addRecipeButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRecipe() {
        grid.asSingleSelect().clear();
        editRecipe(new Recipe());
    }

    private void editRecipe(Recipe recipe) {
        if (recipe == null) {
            closeEditor();
        } else {
            form.setRecipe(recipe);
            form.setVisible(true);
            addClassName("editing");
        }
    }


    private void updateList() {
        grid.setItems(service.getRecipes());
    }

    private void closeEditor() {
        form.setRecipe(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveRecipe(RecipeForm.SaveEvent event) {
        service.saveRecipe(event.getRecipe());
        updateList();
        closeEditor();
    }

    private void deleteRecipe(RecipeForm.DeleteEvent event) {
        service.deleteRecipe(event.getRecipe());
        updateList();
        closeEditor();
    }
}
