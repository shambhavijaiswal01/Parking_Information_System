package com.parking.backend.dto;

import java.time.LocalDateTime;

public class BookingResponse {
    private String slotName;
    private String carNumber;
    private LocalDateTime bookingTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String status;

    // Constructor
    public BookingResponse(String slotName, String carNumber, LocalDateTime bookingTime,
                           LocalDateTime arrivalTime, LocalDateTime departureTime, String status) {
        this.slotName = slotName;
        this.carNumber = carNumber;
        this.bookingTime = bookingTime;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.status = status;
    }

    // Getters
    public String getSlotName() {
        return slotName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public String getStatus() {
        return status;
    }
}
