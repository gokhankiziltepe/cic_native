package com.itu.checkin.client.viewelements;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

@SuppressWarnings("serial")
public class UserBaseForm implements Serializable {
	private String name;
	private String surname;
	private String email;
	private String password;
	private String passwordRepeat;

	@NotNull(message = "isim boş olamaz")
	@Size(min = 2, max = 255, message = "isim uzunluğu hatalı")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull
	@Size(min = 2, max = 255, message = "soyisim uzunluğu hatalı")
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotNull
	@Email(message = "e-posta formatı hatalı")
	@Size(min = 5, max = 255, message = "e-posta uzunluğu hatalı")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Size(min = 3, max = 255, message = "şifre uzunluğunda hata")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	@Size(min = 3, max = 255, message = "şifre tekrarı uzunluğunda hata")
	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

}
