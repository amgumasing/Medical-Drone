package com.example.MedDrone.Repository;

import com.example.MedDrone.Model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
