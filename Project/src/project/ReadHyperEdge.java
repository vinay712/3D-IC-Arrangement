/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import static project.Project.*;
/**
 *
 * @author Vinay
 */
public class ReadHyperEdge {
    private InputReader in;
    HyperEdgeList list;
    public ReadHyperEdge(String file_name) throws FileNotFoundException{
        in=new InputReader(new FileInputStream(file_name));  
    }
     public void input() {
        try{
            String line;
            int no_of_nets;
            int no_of_pins=0;
            //System.out.println("Reading Hyper Edge");
            
            while(!(line=in.readString()).equalsIgnoreCase("NumNets"));
            in.readString();
            no_of_nets=Integer.parseInt(in.readString().trim());
            
            while(!(line=in.readString()).equalsIgnoreCase("Numpins"));
            in.readString();
            no_of_pins=Integer.parseInt(in.readString().trim());
            
            list=new HyperEdgeList(no_of_nets, no_of_pins);
            
            for(int i=1;i<=no_of_nets;i++){
                in.readString();
                in.readString();
                int net_degree=Integer.parseInt(in.readString());
                HyperEdge ed=new HyperEdge(net_degree);
                for(int j=1;j<=net_degree;j++){
                    String block=in.readBlock();
                    Terminals t;
                    Module m;
                    if(j==1 && (t=term.get(block))!=null){
                        ed.add(t);
                    }
                    else if((m=mod.get(block))!=null){
                        ed.add(m);
                    }
                }
                list.add(ed);
            }
            
            //System.out.println("HyperEdge Read Complete");
            
        }
        catch(Exception e){
            throw new IllegalArgumentException("Error Reading Hyperedge");
        }
    }
    
}
