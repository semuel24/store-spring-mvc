package com.store.utils;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class TimeUtil {
	
	public Date getCurrentDate() {
		return new Date();
	}
	
	public Long getCurrentUnixTime() {
		return getCurrentDate().getTime();
	}
	
	//for test
	public static Long StaticCurrentUnixTime() {
		return new Date().getTime();
	}

}
