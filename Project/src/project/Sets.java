/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.Iterator;

/**
 *
 * @author Vinay
 */
public class Sets implements SetPrecision, Comparable{
    MyList<Module> mod;
    double area;
    double power;
    int no_of_modules;
    MyList<Vertex> adj;
    public Sets(){
        mod=new MyList<>();
        adj=new MyList<>();
        area=0;
        power=0;
        no_of_modules=0;
    }
    public void addModule(Module m){
        mod.add(m);
        Vertex v;
        if((v=adj.get(new Integer(m.number)))==null){
            v=new Vertex(m.number);
            adj.add(v);
        }
        area+=m.area;
        power+=m.power;
        area=formatDecimal(area);
        power=formatDecimal(power);
        no_of_modules++;
    }
    public void addVertex(int i,Module m,int d){
        
        Vertex v;
        if((v=adj.get(new Integer(i)))==null){
            v=new Vertex(i);
            adj.add(v);            
        }
        v.add(m, d);
    }
    public double formatDecimal(double d){
        return Double.parseDouble(decformat.format(d));
    }
    public String toString(){
        String S="("+no_of_modules+" , "+area+" , "+power+" )-> ";
        Iterator<Module> it=mod.iterator();
        while(it.hasNext()){
            Module m=it.next();
            S+=" "+m.number+" | ";
        }
        return S;
    }
    public void displayEdgeList(){
        System.out.println("SET EDGE LIST: "+adj.size());
        Iterator<Vertex> it=adj.iterator();
        while(it.hasNext()){
            Vertex v=it.next();
            System.out.println(v);
        }
    }
    public int compareTo(Object s1){
        Sets s=(Sets)s1;
        return (int)(s.area-this.area);
    }
}
