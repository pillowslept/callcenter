package com.almundo.callcenter.business;

import static com.almundo.callcenter.model.EmployeePosition.DIRECTOR;
import static com.almundo.callcenter.model.EmployeePosition.OPERATOR;
import static com.almundo.callcenter.model.EmployeePosition.SUPERVISOR;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeePosition;
import com.almundo.callcenter.model.EmployeeState;
import com.almundo.callcenter.repository.EmployeeRepository;

@Service
public class AvailableEmployees {

	private static final Logger LOGGER = Logger.getLogger(AvailableEmployees.class.getName());
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	public Employee getFreeEmployee(){
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
		for (Employee employee : employeeRepository.filterEmployeesByPosition(employeePosition)) {
			if(employee.getEmployeeState().equals(EmployeeState.FREE)){
				employee.setEmployeeState(EmployeeState.BUSY);
				employeeAvailable = employee;
				break;
			}
		}
		return employeeAvailable;
	}
	
	public List<Employee> getAvailableEmployees() {
		return employeeRepository.getAvailableEmployees();
	}
	
	public void freeEmployee(Employee employee){
		employeeRepository.freeEmployee(employee);
	}
}
