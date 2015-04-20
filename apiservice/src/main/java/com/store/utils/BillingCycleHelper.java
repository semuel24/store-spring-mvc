package com.store.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * core function of billing
 */
public class BillingCycleHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(BillingCycleHelper.class);

	public static Long getNextBillingTimestamp(Long serviceStartTimestamp,
			String period, Long currentTimestamp) {

		if (logger.isErrorEnabled()) {
			logger.error("date of serviceStartTimestamp:"
					+ new Date(serviceStartTimestamp)
					+ " | date of currentTimestamp:"
					+ new Date(currentTimestamp));
		}
		
		Long billingCycleTimestamp = serviceStartTimestamp;

		if (Constants.PERIOD.MONTHLY.getPeriod().equalsIgnoreCase(period)) {
			while (currentTimestamp > billingCycleTimestamp) {
				billingCycleTimestamp = addAPeriod(billingCycleTimestamp,
						Constants.PERIOD.MONTHLY.getPeriod());
			}

			if (logger.isErrorEnabled()) {
				logger.error("billingCycleTimestamp:" + billingCycleTimestamp);
				logger.error("date of billingCycleTimestamp:"
						+ new Date(billingCycleTimestamp));
			}
			return billingCycleTimestamp;
		}

		throw new RuntimeException(
				"BillingCycleHelper getNextBillingTimestamp invalid period: "
						+ period);
	}

	public static boolean bANewBillingCycleShouldStart(
			Long currentCycleEndTimestamp, Long currentTimeStamp) {
		return currentTimeStamp > currentCycleEndTimestamp;
	}

	private static Long addAPeriod(Long timestamp, String period) {
		if (Constants.PERIOD.MONTHLY.getPeriod().equalsIgnoreCase(period)) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(timestamp);
			cal.add(Calendar.MONTH, 1);
			Long addedMillis = cal.getTimeInMillis();
			return addedMillis;
		}

		throw new RuntimeException(
				"BillingCycleHelper addAPeriod invalid period: " + period);
	}

	public static void main(String args[]) throws ParseException {
		// test1();
//		test2();
		
		
	}

	private static void test2() throws ParseException {
		// service start date
		String string1 = "January 31, 2013";
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date1 = format.parse(string1);
		Long serviceStartTimestamp = date1.getTime();
		// current date
		Long currentTimestamp = TimeUtil.StaticCurrentUnixTime();

		Long nextBillingCycleTimestamp = getNextBillingTimestamp(
				serviceStartTimestamp, Constants.PERIOD.MONTHLY.getPeriod(),
				currentTimestamp);
		System.out.println("next billing cycle timestamp: "
				+ nextBillingCycleTimestamp);
		System.out.println("next billing cycle date: "
				+ new Date(nextBillingCycleTimestamp));
	}

	private static void test1() throws ParseException {
		// initialize a date by string
		String string = "January 31, 2014";
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date = format.parse(string);
		Long millis = date.getTime();
		System.out.println("initialized a timestamp: " + millis);
		System.out.println("initialized date: " + date);

		// date add months
		for (int i = 0; i < 15; i++) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(millis);
			// add a month
			cal.add(Calendar.MONTH, 1);
			Long addedMillis = cal.getTimeInMillis();
			System.out.println(addedMillis);
			System.out.println("added timestamp: " + addedMillis);
			date = new Date(addedMillis);
			System.out.println("added date: " + date);
		}
	}
}
