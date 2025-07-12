package com.parking.backend.controller;

import com.parking.backend.model.Location;
import com.parking.backend.model.ParkingHub;
import com.parking.backend.model.Slot;
import com.parking.backend.repository.LocationRepository;
import com.parking.backend.repository.ParkingHubRepository;
import com.parking.backend.repository.SlotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/locations")
@CrossOrigin(origins = "*") // Allow frontend to call from any origin
public class LocationController {

    @Autowired
    private LocationRepository locationRepo;

    @Autowired
    private ParkingHubRepository hubRepo;

    @Autowired
    private SlotRepository slotRepo;

    // 1️⃣ Fetch all unique cities
    @GetMapping("/cities")
    public List<String> getAllCities() {
        return locationRepo.findDistinctCities();
    }

    // 2️⃣ Fetch areas based on selected city
    @GetMapping("/areas")
    public List<String> getAreasByCity(@RequestParam String city) {
        return locationRepo.findAreasByCity(city);
    }

    // 3️⃣ Fetch hubs based on selected area
    @GetMapping("/hubs")
    public List<ParkingHub> getHubsByArea(@RequestParam String area) {
        Location location = locationRepo.findByAreaName(area);
        if (location == null) {
            return new ArrayList<>();
        }
        return hubRepo.findByLocation_LocationId(location.getLocationId().longValue());

    }


    // 4️⃣ Fetch available slots for a hub
    @GetMapping("/slots")
    public List<Slot> getSlotsByHub(@RequestParam Long hubId) {
        return slotRepo.findByParkingHub_HubIdAndIsAvailableTrue(hubId);
    }
}
