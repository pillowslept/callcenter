package com.almundo.callcenter;

import java.util.LinkedList;
import java.util.Queue;

import com.almundo.callcenter.model.IncomingCall;

public class CallQueue {

	private static CallQueue instance;
	private Queue<IncomingCall> callsQueve;
	
	private CallQueue() {
		this.callsQueve = new LinkedList<>();
	}
	
	public static void addCallToQueue(IncomingCall incomingCall){
		getInstance().callsQueve.add(incomingCall);
    }
	
	public static IncomingCall getCall(){
        return getInstance().callsQueve.poll();
    }
	
	public static CallQueue getInstance() {
		if(instance == null) {
			instance = new CallQueue();
		}
		return instance;
	}

	public static int sizeQueue() {
		return getInstance().callsQueve.size();
	}
}
