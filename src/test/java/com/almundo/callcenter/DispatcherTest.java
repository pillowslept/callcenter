package com.almundo.callcenter;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class DispatcherTest {

	@Autowired
	private Dispatcher dispatcher;

	@Test
	public void dispatchTenCallsTest() throws Exception {
		int incomingCalls = 10;

		for (int callNumber = 1; callNumber <= incomingCalls; callNumber++) {
			dispatcher.dispatchCall(createCall(callNumber));
		}
		
		sleep(10000);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();
		List<Employee> employees = dispatcher.getAvailableEmployees();

		Assert.assertTrue(callsAtended.size() == incomingCalls);
		Assert.assertTrue(employees.size() == 10);
		Assert.assertTrue(employees.get(0).isAvailable());
		Assert.assertTrue(employees.get(1).isAvailable());
		Assert.assertTrue(employees.get(2).isAvailable());
		Assert.assertTrue(employees.get(3).isAvailable());
		Assert.assertTrue(employees.get(4).isAvailable());
		Assert.assertTrue(employees.get(5).isAvailable());
		Assert.assertTrue(employees.get(6).isAvailable());
		Assert.assertTrue(employees.get(7).isAvailable());
		Assert.assertTrue(employees.get(8).isAvailable());
		Assert.assertTrue(employees.get(9).isAvailable());
	}
	
	private IncomingCall createCall(int callNumber){
		IncomingCall incomingCall = new IncomingCall();
		incomingCall.setCallNumber(callNumber);
		return incomingCall;
	}
	
	private List<IncomingCall> createCalls(int callsQuantity){
		List<IncomingCall> incomingCalls = new ArrayList<>();
		for (int callNumber = 1; callNumber <= callsQuantity; callNumber++) {
			incomingCalls.add(createCall(callNumber));
		}
		return incomingCalls;
	}
		
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}