package com.almundo.callcenter.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.model.CallState;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.IncomingCall;
import com.almundo.callcenter.repository.CallRepository;

@Service
public class ProcessCall {

	@Autowired
	CallRepository callRepository;
	
	public void finalizeCall(IncomingCall incomingCall, int duration, Employee employee){
		incomingCall.setDuration(duration);
		incomingCall.setAttendedBy(employee.getEmployeePosition());
		incomingCall.setCallState(CallState.ATTENDED);
		callRepository.addCall(incomingCall);
	}

	public List<IncomingCall> getCallsAttended(){
		return callRepository.getCallsAttended();
	}
	
	public void emptyCalls(){
		callRepository.emptyCalls();
	}
}
