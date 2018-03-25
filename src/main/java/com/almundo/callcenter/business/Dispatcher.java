package com.almundo.callcenter.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.CallQueue;
import com.almundo.callcenter.config.CallConfiguration;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.IncomingCall;

@Service
public class Dispatcher {
	
	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	@Autowired
	AvailableEmployees availableEmployees;
	
	List<IncomingCall> callsAtended = new ArrayList<>();
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
			CallQueue.getInstance();
			CallQueue.addCallToQueue(incomingCall);
			callsAtendedAfterBusy.add(incomingCall);
		}
	}
	
	private void attend(IncomingCall incomingCall, Employee employee) throws InterruptedException{
		LOGGER.info("Comenzó llamada: " + incomingCall.getCallNumber()
				+ ", empleado asignado: " + employee.getEmployeePosition());
		incomingCall.setAttendedBy(employee.getEmployeePosition());
		int callDuration = CallConfiguration.getDuration();
		Thread.sleep(callDuration);
		LOGGER.info("Se atendió llamada: " + incomingCall.getCallNumber()
				+ ", empleado asignado: " + employee.getEmployeePosition()
				+ ", duración llamada: " + callDuration + " segundos");
		availableEmployees.freeEmployee(employee);
		callsAtended.add(incomingCall);
		validateCallsQueve();
	}
	
	private void validateCallsQueve() throws InterruptedException{
		if(CallQueue.sizeQueue() > 0){
			CallQueue.getInstance();
			assignEmployee(CallQueue.getCall());
		}
	}
	
	public List<IncomingCall> getCallsAtended() {
		return callsAtended;
	}
	
	public List<Employee> getAvailableEmployees(){
		return availableEmployees.getAvailableEmployees();
	}

	public List<IncomingCall> getCallsAtendedAfterBusy() {
		return callsAtendedAfterBusy;
	}

	public void clean() {
		callsAtended = new ArrayList<>();
		callsAtendedAfterBusy = new ArrayList<>();
	}

}