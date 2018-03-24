package com.almundo.callcenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Dispatcher {
	
	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	@Autowired
	AvailableEmployees availableEmployees;
	
	List<IncomingCall> callsQueve = new ArrayList<>();
	List<IncomingCall> callsAtended = new ArrayList<>();
	List<IncomingCall> callsAtendedAfterBusy = new ArrayList<>();
	
	@Async	
	public void dispatchCall(IncomingCall incomingCall){
		LOGGER.info("Llamada entrante : " + incomingCall.getCallNumber());
		try {
			assignEmployee(incomingCall);
		} catch (InterruptedException e) {
			LOGGER.error("Ocurrió un error atendiendo llamada de " + incomingCall.getCallNumber());	
		}
	}

	private void assignEmployee(IncomingCall incomingCall) throws InterruptedException {
		Employee employee = availableEmployees.assignEmployee();
		if(employee != null){
			attend(incomingCall, employee);
		}else{
			LOGGER.info("No hay empleado disponible que atienda llamada : " + incomingCall.getCallNumber() + ", se pondrá en espera, muchas gracias.");
			callsQueve.add(incomingCall);
			callsAtendedAfterBusy.add(incomingCall);
		}
	}
	
	private void attend(IncomingCall incomingCall, Employee employee) throws InterruptedException{
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
		if(!callsQueve.isEmpty()){
			assignEmployee(callsQueve.remove(0));
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
	
}
