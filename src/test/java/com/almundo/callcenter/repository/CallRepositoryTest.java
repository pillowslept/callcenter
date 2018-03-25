package com.almundo.callcenter.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.almundo.callcenter.model.IncomingCall;
import com.almundo.callcenter.utils.Constants;

@RunWith(MockitoJUnitRunner.class)
public class CallRepositoryTest {
	
	@InjectMocks
	CallRepository callRepository;
	
	@Before
	public void init(){
		callRepository.emptyCalls();
	}
	
	@Test
	public void getCallsAttendedTest() {
		IncomingCall incomingCall = new IncomingCall();
		
		callRepository.addCall(incomingCall);
		List<IncomingCall> callsAttended = callRepository.getCallsAttended();
		
		Assert.assertNotNull(callsAttended);
		Assert.assertEquals(callsAttended.size(), Constants.ONE);
	}
	
	@Test
	public void getCallsBusySystemTest() {
		IncomingCall incomingCall = new IncomingCall();
		
		callRepository.addCallBusySystem(incomingCall);
		List<IncomingCall> callsAttendedBusy = callRepository.getCallsBusySystem();
		
		Assert.assertNotNull(callsAttendedBusy);
		Assert.assertEquals(callsAttendedBusy.size(), Constants.ONE);
	}
	

}