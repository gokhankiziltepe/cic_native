package com.itu.checkin.client.viewelements;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@SuppressWarnings("serial")
public class PlaceForm implements Serializable {
	private Double latitudeText;
	private Double longitudeText;
	private String addressText;
	private String nameText;
	private String descriptionText;
	private Integer capacityText;

	@NotNull(message = "enlem bilgisi boş olamaz")
	public Double getLatitudeText() {
		return latitudeText;
	}

	public void setLatitudeText(Double latitudeText) {
		this.latitudeText = latitudeText;
	}

	@NotNull(message = "boylam bilgisi boş olamaz")
	public Double getLongitudeText() {
		return longitudeText;
	}

	public void setLongitudeText(Double longitudeText) {
		this.longitudeText = longitudeText;
	}

	@NotNull(message = "adres bilgisi boş olamaz")
	@Size(min = 10, max = 1000, message = "adres bilgisi uzunluğu hatalı")
	public String getAddressText() {
		return addressText;
	}

	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}

	@NotNull(message = "adres bilgisi boş olamaz")
	@Size(min = 2, max = 100, message = "isim uzunluğu hatalı")
	public String getNameText() {
		return nameText;
	}

	public void setNameText(String nameText) {
		this.nameText = nameText;
	}

	@Size(max = 500, message = "mekan tanımı uzunluğu hatalı")
	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	@Range(min = 0, max = 99999)
	public Integer getCapacityText() {
		return capacityText;
	}

	public void setCapacityText(Integer capacityText) {
		this.capacityText = capacityText;
	}

}
