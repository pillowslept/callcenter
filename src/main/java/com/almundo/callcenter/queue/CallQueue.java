package com.almundo.callcenter.queue;

import java.util.LinkedList;
import java.util.Queue;

import com.almundo.callcenter.model.IncomingCall;

public class CallQueue {

	private static CallQueue instance;
	private Queue<IncomingCall> callsQueve;
	
	private CallQueue() {
		this.callsQueve = new LinkedList<>();
	}
	
	/**
	 * Agrega una llamada a la cola de espera
	 * @param incomingCall
	 */
	public static void addCallToQueue(IncomingCall incomingCall){
		getInstance().callsQueve.add(incomingCall);
    }
	
	/**
	 * Retorna una llamada de la cola de espera
	 * @return
	 */
	public static IncomingCall getCall(){
        return getInstance().callsQueve.poll();
    }
	
	/**
	 * Retorna instancia actual de la cola o crea una nueva de ser necesaria
	 * @return
	 */
	public static CallQueue getInstance() {
		if(instance == null) {
			instance = new CallQueue();
		}
		return instance;
	}

	/**
	 * Retorna el tamaño de la cola de espera
	 * @return
	 */
	public static int sizeQueue() {
		return getInstance().callsQueve.size();
	}
}
