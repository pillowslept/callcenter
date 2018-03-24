package com.almundo.callcenter;

import static com.almundo.callcenter.EmployeePosition.DIRECTOR;
import static com.almundo.callcenter.EmployeePosition.OPERATOR;
import static com.almundo.callcenter.EmployeePosition.SUPERVISOR;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AvailableEmployees {

	private static final Logger LOGGER = Logger.getLogger(AvailableEmployees.class.getName());
	
	List<Employee> availableEmployees = new ArrayList<>();
	
	public AvailableEmployees(){
		createAvailableEmployees();
	}
	
	public void createAvailableEmployees(){
		availableEmployees.add(createEmployee(OPERATOR));
		availableEmployees.add(createEmployee(OPERATOR));
		availableEmployees.add(createEmployee(OPERATOR));
		availableEmployees.add(createEmployee(OPERATOR));
		availableEmployees.add(createEmployee(OPERATOR));
		availableEmployees.add(createEmployee(OPERATOR));
		availableEmployees.add(createEmployee(DIRECTOR));
		availableEmployees.add(createEmployee(DIRECTOR));
		availableEmployees.add(createEmployee(SUPERVISOR));
		availableEmployees.add(createEmployee(SUPERVISOR));
	}

	public Employee assignEmployee(){
		//Search if exists an operator available for take the call
		Employee employeeAsigned = filterEmployees(OPERATOR);
		if(employeeAsigned == null){
			//If no exists an operator, search if exists a supervisor available for take the call
			employeeAsigned = filterEmployees(SUPERVISOR);
		}
		if(employeeAsigned == null){
			//If no exists an operator or director, search if exists a director available for take the call
			employeeAsigned = filterEmployees(DIRECTOR);
		}
		return employeeAsigned;
	}
	
	private Employee filterEmployees(EmployeePosition employeePosition){
		Employee employeeAvailable = null;
		for (Employee employee : availableEmployees) {
			if(employee.getEmployeePosition().equals(employeePosition) && employee.isAvailable()){
				employee.setAvailable(FALSE);
				employeeAvailable = employee;
				LOGGER.info("Empleado asignado: " + employee.getEmployeePosition());
				break;
			}
		}
		return employeeAvailable;
	}

	private Employee createEmployee(EmployeePosition employeePosition) {
		return new Employee(employeePosition);
	}
	
	public List<Employee> getAvailableEmployees() {
		return availableEmployees;
	}
	
	public void freeEmployee(Employee employee){
		employee.setAvailable(TRUE);
	}
}
