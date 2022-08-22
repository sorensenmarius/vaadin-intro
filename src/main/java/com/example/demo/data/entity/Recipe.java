package com.example.demo.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Recipe extends AbstractEntity {
    @NotBlank
    private String name;
    @Nullable
    private String description;

//    private List<Sizes> sizes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

/*    public List<Sizes> getSizes() {
        return sizes;
    }

    public void setSizes(List<Sizes> sizes) {
        this.sizes = sizes;
    }*/
}
