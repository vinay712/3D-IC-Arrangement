/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Vinay
 */
public class SelectFile implements ActionListener{
    JFrame main;
    String name[]={"ami33","ami49","n100","n200","n300"};
    JButton run;
    JComboBox<String> list;
    public SelectFile(){
        init();
        //System.out.println("CONSTRUCTOR");
    }
    public void init(){
        
        main=new JFrame("Select File");
        main.setSize(300, 250);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        JPanel top=new JPanel();
        top.setLayout(null);
        
        top.setBounds(10, 10, 280, 200);
        top.setBackground(Color.LIGHT_GRAY);
        
        JLabel lab=new JLabel("Select File :");
        lab.setBounds(50, 50, 100, 25);        
        top.add(lab);
        
        list=new JComboBox<>(name);        
        list.setBounds(120, 50, 100, 25);
        list.setSelectedIndex(0);
        top.add(list);
        
        run=new JButton("RUN");        
        run.setBounds(100,120,70,30);        
        top.add(run);        
        run.addActionListener(this);
        
        top.setVisible(true);
        
        main.add(top);
        
        //main.pack();
        main.setLocationRelativeTo(null);
        
        main.setVisible(true);
        main.setResizable(false);
    }
    public void actionPerformed(ActionEvent a){
        if(a.getSource()==run){
            String name=list.getItemAt(list.getSelectedIndex());
            main.dispose();
            new Project().init(name);
        }
    }
    
}
