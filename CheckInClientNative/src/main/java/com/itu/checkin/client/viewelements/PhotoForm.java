package com.itu.checkin.client.viewelements;

import java.io.Serializable;

import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
public class PhotoForm implements Serializable {
	private Part file;

	@NotNull(message = "dosya boş olamaz")
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

}