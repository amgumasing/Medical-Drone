package com.example.MedDrone;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedDroneApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedDroneApplication.class, args);
	}
}
