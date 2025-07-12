package com.parking.backend.repository;

import com.parking.backend.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotRepository extends JpaRepository<Slot, Integer> {
        List<Slot> findByParkingHub_HubIdAndIsAvailableTrue(Long hubId);
}
