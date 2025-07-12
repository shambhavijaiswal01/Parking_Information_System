package com.parking.backend.service;

import com.parking.backend.model.Booking;
import com.parking.backend.repository.BookingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component // Important for Spring to pick this up
public class BookingExpiryScheduler {

    @Autowired
    private BookingRepository bookingRepository;

    @Scheduled(fixedRate = 60000) // every 60 seconds
    @Transactional
    public void expireOldBookings() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(15);
        List<Booking> activeBookings = bookingRepository.findByStatus("Booked");

        for (Booking booking : activeBookings) {
            if (booking.getBookingTime().isBefore(cutoffTime)) {
                booking.setStatus("Expired");
                booking.getSlot().setIsAvailable(true);
                System.out.println("⏰ Expired booking ID: " + booking.getBookingId());
            }
        }

        bookingRepository.flush(); // Save changes immediately
    }
}
