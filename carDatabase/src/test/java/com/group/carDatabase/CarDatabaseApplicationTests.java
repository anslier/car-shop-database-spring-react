package com.group.carDatabase;

import com.group.carDatabase.web.CarController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarDatabaseApplicationTests {

	@Autowired
	private CarController controller;

	@Test
	@DisplayName("First example test case")
	void contextLoads() {

		// The following test checks that the instance of the controller was created and injected successfully
		assertThat(controller).isNotNull();
	}

}
