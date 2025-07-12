package com.parking.backend.controller;

import com.parking.backend.model.Slot;
import com.parking.backend.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    @Autowired
    private SlotRepository slotRepository;

    // ✅ Get available slots by parking hub ID
    @GetMapping("/available/{hubId}")
    public List<Slot> getAvailableSlotsByHub(@PathVariable Long hubId) {
        return slotRepository.findByParkingHub_HubIdAndIsAvailableTrue(hubId);
    }

    // ✅ Mark a slot as not available (car arrived)
    @PostMapping("/occupy/{slotId}")
    public String occupySlot(@PathVariable Integer slotId) {
        Slot slot = slotRepository.findById(slotId).orElse(null);
        if (slot == null) return "Slot not found";

        slot.setIsAvailable(false);
        slotRepository.save(slot);
        return "Slot marked as occupied";
    }

    // ✅ Mark a slot as available (car departed)
    @PostMapping("/release/{slotId}")
    public String releaseSlot(@PathVariable Integer slotId) {
        Slot slot = slotRepository.findById(slotId).orElse(null);
        if (slot == null) return "Slot not found";

        slot.setIsAvailable(true);
        slotRepository.save(slot);
        return "Slot marked as available";
    }
}
