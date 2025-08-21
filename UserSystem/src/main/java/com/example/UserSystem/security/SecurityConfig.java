package com.example.UserSystem.security;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.IOException;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    // ❗ بدون تشفير مؤقتًا (مش للإنتاج)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ✅ نجاح تسجيل الدخول بناء على الدور
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication)
                    throws IOException, ServletException {

                if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_staff"))) {
                    response.sendRedirect("/staff/dashboard");
                } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_gym_member"))) {
                    response.sendRedirect("/dashboard");
                } else {
                    response.sendRedirect("/login?unauthorized");
                }
            }
        };
    }
    @Bean
    public ITemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/"); // مجلد القوالب
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false); // مهم أثناء التطوير
        return templateResolver;
    }
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.addDialect(new SpringSecurityDialect()); // 👈 مهم
        return engine;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/register", "/signup", "/login", "/style22.css", "/LAMA.css",
                             "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
            .requestMatchers("/staff/edit-meals").hasRole("staff")   // edit-meals لموظفي staff
            .requestMatchers("/staff/**", "/coach-dashboard/**").hasRole("staff")
            .requestMatchers("/dashboard", "/success", "/profile-setup").hasRole("gym_member")
            .requestMatchers("/users/**").hasRole("staff")
            .requestMatchers("/meals/add").hasRole("staff")
            .requestMatchers("/class/add").hasRole("staff")
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .maximumSessions(-1) // -1 = unlimited sessions
            .maxSessionsPreventsLogin(false)
        )
        .formLogin(form -> form
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .successHandler(customSuccessHandler())
            .failureUrl("/login?error=true")
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        .userDetailsService(customUserDetailsService);

    return http.build();

    }
}
