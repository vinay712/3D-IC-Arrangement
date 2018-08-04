/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Vinay
 */
public class MyList<E> extends ArrayList<E>{
    public E get(Object ob){
        Iterator<E> it=iterator();
        while(it.hasNext()){
            E temp=it.next();
            if(temp.equals(ob)){
                return temp;
            }
        }
        return null;
    }
    public boolean add(E ob){
        E temp;
        if((temp=get(ob))==null){
            super.add(ob);
        }
        return true;
        
    }
    
    public void display(){
        Iterator<E> it=iterator();
        while(it.hasNext()){
            E temp=it.next();
            System.out.println(temp);
        }
    }
}
