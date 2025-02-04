package com.example.MedDrone.Schedule;

import com.example.MedDrone.Model.Drone;
import com.example.MedDrone.Model.State;
import com.example.MedDrone.Service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DroneScheduler {

    @Autowired
    private DroneService droneService;

    @Scheduled(fixedRate = 60000)
    public void checkAndUpdateDroneStates(){
        List<Drone> drones = droneService.getAvailableDrones();
        for(Drone drone : drones){
            if(drone.getState() == State.DELIVERED){
                droneService.reduceBatteryAfterDelivery(drone.getId(), 10);
                drone.setState(State.RETURNING);
                droneService.registerDrone(drone);
            }
        }
    }
}
