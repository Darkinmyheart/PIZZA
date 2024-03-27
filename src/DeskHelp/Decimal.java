/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DeskHelp;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author lethi
 */
public class Decimal {

    public static String ConvertDoubleToDecimal(Double x) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(x);
        return formattedNumber;
    }
    
    public static BigDecimal COnvertIntToBigDecimal(int x){
        return BigDecimal.valueOf(Double.parseDouble(String.valueOf(x)));
    }
    
    public static String ConvertBigDecimalToStringFormat(BigDecimal BigDecimal){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedNumber = decimalFormat.format(BigDecimal);
        return formattedNumber;
    }
}
