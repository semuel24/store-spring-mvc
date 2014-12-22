package com.store.utils;

public class BillingCycleHelper {

	public static Long getNextBillingTimestamp(Long serviceStartTimestamp, String period, Long currentTimestamp) {
		
		return serviceStartTimestamp + 100000000L;//xxxxxxxx for test
		
//		while(currentTimestamp > currentCycleEndTimestamp) {
//			currentCycleEndTimestamp += period;//somehow
//			
//		}
//		return currentCycleEndTimestamp;
	}
	
	public static boolean bANewBillingCycleShouldStart(Long currentCycleEndTimestamp, Long currentTimeStamp) {
		return currentTimeStamp > currentCycleEndTimestamp;
	}
}
