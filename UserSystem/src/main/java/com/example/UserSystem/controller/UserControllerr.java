package com.example.UserSystem.controller;

import com.example.UserSystem.model.User;
import com.example.UserSystem.service.UserServicee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserControllerr {

    @Autowired
    private UserServicee userService;  // ✅ تم التصحيح

   /* @PreAuthorize("hasRole('ADMIN') or hasRole('staff')")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User updated = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('staff')")
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok("تم تعطيل الحساب بنجاح");
    }
    */

    

}
