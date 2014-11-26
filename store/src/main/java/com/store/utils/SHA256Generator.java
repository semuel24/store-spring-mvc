package com.store.utils;

import java.security.MessageDigest;

public class SHA256Generator {
	public static String hash(String sInput) throws Exception{
		String sOutput = null;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(sInput.getBytes("UTF-8"));
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		
		for(int i = 0; i < byteData.length; i++){
			sb.append(Integer.toString( (byteData[i] & 0xff) + 0x100 , 16).substring(1));
		}
		
		sOutput = sb.toString();
		return sOutput;
	}
}
