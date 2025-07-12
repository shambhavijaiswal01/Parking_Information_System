package com.parking.backend.controller;

import com.parking.backend.model.ParkingHub;
import com.parking.backend.repository.ParkingHubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hubs")
public class ParkingHubController {

    @Autowired
    private ParkingHubRepository parkingHubRepository;

    // ✅ Get all parking hubs
    @GetMapping
    public List<ParkingHub> getAllHubs() {
        return parkingHubRepository.findAll();
    }

    // ✅ Get a specific hub by ID
    @GetMapping("/{id}")
    public ParkingHub getHubById(@PathVariable Long id) {
        return parkingHubRepository.findById(id).orElse(null);
    }

    // ✅ Get hubs by areaName (joined via location table)
    // ✅ Fetch all hubs in a given area
    @GetMapping("/by-area")
    public List<ParkingHub> getHubsByArea(@RequestParam String areaName) {
        System.out.println("Fetching hubs for area: " + areaName);
        return parkingHubRepository.findByLocation_AreaName(areaName);
    }

}
