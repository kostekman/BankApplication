package com.luxoft.bankapp.scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Adam on 22.04.2016.
 */
public class StringChecker {
    private static final Pattern namePattern = Pattern.compile("^[A-Z][a-zA-Z]* [a-zA-z]+([ '-][a-zA-Z]+)*$");

    private static final Pattern numberPattern = Pattern.compile("[0-9]+?(.[0-9]+)");

    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_!#$%&�*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    private static final Pattern phoneNumberPattern = Pattern.compile("^(\\+[0-9]{2})?[0-9]{9}$");

    private static final Pattern cityPattern = Pattern.compile("[A-Z][a-z]*( [A-Z][a-z]*)*");

    public static boolean checkName(String name) {
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public static boolean checkNumber(String number) {
        Matcher matcher = numberPattern.matcher(number);
        return matcher.matches();
    }

    public static boolean checkEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean checkCity(String city) {
        Matcher matcher = cityPattern.matcher(city);
        return matcher.matches();
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
