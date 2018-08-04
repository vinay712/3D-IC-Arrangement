/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.util.Iterator;

import static project.Project.*;
/**
 *
 * @author Vinay
 */
public class TSV {
    PartitionSet psets[];
    int no_of_tsv;
    int n;
    public TSV(PartitionSet psets[]){
        this.psets=psets;
        no_of_tsv=0;
        n=psets.length;
        
    }
    public int findTSV(){
        Iterator<HyperEdge> it=edge.iterator();
        while(it.hasNext()){
            HyperEdge ed=it.next();
            Iterator<Module> it1=ed.mod.iterator();
            int c=0;
            boolean flag[]=new boolean[n];
            while(it1.hasNext()){
                Module mod=it1.next();
                for(int i=0;i<n;i++){
                    Module m;
                    if(!flag[i] && (m=psets[i].mod.get(mod))!=null ){
                        flag[i]=true;
                        c++;
                        //break;
                    }
                }
            }
            if(c>1){
                no_of_tsv++;
            }
            //System.out.println(c+" "+no_of_tsv);
        }
        return no_of_tsv;
    }
    
}
