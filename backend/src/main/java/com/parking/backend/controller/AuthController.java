package com.parking.backend.controller;

import com.parking.backend.model.User;
import com.parking.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    //  SIGNUP
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userService.getUserByMobile(user.getMobileNumber()) != null) {
            return ResponseEntity.badRequest().body("User already exists with this mobile number");
        }
        userService.registerUser(user);
        return ResponseEntity.ok("Signup successful");
    }

    //  LOGIN
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String mobileNumber,
                                        @RequestParam String password) {
        boolean isValid = userService.authenticate(mobileNumber, password);
        if (isValid) return ResponseEntity.ok("Login successful");
        return ResponseEntity.status(401).body("Invalid credentials");
    }


}
