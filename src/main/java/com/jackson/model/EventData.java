package com.jackson.model;

public class EventData {
	public String id;
	public Integer duration;
	public Boolean alert;
	@Override
	public String toString() {
		return "EventData [id=" + id + ", duration=" + duration + ", alert=" + alert + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Boolean getAlert() {
		return alert;
	}
	public void setAlert(Boolean alert) {
		this.alert = alert;
	}

}
