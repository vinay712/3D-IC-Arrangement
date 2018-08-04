/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Vinay
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static project.Project.*;

public class ReadPower {
    
    private InputReader in;
    public ReadPower(String file_name) throws FileNotFoundException{
        in=new InputReader(new FileInputStream(file_name));  
    }
    public void input(){
        try{
            for(Module m: mod){
                double power=in.nextDouble();
                m.addPower(power);
                //System.out.println(m.power);
            }
        }
        catch(Exception e){
            throw new IllegalArgumentException("Unable to Read Power");
        }
    }
    
}
