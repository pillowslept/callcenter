package com.almundo.callcenter.business;

import static com.almundo.callcenter.model.EmployeePosition.DIRECTOR;
import static com.almundo.callcenter.model.EmployeePosition.OPERATOR;
import static com.almundo.callcenter.model.EmployeePosition.SUPERVISOR;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeePosition;
import com.almundo.callcenter.model.EmployeeState;
import com.almundo.callcenter.repository.EmployeeRepository;

@Service
public class AvailableEmployees {

	@Autowired
	EmployeeRepository employeeRepository;
	
	/**
	 * Retorna un empleado de tipo operador, supervisor o director disponible para tomar una llamada
	 * @return
	 */
	public Employee getFreeEmployee(){
		Employee employeeAsigned = filterEmployees(OPERATOR);
		if(employeeAsigned == null){
			employeeAsigned = filterEmployees(SUPERVISOR);
		}
		if(employeeAsigned == null){
			employeeAsigned = filterEmployees(DIRECTOR);
		}
		return employeeAsigned;
	}
	
	/**
	 * Filtra sobre los empleados disponibles por cargo y por disponiblidad
	 * @param employeePosition
	 * @return
	 */
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
	
	/**
	 * Retorna todos los empleados disponibles
	 * @return
	 */
	public List<Employee> getAvailableEmployees() {
		return employeeRepository.getAvailableEmployees();
	}
	
	/**
	 * Libera un empleado
	 * @param employee
	 */
	public void freeEmployee(Employee employee){
		employee.setEmployeeState(EmployeeState.FREE);
		employeeRepository.freeEmployee(employee);
	}
}
