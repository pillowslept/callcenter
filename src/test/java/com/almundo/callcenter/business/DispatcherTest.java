package com.almundo.callcenter.business;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.EmployeeState;
import com.almundo.callcenter.model.IncomingCall;
import com.almundo.callcenter.testbuilder.CallTestBuilder;
import com.almundo.callcenter.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class DispatcherTest {
	
	private static final Logger LOGGER = Logger.getLogger(DispatcherTest.class.getName());

	@Autowired
	private Dispatcher dispatcher;
	
	private static final int AVAILABLE_EMPLOYEES = 10;
	private static final int SLEEP_TEN_SECONDS = 10000;
	private static final int SLEEP_TWENTY_SECONDS = 20000;

	@Before
	public void init(){
		dispatcher.clean();
	}

	/**
	 * Cuando ingresan 10 llamadas al sistema, todas las llamadas comienzan a ser atendidas inmediatamente, esto debido a que
	 * el sistema posee 10 empleados disponibles para atender las llamadas entrantes.
	 */
	@Test
	public void dispatchTenCallsTest() {
		int incomingCalls = Constants.TEN_CALLS;
		logStartProcess(incomingCalls);

		for (int callNumber = Constants.ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(new CallTestBuilder().withId(callNumber).build());
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAttended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAttendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertEquals(callsAttended.size(), incomingCalls);
		Assert.assertEquals(callsAttendedAfterBusy.size(), Constants.ZERO);
		assertAllEmployeesFree(employees);
	}

	/**
	 * Cuando no existen empleados disponibles o ingresan más de 10 llamadas al sistema, lo que se hace es informar que cada llamada se encuentra en espera
	 * y que se procederá a atenderla en el momento en que un empleado ocupado pase a estar disponible, esto se hace usando una cola de llamadas en la cual
	 * se van agregando las llamadas pendientes y esta cola es leída inmediatamente cuando un empleado es liberado.
	 */
	@Test
	public void dispatchFifteenCallsTest() {
		int incomingCalls = Constants.FIFTEEN_CALLS;
		logStartProcess(incomingCalls);

		for (int callNumber = Constants.ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(new CallTestBuilder().withId(callNumber).build());
		}
		
		sleep(SLEEP_TWENTY_SECONDS);
		
		List<IncomingCall> callsAttended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAttendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertEquals(callsAttended.size(), incomingCalls);
		Assert.assertEquals(callsAttendedAfterBusy.size(), incomingCalls - Constants.TEN_CALLS);
		assertAllEmployeesFree(employees);
	}
	
	/**
	 * Si al sistema ingresan menos de 10 llamadas, existirán empleados que no tomen ninguna de estas llamadas 
	 */
	@Test
	public void dispatchFiveCallsTest() {
		int incomingCalls = Constants.FIVE_CALLS;
		logStartProcess(incomingCalls);
		
		for (int callNumber = Constants.ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(new CallTestBuilder().withId(callNumber).build());
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAttended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAttendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertEquals(callsAttended.size(), incomingCalls);
		Assert.assertEquals(callsAttendedAfterBusy.size(), Constants.ZERO);
		assertAllEmployeesFree(employees);
	}
	
	/**
	 * Se espera que al terminar todo el procesamiento de las llamdas entrantes, todos los empleados del sistema se encuentren libres
	 * @param employees
	 */
	private void assertAllEmployeesFree(List<Employee> employees){
		Assert.assertTrue(employees.size() == AVAILABLE_EMPLOYEES);
		for (Employee employee : employees) {
			Assert.assertEquals(employee.getEmployeeState(), EmployeeState.FREE);
		}
	}

	/**
	 * Esperar un tiempo a que termine la ejecución de un proceso
	 * @param time
	 */
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Informa con cuantas llamadas concurrentes comienza el procesamiento 
	 * @param incomingCalls
	 */
	private void logStartProcess(int incomingCalls){
		LOGGER.info("Comenzando procesamiento con " + incomingCalls + " llamadas concurrentes");
	}

}