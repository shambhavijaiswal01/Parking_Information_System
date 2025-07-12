package com.parking.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_hub")
public class ParkingHub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hubId;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "hub_name", nullable = false)
    private String hubName;

    @Column(name = "total_slots", nullable = false)
    private Integer totalSlots;

    @Column(name = "available_slots", nullable = false)
    private Integer availableSlots;

    // --- Getters and Setters ---
    public Long getHubId() {
        return hubId;
    }

    public void setHubId(Long hubId) {
        this.hubId = hubId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getHubName() {
        return hubName;
    }

    public void setHubName(String hubName) {
        this.hubName = hubName;
    }

    public Integer getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(Integer totalSlots) {
        this.totalSlots = totalSlots;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }
}
