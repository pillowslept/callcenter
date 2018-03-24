package com.almundo.callcenter;

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
	public void dispatchCalls() throws Exception {
		int incomingCalls = 10;

		for (int callNumber = 1; callNumber <= incomingCalls; callNumber++) {
			IncomingCall incomingCall = new IncomingCall();
			incomingCall.setCallNumber(callNumber);
			dispatcher.dispatchCall(incomingCall);
		}
		
		sleep(50000);
		
		List<IncomingCall> callsAtended = dispatcher.getCallsAtended();

		Assert.assertTrue(callsAtended.size() == 10);
	}
	
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}