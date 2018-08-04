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
public class Power {
    PartitionSet psets[];
    int n;
    public Power(PartitionSet psets[]){
        this.psets=psets;
        this.n=psets.length;
    }    
    public double deviation(){
        double mean=0;
        double total=0;
        for(int i=0;i<n;i++){
            total+=psets[i].power;
        }
        mean=total/n;
        double dev=0;
        for(int i=0;i<n;i++){
                dev+=Math.pow(psets[i].power - mean , 2);
        }
        dev/=n;
        dev=Math.sqrt(dev);        
        double comp=(dev/mean);
        return (comp);
    }
    
}
