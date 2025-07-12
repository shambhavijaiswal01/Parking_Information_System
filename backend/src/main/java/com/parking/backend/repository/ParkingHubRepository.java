package com.parking.backend.repository;

import com.parking.backend.model.ParkingHub;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingHubRepository extends JpaRepository<ParkingHub, Long> {
    List<ParkingHub> findByLocation_AreaName(String areaName);
    List<ParkingHub> findByLocation_LocationId(Long locationId);
}
