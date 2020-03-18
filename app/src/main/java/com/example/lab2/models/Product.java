package com.example.lab2.models;

import android.graphics.drawable.Drawable;

public class Product {
    private String name;
    private int img;
    private String description;

    public Product(String name, int img, String description) {
        this.name = name;
        this.img = img;
        this.description = description;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
