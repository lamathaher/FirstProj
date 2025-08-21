package com.example.UserSystem.service;

import com.example.UserSystem.model.Meal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    private final String FILE_PATH = "meals.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ✅ وجبات ثابتة
    private final List<Meal> staticMeals = List.of(
        new Meal("Flank Steak Fajita", "/images/steak.png", "high-protein", 350, 30, 10, 12),
        new Meal("Blackened Shrimp", "/images/shrimp.png", "high-protein", 320, 28, 8, 10),
        new Meal("Chicken Tinga", "/images/chicken.png", "high-protein", 340, 32, 9, 11),
        new Meal("Corn Avocado Salad", "/images/salad.png", "salads", 300, 12, 15, 14),
        new Meal("Creamy Pesto Salmon", "/images/salmon.png", "high-protein", 450, 28, 3, 18),
        new Meal("Chicken Philly Cheesesteak", "/images/meat.png", "carbs", 400, 29, 25, 15),
        new Meal("Shrimp Avocado Salad", "/images/shrimpSalad.png", "salads", 330, 26, 9, 11),
        new Meal("Chicken Avocado Burrito", "/images/borrito.png", "carbs", 370, 31, 12, 14),
        new Meal("Arugula Pear Salad", "/images/pearsalad.png", "salads vegan", 370, 31, 12, 14),
        new Meal("Bruschetta Pizza", "/images/pizza.png", "vegan carbs", 400, 3, 22, 14),
        new Meal("Meghan Markle Pasta", "/images/pasta.png", "vegan carbs", 440, 5, 22, 10)
    );

    // ✅ كل الوجبات (ثابتة + مضافة)
    public List<Meal> getMeals() {
        List<Meal> meals = new ArrayList<>(staticMeals);
        meals.addAll(readMealsFromFile());
        return meals;
    }

    // ✅ الوجبات الغير مؤرشفة فقط
    public List<Meal> getActiveMeals() {
        return getMeals().stream()
                .filter(meal -> !meal.isArchived())
                .collect(Collectors.toList());
    }

    // ✅ أضف وجبة جديدة
    public void addMeal(Meal meal) {
        List<Meal> meals = readMealsFromFile();
        meals.add(meal);
        writeMealsToFile(meals);
    }

    // ✅ أرشفة وجبة مضافة (فقط من الملف، مو الثابتة)
    public void archiveMeal(String mealName) {
        List<Meal> meals = readMealsFromFile();

        meals.stream()
                .filter(meal -> meal.getName().equalsIgnoreCase(mealName))
                .findFirst()
                .ifPresent(meal -> meal.setArchived(true));

        writeMealsToFile(meals);
    }

    private List<Meal> readMealsFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) return new ArrayList<>();
            return objectMapper.readValue(file, new TypeReference<List<Meal>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeMealsToFile(List<Meal> meals) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), meals);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
