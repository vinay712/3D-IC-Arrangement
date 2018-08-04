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
public class HyperEdge {
    Terminals term;
    MyList<Module> mod;
    int no_of_modules;
    public HyperEdge(int no_of_modules){
        term=null;
        this.no_of_modules=no_of_modules;
        mod=new MyList<>();
    }
    public void add(Module m){
        mod.add(m);
    }
    public void add(Terminals t){
        term=t;
    }
    
}
