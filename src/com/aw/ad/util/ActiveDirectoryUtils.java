package com.aw.ad.util;

import com.aw.ad.ADUserDetails;
import com.aw.ad.UserDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * User: gmc
 * Date: 07/03/11
 */
public class ActiveDirectoryUtils {

    private static final String ACCOUNT_NEVER_EXPIRE_VALUE = "9223372036854775807";
    // UserAccountFlags
    private static final int ADS_UF_DONT_EXPIRE_PASSWD = 0x10000;
    private static final int ACCOUNT_DISABLE = 0x0002;

    public UserDetails getUserDetailsFrom(String userName, String fullDn, String userAccountControlStr, String pwdLastSet, String accountExpires, String maxPwdAgeStr) {
        int userAccountControl = Integer.parseInt(userAccountControlStr);
        boolean accountNeverExpire = accountExpires.equals("0") || ACCOUNT_NEVER_EXPIRE_VALUE.equals(accountExpires);
        boolean accountDisabled = (userAccountControl & ACCOUNT_DISABLE) == ACCOUNT_DISABLE;
        boolean credentialsHasToBeChangedAtFirst = pwdLastSet.equals("0");
        boolean credentialsNeverExpire = (userAccountControl & ADS_UF_DONT_EXPIRE_PASSWD) == ADS_UF_DONT_EXPIRE_PASSWD;
        Date pwdLastSetDate = getDateTimeFrom(pwdLastSet);
        int maxPwdAgeInDays = getNumberOfDays(maxPwdAgeStr);
        Date currentDateTime = new Date();
        Date currentDate = truncTimeFrom(currentDateTime);
        boolean credentialsExpired = false;
        int daysBeforeCredentialsExpiration = Integer.MAX_VALUE;
        Date credentialsExpiresDate = null;
        if (!credentialsNeverExpire) {
            credentialsExpiresDate = addDaysToDate(maxPwdAgeInDays, pwdLastSetDate);
            credentialsExpired = credentialsExpiresDate.compareTo(currentDateTime) < 0;
            daysBeforeCredentialsExpiration = (int) TimeUnit.DAYS.convert(credentialsExpiresDate.getTime() - currentDateTime.getTime(), TimeUnit.MILLISECONDS);
        }
        boolean accountExpired = false;
        int daysBeforeAccountExpiration = Integer.MAX_VALUE;
        Date accountExpiresDate = null;
        if (!accountNeverExpire) {
            accountExpiresDate = getDateFrom(accountExpires);
            accountExpired = accountExpiresDate.compareTo(currentDate) < 0;
            daysBeforeAccountExpiration = (int) TimeUnit.DAYS.convert(accountExpiresDate.getTime() - currentDate.getTime(), TimeUnit.MILLISECONDS);
        }
        ADUserDetails userDetails = new ADUserDetails();
        userDetails.setUsername(userName);
        userDetails.setDn(fullDn);
        userDetails.setEnabled(!accountDisabled);
        userDetails.setAccountNeverExpire(accountNeverExpire);
        userDetails.setAccountNonExpired(!accountExpired);
        userDetails.setAccountExpiration(accountExpiresDate);
        userDetails.setTimeBeforeAccountExpiration(daysBeforeAccountExpiration);
        userDetails.setCredentialsNeverExpire(credentialsNeverExpire);
        userDetails.setCredentialsHasToBeChangedAtFirst(credentialsHasToBeChangedAtFirst);
        userDetails.setCredentialsNonExpired(!credentialsExpired);
        userDetails.setCredentialsExpiration(credentialsExpiresDate);
        userDetails.setTimeBeforeCredentialsExpiration(daysBeforeCredentialsExpiration);
        return userDetails;
    }


    private final static long DIFF_NET_JAVA_FOR_DATE_AND_TIMES = 11644473600000L;
    private final static long DIFF_NET_JAVA_FOR_DATES = 11644473600000L + 24 * 60 * 60 * 1000;

    private Date getDateFrom(String adDateStr) {
        long adDate = Long.parseLong(adDateStr);
        long milliseconds = (adDate / 10000) - DIFF_NET_JAVA_FOR_DATES;
        Date date = new Date(milliseconds);
        return date;
    }

    private Date getDateTimeFrom(String adDateTimeStr) {
        long adDateTime = Long.parseLong(adDateTimeStr);
        long milliseconds = (adDateTime / 10000) - DIFF_NET_JAVA_FOR_DATE_AND_TIMES;
        Date date = new Date(milliseconds);
        return date;
    }

    private final static int ONE_HUNDRED_NANOSECOND = 10000000;
    private final static long SECONDS_IN_DAY = 86400;

    private int getNumberOfDays(String oneHundredNanosecondInterval) {
        long interval = Math.abs(Long.parseLong(oneHundredNanosecondInterval));
        long intervalSecs = interval / ONE_HUNDRED_NANOSECOND;
        int intervalDays = (int) (intervalSecs / SECONDS_IN_DAY);
        return intervalDays;
    }

    private Date truncTimeFrom(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Date addDaysToDate(int daysToAdd, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysToAdd);
        return cal.getTime();
    }

}
