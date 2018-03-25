package com.almundo.callcenter.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.CallQueue;
import com.almundo.callcenter.config.CallConfiguration;
import com.almundo.callcenter.model.CallState;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.IncomingCall;
import com.almundo.callcenter.utils.Constants;

@Service
public class Dispatcher {
	
	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	@Autowired
	AvailableEmployees availableEmployees;
	
	@Autowired
	ProcessCall processCall;
	
	List<IncomingCall> callsAtendedAfterBusy = new ArrayList<>();
	
	@Async
	public void dispatchCall(IncomingCall incomingCall){
		LOGGER.info("Ring ring. Llamada entrante : " + incomingCall.getCallNumber());
		try {
			assignEmployee(incomingCall);
		} catch (InterruptedException e) {
			LOGGER.error("Ocurrió un error atendiendo llamada de " + incomingCall.getCallNumber());	
		}
	}

	private void assignEmployee(IncomingCall incomingCall) throws InterruptedException {
		Employee employee = availableEmployees.getFreeEmployee();
		if(employee != null){
			attend(incomingCall, employee);
		}else{
			LOGGER.info("No hay empleados disponibles para atender la llamada : " + incomingCall.getCallNumber() + ", en un momento será atendido...");
			incomingCall.setCallState(CallState.WAITING);
			CallQueue.getInstance();
			CallQueue.addCallToQueue(incomingCall);
			callsAtendedAfterBusy.add(incomingCall);
		}
	}
	
	private void attend(IncomingCall incomingCall, Employee employee) throws InterruptedException{
		int callDuration = CallConfiguration.getDuration();
		incomingCall.setCallState(CallState.PROGRESS);
		LOGGER.info("Comenzó llamada: " + incomingCall.getCallNumber()
				+ ", empleado asignado: " + employee.getEmployeePosition());
		Thread.sleep(callDuration);
		LOGGER.info("Se atendió llamada: " + incomingCall.getCallNumber()
				+ ", empleado asignado: " + employee.getEmployeePosition()
				+ ", duración llamada: " + callDuration + " segundos");
		availableEmployees.freeEmployee(employee);
		processCall.finalizeCall(incomingCall, callDuration, employee);
		validateCallsQueve();
	}
	
	private void validateCallsQueve() throws InterruptedException{
		if(CallQueue.sizeQueue() > Constants.ZERO){
			CallQueue.getInstance();
			assignEmployee(CallQueue.getCall());
		}
	}
	
	public List<IncomingCall> getCallsAtended() {
		return processCall.getCallsAttended();
	}
	
	public List<Employee> getAvailableEmployees(){
		return availableEmployees.getAvailableEmployees();
	}

	public List<IncomingCall> getCallsAtendedAfterBusy() {
		return callsAtendedAfterBusy;
	}

	public void clean() {
		processCall.emptyCalls();
		callsAtendedAfterBusy = new ArrayList<>();
	}

}
