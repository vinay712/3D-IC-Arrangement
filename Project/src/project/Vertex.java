/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vinay
 */
public class Vertex {
    int n;
    HashMap<Module, Integer> hm;
    public Vertex(int n){
        this.n=n;
        hm=new HashMap<>();
    }
    public int add(Module mod){
        int d=0;
        if(hm.containsKey(mod)){
            d=hm.get(mod)+1;
        }
        else
        {
            d=1;
        }
        hm.put(mod,d);
        return d;
    }
    public void add(Module mod,int d){
            hm.put(mod,d);
            
    }
    public String toString(){
        String S=""+n+" ->";
        for(Map.Entry<Module, Integer> i:hm.entrySet()){
            S+=" ( "+i.getKey().number+" , "+i.getValue()+" ) ";
        }
        return S;
    }
    public boolean equals(Object ob){
        //System.out.print("Vertex");
        if(ob instanceof Integer){
            int v=(Integer)ob;
            if(v==n){
                return true;
            }
        }
        return false;
    }
    public int hashCode(){
        return n;
    }
}
