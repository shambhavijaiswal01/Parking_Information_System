package com.parking.backend.repository;

import com.parking.backend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT DISTINCT l.city FROM Location l")
    List<String> findDistinctCities();

    @Query("SELECT l.areaName FROM Location l WHERE l.city = :city")
    List<String> findAreasByCity(@Param("city") String city);

    Location findByAreaName(String areaName);
}
