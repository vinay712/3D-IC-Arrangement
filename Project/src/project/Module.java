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
public class Module implements SetPrecision{
    int number;
    String name;
    double length;
    double breadth;
    double area;
    double power;
    public Module(){
        number=0;
        name="";
        length=breadth=area=0.0;
        area=0.0;
        
    }
    public Module(int number,String name,double length,double breadth){
        this.name=name;
        this.number=number;
        this.length=formatDecimal(length);
        this.breadth=formatDecimal(breadth);
        this.area=formatDecimal(length*breadth);
        
    }
    public Module(int number,String name,double area){
        this.name=name;
        this.number=number;        
        this.area=formatDecimal(area);        
    }
    public void addPower(double power){
        this.power=formatDecimal(power);
    }
    public String toString(){
        return number + "\t" + name + "\t" + length + "\t" + breadth + "\t" + area;
    }
    
    public boolean equals(Object ob){
        if(ob instanceof String){
            String S=(String)ob;
            if(S.equalsIgnoreCase(name))
            {
                return true;
            }
        }
        else if(ob instanceof Integer){
            int n=(Integer)ob;
            if(n==number){
                return true;
            }
        }
        else if(ob instanceof Module){
            Module m=(Module)ob;
            if(m.hashCode()==hashCode()){
                return true;
            }
        }
        return false;
    }
    public int hashCode(){
        return number;
    }
    
    public double formatDecimal(double d){
        return Double.parseDouble(decformat.format(d));
    }
}
