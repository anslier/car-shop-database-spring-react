package com.group.carDatabase.web;

import com.group.carDatabase.domain.Car;
import com.group.carDatabase.domain.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {

	@Autowired
	private CarRepository carRepository;

	@RequestMapping("/cars")
	public Iterable<Car> getCars() {

		// Fetch and return cars
		return carRepository.findAll();
	}
}
