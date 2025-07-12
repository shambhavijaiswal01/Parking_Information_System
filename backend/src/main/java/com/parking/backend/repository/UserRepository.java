package com.parking.backend.repository;

import com.parking.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMobileNumber(String mobileNumber);
}
