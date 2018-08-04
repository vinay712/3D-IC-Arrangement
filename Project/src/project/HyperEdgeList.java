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
public class HyperEdgeList {
    MyList<HyperEdge> edge;
    int no_of_nets;
    int no_of_pins;
    public  HyperEdgeList(int no_of_nets,int no_of_pins){
        this.no_of_nets=no_of_nets;
        this.no_of_pins=no_of_pins;
        edge=new MyList<>();
    }
    public void add(HyperEdge ed){
        edge.add(ed);
    }
    
    public void display(){
        System.out.println("Number of nets: "+no_of_nets);
        System.out.println("Number of pins: "+no_of_pins);
        
        for (HyperEdge ed:edge){
            System.out.print(ed.no_of_modules+"\t"+ed.term+"\t");
            int c=0;
            for(Module mod:ed.mod){
                c++;
                    System.out.print(mod+" | ");
            }
            System.out.println(c);
        }
    }
}
