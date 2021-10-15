/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sangnv.utils;


import java.sql.Date;

/**
 *
 * @author Shang
 */
public class FormatSupport {

    public static boolean isNumeric(String numString) {
        try {
            Integer.parseInt(numString);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isSQLDate(String dateString) {
        boolean result = true;
        if (dateString != null) {
            try {
                Date.valueOf(dateString);
            } catch (IllegalArgumentException e) {
                result = false;
            }
        }else{
            result = false;
        }
        return result;
    }

}
