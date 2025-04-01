package com.dms.supplymanager.controllers;

import com.dms.supplymanager.entities.User;
import com.dms.supplymanager.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin/user")
    public ResponseEntity<Map<String,Object>> userSignIn(@RequestBody User user) {
        Map<String,Object> map = authService.userSignIn(user);
        if(map.containsKey("error"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PostMapping("/signin/admin")
    public ResponseEntity<Map<String,Object>> adminSignIn(@RequestBody User user) {
        Map<String,Object> map = authService.adminSignIn(user);
        if(map.containsKey("error"))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }

    @PostMapping("/signup/user")
    public ResponseEntity<String> userSignUp(@RequestBody User user) {
        Map<String,String> map = authService.userSignUp(user);
        if(map.containsKey("error"))
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(map.get("error"));
        return ResponseEntity.status(HttpStatus.CREATED).body(map.get("success"));
    }
    @PostMapping("/signup/admin")
    public ResponseEntity<String> adminSignUp(@RequestBody User user) {
        Map<String,String> map = authService.adminSignUp(user);
        if(map.containsKey("error"))
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(map.get("error"));
        return ResponseEntity.status(HttpStatus.CREATED).body(map.get("success"));
    }
}
