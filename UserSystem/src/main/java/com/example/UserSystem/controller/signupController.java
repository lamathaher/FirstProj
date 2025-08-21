package com.example.UserSystem.controller;

import com.example.UserSystem.model.User;
import com.example.UserSystem.repository.UserRepository;
import com.example.UserSystem.security.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class signupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    // عرض صفحة التسجيل
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("user") User user, HttpServletRequest request) {
        // حفظ كلمة المرور مشفرة
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));

        // حفظ المستخدم الجديد في قاعدة البيانات
        userRepository.save(user);

        // تحميل بيانات المستخدم من قاعدة البيانات
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

        // إنشاء Authentication Token باستخدام بيانات المستخدم مع الصلاحيات
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // تعيين Authentication في سياق الأمان (Security Context)
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // حفظ Security Context في جلسة الويب
        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        // التوجيه بناءً على دور المستخدم
        boolean isStaff = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_staff"));

        boolean isGymMember = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_gym_member"));

        if (isStaff) {
            return "redirect:/staff/dashboard";  // إذا كان مدرب
        } else if (isGymMember) {
            return "redirect:/profile-setup";   // إذا كان مشترك في الجيم
        } else {
            return "redirect:/login";            // بشكل افتراضي توجه لتسجيل الدخول
        }
    }
}