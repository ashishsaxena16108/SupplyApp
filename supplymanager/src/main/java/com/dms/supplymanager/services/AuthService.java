package com.dms.supplymanager.services;

import com.dms.supplymanager.Util.JwtUtil;
import com.dms.supplymanager.entities.User;
import com.dms.supplymanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    public Map<String,Object> userSignIn(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        Map<String,Object> m = new HashMap<>();
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword()) && !existingUser.isAdmin()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(),existingUser.getRole());
            m.put("token", jwt);
            m.put("user", existingUser);
            return m;
        } else {
            m.put("error", "Invalid username or password for user");
            return m;
        }
    }

    public Map<String,Object> adminSignIn(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        Map<String,Object> m = new HashMap<>();
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword()) && existingUser.isAdmin()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final String jwt = jwtUtil.generateToken(userDetails.getUsername(),existingUser.getRole());
            System.out.println(jwt);
            m.put("token", jwt);
            m.put("user", existingUser);
            return m;
        } else {
            m.put("error", "Invalid username or password for admin");
            return m;
        }
    }

    public Map<String,String> userSignUp(User user) {
        Map<String,String> map = new HashMap<>();
        if (userRepository.findByEmail(user.getEmail()) != null) {
            map.put("error","Email already in use");
            return map;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.INSPECTOR);
        userRepository.save(user);
        map.put("success","User sign-up successful");
        return map;

    }
    public Map<String,String> adminSignUp(User user) {
        Map<String,String> map = new HashMap<>();
        if (userRepository.findByEmail(user.getEmail()) != null) {
            map.put("error","Email already in use");
            return map;
        }
        user.setRole(User.Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        map.put("success","User sign-up successful");
        return map;
    }

}
