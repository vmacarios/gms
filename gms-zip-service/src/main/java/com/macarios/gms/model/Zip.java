package com.macarios.gms.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "zip")
public class Zip {

	@Id
	@GeneratedValue
	private Integer id;
	private String zip;
	private String address;
	private String comp;
	private String neighborhood;
	private String city;
	private String state;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Instant createdAt;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Instant updatedAt;

	public Zip() {
	}

	public Zip(Integer id, String zip, String address, String comp, String neighborhood, String city, String state, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.zip = zip;
		this.address = address;
		this.comp = comp;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Zip(String zip, String address, String comp, String neighborhood, String city, String state, Instant createdAt, Instant updatedAt) {
		this.zip = zip;
		this.address = address;
		this.comp = comp;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComp() {
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "Zip{" +
				"id=" + id +
				", zip=" + zip +
				", address='" + address + '\'' +
				", comp='" + comp + '\'' +
				", neighborhood='" + neighborhood + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				'}';
	}
}
