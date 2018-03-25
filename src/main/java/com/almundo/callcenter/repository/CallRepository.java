package com.almundo.callcenter.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.almundo.callcenter.model.IncomingCall;

@Repository
public class CallRepository {

	List<IncomingCall> callsAttended;
	List<IncomingCall> callsAttendedAfterBusy;

	public CallRepository(){
		callsAttended = new ArrayList<>();
	}
	
	/**
	 * Agrega una llamada procesada
	 * @param incomingCall
	 */
	public void addCall(IncomingCall incomingCall){
		callsAttended.add(incomingCall);
	}

	/**
	 * Retorna todas las llamadas atendidas
	 * @return
	 */
	public List<IncomingCall> getCallsAttended(){
		return callsAttended;
	}

	/**
	 * Agrega una llamada que se atendió luego de ser puesta en espera
	 * @param incomingCall
	 */
	public void addCallBusySystem(IncomingCall incomingCall) {
		callsAttendedAfterBusy.add(incomingCall);
	}
	
	/**
	 * Retorna todas las llamadas que se atendieron luego de ser puestas en espera
	 * @return
	 */
	public List<IncomingCall> getCallsBusySystem() {
		return callsAttendedAfterBusy;
	}
	
	/**
	 * Limpia las llamadas que han sido procesadas
	 */
	public void emptyCalls(){
		callsAttended = new ArrayList<>();
		callsAttendedAfterBusy = new ArrayList<>();
	}

}
