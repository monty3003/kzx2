/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.bej.orm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 *
 * @author diego
 */
public class Conversor {

    public static Date deStringToDate(String date) {
        Date result = null;
        Calendar cal = GregorianCalendar.getInstance();
        String dia = null;
        String mes = null;
        String anho = null;
        Integer d = null;
        Integer m = null;
        Integer a = null;
        try {
            StringTokenizer tokens = new StringTokenizer(date.toString(), "/", false);
            dia = tokens.nextToken();
            mes = tokens.nextToken();
            anho = tokens.nextToken();
            d = new Integer(dia);
            m = new Integer(mes);
            a = new Integer(anho);
            cal = new GregorianCalendar(d, m, a);
            result = cal.getTime();
        } catch (Exception e) {
            e.getLocalizedMessage();
        } finally {
            return result;
        }
    }

    public static String deDateToString(Date date) {
        String result = null;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
}
