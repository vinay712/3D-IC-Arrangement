/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.text.DecimalFormat;

/**
 *
 * @author Vinay
 */
public class FormatNumbers {
    
    DecimalFormat decformat=new DecimalFormat("#0.00000");
    public double formatDecimal(double d){
        return Double.parseDouble(decformat.format(d));
    }
    
    
}
