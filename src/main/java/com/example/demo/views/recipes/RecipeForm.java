package com.example.demo.views.recipes;

import com.example.demo.data.entity.Recipe;
import com.example.demo.enums.Sizes;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.Arrays;

public class RecipeForm extends VerticalLayout {
    TextField name = new TextField("Name");
    TextField description = new TextField("Description");
    CheckboxGroup<Sizes> sizes = new CheckboxGroup<>("Size", Arrays.asList(Sizes.values()));

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Recipe> binder = new BeanValidationBinder<>(Recipe.class);
    private Recipe recipe;

    public RecipeForm() {
        binder.bindInstanceFields(this);
        add(name, description, sizes, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new DeleteEvent(this, recipe)));
        close.addClickListener(e -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        binder.readBean(recipe);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(recipe);
            fireEvent(new SaveEvent(this, recipe));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class RecipeFormEvent extends ComponentEvent<RecipeForm> {
        private Recipe recipe;

        protected RecipeFormEvent(RecipeForm source, Recipe recipe) {
            super(source, false);
            this.recipe = recipe;
        }

        public Recipe getRecipe() {
            return recipe;
        }
    }

    public static class SaveEvent extends RecipeFormEvent {
        SaveEvent(RecipeForm source, Recipe recipe) {
            super(source, recipe);
        }
    }

    public static class DeleteEvent extends RecipeFormEvent {
        DeleteEvent(RecipeForm source, Recipe recipe) {
            super(source, recipe);
        }

    }

    public static class CloseEvent extends RecipeFormEvent {
        CloseEvent(RecipeForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

