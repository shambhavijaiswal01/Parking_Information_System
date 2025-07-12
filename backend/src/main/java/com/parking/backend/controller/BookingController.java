package com.parking.backend.controller;

import com.parking.backend.dto.BookingResponse;
import com.parking.backend.model.*;
import com.parking.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private SlotRepository slotRepo;

    // --- 1. Create Booking ---
    @Transactional
    @PostMapping("/create")
    public ResponseEntity<String> createBooking(@RequestParam String mobileNumber,
                                                @RequestParam String carNumber,
                                                @RequestParam Integer slotId) {

        User user = userRepo.findByMobileNumber(mobileNumber);
        if (user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        Slot slot = slotRepo.findById(slotId).orElse(null);
        if (slot == null || !slot.getIsAvailable())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Slot not available");

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSlot(slot);
        booking.setCarNumber(carNumber);
        booking.setBookingTime(LocalDateTime.now());
        booking.setArrivalTime(null);
        booking.setDepartureTime(null);
        booking.setStatus("Booked");

        slot.setIsAvailable(false);
        slotRepo.save(slot);

        bookingRepo.save(booking);
        return ResponseEntity.ok("Booking created successfully!");
    }

    // --- 2. Get User Bookings ---
    @GetMapping("/user/{mobileNumber}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(@PathVariable String mobileNumber) {
        User user = userRepo.findByMobileNumber(mobileNumber);
        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        List<Booking> bookings = bookingRepo.findByUser(user);
        List<BookingResponse> responses = bookings.stream().map(booking -> new BookingResponse(
                booking.getSlot().getSlotName(),
                booking.getCarNumber(),
                booking.getBookingTime(),
                booking.getArrivalTime(),
                booking.getDepartureTime(),
                booking.getStatus()
        )).toList();

        return ResponseEntity.ok(responses);
    }

    // --- 3. Mark Arrival ---
    @Transactional
    @PostMapping("/arrive")
    public ResponseEntity<String> markArrival(@RequestParam String carNumber) {
        Booking booking = bookingRepo.findTopByCarNumberAndStatusOrderByBookingTimeDesc(carNumber, "Booked");
        if (booking == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active booking found for this car.");

        booking.setArrivalTime(LocalDateTime.now());
        booking.setStatus("Arrived");
        bookingRepo.save(booking);
        return ResponseEntity.ok("Arrival recorded.");
    }

    // --- 4. Mark Departure ---
    @Transactional
    @PostMapping("/depart")
    public ResponseEntity<String> markDeparture(@RequestParam String carNumber) {
        Booking booking = bookingRepo.findTopByCarNumberAndStatusOrderByBookingTimeDesc(carNumber, "Arrived");
        if (booking == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No arrived booking found for this car.");

        booking.setDepartureTime(LocalDateTime.now());
        booking.setStatus("Departed");
        ResponseEntity.ok("Departed");

        // Free the slot
        Slot slot = booking.getSlot();
        slot.setIsAvailable(true);
        slotRepo.save(slot);

        bookingRepo.save(booking);
        return ResponseEntity.ok("Departure recorded and slot freed.");
    }

    // --- 5. Expire Bookings Not Arrived Within 15 Minutes ---
   /* @Scheduled(fixedRate = 300000) // Runs every 5 minutes
    @Transactional
    public void expireOldBookings() {
        List<Booking> bookings = bookingRepo.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : bookings) {
            if ("Booked".equals(booking.getStatus()) &&
                    booking.getArrivalTime() == null &&
                    booking.getBookingTime().plusMinutes(15).isBefore(now)) {

                booking.setStatus("Expired");
                Slot slot = booking.getSlot();
                slot.setIsAvailable(true);
                slotRepo.save(slot);
                bookingRepo.save(booking);
            }
        }
    }*/
}
