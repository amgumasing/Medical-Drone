package com.example.MedDrone.Repository;

import com.example.MedDrone.Model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByState(String state);
}
