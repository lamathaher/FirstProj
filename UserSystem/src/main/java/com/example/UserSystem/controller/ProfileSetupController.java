package com.example.UserSystem.controller;

import com.example.UserSystem.model.User;
import com.example.UserSystem.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileSetupController {

    @Autowired
    private UserRepository userRepository;

    // عرض نموذج تعبئة البيانات
    @GetMapping("/profile-setup")
    public String showProfileSetupForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        System.out.println("userDetails: " + userDetails);
        if (userDetails != null) {
            User user = userRepository.findByEmail(userDetails.getUsername());
            if (user != null) {
                model.addAttribute("user", user);
                return "profile-setup";
            }
        }
        return "redirect:/login";
    }

    // حفظ البيانات بعد تعبئة الفورم
    @PostMapping("/profile-complete")
    public String completeProfile(@RequestParam Long id,
                                  @RequestParam double weight,
                                  @RequestParam String weight_unit,
                                  @RequestParam double height,
                                  @RequestParam String height_unit,
                                  @RequestParam int age,
                                  @RequestParam String gender,
                                  @RequestParam String goal,
                                  @RequestParam String allergies,
                                  @RequestParam String dietaryPreferences) {
        System.out.println("ID Received in /profile-complete: " + id);

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            System.out.println("❌ لم يتم العثور على مستخدم بالمعرف: " + id);
            return "redirect:/register";
        }

        User user = optionalUser.get();

        user.setWeight(weight);
        user.setWeight_unit(weight_unit);
        user.setHeight(height);
        user.setHeight_unit(height_unit);
        user.setAge(age);
        user.setGender(gender);
        user.setGoal(goal);
        user.setAllergies(allergies);
        user.setDietaryPreferences(dietaryPreferences);

        userRepository.save(user);

        // بعد حفظ البيانات، نوجه المستخدم إلى لوحة التحكم
        return "redirect:/dashboard";
    }

    // صفحة نجاح تعبئة الملف الشخصي (اختيارية)
    @GetMapping("/profile-complete")
    public String showSuccessPage() {
    	return "redirect:/dashboard";
    }
}
