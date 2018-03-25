package com.almundo.callcenter.repository;

import static com.almundo.callcenter.model.EmployeePosition.DIRECTOR;
import static com.almundo.callcenter.model.EmployeePosition.OPERATOR;
import static com.almundo.callcenter.model.EmployeeState.BUSY;
import static com.almundo.callcenter.model.EmployeeState.FREE;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.almundo.callcenter.model.Employee;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRepositoryTest {
	
	@InjectMocks
	EmployeeRepository employeeRepository;
	
	@Test
	public void getAvailableEmployeesTest() {
		
		List<Employee> employees = employeeRepository.getAvailableEmployees();
		
		Assert.assertNotNull(employees);
		Assert.assertEquals(employees.size(), 10);
	}
	
	@Test
	public void freeEmployeeTest() {
		Employee employee = new Employee(DIRECTOR, BUSY);
		
		employeeRepository.freeEmployee(employee);
		
		Assert.assertNotNull(employee);
		Assert.assertEquals(employee.getEmployeeState(), FREE);
	}
	
	@Test
	public void filterEmployeesByPositionDirectorTest() {
		List<Employee> employees = employeeRepository.filterEmployeesByPosition(DIRECTOR);
		
		Assert.assertNotNull(employees);
		Assert.assertEquals(employees.size(), 2);
	}
	
	@Test
	public void filterEmployeesByPositionOperatorTest() {
		List<Employee> employees = employeeRepository.filterEmployeesByPosition(OPERATOR);
		
		Assert.assertNotNull(employees);
		Assert.assertEquals(employees.size(), 6);
	}
	

}