package com.group.carDatabase.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ownerId;

	private String firstname;
	private String lastname;

	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner")
	private List<Car> cars;

	//@ManyToMany(cascade=CascadeType.PERSIST)
	//@JoinTable(name="car_owner", joinColumns = { @JoinColumn(name="ownerid") }, inverseJoinColumns = { @JoinColumn(name="id") })
	//private Set<Car> cars = new HashSet<Car>();

	public Owner() {
	}

	public Owner(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	//public Set<Car> getCars() {
	//	return cars;
	//}
	//
	//public void setCars(Set<Car> cars) {
	//	this.cars = cars;
	//}

	@Override
	public String toString() {
		return "Owner{" +
				"ownerId=" + ownerId +
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", cars=" + cars +
				'}';
	}
}
