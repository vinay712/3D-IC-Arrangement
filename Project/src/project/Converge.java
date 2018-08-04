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
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
class Converge extends JPanel {
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
    public Converge() {
        GRAPH_POINT_COLOR = new Color(150, 150, 150, 180);
        GRAPH_POINT_WIDTH = 6;
        GRAPH_STRING_COLOR = Color.BLUE;
    }
    public Converge(List<Double> x,List<Double> y,String location){
        this.x=x;
        this.y=y;
        GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
        GRAPH_POINT_WIDTH = 6;
        GRAPH_STRING_COLOR = Color.BLUE;
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
            //System.out.println(x.get(i)+"\t"+y.get(i));
            int x1 = (int) ((x.get(i) - MIN_X) * xScale + BORDER_GAP);
            int y1 = (int) ((MAX_Y - MIN_Y - (y.get(i) - MIN_Y) ) * yScale + BORDER_GAP);
            graphPoints.add(new Point(x1, y1));

        }

        Stroke oldStroke = g2.getStroke();
        g2.setStroke(oldStroke);      
        g2.setColor(GRAPH_POINT_COLOR);
        int min=graphPoints.get(graphPoints.size()-1).y;
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
            int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;
            int ovalW = GRAPH_POINT_WIDTH;
            int ovalH = GRAPH_POINT_WIDTH;
            g2.fillOval(x, y, ovalW, ovalH);
            if(i>0){
                g2.drawLine(graphPoints.get(i-1).x, graphPoints.get(i-1).y, graphPoints.get(i).x, graphPoints.get(i).y);
            }
            if(graphPoints.get(i).y==min){
                g2.setColor(Color.BLACK);
            }
        }
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
}
