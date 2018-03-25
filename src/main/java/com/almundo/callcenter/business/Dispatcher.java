package com.almundo.callcenter.business;

import static com.almundo.callcenter.model.CallState.PROGRESS;
import static com.almundo.callcenter.model.CallState.WAITING;
import static com.almundo.callcenter.utils.Constants.ZERO;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.almundo.callcenter.config.CallConfiguration;
import com.almundo.callcenter.model.Employee;
import com.almundo.callcenter.model.IncomingCall;
import com.almundo.callcenter.queue.CallQueue;

@Service
public class Dispatcher {
	
	private static final Logger LOGGER = Logger.getLogger(Dispatcher.class.getName());

	@Autowired
	AvailableEmployees availableEmployees;
	
	@Autowired
	ProcessCall processCall;
	
	/**
	 * Recibe y despacha cada llamada que ingresa al sistema
	 * @param incomingCall
	 */
	@Async
	public void dispatchCall(IncomingCall incomingCall){
		LOGGER.info("Ring ring. Llamada entrante : " + incomingCall.getCallNumber());
		try {
			assignEmployee(incomingCall);
		} catch (InterruptedException e) {
			LOGGER.error("Ocurrió un error atendiendo llamada de " + incomingCall.getCallNumber());	
		}
	}

	/**
	 * Busca un empleado disponible y lo asigna para comenzar una llamada, en caso de no existir empleados
	 * disponibles, adiciona la llamada a la cola de espera
	 * @param incomingCall
	 * @throws InterruptedException
	 */
	private void assignEmployee(IncomingCall incomingCall) throws InterruptedException {
		Employee employee = availableEmployees.getFreeEmployee();
		if(employee != null){
			attend(incomingCall, employee);
		}else{
			LOGGER.info("No hay empleados disponibles para atender la llamada : " + incomingCall.getCallNumber() + ", en un momento será atendido...");
			incomingCall.setCallState(WAITING);
			CallQueue.getInstance();
			CallQueue.addCallToQueue(incomingCall);
		}
	}
	
	/**
	 * Valida si la llamada a atender estaba en espera y se procede a guardarla
	 * @param incomingCall
	 */
	private void validateCallBusySystem(IncomingCall incomingCall){
		if(incomingCall.getCallState().equals(WAITING)){
			processCall.addCallBusySystem(incomingCall);
		}
	}
	
	/**
	 * Comienza la atención de una llamada con un empleado asignado, libera el empleado y termina la llamada
	 * @param incomingCall
	 * @param employee
	 * @throws InterruptedException
	 */
	private void attend(IncomingCall incomingCall, Employee employee) throws InterruptedException{
		validateCallBusySystem(incomingCall);

		incomingCall.setCallState(PROGRESS);
		incomingCall.setDuration(CallConfiguration.getDuration());

		LOGGER.info("Comenzó llamada: " + incomingCall.getCallNumber()
				+ ", empleado asignado: " + employee.getEmployeePosition());
		Thread.sleep(incomingCall.getDuration());
		LOGGER.info("Se atendió llamada: " + incomingCall.getCallNumber()
				+ ", empleado asignado: " + employee.getEmployeePosition()
				+ ", duración llamada: " + incomingCall.getDuration() + " segundos");

		availableEmployees.freeEmployee(employee);
		
		processCall.finalizeCall(incomingCall, employee);
		
		validateCallsQueve();
	}
	
	/**
	 * Valida si existen llamadas en espera para ser atendidas
	 * @throws InterruptedException
	 */
	private void validateCallsQueve() throws InterruptedException{
		if(CallQueue.sizeQueue() > ZERO){
			CallQueue.getInstance();
			assignEmployee(CallQueue.getCall());
		}
	}
	
	/**
	 * Retorna todas las llamadas atendidas
	 * @return
	 */
	public List<IncomingCall> getCallsAtended() {
		return processCall.getCallsAttended();
	}
	
	/**
	 * Retorna todos los empleados disponibles
	 * @return
	 */
	public List<Employee> getAvailableEmployees(){
		return availableEmployees.getAvailableEmployees();
	}

	/**
	 * Retorna todas las llamadas que se atendieron luego de ser puestas en espera
	 * @return
	 */
	public List<IncomingCall> getCallsAtendedAfterBusy() {
		return processCall.getCallsBusySystem();
	}

	/**
	 * Limpia las llamadas que han sido procesadas
	 */
	public void clean() {
		processCall.emptyCalls();
	}

}
