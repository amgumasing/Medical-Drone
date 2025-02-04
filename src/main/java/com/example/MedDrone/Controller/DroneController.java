package com.example.MedDrone.Controller;

import com.example.MedDrone.Model.Drone;
import com.example.MedDrone.Model.Medication;
import com.example.MedDrone.Service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class DroneController {

    @Autowired
    private DroneService droneService;

    @PostMapping("/register")
    public ResponseEntity<Drone> registerDrone(@RequestBody Drone drone){
        return ResponseEntity.ok(droneService.registerDrone(drone));
    }

    @PostMapping("/{droneId}/load")
    public ResponseEntity<?> loadDrone(@PathVariable Long droneId, @RequestBody Medication medication){
        boolean success = droneService.loadDrone(droneId, medication);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{droneId}/medications")
    public ResponseEntity<List<Medication>> getLoadedMedication(@PathVariable Long droneId){
        return ResponseEntity.ok(droneService.getLoadedMedications(droneId));
    }

    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAvailableDrones(){
        return ResponseEntity.ok(droneService.getAvailableDrones());
    }

    @GetMapping("/{droneId}/battery")
    public ResponseEntity<Integer> getDroneBattery(@PathVariable Long droneId){
        return ResponseEntity.ok(droneService.getDroneBattery(droneId));
    }

}
