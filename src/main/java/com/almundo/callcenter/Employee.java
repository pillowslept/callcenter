package com.almundo.callcenter;

public class Employee {

	private String employeeName;
	private String position;
	private boolean available;
	
	public Employee(String employeeName, String position){
		this.employeeName = employeeName;
		this.position = position;
		this.available = true;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
