/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author diego
 */
public class Conversor {

    public static Date deStringToDate(String date, String pattern) {
        Date result = null;
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            result = df.parse(date);
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            return result;
        }
    }

    public static String deDateToString(Date date, String pattern) {
        String result = null;
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            result = df.format(date);
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            return result;
        }
    }

    public static byte[] deBooleanToByte(Boolean b) {
        byte[] result = {0};
        try {
            result[0] = (byte) (b ? 1 : 0);
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            return result;
        }
    }

    public static Boolean deBytetoBoolean(byte[] b) {
        Boolean result = false;
        try {
            result = b[0] == 1;
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            return result;
        }
    }

    public static String numberToStringPattern(Object number) {
        NumberFormat nf = new DecimalFormat(ConfiguracionEnum.MONEDA_PATTERN.getSymbol());
        return nf.format(number);
    }
}
