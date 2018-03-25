package com.almundo.callcenter.business;

import static com.almundo.callcenter.model.EmployeePosition.DIRECTOR;
import static com.almundo.callcenter.model.EmployeePosition.OPERATOR;
import static com.almundo.callcenter.model.EmployeePosition.SUPERVISOR;
import static com.almundo.callcenter.model.EmployeeState.BUSY;
import static com.almundo.callcenter.model.EmployeeState.FREE;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeePosition;
import com.almundo.callcenter.repository.EmployeeRepository;

@RunWith(MockitoJUnitRunner.class)
public class AvailableEmployeesTest {
	
	@InjectMocks
	AvailableEmployees availableEmployees;
	
	@Mock
	EmployeeRepository employeeRepository;

	@Test
	public void getFreeEmployeeOperatorTest() {
		Mockito.when(employeeRepository.filterEmployeesByPosition(OPERATOR)).thenReturn(createAvailableEmployees(OPERATOR));

		Employee employee = availableEmployees.getFreeEmployee();
		
		Assert.assertNotNull(employee);
		Assert.assertEquals(employee.getEmployeePosition(), OPERATOR);
		Assert.assertEquals(employee.getEmployeeState(), BUSY);
	}
	
	@Test
	public void getFreeEmployeeSupervisorTest() {
		Mockito.when(employeeRepository.filterEmployeesByPosition(SUPERVISOR)).thenReturn(createAvailableEmployees(SUPERVISOR));

		Employee employee = availableEmployees.getFreeEmployee();
		
		Assert.assertNotNull(employee);
		Assert.assertEquals(employee.getEmployeePosition(), SUPERVISOR);
		Assert.assertEquals(employee.getEmployeeState(), BUSY);
	}
	
	@Test
	public void getFreeEmployeeDirectorTest() {
		Mockito.when(employeeRepository.filterEmployeesByPosition(DIRECTOR)).thenReturn(createAvailableEmployees(DIRECTOR));

		Employee employee = availableEmployees.getFreeEmployee();
		
		Assert.assertNotNull(employee);
		Assert.assertEquals(employee.getEmployeePosition(), DIRECTOR);
		Assert.assertEquals(employee.getEmployeeState(), BUSY);
	}
	
	@Test
	public void getAvailableEmployeesTest() {
		Mockito.when(employeeRepository.getAvailableEmployees()).thenReturn(createAvailableEmployees(DIRECTOR));

		List<Employee> employees = availableEmployees.getAvailableEmployees();
		
		Assert.assertNotNull(employees);
		Assert.assertEquals(employees.size(), 2);
	}
	
	@Test
	public void freeEmployeeTest() {
		Employee employee = new Employee(DIRECTOR, BUSY);
		
		availableEmployees.freeEmployee(employee);
		
		Assert.assertNotNull(employee);
		Assert.assertEquals(employee.getEmployeeState(), FREE);
	}
	
	private List<Employee> createAvailableEmployees(EmployeePosition employeePosition){
		List<Employee> availableEmployees = new ArrayList<>();
		availableEmployees.add(createEmployee(employeePosition));
		availableEmployees.add(createEmployee(employeePosition));
		return availableEmployees;
	}
	
	private Employee createEmployee(EmployeePosition employeePosition) {
		return new Employee(employeePosition, FREE);
	}

}