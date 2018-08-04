/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.*;

/**
 *
 * @author Vinay
 */
public class FileManipulation {
    String directory;
    String file;
    String outputDirectory;
    String dir;
    public FileManipulation(String directory,String file){
        this.directory=directory;
        this.file=file;             
        outputDirectory=directory+file+"\\";
        dir=outputDirectory;
    }    
    public void changeDirectory(int no_of_layers,int threshold){
        outputDirectory=dir+no_of_layers+"Layers\\"+threshold+"%\\";
        
    }
    public void write(int no_of_tsv){        
        createDirectory();
        //System.out.println("Directory Created");
        try{
            File oldFile=new File(directory+file+".txt");
            File newFile=new File(outputDirectory+file+"_"+no_of_tsv+".txt");
            //System.out.println(no_of_tsv+" TEST ");
            if(!newFile.exists())
            {
                newFile.createNewFile();
            }
            FileOutputStream out=new FileOutputStream(newFile,true);
            FileInputStream in=new FileInputStream(oldFile);

            byte[] buffer = new byte[1024];
            int length;
            while((length=in.read(buffer)) >0){
                //System.out.println(buffer);
                out.write(buffer, 0, length);

            }
            in.close();
            out.close();
            if(!oldFile.delete()){
                throw new IllegalArgumentException("File Deleted");
            }
            
        }
        catch(Exception e){
            System.out.println("UNABLE TO WRITE");
            throw new IllegalArgumentException("File Not Found");
        }
    }
    public void createDirectory(){
        try{
            File file = new File(outputDirectory);
            //System.out.println("file: "+file.getAbsolutePath());
            if (!file.exists()) {
                //System.out.println("Creating Directory");
                file.mkdirs();
                //System.out.println("Directory Created");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Failed to Create Directory");
            throw new IllegalArgumentException("Unable to create directory");
        }
    }
    public void deleteDirectory(){   
        File file=new File(outputDirectory);
        try{
            if(file.exists()){
                if(file.list().length==0){
                   file.delete();
                }
                else{
                    String files[] = file.list();
                    for (String temp : files) {
                        File fileDelete = new File(file,temp);
                        if(fileDelete.delete())
                        {
                            //System.out.println("File Deleted: "+fileDelete.getAbsolutePath());
                        }
                        else{
                            System.out.println("Unable to Delete");                            
                        }
                    }
                    if(file.list().length==0){
                        file.delete();
                    }
                }
                String temp=outputDirectory.substring(0,outputDirectory.length()-2);
                temp=temp.substring(0, temp.lastIndexOf("\\"));
                file=new File(temp);
                if(file.list().length==0){
                    file.delete();
                    temp=temp.substring(0,temp.length()-2);
                    temp=temp.substring(0, temp.lastIndexOf("\\"));
                    file=new File(temp);
                    if(file.list().length==0){
                        file.delete();
                    }
                }
            }
        }
       catch(Exception e){
           throw new IllegalArgumentException("Unable to delete directory");               
       } 
    }
    
}
