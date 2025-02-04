package com.example.MedDrone.Service;


import com.example.MedDrone.Model.Drone;
import com.example.MedDrone.Model.Medication;
import com.example.MedDrone.Model.Model;
import com.example.MedDrone.Model.State;
import com.example.MedDrone.Repository.DroneRepository;
import com.example.MedDrone.Repository.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DroneServiceTest {
    @InjectMocks
    private DroneService droneService;

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private MedicationRepository medicationRepository;


    @Test
    public void testRegisterDrone() {
        Drone drone = new Drone();
        drone.setSerialNumber("12345");
        drone.setModel(Model.LIGHTWEIGHT);
        drone.setWeightLimit(500);
        drone.setBatteryCapacity(100);
        drone.setState(State.IDLE);

        Mockito.when(droneRepository.save(drone)).thenReturn(drone);

        Drone registeredDrone = droneService.registerDrone(drone);

        assertNotNull(registeredDrone);
        assertEquals("12345", registeredDrone.getSerialNumber());
    }

    @Test
    public void testLoadDrone() {
        Long droneId = 1L;
        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(100);
        drone.setState(State.IDLE);
        drone.setWeightLimit(1000);
        drone.setMedications(new HashSet<>());

        Medication medication = new Medication();
        medication.setName("Painkiller");
        medication.setWeight(200);
        medication.setCode("PAIN_123");
        medication.setImage("image.jpg");

        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));
        Mockito.when(droneRepository.save(drone)).thenReturn(drone);

        boolean result = droneService.loadDrone(droneId, medication);

        assertTrue(result);
        assertEquals(State.LOADING, drone.getState());
        Mockito.verify(droneRepository).save(drone);
    }

    @Test
    public void testGetLoadedMedications() {
        Long droneId = 1L;
        Drone drone = new Drone();
        drone.setId(droneId);

        Medication medication1 = new Medication();
        medication1.setName("Painkiller");
        medication1.setWeight(200);
        medication1.setCode("PAIN_123");
        medication1.setImage("image1.jpg");

        Medication medication2 = new Medication();
        medication2.setName("Antibiotic");
        medication2.setWeight(150);
        medication2.setCode("ANT_456");
        medication2.setImage("image2.jpg");

        List<Medication> medications = Arrays.asList(medication1, medication2);
        drone.setMedications(new HashSet<>(medications));

        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        List<Medication> loadedMedications = droneService.getLoadedMedications(droneId);

        assertNotNull(loadedMedications);
        assertEquals(2, loadedMedications.size());
        assertEquals("Antibiotic", loadedMedications.get(0).getName());
        assertEquals("Painkiller", loadedMedications.get(1).getName());
    }

    @Test
    public void testGetAvailableDrones() {
        List<Drone> drones = new ArrayList<>();
        Drone drone = new Drone();
        drone.setState(State.IDLE);
        drones.add(drone);

        Mockito.when(droneRepository.findByState(String.valueOf(State.IDLE))).thenReturn(drones);

        List<Drone> availableDrones = droneService.getAvailableDrones();

        assertNotNull(availableDrones);
        assertEquals(1, availableDrones.size());
        assertEquals(State.IDLE, availableDrones.get(0).getState());
    }

    @Test
    public void testGetDroneBattery() {
        Long droneId = 1L;
        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(80);

        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        int batteryCapacity = droneService.getDroneBattery(droneId);

        assertEquals(80, batteryCapacity);
    }

    @Test
    public void testReduceBatteryAfterDelivery() {
        Long droneId = 1L;
        Drone drone = new Drone();
        drone.setId(droneId);
        drone.setBatteryCapacity(80); // Initial battery capacity
        drone.setState(State.DELIVERING);

        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(drone));

        droneService.reduceBatteryAfterDelivery(droneId, 20); // Reduce by 20%

        assertEquals(60, drone.getBatteryCapacity());
        assertEquals(State.DELIVERED, drone.getState());
        Mockito.verify(droneRepository).save(drone);

    }
}