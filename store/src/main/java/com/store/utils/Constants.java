package com.store.utils;

public class Constants {
	
	//error codes
	public static String SUCCESS = "0";
	public static String GENERAL_FAILURE = "103";
	public static String NULL_FORM = "1";
	public static String USER_ALREADY_EXISTS = "2";
	public static String USER_MISSING = "3";
	public static String EMAIL_INCORRECT = "4";
	public static String USERNAME_INCORRECT = "5";
	public static String LOGIN_FAILURE = "6";
	public static String USER_DEACTIVE = "7";
	public static String IP_UNKNOWN = "8";
	public static String REACH_USAGE_LIMIT = "9";
	public static String INVALID_CHANGE_PASSWORD_CODE = "10";
	
	public static String SESSION = "sessionkey";
	public static String EMAIL = "email";
	public static Integer ENABLED = 1;
	public static Integer DISABLED = 0;
	
	public static Long FREE_TRIAL_USAGE_LIIMIT = 838860800L;//in bytes, 800 MB
	
	public static Integer DEFAULT_SESSION_TIMEOUT = 300;//in seconds
	
	public static String AdminApiKey = "2e078028-3196-4361-a027-d9f19835cc7a";
	public static String APIKEY = "apikey";
	
	public static enum PRODUCT {
		FREETRIAL("freetrial"),
		SHARED_MEMBERSHIP("normalmember"),
		DEDICATE_MEMBERSHIP("dedicatemember");
		
		private String productKey;
		
		private PRODUCT(String _productKey){
			this.productKey = _productKey;
		}
		
		public String getProductKey() {
			return this.productKey;
		}	
		
		@Override
		public String toString() {
			return this.productKey;
		}
	}
	
	public static enum PERIOD {
		MONTHLY("monthly");
		
		private String period;
		
		private PERIOD(String _period) {
			this.period = _period;
		}
		
		public String getPeriod() {
			return this.period;
		}
		
		@Override
		public String toString() {
			return this.period;
		}
	}
}
