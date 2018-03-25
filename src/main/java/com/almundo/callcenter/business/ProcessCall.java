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
	
	/**
	 * Marca como atentida una llamada y la almacena
	 * @param incomingCall
	 * @param duration
	 * @param employee
	 */
	public void finalizeCall(IncomingCall incomingCall, Employee employee){
		incomingCall.setAttendedBy(employee.getEmployeePosition());
		incomingCall.setCallState(CallState.ATTENDED);
		callRepository.addCall(incomingCall);
	}

	/**
	 * Retorna todas las llamadas atendidas
	 * @return
	 */
	public List<IncomingCall> getCallsAttended(){
		return callRepository.getCallsAttended();
	}
	
	/**
	 * Agrega una llamada que se atendió luego de ser puesta en espera
	 * @param incomingCall
	 */
	public void addCallBusySystem(IncomingCall incomingCall){
		callRepository.addCallBusySystem(incomingCall);
	}
	
	/**
	 * Retorna todas las llamadas que se atendieron luego de ser puestas en espera
	 * @return
	 */
	public List<IncomingCall> getCallsBusySystem(){
		return callRepository.getCallsBusySystem();
	}
	
	/**
	 * Limpia las llamadas que han sido procesadas
	 */
	public void emptyCalls(){
		callRepository.emptyCalls();
	}
}
