package com.example.MedDrone;

import com.example.MedDrone.Model.Drone;
import com.example.MedDrone.Model.Medication;
import com.example.MedDrone.Model.Model;
import com.example.MedDrone.Model.State;
import com.example.MedDrone.Repository.DroneRepository;
import com.example.MedDrone.Repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;

    @Override
    public void run(String... args) throws Exception {
        Drone drone1 = new Drone();
        drone1.setSerialNumber("SN100");
        drone1.setModel(Model.MIDDLEWEIGHT);
        drone1.setWeightLimit(500);
        drone1.setBatteryCapacity(100);
        drone1.setState(State.IDLE);

        Drone drone2 = new Drone();
        drone2.setSerialNumber("SN101");
        drone2.setModel(Model.MIDDLEWEIGHT);
        drone2.setWeightLimit(800);
        drone2.setBatteryCapacity(80);
        drone2.setState(State.IDLE);

        droneRepository.saveAll(Arrays.asList(drone1, drone2));

        Medication med1 = new Medication();
        med1.setName("Med1");
        med1.setWeight(100);
        med1.setCode("M1_001");
        med1.setImage("med1.png");

        Medication med2 = new Medication();
        med2.setName("Med2");
        med2.setWeight(200);
        med2.setCode("M2_002");
        med2.setImage("med2.png");

        medicationRepository.saveAll(Arrays.asList(med1, med2));

    }
}
