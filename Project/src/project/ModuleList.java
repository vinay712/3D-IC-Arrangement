/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;

/**
 *
 * @author Vinay
 */
public class ModuleList {
    MyList<Module> mod;
    MyList<Terminals> term;
    int total_area;
    int no_of_modules;
    int no_of_terminals;
    public ModuleList(int no_of_modules,int no_of_terminals){
        this.no_of_modules=no_of_modules;
        this.no_of_terminals=no_of_terminals;
        mod=new MyList<>();
        term=new MyList<>();
        total_area=0;
    }
    
    public void add(Module m){
        mod.add(m);
        total_area+=m.area;
    }
    
    public void add(Terminals t){
        term.add(t);
    }
    
    public void display(){
        System.out.println("Number of Modules: "+no_of_modules);
        for(Module m:mod){
            System.out.println(m);
        }
        System.out.println("Number of Terminals: "+no_of_terminals);
        for(Terminals t:term){
            System.out.println(t);
        }
    }
}
