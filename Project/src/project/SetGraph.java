/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Vinay
 */
public class SetGraph {    
    MyList<Vertex> adj;
    ArrayList<Integer> visited;
    Sets set;
    int offset;
    MyList<Sets> dis;
    public SetGraph(Sets set,int offset, MyList<Sets> dis){
        adj=set.adj;
        visited=new ArrayList<>();
        this.set=set;
        this.dis=dis;
        this.offset=offset;
        prepareAdjacencyList();
    }
    public void prepareAdjacencyList(){
        Iterator<Module> it=set.mod.iterator();
        while(it.hasNext()){
            Module m=it.next();
            visited.add(m.number);
        }  
        findDisjoint();
    }
    public void findDisjoint(){
        Iterator<Module> it=set.mod.iterator();
        while(it.hasNext()){
            Module m=it.next();
            if(visited.contains(m.number)){
                Sets temp=new Sets();
                dfs(m,temp);
                dis.add(temp);
            }
        }  
    }
    public void dfs(Module m, Sets s){
        visited.remove(Integer.valueOf(m.number));
        s.addModule(m);
        for(Map.Entry<Module, Integer> ad:adj.get(new Integer(m.number)).hm.entrySet()){
            if(visited.contains(ad.getKey().number) && ad.getValue()!=offset){
                dfs(ad.getKey(),s);
            }
            if(ad.getValue()!=offset && (set.mod.get(new Integer(ad.getKey().number))!=null)){                
                s.addVertex(m.number, ad.getKey(), ad.getValue());
            }
        }
        
    }
}
