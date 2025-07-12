package com.parking.backend.repository;

import com.parking.backend.model.Booking;
import com.parking.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser(User user);
    List<Booking> findByStatus(String status);
    Booking findTopByCarNumberAndStatusOrderByBookingTimeDesc(String carNumber, String status);
}
