package com.almundo.callcenter;

public class IncomingCall {

	private String personName;
	private String duration;
	private int callNumber;
	private EmployeePosition attendedBy;

	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public EmployeePosition getAttendedBy() {
		return attendedBy;
	}
	public void setAttendedBy(EmployeePosition attendedBy) {
		this.attendedBy = attendedBy;
	}
	public int getCallNumber() {
		return callNumber;
	}
	public void setCallNumber(int callNumber) {
		this.callNumber = callNumber;
	}
	
}
