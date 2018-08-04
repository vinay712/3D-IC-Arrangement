/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Vinay
 */
public class Statistics implements SetPrecision{
    TreeMap<Integer, Struc> tm;
    String directory;
    String dir;
    String file;
    int no_of_execution;
    MyList<Point> points;
    public Statistics(String directory,String file) {
        this.directory=directory;
        this.file=file;
        this.no_of_execution=0;
        this.directory+=file+"\\";
        dir=this.directory;
        tm=new TreeMap<>();
        points=new MyList<>();
    }
    public void changeDirectory(int no_of_layers,int threshold){
        directory=dir+no_of_layers+"Layers\\"+threshold+"%\\";
        
    }
    public void add(int tsv,double area,double power,double area_increase){
        Struc d=null;
        if(tm.containsKey(tsv)){
            d=tm.get(tsv);
            //d.area_deviation=formatDecimal((Math.sqrt(d.area_deviation*d.area_deviation*d.no_of_stimulation*d.no_of_stimulation+area*area))/(d.no_of_stimulation+1));
            //d.power_deviation=formatDecimal((Math.sqrt(d.power_deviation*d.power_deviation*d.no_of_stimulation*d.no_of_stimulation+power*power))/(d.no_of_stimulation+1));
            d.no_of_stimulation+=1;
            if(d.area_increase>area_increase){
                d.area_increase=area_increase;
                d.area_deviation=area;
                d.power_deviation=power;
            }
            /*if(d.power_deviation<power){
                d.area_deviation=area;
                d.power_deviation=power;
            }*/
        }
        else
        {
            d=new Struc(1,area,power,area_increase);
        }
        tm.put(tsv,d);
        this.no_of_execution++;
        points.add(new Point(tsv,area,power,area_increase));
    }
    public void add(int tsv,int n,double area,double power,double area_increase){
        Struc d=new Struc(n, area,power,area_increase);
        tm.put(tsv,d);
    }
    
    public void prepareReport(int successfulsubmission){
        if(successfulsubmission==0){
            return;
        }
        try{            
            FileOutputStream fos=new FileOutputStream(directory+"Report.txt");
            PrintWriter pw=new PrintWriter(fos);
            //System.out.println("In the file: "+this.no_of_execution);
            //System.out.println("Writing into File");
            pw.println("Number of Stimulations : "+this.no_of_execution);
            pw.println("# Number of TSV\tNumber of Stimulation\tArea Increase\tArea Deviation\tPower Deviation\tPercentage");
            for(Map.Entry<Integer, Struc> i:tm.entrySet()){
                double percentage=(double)(i.getValue().no_of_stimulation*100.0)/(double)(this.no_of_execution);
                percentage=formatDecimal(percentage);
                pw.println("  "+i.getKey()+"\t\t\t"+i.getValue().no_of_stimulation+"\t\t"+i.getValue().area_increase+"\t\t"+i.getValue().area_deviation+"\t\t"+i.getValue().power_deviation+"\t\t\t"+percentage+"%");
            }
            pw.close();
            fos.close();
            prepareGraphPoints();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Unable to Create Report");
        }
    }
    public void prepareInitialMap(){
        try{
            File file=new File(directory+"Report.txt");
            tm.clear();
            this.no_of_execution=0;
            //System.out.println("Preparing initial List");
            if(file.exists()){
                FileInputStream fis=new FileInputStream(directory+"Report.txt");
                InputReader in=new InputReader(fis);
                in.readString();
                in.readString();
                in.readString();
                in.readString();
                int temp=in.nextInt();
                this.no_of_execution=temp;
                int num=0;
                while(num<temp){
                    int tsv=in.nextInt();
                    int stimulation=in.nextInt();
                    double area_increase=Double.parseDouble(in.readString());
                    double area=Double.parseDouble(in.readString());
                    double power=Double.parseDouble(in.readString());
                    in.readString();
                    num+=stimulation;
                    add(tsv,stimulation,area,power,area_increase);
                }
                in=null;
                fis.close();
                file=null;
            }
            
            //this.no_of_execution+=no_of_execution;
        }
        catch(Exception e){
            System.out.println(e);
            throw new IllegalArgumentException("Unable to Prepare Initial Statistical Report");
        }
    }
    public void prepareGraphPoints(){
        try{
            FileOutputStream fos=new FileOutputStream(directory+"temp.txt");
            PrintWriter pw=new PrintWriter(fos);
            pw.println("Number of Graph Points: "+this.no_of_execution);
            pw.println("# Number of TSVs \tArea Deviation \tPower Deviation \tIncrease Area");
            File in=new File(directory+"Points.txt");
            File out=new File(directory+"temp.txt");
            if(in.exists()){
                FileInputStream fis=new FileInputStream(directory+"Points.txt");
                InputReader fin=new InputReader(fis);
                fin.readString();
                fin.readString();
                fin.readString();
                fin.readString();
                int temp=fin.nextInt();
                for(int i=0;i<temp;i++){
                    pw.println(" "+fin.readString()+"\t\t\t"+fin.readString()+"\t\t"+fin.readString()+"\t\t"+fin.readString());
                }   
                fin.close();
                fis.close();
            }
            Iterator<Point> it=points.iterator();
            while(it.hasNext()){
                Point p=it.next();
                pw.println(" "+p.tsv+"\t\t\t"+p.area_deviation+"\t\t\t"+p.power_deviation+"\t\t\t"+p.area_increase);
            }
            pw.close();
            fos.close();
            in.delete();
            if(!out.renameTo(in)){
                throw new IllegalArgumentException("Unable to Rename Graph Points");
            }
            in=null;
            out=null;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("Unable to Prepare Graph Points");
        }
        points.clear();
    }
    public double formatDecimal(double d){
        return Double.parseDouble(decformat.format(d));
    }
    public void display(){
        for(Map.Entry<Integer, Struc> i:tm.entrySet()){
                double percentage=(double)(i.getValue().no_of_stimulation*100.0)/(double)(this.no_of_execution);
                percentage=formatDecimal(percentage);
                System.out.println("  "+i.getKey()+"\t\t\t"+i.getValue().no_of_stimulation+"\t\t"+i.getValue().area_increase+"\t\t"+i.getValue().area_deviation+"\t\t"+i.getValue().area_deviation+"\t\t"+percentage+"%");
            }
    }
}

class Struc{
    int no_of_stimulation;
    double area_deviation;
    double power_deviation;
    double area_increase;
    
    public Struc(){
        no_of_stimulation=1;
        area_deviation=0.0;
        power_deviation=0.0;
        area_increase=0.0;
    }
    public Struc(int no_of_stimulation,double area_deviation, double power_deviation,double area_increase){
        this.area_deviation=area_deviation;
        this.no_of_stimulation=no_of_stimulation;
        this.power_deviation=power_deviation;
        this.area_increase=area_increase;
    }
}

class Point{
    int tsv;
    double area_deviation;
    double power_deviation;
    double area_increase;
    public Point(){
        tsv=0;
        area_deviation=0;
        power_deviation=0.0;
        area_increase=0.0;
    }
    public Point(int tsv, double area_deviation,double power_deviation,double area_increase){
        this.tsv=tsv;
        this.area_deviation=area_deviation;
        this.power_deviation=power_deviation;
        this.area_increase=area_increase;
    }
}