package com.group.carDatabase;

import com.group.carDatabase.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CarDatabaseApplication implements CommandLineRunner {

	// initiate logger
	private static final Logger logger = LoggerFactory.getLogger(CarDatabaseApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CarDatabaseApplication.class, args);

		logger.info("Application started...");
	}

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {

		// Add owner objects and save these to db
		Owner owner1 = new Owner("John" , "Johnson");
		Owner owner2 = new Owner("Mary" , "Robinson");
		ownerRepository.saveAll(Arrays.asList(owner1, owner2));

		// Add car object and link to owners and save to db
		carRepository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2021, 59000, owner1));
		carRepository.save(new Car("Nissan", "Leaf", "White", "SSJ-3002", 2019, 29000, owner2));
		carRepository.save(new Car("Toyota", "Prius", "Silver", "KKO-0212", 2020, 39000, owner2));

		// Fetch all cars and log to console
		for (Car car : carRepository.findAll()) {

			logger.info(car.getBrand() + " " + car.getModel());
		}

		// save two users to the database with bcrypt hashed passwords
		// Username: user, password: user
		userRepository.save(new User("user", "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue", "USER"));
		// Username: admin, password: admin
		userRepository.save(new User("admin", "$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW", "ADMIN"));
		// Username: test, password: test
		userRepository.save(new User("test", "$2a$10$QusmkhaV9OEYdvq5rJjoMeXh3IdcRenvtProCvgG6ebGKYCpvtpfu", "USER"));

	}
}
