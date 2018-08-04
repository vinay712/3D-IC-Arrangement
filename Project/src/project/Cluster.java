/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author Vinay
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vinay
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
class Cluster extends JPanel {
    int PREF_W = 800;
    int PREF_H = 650;
    int BORDER_GAP = 30;
    Color GRAPH_POINT_COLOR;
    Color GRAPH_STRING_COLOR;
    Stroke GRAPH_STROKE = new BasicStroke(3f);
    int GRAPH_POINT_WIDTH;
    List<Double> x;
    List<Double> y;
    String location;
    double MAX_X,MAX_Y,MIN_X,MIN_Y;
    public Cluster() {
        GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
        GRAPH_POINT_WIDTH = 6;
        GRAPH_STRING_COLOR = Color.BLUE;
    }
    public Cluster(List<Double> x,List<Double> y,String location){
        this.x=x;
        this.y=y;
        GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
        GRAPH_POINT_WIDTH = 6;
        GRAPH_STRING_COLOR = Color.BLUE;
        //int w=this.getWidth();
        this.location=location;

        MAX_X=findMax(x);
        MAX_Y=findMax(y);

        MIN_X=findMin(x);
        MIN_Y=findMin(y);
      
   }

   protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) this.getWidth() - 2 * BORDER_GAP) / (MAX_X - MIN_X );
        double yScale = ((double) this.getHeight() - 2 * BORDER_GAP) / (MAX_Y -MIN_Y );


        List<Point> graphPoints = new ArrayList<Point>();
        for (int i = 0; i < x.size(); i++) {
            int x1 = (int) ((x.get(i) - MIN_X) * xScale + BORDER_GAP);
            int y1 = (int) ((MAX_Y - MIN_Y - (y.get(i) - MIN_Y) ) * yScale + BORDER_GAP);
            graphPoints.add(new Point(x1, y1));
        }

        Stroke oldStroke = g2.getStroke();
        int x,y,h;
        g2.setStroke(oldStroke);   
        g2.setColor(Color.GREEN);
        for (int i = 0; i < 5; i++) {
            h=(6-i)*3;
            x = graphPoints.get(i).x - h / 2;
            y = graphPoints.get(i).y - h / 2;
            g2.fillOval(x, y, h, h);
        }
        double w=30;
        Point p=findCluster(graphPoints);
        g2.setColor(GRAPH_POINT_COLOR);
        for (int i = 0; i < graphPoints.size(); i++) {
            x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.fillOval(x, y, ovalW, ovalH);
        }
        g2.setColor(Color.BLUE);
        w=w+GRAPH_POINT_WIDTH;
        for (int i = 0; i < graphPoints.size(); i++) {
            Point p1 = graphPoints.get(i);
            int d=distance(p, p1);
            if(d<=w){
                x = p1.x - GRAPH_POINT_WIDTH / 2;
                y = p1.y - GRAPH_POINT_WIDTH / 2;
                int ovalW = GRAPH_POINT_WIDTH;
                int ovalH = GRAPH_POINT_WIDTH;
                g2.fillOval(x, y, ovalW, ovalH);
            }
        }
    }
    public Point findCluster(List<Point> graphPoints){
        int meanx=0,meany=0;
        Iterator<Point> it=graphPoints.iterator();
        while(it.hasNext()){
            Point p=it.next();
            meanx+=p.x;
            meany+=p.y;
        }
        meanx/=graphPoints.size();
        meany/=graphPoints.size();
        Point p=new Point(meanx, meany);
        Quardrant quad[]=new Quardrant[4];
        for(int i=0;i<quad.length;i++){
            quad[i]=new Quardrant();
        }        
        it=graphPoints.iterator();
        while(it.hasNext()){
            Point p1=it.next();
            if(p1.x>meanx && p1.y>meany){
                quad[0].add(p1);
            }
            else if(p1.x<meanx && p1.y>meany){
                quad[1].add(p1);
            }
            else if(p1.x<meanx && p1.y<meany){
                quad[2].add(p1);
            }
            else if(p1.x>meanx && p1.y<meany){
                quad[3].add(p1);
            }
        }     
        int min=Integer.MAX_VALUE;
        int index=0;
        for(int i=0;i<quad.length;i++){
            quad[i].calculate();
            if(quad[i].size>graphPoints.size()/4){
                int t=quad[i].deviation;
                if(t<min){
                    min=t;
                    index=i;
                    p=quad[i].mean;
                }
            }
        }  
        return p;
    }
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }
    public double findMax(List<Double> n){
        double max=n.get(0);
        for(int i=1;i<n.size();i++){
            if(n.get(i)>max){
                max=n.get(i);
            }
        }       
        return max;       
    } 
    public double findMin(List<Double> n){
        double min=n.get(0);
        for(int i=1;i<n.size();i++){
            if(n.get(i)<min){
                min=n.get(i);
            }
        }       
        return min;       
    }
    void getSnapShot(String name){  
         BufferedImage bufImg = new BufferedImage((int)(this.getSize().width), (int)(this.getSize().height),BufferedImage.TYPE_INT_RGB); 
         this.paint(bufImg.createGraphics()); 
         File imageFile = new File(location+name+"_GRAPH.JPEG"); 
         this.paint(bufImg.getGraphics());
         try{  
             ImageIO.write(bufImg, "jpeg", imageFile);  
         }
         catch(Exception ex){ 
             System.out.println(ex.getMessage());
         }  
    } 
    public int distance(Point p1,Point p2){
        return (int)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
    }
}

class Quardrant{
    Point mean;
    int deviation;
    int size;
    ArrayList<Point> list;
    public Quardrant(){
        list=new ArrayList<>();
        mean=new Point();
        size=0;
    }
    public void add(Point p){
        list.add(p);
        size++;
    }
    public void calculate(){
        int sumx=0,sumy=0;
        Iterator<Point> it=list.iterator();
        while(it.hasNext()){
            Point p=it.next();
            sumx+=p.x;
            sumy+=p.y;
        }
        mean.x=sumx/list.size();
        mean.y=sumy/list.size();
        it=list.iterator();
        while(it.hasNext()){
            Point p=it.next();
            int d=distance(mean, p);
            deviation+=d*d;
        }
        deviation=(int)Math.sqrt(deviation)/list.size();
    }
    public int distance(Point p1,Point p2){
        return (int)Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
    }
}
