/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Point;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;


/**
 *
 * @author Vinay
 */
public class ReadModule {
    private InputReader in;
    ModuleList list;
    public ReadModule(String file_name) throws FileNotFoundException{
        in=new InputReader(new FileInputStream(file_name));  
    }
    public void input() {
        try{
            String line;
            int no_of_modules=0;
            int no_of_terminals=0;
            //System.out.println("Reading Modules");
            int no_of_soft_blocks,no_of_hard_blocks;
            
            while(!(line=in.readString()).equalsIgnoreCase("NumSoftRectangularBlocks"));
            in.readString();
            no_of_soft_blocks=Integer.parseInt(in.readString().trim());
            
            //System.out.println(no_of_soft_blocks);
            
            while(!(line=in.readString()).equalsIgnoreCase("NumHardRectilinearBlocks"));
            in.readString();
            no_of_hard_blocks=Integer.parseInt(in.readString().trim());
            
            //System.out.println(no_of_hard_blocks);
            
            while(!(line=in.readString()).equalsIgnoreCase("NumTerminals"));
            in.readString();
            no_of_terminals=Integer.parseInt(in.readString().trim());
            
            no_of_modules=(no_of_hard_blocks>no_of_soft_blocks)?no_of_hard_blocks:no_of_soft_blocks;
            
            list=new ModuleList(no_of_modules, no_of_terminals);
            
            for(int i=1;i<=no_of_soft_blocks;i++){
                Module m=in.readSoftModule(i);
                this.list.add(m);
                //System.out.println(m.name);
            }
            for(int i=1;i<=no_of_hard_blocks;i++){
                /*String name=in.readString();
                in.readString();
                int num=in.nextInt();
                Point p[]=new Point[num];
                for(int j=0;j<num;j++){
                    p[j]=in.readPoint();
                }
                double area=computeArea(p);
                Module m=new Module(i ,name, area);*/
                Module m=in.readHardModule(i);
                this.list.add(m);
                //throw new EOFException();
            }
            
            for(int i=1;i<=no_of_terminals;i++){
                String name=in.readString();
                in.readString();
                this.list.add(new Terminals(i ,name));
            }
            
            //System.out.println("Modules Read Complete");
        }
        catch(Exception e){
            //System.out.println("Invalid Input\n"+e.getMessage());
            throw (new IllegalArgumentException("Error Reading Module"));
            
        }
    }
    public double computeArea(Point p[]){
        double area=0;
        int l=p.length;
        for(int i=0;i<l;i++){
            int j=(i+1)%l;
            int t=p[i].x*p[j].y-p[i].y*p[j].x;
            area+=t;
        }
        area=Math.abs(area);
        area/=2.0;
        return area;
    }
}
