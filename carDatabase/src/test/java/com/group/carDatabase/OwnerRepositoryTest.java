package com.group.carDatabase;

import com.group.carDatabase.domain.Owner;
import com.group.carDatabase.domain.OwnerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OwnerRepositoryTest {

	@Autowired
	private OwnerRepository repository;

	// test the addition of a new owner to the database
	@Test
	@DisplayName("Save owner test")
	void saveOwner() {

		repository.save(new Owner("Lucy", "Smith"));
		assertThat(repository.findByFirstname("Lucy").isPresent()).isTrue();
	}

	// test the deletion of owner from the database
	@Test
	@DisplayName("Delete owner test")
	void deleteOwners() {

		repository.save(new Owner("Lisa", "Morrison"));
		repository.deleteAll();
		assertThat(repository.count()).isEqualTo(0);
	}
}
