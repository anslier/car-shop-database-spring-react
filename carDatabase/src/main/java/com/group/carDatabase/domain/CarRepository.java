package com.group.carDatabase.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@RepositoryRestResource(path="vehicles")
@RepositoryRestResource
public interface CarRepository extends CrudRepository<Car, Long> {

	// Fetch cars by brand
	//List<Car> findByBrand(String brand);

	List<Car> findByModel(String model);

	// Fetch cars by color
	//List<Car> findByColor(String color);

	List<Car> findByRegisterNumber(String registerNumber);

	// Fetch cars by buildYear
	List<Car> findByBuildYear(Integer buildYear);

	List<Car> findByPrice(Integer price);

	// Fetch cars by brand and model
	List<Car> findByBrandAndModel(String brand, String model);

	// Fetch cars by brand or color
	List<Car> findByBrandOrColor(String brand, String color);

	// Fetch cars by brand and sort by year
	List<Car> findByBrandOrderByBuildYearAsc(String brand);

	// Fetch cars by brand using SQL
	//@Query("select c from Car c where c.brand = ?1")
	//List<Car> findByBrand(String brand);

	// Fetch cars by brand using SQL
	@Query("select c from Car c where c.brand like %?1")
	List<Car> findByBrandEndsWith(String brand);

	// Fetch cars by brand
	List<Car> findByBrand(@Param("brand") String brand);

	// Fetch cars by color
	List<Car> findByColor(@Param("color") String color);

}

// offers methods to fetch entities using pagination and sorting
// public interface CarRepository extends PagingAndSortingRepository<Car, Long> {
//}

