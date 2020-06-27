package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[a-zA-Z\\s]+", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_YEAR_REGEX = Pattern.compile("^\\d{4}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_INTEGER_REGEX = Pattern.compile("^\\d+$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validateInteger(String integerStr) {
        Matcher matcher = VALID_INTEGER_REGEX.matcher(integerStr);
        return matcher.find();
    }

    public static boolean validateName(String str) {
        Matcher matcher=VALID_NAME_REGEX.matcher(str);
        return matcher.find();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
