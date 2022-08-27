package com.jackson.model;

public class LogRecord {
	public String id;
    public String state;
    public Double timestamp;
	
	public LogRecord(){
		
	}
	
	public LogRecord(String id, String state, Double timestamp){
		this.id = id;
		this.state = state;
		this.timestamp = timestamp;
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Double timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "LogRecord [id=" + id + ", state=" + state + ", timestamp=" + timestamp + "]";
	}

	
}
