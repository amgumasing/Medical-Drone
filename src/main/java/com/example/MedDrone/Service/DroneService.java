package com.example.MedDrone.Service;


import com.example.MedDrone.Model.Drone;
import com.example.MedDrone.Model.Medication;
import com.example.MedDrone.Model.State;
import com.example.MedDrone.Repository.DroneRepository;
import com.example.MedDrone.Repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    public Drone registerDrone(Drone drone){
        return droneRepository.save(drone);
    }

    public boolean loadDrone(Long droneId, Medication medication){

        Optional<Drone> droneOptional = droneRepository.findById(droneId);

        if(!droneOptional.isPresent()) {
            return false;
        }

        Drone drone = droneOptional.get();


        if(drone.getBatteryCapacity() < 25 || drone.getState() != State.IDLE){
            return false;
        }

        int currentWeight = drone.getMedications().stream()
                .mapToInt(Medication::getWeight)
                .sum();

        if(currentWeight + medication.getWeight() > drone.getWeightLimit()){
            return false;
        }

        drone.getMedications().add(medication);
        drone.setState(State.LOADING);
        droneRepository.save(drone);
        return true;
    }

    public List<Medication> getLoadedMedications(Long droneId) {

        Optional<Drone> droneOptional = droneRepository.findById(droneId);

        return droneOptional.map(drone -> new ArrayList<>(drone.getMedications())).orElse(null);
    }


    public List<Drone> getAvailableDrones(){

        return droneRepository.findByState("IDLE");
    }

    public int getDroneBattery(Long droneId){

        Optional<Drone> droneOptional = droneRepository.findById(droneId);

        return droneOptional.map(Drone::getBatteryCapacity).orElse(-1);
    }

    public void reduceBatteryAfterDelivery(Long droneId, int percentage){

        Optional<Drone> droneOptional = droneRepository.findById(droneId);

        if(droneOptional.isPresent()){
            Drone drone = droneOptional.get();
            drone.setBatteryCapacity(drone.getBatteryCapacity() - percentage);
            drone.setState(State.DELIVERED);
            droneRepository.save(drone);
        }
    }
}
