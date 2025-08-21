/*package com.example.UserSystem.security;

import com.example.UserSystem.model.User;
import com.example.UserSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // محاولة إيجاد المستخدم حسب البريد الإلكتروني
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("User not found with email: " + email); // لتتبع الأخطاء أثناء التطوير
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        System.out.println("User found: " + user.getEmail()); // لتأكيد العثور على المستخدم

        // إضافة الدور بصيغة ROLE_... كما يتطلب Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
        
    }
    
}


*/

package com.example.UserSystem.security;

import com.example.UserSystem.model.User;
import com.example.UserSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            System.out.println("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        System.out.println("User found: " + user.getEmail());

        return new CustomUserDetails(user); // ✅ هذا هو التعديل الأهم
    }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
  




    }
    


