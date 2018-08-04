/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import static project.SetPrecision.decformat;

/**
 *
 * @author Vinay
 */
public class Project {

    /**
     * @param args the command line arguments
     */
    public static ReadModule rm;
    public static ReadHyperEdge ed;
    public static ReadPower pow;
    public static Graph graph;
    public static MyList<Module> mod;
    public static MyList<Terminals> term;
    public static MyList<HyperEdge> edge;
    public static Vertex adj[];    
    public static PrintWriter pw;
    public static Statistics st;
    public static FileManipulation fm;
    public static String folder;
    public static String file;
    public static int unsuccessfulStimuation;
    public static void main(String[] args) throws Exception {
        
        //new SelectFile();
        
        String name[]={"ami33","ami49","n100","n200","n300"};        
        for(int i=0;i<name.length;i++){
            (new Project()).init(name[i]);
        }
    }
    public void init(String file){
        //int no_of_execution=10;
        folder="E:\\Project\\thermal_benchmark\\";
        this.file=file;
        unsuccessfulStimuation=0;
        try{
            
            fm=new FileManipulation(folder,file);
            //fm.deleteDirectory();
            st=new Statistics(folder, file);
            
            //pw=new PrintWriter(new FileOutputStream(folder+file+".txt"));
            
            rm=new ReadModule(folder+file+".blocks");
            rm.input();
            //rm.list.display();
        
            mod=rm.list.mod;
            term=rm.list.term;
        
            ed=new ReadHyperEdge(folder+file+".nets");
            ed.input();
            //ed.list.display();        
            edge=ed.list.edge;
            
            pow=new ReadPower(folder+file+".power");
            pow.input();
        
            graph=new Graph(rm.list.no_of_modules,ed.list.no_of_nets);
            graph.constructGraph();
            //graph.display();
            adj=graph.adjacency_list;
            //execution(no_of_execution);
            
            
            //new Display(this,file);
            
            System.out.println(file);
            Stimulate();
            
            
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void execution(int no_of_execution,int no_of_layers,int threshold)throws Exception{
        //System.out.println("New Execution: "+no_of_execution);
        fm.changeDirectory(no_of_layers, threshold);
        st.changeDirectory(no_of_layers, threshold);
        st.prepareInitialMap();
        unsuccessfulStimuation=0;
        //st.display();
        int min=Integer.MAX_VALUE;
        double min_power=Double.MAX_VALUE;
        double min_area=Double.MAX_VALUE;
        double inc_area=Double.MAX_VALUE;
        for(int i=0;i<no_of_execution;i++){
            //System.out.println("Computing: "+i);
            pw=new PrintWriter(new FileOutputStream(folder+file+".txt"));
            //System.out.println(i);
            if(graph.compute(no_of_layers,(double)(threshold)/100.0)){                    
                int no_of_tsv=graph.no_of_tsv; 
                double area=graph.area_thres;
                double power=graph.power_thres;
                double area_increase=graph.area_increase;
                //System.out.println(" Successful");
                pw.println("............................");
                pw.close();
                fm.write(no_of_tsv);
                st.add(no_of_tsv,area,power,area_increase);
                if(min>no_of_tsv){
                    min=no_of_tsv;
                    min_power=power;
                    min_area=area;
                    inc_area=area_increase;
                }
                else if(min == no_of_tsv && inc_area>area_increase){
                    inc_area=area_increase;
                    min_power=power;
                    min_area=area;                        
                }
            }
            else{
                unsuccessfulStimuation++;
                pw.close();
                //System.out.println("CHECK");
                try{
                File oldFile=new File(folder+file+".txt");
                oldFile.delete();
                }
                catch(Exception e){
                    System.out.println("TEST");
                }

            }
            //System.out.println();
            graph.reset(); 
            //System.out.println("Number of TSVs is "+no_of_tsv);
        }
        //pw.close();
        if(min!=Integer.MAX_VALUE){
            System.out.println(no_of_layers+"\t"+threshold+"%\t"+min+"\t"+min_area+"\t"+min_power+"\t"+inc_area+"%");
            //min_area=min_power=inc_area=0;
            //min=0;
        }
        st.prepareReport(no_of_execution-unsuccessfulStimuation);
        
    }
    public void Stimulate() throws Exception{
        int l[]={3,4,5,6};
        int t[]={5,10,15,20,30,50};
        int sti=1;
        for(int i=0;i<l.length;i++){
            for(int j=0;j<t.length;j++){
                execution(sti,l[i],t[j]);
            }
        }
        
    }
    public static double formatDecimal(double d){
        return Double.parseDouble(decformat.format(d));
    }
}
