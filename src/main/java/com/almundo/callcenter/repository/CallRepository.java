package com.almundo.callcenter.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.almundo.callcenter.model.IncomingCall;

@Repository
public class CallRepository {

	List<IncomingCall> callsAttended;
	
	public CallRepository(){
		callsAttended = new ArrayList<>();
	}
	
	public void addCall(IncomingCall incomingCall){
		callsAttended.add(incomingCall);
	}

	public List<IncomingCall> getCallsAttended(){
		return callsAttended;
	}
	
	public void emptyCalls(){
		callsAttended = new ArrayList<>();
	}
	
}
