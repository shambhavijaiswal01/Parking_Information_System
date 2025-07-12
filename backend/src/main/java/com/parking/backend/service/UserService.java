// File: UserService.java
package com.parking.backend.service;

import com.parking.backend.model.User;
import com.parking.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ⬇️ NEW METHOD to fix the error
    public User getUserByMobile(String mobileNumber) {
        return userRepository.findByMobileNumber(mobileNumber);
    }

    public User registerUser(User user) {
       return userRepository.save(user);
    }

    public boolean authenticate(String mobileNumber, String rawPassword) {
        User user = userRepository.findByMobileNumber(mobileNumber);
        return user != null && user.getPassword().equals(rawPassword);
    }
}
