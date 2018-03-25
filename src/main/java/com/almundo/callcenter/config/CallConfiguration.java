package com.almundo.callcenter.config;

import java.util.Random;

public class CallConfiguration {
	
	private static final int MIN_DURATION = 5000;
	private static final int MAX_DURATION = 10000;
	
	public static int getDuration(){
		Random random = new Random();
		return random.nextInt(MAX_DURATION - MIN_DURATION + 1) + MIN_DURATION;
	}

}
