package com.almundo.callcenter.repository;

import static com.almundo.callcenter.model.EmployeePosition.DIRECTOR;
import static com.almundo.callcenter.model.EmployeePosition.OPERATOR;
import static com.almundo.callcenter.model.EmployeePosition.SUPERVISOR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeePosition;
import com.almundo.callcenter.model.EmployeeState;

@Repository
public class EmployeeRepository {

	private static final Logger LOGGER = Logger.getLogger(EmployeeRepository.class.getName());
	
	List<Employee> availableEmployees;
	
	public EmployeeRepository(){
		availableEmployees = new ArrayList<>();
		createAvailableEmployees();
	}
	
	/**
	 * Crea los empleados disponibles del sistema
	 */
	public void createAvailableEmployees(){
		LOGGER.info("Comienza la creación de empleados disponibles.");
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
		LOGGER.info("Termina la creación de empleados disponibles.");
	}

	/**
	 * Retorna todos los empleados disponibles
	 * @return
	 */
	public List<Employee> getAvailableEmployees(){
		return availableEmployees;
	}

	/**
	 * Crea un empleado disponible
	 * @param employeePosition
	 * @return
	 */
	private Employee createEmployee(EmployeePosition employeePosition) {
		return new Employee(employeePosition, EmployeeState.FREE);
	}

	/**
	 * Libera un empleado
	 * @param employee
	 */
	public void freeEmployee(Employee employee){
		employee.setEmployeeState(EmployeeState.FREE);
	}
	
	/**
	 * Filtra empleados disponibles por un cargo
	 * @param employeePosition
	 * @return
	 */
	public List<Employee> filterEmployeesByPosition(EmployeePosition employeePosition){
		return this.availableEmployees
				.stream()
				.filter(employee -> (employee.getEmployeePosition()
						.equals(employeePosition)))
				.collect(Collectors.toList());
	}
	
}
