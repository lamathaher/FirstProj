package com.example.UserSystem.model;

public class Meal {
    private boolean archived = false;

    private String name;
    private String image; // اسم الصورة فقط مثل "steak.png"
    private String category; // high-protein, vegan, carbs, ...
    private int calories;
    private int protein;
    private int carbs;
    private int fat;
    public Meal() {
    }
    public Meal(String name, String imageUrl, String category, int calories, int protein, int carbs, int fat) {
        this.name = name;
        this.image = imageUrl;
        this.category = category;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}
	public int getProtein() {
		return protein;
	}
	public void setProtein(int protein) {
		this.protein = protein;
	}
	public int getCarbs() {
		return carbs;
	}
	public void setCarbs(int carbs) {
		this.carbs = carbs;
	}
	public int getFat() {
		return fat;
	}
	public void setFat(int fat) {
		this.fat = fat;
	}
	 public boolean isArchived() {
	        return archived;
	    }

	    public void setArchived(boolean archived) {
	        this.archived = archived;
	    }

    // Getters and Setters
}

