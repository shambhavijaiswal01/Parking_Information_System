package com.parking.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "parking_slot")
public class Slot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer slotId;

    @ManyToOne
    @JoinColumn(name = "hub_id", nullable = false)
    private ParkingHub parkingHub;

    @Column(name = "slot_number")
    private String slotName;

    @Column(name = "is_available")
    private Boolean isAvailable;

    // --- Getters and Setters ---
    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public ParkingHub getParkingHub() {
        return parkingHub;
    }

    public void setParkingHub(ParkingHub parkingHub) {
        this.parkingHub = parkingHub;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
