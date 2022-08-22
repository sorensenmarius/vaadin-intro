package com.example.demo.layouts;

import com.example.demo.views.MainView;
import com.example.demo.views.recipes.RecipesView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Vaadin CRM");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(
                new DrawerToggle(),
                logo
        );

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink homeLink = new RouterLink("Home", MainView.class);
        homeLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink recipesLink = new RouterLink("Recipes", RecipesView.class);
        recipesLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                homeLink,
                recipesLink
        ));
    }
}