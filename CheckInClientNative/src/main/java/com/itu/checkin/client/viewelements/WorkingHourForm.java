package com.itu.checkin.client.viewelements;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
public class WorkingHourForm implements Serializable {
	private String dayText;
	private String openingHour;
	private String closingHour;

	@NotNull(message = "gün boş bırakılamaz")
	@Size(min = 1, max = 1, message = "gün yanlış seçildi")
	public String getDayText() {
		return dayText;
	}

	public void setDayText(String dayText) {
		this.dayText = dayText;
	}

	@NotNull(message = "açılış saati boş bırakılamaz")
	@Size(min = 1, max = 2, message = "açılış saati yanlış seçildi")
	public String getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(String openingHour) {
		this.openingHour = openingHour;
	}

	@NotNull(message = "kapanış saati boş bırakılamaz")
	@Size(min = 1, max = 2, message = "kapanış saati yanlış seçildi")
	public String getClosingHour() {
		return closingHour;
	}

	public void setClosingHour(String closingHour) {
		this.closingHour = closingHour;
	}

}
