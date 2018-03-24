package com.almundo.callcenter;

public class Employee {

	private boolean available;
	private EmployeePosition employeePosition;
	
	public Employee(EmployeePosition employeePosition){
		this.employeePosition = employeePosition;
		this.available = true;
	}
	
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public EmployeePosition getEmployeePosition() {
		return employeePosition;
	}
	public void setEmployeePosition(EmployeePosition employeePosition) {
		this.employeePosition = employeePosition;
	}
	
}
