package com.store.utils;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class StoreCharsetUtil {
	public static String EncodeString(String input) {
//		if(input == null) {
//			return null;
//		} 
//		
//		return Charset.forName("UTF-8").encode(input).toString();
//		PrintStream out = null;
//		try {
//			out = new PrintStream(System.out, true, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    out.println("input###" + input);
//	    
//		return input;
		
		System.out.println("New input###" + input);
		return input;
	}
}
