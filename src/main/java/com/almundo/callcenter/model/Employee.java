package com.almundo.callcenter.model;


public class Employee {

	private EmployeePosition employeePosition;
	private EmployeeState employeeState;
	
	public Employee(EmployeePosition employeePosition, EmployeeState employeeState){
		this.employeePosition = employeePosition;
		this.employeeState = employeeState;
	}
	
	public EmployeePosition getEmployeePosition() {
		return employeePosition;
	}
	public void setEmployeePosition(EmployeePosition employeePosition) {
		this.employeePosition = employeePosition;
	}
	public EmployeeState getEmployeeState() {
		return employeeState;
	}
	public void setEmployeeState(EmployeeState employeeState) {
		this.employeeState = employeeState;
	}
	
}
