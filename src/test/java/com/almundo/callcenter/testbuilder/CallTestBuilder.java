package com.almundo.callcenter.testbuilder;

import com.almundo.callcenter.model.CallState;
import com.almundo.callcenter.model.IncomingCall;

public class CallTestBuilder {
	
	private static final int CALL_NUMBER = 1;
	private static final CallState CALL_STATE = CallState.INCOMING;

	private int callNumber;
	private CallState callState;
	
	public CallTestBuilder() {
		this.callNumber = CALL_NUMBER;
		this.callState = CALL_STATE;
	}
	
	public IncomingCall build() {
		IncomingCall incomingCall = new IncomingCall();
		incomingCall.setCallNumber(callNumber);
		incomingCall.setCallState(callState);
		return incomingCall;
	}
	
	public CallTestBuilder withId(int callNumber){
		this.callNumber = callNumber;
		return this;
	}

}