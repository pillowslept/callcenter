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

	@Test
	public void dispatchTenCallsTest() {
		int incomingCalls = Constants.TEN_CALLS;
		logStartProcess(incomingCalls);

		for (int callNumber = Constants.ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(new CallTestBuilder().withId(callNumber).build());
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertEquals(callsAtended.size(), incomingCalls);
		Assert.assertEquals(callsAtendedAfterBusy.size(), Constants.ZERO);
		assertAllEmployeesFree(employees);
	}

	@Test
	public void dispatchFifteenCallsTest() {
		int incomingCalls = Constants.FIFTEEN_CALLS;
		logStartProcess(incomingCalls);

		for (int callNumber = Constants.ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(new CallTestBuilder().withId(callNumber).build());
		}
		
		sleep(SLEEP_TWENTY_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertEquals(callsAtended.size(), incomingCalls);
		Assert.assertEquals(callsAtendedAfterBusy.size(), incomingCalls - Constants.TEN_CALLS);
		assertAllEmployeesFree(employees);
	}
	
	@Test
	public void dispatchFiveCallsTest() {
		int incomingCalls = Constants.FIVE_CALLS;
		logStartProcess(incomingCalls);
		
		for (int callNumber = Constants.ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(new CallTestBuilder().withId(callNumber).build());
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertEquals(callsAtended.size(), incomingCalls);
		Assert.assertEquals(callsAtendedAfterBusy.size(), Constants.ZERO);
		assertAllEmployeesFree(employees);
	}
	
	private void assertAllEmployeesFree(List<Employee> employees){
		Assert.assertTrue(employees.size() == AVAILABLE_EMPLOYEES);
		for (Employee employee : employees) {
			Assert.assertEquals(employee.getEmployeeState(), EmployeeState.FREE);
		}
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void logStartProcess(int incomingCalls){
		LOGGER.info("Comenzando procesamiento con " + incomingCalls + " llamadas concurrentes");
	}

}