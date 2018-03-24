package com.almundo.callcenter;

import java.util.List;

import org.junit.Assert;
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

	@Test
	public void dispatchTenCallsTest() {
		int incomingCalls = 10;

		for (int callNumber = 1; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(createCall(callNumber));
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertTrue(callsAtended.size() == incomingCalls);
		Assert.assertTrue(callsAtendedAfterBusy.size() == 0);
		assertAllEmployeesFree(employees);
	}
	
	@Test
	public void dispatchFifteenCallsTest() {
		int incomingCalls = 15;

		for (int callNumber = 1; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(createCall(callNumber));
		}
		
		sleep(SLEEP_TWENTY_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertTrue(callsAtended.size() == incomingCalls);
		Assert.assertTrue(callsAtendedAfterBusy.size() == incomingCalls - 10);
		assertAllEmployeesFree(employees);
	}
	
	@Test
	public void dispatchFiveCallsTest() {
		int incomingCalls = 5;

		for (int callNumber = 1; callNumber <= incomingCalls; callNumber++) {
			sleep(100);
			dispatcher.dispatchCall(createCall(callNumber));
		}
		
		sleep(SLEEP_TEN_SECONDS);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<IncomingCall> callsAtendedAfterBusy = dispatcher.getCallsAtendedAfterBusy();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertTrue(callsAtended.size() == incomingCalls);
		Assert.assertTrue(callsAtendedAfterBusy.size() == 0);
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