package com.almundo.callcenter.model;


public class IncomingCall {

	private int callNumber;
	private int duration;
	private CallState callState;
	private EmployeePosition attendedBy;

	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
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
	public CallState getCallState() {
		return callState;
	}
	public void setCallState(CallState callState) {
		this.callState = callState;
	}
	
}
