package com.almundo.callcenter.config;

import java.util.Random;

import com.almundo.callcenter.utils.Constants;

public class CallConfiguration {
	
	private static final int MIN_DURATION = 5000;
	private static final int MAX_DURATION = 10000;
	
	/**
	 * Genera un tiempo aleatorio entre 5 y 10 segundos para una llamada
	 * @return
	 */
	public static int getDuration(){
		Random random = new Random();
		return random.nextInt(MAX_DURATION - MIN_DURATION + Constants.ONE) + MIN_DURATION;
	}

}
