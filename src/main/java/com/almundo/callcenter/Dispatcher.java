package com.almundo.callcenter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Dispatcher {
	
	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	Employee operator;
	Employee director;
	Employee supervisor;

	List<IncomingCall> callsQueve = new ArrayList<>();
	List<IncomingCall> callsAtended = new ArrayList<>();
	
	public Dispatcher(){
		createAvailableEmployes();
	}
	
	@Async	
	public void dispatchCall(IncomingCall incomingCall){
		LOGGER.info("Llamada entrante : " + incomingCall.getCallNumber());
		try {
			assignEmployee(incomingCall);
		} catch (InterruptedException e) {
			LOGGER.error("Ocurrió un error atendiendo llamada de " + incomingCall.getPersonName());	
		}
	}

	private void assignEmployee(IncomingCall incomingCall) throws InterruptedException {
		if(operator.isAvailable()){
			attend(incomingCall, operator);
		}else if(supervisor.isAvailable()){
			attend(incomingCall, supervisor);
		}else if(director.isAvailable()){
			attend(incomingCall, director);
		}else{
			callsQueve.add(incomingCall);
		}
	}
	
	private void attend(IncomingCall incomingCall, Employee employee) throws InterruptedException{
		employee.setAvailable(Boolean.FALSE);
		int callDuration = CallConfiguration.getDuration();
		Thread.sleep(callDuration);
		LOGGER.info("Se atendió llamada: " + incomingCall.getCallNumber()
				+ ", empleado asignado: " + employee.getPosition()
				+ ", duración llamada: " + callDuration + " segundos");
		employee.setAvailable(Boolean.TRUE);
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

	private void createAvailableEmployes(){
		LOGGER.info("Creando empleados disponibles");
		operator = new Employee("Juan", "Operador");
		director = new Employee("Pedro", "Director");
		supervisor = new Employee("Ana", "Supervisor");
	}

}
