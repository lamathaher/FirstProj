package com.example.UserSystem.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlanService {

    /**
     * Builds a workout + meal plan based on goal, dietary preference, and allergies.
     *
     * @param goal       "lose_weight", "build_muscle", or "maintain_health"
     * @param diet       "vegan", "vegetarian", "keto", etc.
     * @param allergies  list of allergy keys like ["nuts", "gluten"]
     * @return map with keys "workout" and "meals"
     */
    public Map<String, String> getPlanContent(String goal, String diet, List<String> allergies) {
        // Handle null inputs gracefully
        if (goal == null) goal = "";
        if (diet == null) diet = "";
        if (allergies == null) allergies = Collections.emptyList();

        // Normalize inputs to lowercase and trim for consistent key matching
        String normalizedGoal = goal.toLowerCase(Locale.ROOT);
        String normalizedDiet = diet.toLowerCase(Locale.ROOT);
        List<String> sortedAllergies = new ArrayList<>();
        for (String a : allergies) {
            if (a != null && !a.isBlank()) {
                sortedAllergies.add(a.toLowerCase(Locale.ROOT).trim());
            }
        }
        Collections.sort(sortedAllergies);  // Sort to get consistent key order
        String allergyKey = String.join(",", sortedAllergies);

        // Composite key built from goal, diet, and allergies
        String compositeKey = String.format("%s|%s|%s", normalizedGoal, normalizedDiet, allergyKey);

        // Predefined specific plans for 6 main cases
        Map<String, Map<String, String>> plans = new HashMap<>();

        plans.put("lose_weight|vegan|", Map.of(
                "workout", "Focus on moderate cardio (30–45 minutes daily) with light HIIT 3 times a week.",
                "meals", "Low-calorie vegan meals: green smoothie breakfast, whole grain salad with chickpeas for lunch, grilled vegetables with tofu for dinner."
        ));

        plans.put("build_muscle|keto|dairy", Map.of(
                "workout", "Strength training 4 days a week with compound lifts like squats, deadlifts, and bench press.",
                "meals", "High-fat, dairy-free keto meals: avocado and egg breakfast, steak with sautéed broccoli for lunch, fatty fish with greens for dinner."
        ));

        plans.put("maintain_health|vegetarian|gluten", Map.of(
                "workout", "Flexibility and mobility exercises plus daily 20-minute walks and light strength sessions twice a week.",
                "meals", "Gluten-free vegetarian options: quinoa breakfast bowl, roasted vegetable lunch with lentils, vegetable soup with brown rice for dinner."
        ));

        plans.put("lose_weight|vegetarian|nuts", Map.of(
                "workout", "Light-to-moderate cardio daily combined with bodyweight strength training 3 times a week.",
                "meals", "Nut-free vegetarian meals: plant-based yogurt with berries (no nuts) for breakfast, large bean salad for lunch, cooked veggies with plant protein for dinner."
        ));

        plans.put("build_muscle|vegan|", Map.of(
                "workout", "Progressive resistance training 4–5 days including push, pull, and leg days.",
                "meals", "Vegan muscle-building meals: protein oatmeal, lentil burgers with rice, chickpea curry with sweet potato."
        ));

        plans.put("maintain_health|keto|other", Map.of(
                "workout", "Balanced mix of light cardio and moderate strength 3 times a week to maintain fitness.",
                "meals", "Moderate keto meals: veggie omelet for breakfast, chicken salad for lunch, grilled steak with sautéed spinach for dinner."
        ));

        // Return specific plan if exact key match found
        if (plans.containsKey(compositeKey)) {
            return plans.get(compositeKey);
        }

        // Fallback logic: generalized plans based on goal only
        if ("lose_weight".equals(normalizedGoal)) {
            return Map.of(
                    "workout", "Increase daily activity + 30 minutes of cardio each day with bodyweight exercises.",
                    "meals", "Slight calorie deficit focusing on vegetables and lean protein; reduce refined carbs."
            );
        } else if ("build_muscle".equals(normalizedGoal)) {
            return Map.of(
                    "workout", "Targeted resistance training 4 days a week with gradual load progression.",
                    "meals", "Higher calorie intake with emphasis on protein: evenly spaced meals to support recovery."
            );
        } else if ("maintain_health".equals(normalizedGoal)) {
            return Map.of(
                    "workout", "Balanced routine: cardio, strength, and mobility distributed across the week.",
                    "meals", "Macronutrient balance emphasizing whole foods."
            );
        }

        // Generic default plan if no match or goal unknown
        return Map.of(
                "workout", "Regular light activity such as daily walking or bodyweight movements.",
                "meals", "Balanced meals with a mix of carbs, protein, and healthy fats."
        );
    }
}
