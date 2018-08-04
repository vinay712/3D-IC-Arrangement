/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import com.sun.xml.internal.messaging.saaj.soap.Envelope;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import static project.Project.st;

/**
 *
 * @author Vinay
 */
public class Display implements ActionListener{
    Project proj;
    JTextField no_of_stimulation;
    DefaultTableModel defaultModel;
    JLabel error,note;
    JButton exec;
    JButton reset,statistics,graph;
    JButton back;
    JButton selectFile;
    JTextField fileStimulations;
    JFrame main,secondary;
    JTextField num,max,min,mean,mode,quar,tsv;
    JComboBox<Integer> numberOfLayers,threshold;
    Color c1,c2;
    String title;
    Integer n[]={2,3,4,5,6};
    Integer dev[]={1,2,3,5,10,15,20,30,40,50};
    int layer,thres;
    public Display(Project proj, String title){
        this.proj=proj;
        this.title=title;
        init();
        displayTable();
    }
    public Display(){
        init();
    }

    public void init() {
        c1=Color.LIGHT_GRAY;
        c2=Color.LIGHT_GRAY;
        
        
        Dimension d=new Dimension(520,600);
        
        main = new JFrame();
        main.setTitle(title);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(d);
        
         
        secondary = new JFrame();
        secondary.setTitle("Statistics");
        secondary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secondary.setSize(d);
        
        Stimulations();
        
        main.setLocationRelativeTo(null);
        main.setVisible(true);
        main.setResizable(false);
        
        prepareStatisticFrame();
        
        secondary.setLayout(null);        
        secondary.getContentPane().setBackground(c2);
        secondary.setLocationRelativeTo(null);
        secondary.setVisible(false);
        secondary.setResizable(false);
        
    }
    public void Stimulations(){
        
        JPanel vtop = new JPanel();
        vtop.setBounds(10, 10, 500, 40);
        vtop.setBackground(c1);
        vtop.setLayout(null);
        
        
        selectFile =new JButton("Change File");
        selectFile.setBounds(10, 10, 120, 30);
        selectFile.setBackground(Color.DARK_GRAY);
        selectFile.setForeground(Color.WHITE);
        selectFile.addActionListener(this);
        vtop.add(selectFile);
        
        main.add(vtop);
        
        JPanel top = new JPanel();
        top.setBounds(10, 50, 500, 180);
        top.setBackground(c1);
        top.setLayout(null);
        
        stimulationUpper(top);
                
        main.add(top);
        
        JPanel middle=new JPanel();  
        middle.setBounds(10, 230, 500, 250);
        middle.setBackground(c1);
        middle.setLayout(null);
        
        stimulationMiddle(middle);
        
        main.add(middle);
        
        
        JPanel bottom=new JPanel();
        bottom.setBounds(10, 500, 500, 80);
        bottom.setBackground(c1);
        bottom.setLayout(null);
        
        stimulationLower(bottom);
        
        main.add(bottom);
        
        
        
    }
    public void stimulationUpper(JPanel top){
        JLabel lab=new JLabel("No of Layers :");
        lab.setBounds(100, 0, 150, 25);        
        top.add(lab);
        
        numberOfLayers=new JComboBox <Integer>(n);
        numberOfLayers.setBounds(220, 0, 40, 25);
        numberOfLayers.setSelectedIndex(0);
        top.add(numberOfLayers);
        
        lab=new JLabel("Deviation Limit  :");
        lab.setBounds(100, 35, 150, 25);        
        top.add(lab);
        
        threshold=new JComboBox <Integer>(dev);
        threshold.setBounds(220, 35, 40, 25);
        threshold.setSelectedIndex(4);
        top.add(threshold);
        
        lab=new JLabel("%");
        lab.setBounds(265, 35, 12, 25);        
        top.add(lab);
        
        lab=new JLabel("No of Stimulation :");
        lab.setBounds(100, 70, 150, 25);        
        top.add(lab);
        
        no_of_stimulation=new JTextField("0", 4);
        no_of_stimulation.setBounds(220,70,70,25);        
        top.add(no_of_stimulation);
        
        exec=new JButton("RUN");
        exec.setBounds(320,70,70,30);        
        top.add(exec);        
        exec.addActionListener(this);
        
        error=new JLabel();
        error.setForeground(Color.RED);
        error.setBounds(150,100,200 ,25);
        top.add(error);
        
        /**/
        
        lab=new JLabel("Number of Stimulations Done : ");
        lab.setBounds(100,120,180,30);
        top.add(lab);
        
        fileStimulations=new JTextField("0");
        fileStimulations.setBounds(290, 120, 70, 30);
        fileStimulations.setEditable(false);
        top.add(fileStimulations);
    }
    public void stimulationMiddle(JPanel middle){
        String[] columns = new String[] {"Number of TSV", "Number of Stimulation", "Area Increase", "Area Deviation", "Power Deviation"};
        
        defaultModel = new DefaultTableModel(columns, 0);
        
        JTable myTable = new JTable(defaultModel){
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
 
        myTable.setColumnSelectionAllowed(false);
        myTable.setRowSelectionAllowed(true);
        myTable.setPreferredScrollableViewportSize(new Dimension(450,300));
        myTable.setFillsViewportHeight(true);
        myTable.setAutoCreateRowSorter(true);
        myTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane js=new JScrollPane(myTable);
        js.setBounds(10, 0, 480 , 250);
        js.setVisible(true);
        
        middle.add(js);
    }
    public void stimulationLower(JPanel bottom){
        statistics=new JButton("Statistics");
        statistics.setBounds(50,500,100,30);
        statistics.addActionListener(this);
        
        graph=new  JButton("Graph");
        graph.setBounds(200,500,100,30);
        graph.addActionListener(this);
        
        reset=new JButton("RESET");
        reset.setBounds(350,500,100,30);
        reset.addActionListener(this);
        
        statistics.setEnabled(false);
        graph.setEnabled(false);
        reset.setEnabled(false);
        
        note=new JLabel("");
        note.setForeground(Color.blue);
        note.setBounds(150, 520, 300, 50);
        
        bottom.add(statistics);
        bottom.add(graph);
        bottom.add(reset);  
        bottom.add(note);
        
    }
    public void prepareStatisticFrame(){
        JPanel myPanel=new JPanel();
        
        myPanel.setBounds(200, 100, 100, 200);
        myPanel.setBackground(c2);
        main.add(myPanel);
        
        back=new JButton("Back");
        back.setBounds(400,20,100,30);
        back.addActionListener(this);
        secondary.add(back);
        
        JLabel head=new JLabel("Number of Stimulation: ");
        head.setBounds(50,70,220,30);
        secondary.add(head);
        
        num=new JTextField("0");        
        num.setBounds(320, 70, 70, 20);
        num.setEditable(false);
        secondary.add(num);
        
        
        head=new JLabel("Minimum Number of TSVs: ");
        head.setBounds(50,120,220,30);
        secondary.add(head);
        
        min=new JTextField("0");        
        min.setBounds(320, 120, 40, 20);
        min.setEditable(false);
        secondary.add(min);
        
        head=new JLabel("Maximum Number of TSVs: ");
        head.setBounds(50,170,220,30);
        secondary.add(head);
        
        max=new JTextField("0");        
        max.setBounds(320, 170, 40, 20);
        max.setEditable(false);
        secondary.add(max);
        
        head=new JLabel("Mean TSVs: ");
        head.setBounds(50,220,220,30);
        secondary.add(head);
        
        mean=new JTextField("0");        
        mean.setBounds(320, 220, 40, 20);
        mean.setEditable(false);
        secondary.add(mean);
        
        head=new JLabel("Mode TSV: ");
        head.setBounds(50,270,220,30);
        secondary.add(head);
        
        mode=new JTextField("0");        
        mode.setBounds(320, 270, 40, 20);
        mode.setEditable(false);
        secondary.add(mode);
        
        head=new JLabel("Number of Stimulation: ");
        head.setBounds(50,300,220,30);
        secondary.add(head);
        
        tsv=new JTextField("0");        
        tsv.setBounds(320, 300, 40, 20);
        tsv.setEditable(false);
        secondary.add(tsv);
        
        head=new JLabel("First Quartile Range: ");
        head.setBounds(50,350,220,30);
        secondary.add(head);
        
        quar=new JTextField("0-0");        
        quar.setBounds(320, 350, 80, 30);
        quar.setEditable(false);
        secondary.add(quar);
        
    }
    public void actionPerformed(ActionEvent e){
        //System.out.println("EVENT");
        if(e.getSource()==reset){
            while(defaultModel.getRowCount() > 0)
            {
                defaultModel.removeRow(0);
                fileStimulations.setText("0");
            }
            int layer=numberOfLayers.getItemAt(numberOfLayers.getSelectedIndex());
            int dev=threshold.getItemAt(threshold.getSelectedIndex());
            proj.fm.changeDirectory(layer, dev);
            proj.fm.deleteDirectory();
            error.setText("");
            statistics.setEnabled(false);
            graph.setEnabled(false);
            reset.setEnabled(false);
            //System.out.println("DELETED");
        }
        else if(e.getSource()==exec){
            String S=no_of_stimulation.getText();
            try{
                int numberOfExecution=Integer.parseInt(S);
                layer=numberOfLayers.getItemAt(numberOfLayers.getSelectedIndex());
                thres=threshold.getItemAt(threshold.getSelectedIndex());
                note.setText("For "+layer+" Layers with Deviation Limit "+thres+" %");
                if(numberOfExecution>=0 && numberOfExecution<=1000){
                    while(defaultModel.getRowCount() > 0)
                    {
                        defaultModel.removeRow(0);
                        fileStimulations.setText("0");
                    }
                    error.setText("");
                    proj.execution(numberOfExecution,layer,thres);
                    int unsuccessful=proj.unsuccessfulStimuation;
                    if(unsuccessful!=0){
                        error.setText("Failed Stimulations: "+unsuccessful);
                   }
                    displayTable();
                }
                else{
                    error.setText("Enter Between 1 and 1000");
                }
            }
            catch(NumberFormatException ed){
                error.setText("Invalid Entry!! Enter Number");
            }
            catch(Exception ed){
                System.out.println(ed);
            } 
            boolean flag=false;
            if(!fileStimulations.getText().equals("0")){
                flag=true;
            }
            statistics.setEnabled(flag);
            graph.setEnabled(flag);
            reset.setEnabled(flag);
        }
        else if(e.getSource()==statistics){
            if(compute()){
                secondary.setVisible(true);
                main.setVisible(false);
            }
        }
        else if(e.getSource()==back){
            main.setVisible(true);
            secondary.setVisible(false);
            
        }
        else if(e.getSource()==selectFile){
            main.dispose();
            secondary.dispose();
            new SelectFile();
        }
        else if(e.getSource()==graph){
            //plotConvergenceGraph(layer,thres);
            //plotClusteringGraph(layer, thres);
            //plotCoverGraph(layer, thres);
        }
    }
    public void displayTable(){
        try{
            File file=new File(proj.st.directory+"Report.txt");
            //System.out.println("DISPLAY TABLE");
            if(file.exists()){
                FileInputStream fis=new FileInputStream(proj.st.directory+"Report.txt");
                InputReader in=new InputReader(fis);
                in.readString();
                in.readString();
                in.readString();
                in.readString();
                int temp=in.nextInt();
                fileStimulations.setText(""+temp);
                int num=0;
                while(num<temp){
                    int tsv=in.nextInt();
                    int stimulation=in.nextInt();
                    String area_inc=in.readString();
                    String area=in.readString();
                    String power=in.readString();
                    in.readString();
                    num+=stimulation;
                    defaultModel.addRow(new Object[]{tsv,stimulation,area_inc,area,power});
                }
                in=null;
                fis.close();
                file=null;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public boolean compute(){
        num.setText("0");
        min.setText("");
        max.setText("");
        mean.setText("");
        mode.setText("");
        quar.setText("");
        tsv.setText("");
        try{
            File file=new File(proj.st.directory+"Report.txt");
            //System.out.println("DISPLAY TABLE");
            if(file.exists()){
                FileInputStream fis=new FileInputStream(proj.st.directory+"Report.txt");
                InputReader in=new InputReader(fis);
                in.readString();
                in.readString();
                in.readString();
                in.readString();
                int temp=in.nextInt();
                //System.out.println(temp);
                num.setText(""+temp);
                if(temp==0){
                    min.setText("");
                    max.setText("");
                    mean.setText("");
                    mode.setText("");
                    quar.setText("");
                    tsv.setText("");
                    return false;
                }
                int num=0;
                int cmin=Integer.MAX_VALUE,cmax=0,csum=0,cmode=0,cquar=0,ctsv=0;
                while(num<temp){
                    int tsv=in.nextInt();
                    int stimulation=in.nextInt();
                    if(tsv>cmax){
                        cmax=tsv;
                    }
                    if(tsv<cmin){
                        cmin=tsv;
                    }
                    csum+=(tsv*stimulation);
                    if(stimulation>cmode){
                        cmode=stimulation;
                        ctsv=tsv;
                    }
                    if(num<(temp/4)){
                        cquar=tsv;
                    }
                    String area=in.readString();
                    String power=in.readString();
                    String per=in.readString();
                    
                    num+=stimulation;
                }
                in=null;
                fis.close();
                file=null;
                min.setText(""+cmin);
                max.setText(""+cmax);
                int cmean=(int)((double)csum/(double)temp);
                mean.setText(""+cmean);
                tsv.setText(""+cmode);
                quar.setText(""+cmin+" - "+cquar);
                mode.setText(""+ctsv);
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return false;
        //compute();
    }
    public void plotConvergenceGraph(int layers,int deviation){
        try{
            File file=new File(st.directory+"Points.txt");
            //System.out.println("DISPLAY TABLE");
            if(file.exists()){
                FileInputStream fis=new FileInputStream(st.directory+"Points.txt");
                InputReader in=new InputReader(fis);
                in.readString();
                in.readString();
                in.readString();
                in.readString();
                int temp=in.nextInt();                
                List<Double> area= new ArrayList<>();
                List<Double> power = new ArrayList<>();
                List<Double> tsv = new ArrayList<>();
                List<Double> stimulation=new ArrayList<>();
                double min=Double.MAX_VALUE;
                for(int i=0;i<temp;i++){
                    double tsv1=Double.parseDouble(in.readString());
                    double area1=Double.parseDouble(in.readString());
                    double power1=Double.parseDouble(in.readString());
                    if(min>=tsv1){
                        stimulation.add((double)(i+1));
                        area.add(area1);
                        tsv.add(tsv1);
                        power.add(power1);
                        min=tsv1;
                    }
                }
                tsv.add(min);
                stimulation.add((double)(temp));
                in=null;
                fis.close();
                file=null;
                JFrame frame = new JFrame("DrawGraph");
                frame.setTitle("CONVERGENCE TEST FOR "+layers+" LAYERS WITH "+deviation+" % DEVIATION LIMIT ");
                Converge chart = new Converge(stimulation,tsv,st.directory);
                frame.add(chart);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.addComponentListener(new ComponentAdapter() {    
                    public void componentResized(ComponentEvent e)
                    {
                        chart.setSize(frame.getWidth(),frame.getHeight()-20);
                        chart.repaint();
                    }
                });
                frame.setVisible(true);
                chart.getSnapShot("CONVERGENCE");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void plotClusteringGraph(int layers,int deviation){
        try{
            File file=new File(st.directory+"Report.txt");
            //System.out.println("DISPLAY TABLE");
            if(file.exists()){
                FileInputStream fis=new FileInputStream(st.directory+"Report.txt");
                InputReader in=new InputReader(fis);
                in.readString();
                in.readString();
                in.readString();
                in.readString();
                int temp=in.nextInt();                
                List<Double> area= new ArrayList<>();
                List<Double> power = new ArrayList<>();
                List<Double> tsv = new ArrayList<>();
                List<Double> stimulation = new ArrayList<>();
                int num=0;
                while(num<temp){
                    double tsv1=Double.parseDouble(in.readString());
                    double stimulation1=Double.parseDouble(in.readString());
                    num+=stimulation1;
                    double area1=Double.parseDouble(in.readString());
                    double power1=Double.parseDouble(in.readString());
                    in.readString();
                    stimulation.add(stimulation1);
                    area.add(area1);
                    tsv.add(tsv1);
                    power.add(power1);
                }
                in=null;
                fis.close();
                file=null;
                
                JFrame frame = new JFrame("DrawGraph");
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("CLUSTERING FOR "+layers+" LAYERS WITH "+deviation+" % DEVIATION LIMIT ");
                Cluster chart = new Cluster(power,area,st.directory);
                //panel.setBounds(0,0, frame.getWidth(),frame.getHeight());
                frame.add(chart);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.addComponentListener(new ComponentAdapter() {    
                    public void componentResized(ComponentEvent e)
                    {
                        chart.setSize(frame.getWidth(),frame.getHeight()-20);
                        chart.repaint();
                    }
                });
                frame.setVisible(true);
                chart.getSnapShot("CLUSTER");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void plotCoverGraph(int layers,int deviation){
        try{
            
            File file=new File(st.directory+"Points.txt");
            //System.out.println("DISPLAY TABLE");
            List<Integer> tsv=null;
            if(file.exists()){
                FileInputStream fis=new FileInputStream(st.directory+"Points.txt");
                InputReader in=new InputReader(fis);
                in.readString();
                in.readString();
                in.readString();
                in.readString();
                int temp=in.nextInt(); 
                tsv = new ArrayList<>();
                for(int i=0;i<temp;i++){
                    int tsv1=in.nextInt();
                    double area1=Double.parseDouble(in.readString());
                    double power1=Double.parseDouble(in.readString());
                    tsv.add(tsv1);
                }
                in=null;
                fis.close();
                file=null;
            }
            file=new File(st.directory+"Report.txt");
            //System.out.println("DISPLAY TABLE");
            Double minx=Double.MAX_VALUE;
            Double maxx=Double.MIN_VALUE;
            Double miny=Double.MAX_VALUE;
            Double maxy=Double.MIN_VALUE;
            if(file.exists()){
                FileInputStream fis=new FileInputStream(st.directory+"Report.txt");
                InputReader in=new InputReader(fis);
                in.readString();
                in.readString();
                in.readString();
                in.readString();
                int temp=in.nextInt();   
                int num=0;
                while(num<temp){
                    double tsv1=Double.parseDouble(in.readString());
                    double stimulation1=Double.parseDouble(in.readString());
                    num+=stimulation1;
                    double area1=Double.parseDouble(in.readString());
                    double power1=Double.parseDouble(in.readString());
                    in.readString();
                    if(minx>tsv1){
                        minx=tsv1;
                    }
                    if(maxx<tsv1){
                        maxx=tsv1;
                    }
                    if(miny>stimulation1){
                        miny=stimulation1;
                    }
                    if(maxy<stimulation1){
                        maxy=stimulation1;
                    }
                }
                in=null;
                fis.close();
                file=null;
                
                JFrame frame = new JFrame("DrawGraph");
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("COVER POINTS FOR "+layers+" LAYERS WITH "+deviation+" % DEVIATION LIMIT ");
                Cover envelope = new Cover(tsv,st.directory);
                envelope.setMaxY(maxy);
                envelope.setMaxX(maxx);
                envelope.setMinY(0);
                envelope.setMinX(minx);
                //panel.setBounds(0,0, frame.getWidth(),frame.getHeight());
                frame.add(envelope);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.addComponentListener(new ComponentAdapter() {    
                    public void componentResized(ComponentEvent e)
                    {
                        envelope.setSize(frame.getWidth(),frame.getHeight()-20);
                        envelope.repaint();
                    }
                });
                frame.setVisible(true);
                envelope.getSnapShot("Envelope");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
