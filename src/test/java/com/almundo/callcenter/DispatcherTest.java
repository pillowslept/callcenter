package com.almundo.callcenter;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class DispatcherTest {

	@Autowired
	private Dispatcher dispatcher;
	
	private static final int AVAILABLE_EMPLOYEES = 10;
	private static final int SLEEP_TEN_SECONDS = 10000;
	private static final int SLEEP_TWENTY_SECONDS = 20000;
	private static final int FIVE_CALLS = 5;
	private static final int TEN_CALLS = 10;
	private static final int FIFTEEN_CALLS = 15;
	private static final int ZERO = 0;
	private static final int ONE = 1;

	@Before
	public void init(){
		dispatcher.clean();
	}
	
	@Test
	public void dispatchTenCallsTest() {
		int incomingCalls = TEN_CALLS;

		for (int callNumber = ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(createCall(callNumber));
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();
		System.out.println(callsAtended.size());
		Assert.assertTrue(callsAtended.size() == incomingCalls);
		Assert.assertTrue(callsAtendedAfterBusy.size() == ZERO);
		assertAllEmployeesFree(employees);
	}
	
	@Test
	public void dispatchFifteenCallsTest() {
		int incomingCalls = FIFTEEN_CALLS;

		for (int callNumber = ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(createCall(callNumber));
		}
		
		sleep(SLEEP_TWENTY_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertTrue(callsAtended.size() == incomingCalls);
		Assert.assertTrue(callsAtendedAfterBusy.size() == incomingCalls - TEN_CALLS);
		assertAllEmployeesFree(employees);
	}
	
	@Test
	public void dispatchFiveCallsTest() {
		int incomingCalls = FIVE_CALLS;

		for (int callNumber = ONE; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(createCall(callNumber));
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertTrue(callsAtended.size() == incomingCalls);
		Assert.assertTrue(callsAtendedAfterBusy.size() == ZERO);
		assertAllEmployeesFree(employees);
	}

	private void assertAllEmployeesFree(List<Employee> employees){
		Assert.assertTrue(employees.size() == AVAILABLE_EMPLOYEES);
		for (Employee employee : employees) {
			Assert.assertTrue(employee.isAvailable());
		}
	}

	private IncomingCall createCall(int callNumber){
		IncomingCall incomingCall = new IncomingCall();
		incomingCall.setCallNumber(callNumber);
		return incomingCall;
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}