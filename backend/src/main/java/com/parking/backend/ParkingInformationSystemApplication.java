package com.parking.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableScheduling
public class ParkingInformationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingInformationSystemApplication.class, args);
	}
}
