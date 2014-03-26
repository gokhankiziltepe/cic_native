package com.itu.checkin.client.viewelements;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
public class PhoneForm implements Serializable {
	private String phoneText;
	private static final String PHONE_PATTERN = "^[2-9]\\d{2}-\\d{3}-\\d{4}$";

	@NotNull(message = "telefon numarası boş olamaz")
	@Size(min = 12, max = 20, message = "telefon numarası uzunluğu hatalı")
	@Pattern(regexp = PHONE_PATTERN, message = "telefon numarası formatı hatalı")
	public String getPhoneText() {
		return phoneText;
	}

	public void setPhoneText(String phoneText) {
		this.phoneText = phoneText;
	}

}
