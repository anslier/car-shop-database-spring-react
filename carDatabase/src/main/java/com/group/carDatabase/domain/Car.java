package com.group.carDatabase.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Car {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private String brand;
	private String model;
	private String color;
	private String registerNumber;

	private Integer buildYear;
	private Integer price;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner")
	private Owner owner;

	//@ManyToMany(mappedBy="cars")
	//private Set<Owner> owners = new HashSet<Owner>();

	public Car() {
	}

	public Car(String brand, String model, String color, String registerNumber, Integer buildYear, Integer price, Owner owner) {
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.registerNumber = registerNumber;
		this.buildYear = buildYear;
		this.price = price;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public Integer getBuildYear() {
		return buildYear;
	}

	public void setBuildYear(Integer buildYear) {
		this.buildYear = buildYear;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	//public Set<Owner> getOwners() {
	//	return owners;
	//}
	//
	//public void setOwners(Set<Owner> owners) {
	//	this.owners = owners;
	//}

	@Override
	public String toString() {
		return "Car{" +
				"id=" + id +
				", brand='" + brand + '\'' +
				", model='" + model + '\'' +
				", color='" + color + '\'' +
				", registerNumber='" + registerNumber + '\'' +
				", buildYear=" + buildYear +
				", price=" + price +
				", owner=" + owner +
				'}';
	}
}
