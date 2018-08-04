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
import java.util.List;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
class Cover extends JPanel {
   int PREF_W = 800;
   int PREF_H = 650;
   int BORDER_GAP = 30;
   Color GRAPH_POINT_COLOR;
   Color GRAPH_STRING_COLOR;
   Stroke GRAPH_STROKE = new BasicStroke(3f);
   int GRAPH_POINT_WIDTH;
   List<Integer> x;
   String location;
   double MAX_X,MAX_Y,MIN_X,MIN_Y;
   double xScale,yScale;
   int DIV;
   public Cover() {
       GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
       GRAPH_POINT_WIDTH = 6;
       GRAPH_STRING_COLOR = Color.BLUE;
   }
    public Cover(List<Integer> x,String location){
        this.x=x;
        GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
        GRAPH_POINT_WIDTH = 6;
        DIV=100;
        //int w=this.getWidth();
        this.location=location;
    }
    public void setMinX(double n){
        MIN_X=n;
    }
    public void setMinY(double n){
        MIN_Y=n;
    }
    public void setMaxX(double n){
        MAX_X=n;
    }
    public void setMaxY(double n){
        MAX_Y=n;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //System.out.println(panel.getWidth()+" "+panel.getHeight());

        xScale = ((double) this.getWidth() - 2 * BORDER_GAP) / (MAX_X - MIN_X );
        yScale = ((double) this.getHeight() - 2 * BORDER_GAP) / (MAX_Y -MIN_Y );

        //System.out.println(xScale+" "+yScale);
        Color c[]={Color.BLUE,Color.CYAN,Color.GRAY,Color.GREEN,Color.MAGENTA,Color.ORANGE};

        Stroke oldStroke = g2.getStroke();
        g2.setStroke(oldStroke);      
        g2.setColor(GRAPH_POINT_COLOR);
        TreeMap<Integer,Integer> tm=new TreeMap<>();
        int n=(int)(Math.ceil(x.size()/DIV));
        for(int i=0;i<=n;i++){
            g2.setColor(c[i%c.length]);
            List<Point> graphPoints=createPoints(tm, x, i*DIV, i*DIV+DIV);
            for (int j = 0; j < graphPoints.size(); j++) {
                int x = graphPoints.get(j).x - GRAPH_POINT_WIDTH / 2;
                int y = graphPoints.get(j).y - GRAPH_POINT_WIDTH / 2;
                int ovalW = GRAPH_POINT_WIDTH;
                int ovalH = GRAPH_POINT_WIDTH;
                g2.fillOval(x, y, ovalW, ovalH);
                if(j>0){
                   g2.drawLine(graphPoints.get(j-1).x, graphPoints.get(j-1).y, graphPoints.get(j).x, graphPoints.get(j).y);
                }
            }
            graphPoints.clear();
        }
                
        
    }
    public List<Point> constructPoints(List<Integer> x,List<Integer> y){
        List<Point> graphPoints = new ArrayList<Point>();
        int k=0;
        int y2;
        for (int i = (int)MIN_X; i <= (int)MAX_X; i++) {
            y2=0;
            if(k<x.size() && x.get(k)== i){
                y2=y.get(k);
                k++;
            }
            int x1 = (int) ((i - MIN_X) * xScale + BORDER_GAP);
            int y1 = (int) ((MAX_Y - MIN_Y - (y2 - MIN_Y) ) * yScale + BORDER_GAP);
            //System.out.println(x1+" "+y1);
            graphPoints.add(new Point(x1, y1));

        }
        x.clear();
        y.clear();
        return graphPoints;
    }
    public List<Point> createPoints(TreeMap<Integer,Integer> tm,List<Integer> x,int start,int end){        
        for(int i=start;i<end && i<x.size();i++){
            Integer m=tm.get(x.get(i));
            if(m==null){
                tm.put(x.get(i),1);
            }
            else{
                tm.put(x.get(i),m+1);
            }
        }
        return constructPoints(new ArrayList<Integer>(tm.keySet()), new ArrayList<Integer>(tm.values()));
    }
    
    public Dimension getPreferredSize() {
       return new Dimension(PREF_W, PREF_H);
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
}
