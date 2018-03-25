package com.almundo.callcenter.business;

import static com.almundo.callcenter.model.EmployeePosition.SUPERVISOR;
import static com.almundo.callcenter.model.EmployeeState.BUSY;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.almundo.callcenter.config.CallConfiguration;
import com.almundo.callcenter.model.CallState;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.IncomingCall;
import com.almundo.callcenter.repository.CallRepository;
import com.almundo.callcenter.utils.Constants;

@RunWith(MockitoJUnitRunner.class)
public class ProcessCallTest {
	
	@InjectMocks
	ProcessCall processCall;
	
	@Mock
	CallRepository callRepository;

	@Test
	public void finalizeCallTest() {
		IncomingCall incomingCall = new IncomingCall();
		int duration = CallConfiguration.getDuration();
		Employee employee = new Employee(SUPERVISOR, BUSY);

		processCall.finalizeCall(incomingCall, duration, employee);
		
		Assert.assertNotNull(incomingCall);
		Assert.assertEquals(incomingCall.getCallState(), CallState.ATTENDED);
		Assert.assertEquals(incomingCall.getAttendedBy(), employee.getEmployeePosition());
	}
	
	@Test
	public void getCallsAttendedTest() {
		List<IncomingCall> callsAttended = new ArrayList<>();
		Mockito.when(callRepository.getCallsAttended()).thenReturn(callsAttended);

		List<IncomingCall> calls = processCall.getCallsAttended();
		
		Assert.assertNotNull(calls);
		Assert.assertEquals(calls.size(), Constants.ZERO);
	}
	
	@Test
	public void getCallsBusySystemTest() {
		List<IncomingCall> callsAttended = new ArrayList<>();
		Mockito.when(callRepository.getCallsBusySystem()).thenReturn(callsAttended);

		List<IncomingCall> calls = processCall.getCallsBusySystem();
		
		Assert.assertNotNull(calls);
		Assert.assertEquals(calls.size(), Constants.ZERO);
	}
	
	@Test
	public void addCallBusySystemTest() {
		IncomingCall incomingCall = new IncomingCall();

		processCall.addCallBusySystem(incomingCall);
		
		Assert.assertNotNull(incomingCall);
	}

}