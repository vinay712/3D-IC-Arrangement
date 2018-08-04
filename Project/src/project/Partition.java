/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import static project.SetPrecision.decformat;

/**
 *
 * @author Vinay,Priya,Shashwat
 */
public class Partition implements SetPrecision{
    MyList<Sets> dis;
    PartitionSet psets[];
    int n;
    double deviation;
    double threshold;
    double total,max;
    public Partition(MyList<Sets> dis,int n,double threshold){
        this.dis=dis;
        deviation=Double.MAX_VALUE;
        this.n=n;
        this.threshold=threshold;
        psets=new PartitionSet[n];
        for(int i=0;i<n;i++){
            psets[i]=new PartitionSet();
        }
        randomAllocate();
        //allocate();
        equallyAllocate();
        for(int i=0;i<n;i++){
            psets[i].prepareFinalSet();
        }
    }    
    public void randomAllocate(){
        Random r=new Random();
        Iterator<Sets> it=dis.iterator();
        while(it.hasNext()){
            Sets s=it.next();
            int i=r.nextInt(n);
            psets[i].addSet(s);
        }
    }
    public void allocate(){
        Collections.sort(dis);
        Iterator<Sets> it=dis.iterator();
        for(int i=0;i<n && it.hasNext();i++){
            psets[i].addSet(it.next());
        }
        while(it.hasNext()){
            Sets s=it.next();
            int p=0;
            for(int i=1;i<n;i++){
                if(psets[i].area < psets[p].area){
                    p=i;
                }
            }           
            psets[p].addSet(s);
        }
    }
    public void equallyAllocate(){
        int t=3;
        double minDiff[]=new double[t];
        for(int i=t-1;i>=0;i--){
            minDiff[i]=(Double.MAX_VALUE);
            //System.out.println(minDiff[i]);
        }
        outer: while((deviation=deviation())>threshold){
            //System.out.println("Inside Equally Allocate") ;
            //System.out.println("deviation "+deviation);
            PartitionSet max=psets[0];
            PartitionSet min=psets[0];
            for(int i=1;i<n;i++){
                if(psets[i].area>max.area){
                    max=psets[i];
                }
                if(psets[i].area<min.area){
                    min=psets[i];
                }
            }
            double diff=(max.area-min.area)/2;
            /*System.out.println("BEFORE: "+(diff*2));
            System.out.println("SET 1: "+ max.displayDisjointSet());
            System.out.println("SET 2: "+ min.displayDisjointSet());
            /*if(diff>=this.diff){
                break;                
            }
            
            System.out.println(diff);
            this.diff=diff;*/
            //System.out.print(diff+"\t");
            double minValue=Double.MAX_VALUE;
            Sets s=null;
            Iterator<Sets> it=max.adj.iterator();
            while(it.hasNext()){
                Sets temp=it.next();
                double diff1=Math.abs(temp.area-diff);
                if(diff1 < minValue){
                    minValue=diff1;
                    s=temp;
                }
            }
            max.removeSet(s);
            min.addSet(s);
            double tempDiff=(Math.abs(max.area - min.area))/2;
            tempDiff=formatDecimal(tempDiff);
            //System.out.println(tempDiff);
            /*System.out.println("AFTER: "+(tempDiff1*2));
            System.out.println("SET 1: "+ max.displayDisjointSet());
            System.out.println("SET 2: "+ min.displayDisjointSet());
            /*if(tempDiff1 == tempDiff){
                min.removeSet(s);
                max.addSet(s);
                break;
            }*/
            insert(minDiff,tempDiff);
            //System.out.println(tempDiff+" "+minDiff[0]+" "+minDiff[1]+" "+minDiff[2]);
            for(int i=0;i<t;i++){
                if(minDiff[i]==tempDiff){
                    break outer;
                }
            }
            //System.out.println("MIN DIFF:"+(minDiff*2));
        }        
    }
    public void insert(double a[],double n){
        for(int i=0;i<a.length;i++){
            if(n<a[i]){
                for(int j=a.length-1;j>i;j--){
                    a[j]=a[j-1];
                }
                a[i]=n;
            }
        }
    }
    public double deviation(){
        double mean=0;
        total=0;
        max=0;
        for(int i=0;i<n;i++){
            total+=psets[i].area;
            if(psets[i].area>max){
                max=psets[i].area;
            }
        }
        /*mean=total/n;
        double dev=0;
        for(int i=0;i<n;i++){
                dev+=Math.pow(psets[i].area - mean , 2);
        }
        dev/=n;
        dev=Math.sqrt(dev);        
        double comp=(dev/mean);
        return (comp);*/
        //System.out.println(total);
        return (max*n-total)/total;
    }
    public double formatDecimal(double d){
        return Double.parseDouble(decformat.format(d));
    }
    
}
