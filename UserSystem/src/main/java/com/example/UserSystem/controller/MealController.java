package com.example.UserSystem.controller;

import com.example.UserSystem.model.Meal;
import com.example.UserSystem.service.MealService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MealController {

    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    // عرض كل الوجبات
    @GetMapping("/user/meals")
    public String userMeals(Model model) {
        model.addAttribute("meals", mealService.getMeals());
        return "meals";
    }

    // صفحة إضافة وجبة جديدة + فورم الحصص بنفس الصفحة
    @GetMapping("/staff/edit-meals")
    public String showAddMealForm(Model model) {
        model.addAttribute("meal", new Meal()); // نموذج وجبة فارغ
        return "edit-meal"; // نفس الصفحة تحتوي على الفورمين
    }

    // حفظ الوجبة الجديدة
    @PostMapping("/staff/add-meal")
    public String addMeal(@ModelAttribute Meal meal) {
        mealService.addMeal(meal);
        return "redirect:/staff/edit-meals"; // رجعة لنفس الصفحة لإضافة المزيد
    }

   

    // صفحة عرض الوجبات (ثابتة + ديناميكية)
    @GetMapping("/meals")
    public String showMeals(Model model) {
        List<Meal> meals = mealService.getMeals();
        model.addAttribute("meals", meals);
        return "meals";
    }

    // API لو بدك JSON للوجبات
    @GetMapping("/api/meals")
    @ResponseBody
    public List<Meal> getMealsJson() {
        return mealService.getMeals();
    }

    // أرشفة وجبة بناءً على اسمها
    @GetMapping("/meals/archive/{name}")
    public String archiveMeal(@PathVariable String name) {
        mealService.archiveMeal(name);
        return "redirect:/user/meals";
    }
}
