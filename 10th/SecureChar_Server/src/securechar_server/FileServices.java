/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import Forms.MainForm;
import Messages.Packet;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mohammed Muayad
 */
public class FileServices {
    private String ClientID;
    private BufferedReader br;
    private BufferedWriter bw;
    private FileWriter fileWriter;
    private FileReader fileReader;
    private BufferedReader bReader;
    private  String Path;
    

    public FileServices() {
    }
    
    public void saveChat(Packet pkt){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
            Date date = new Date();
 
            String fileDirectory="c:\\Secure Chat\\History\\"+pkt.getClientID();
            String fileName=fileDirectory+"\\"+dateFormat.format(date)+".txt";
            File file = new File(fileDirectory);
            
            //check if the directory is not exist;
            if(!file.exists()){
                file.mkdirs();
                MainForm.logText.append(fileDirectory+" "+"Created \n");
            }
            //writer the file
            fileWriter=new FileWriter(fileName);
            fileWriter.write(pkt.getData2());
            fileWriter.close();
            MainForm.logText.append(fileName+" History saved \n");
        } catch (IOException ex) {
            Logger.getLogger(FileServices.class.getName()).log(Level.SEVERE, null, ex);
        }
            

    }
    public void getFile(String path){

    }
    public boolean getInfo() throws IOException{
       String line;
       String fileDirectory="c:\\Secure Chat\\";
       String fileName=fileDirectory+"info.txt";
       File file = new File(fileName);
       File dir = new File(fileDirectory);
       boolean chk=false;

       //check if the directory is not exist;
       if(!file.exists()){
           JOptionPane.showMessageDialog(null, "info.txt not found. \nPlease create the file and include server port. \n"
                   + "File path: "+fileName);
           if(!dir.exists())
              dir.mkdirs();    
           return false;
       }
            fileReader=new FileReader(fileName);
            bReader = new BufferedReader(fileReader);
            
            while((line=bReader.readLine()) !=null){
                System.out.println(line);
                    if(line.matches("\\d+")){
                        Info.setServerPort(Integer.parseInt(line));
                        chk=true;
                    }
            }
               bReader.close();
               if(chk==false){
                    JOptionPane.showMessageDialog(null,"Wrong Server Port Format. \n"
                            + "Please check "+fileName);
                    System.exit(0);
               }

       return true;
    }
    
    public void saveFile(Packet pkt){
        FileOutputStream fos=null;
        try {
            String fileDirectory="c:\\Secure Chat\\Received Files\\"+pkt.getClientID();
            String fileName=fileDirectory+"\\"+pkt.getData1();
            File file = new File(fileDirectory);
           // check if the directory is not exist;
            if(!file.exists()){
                file.mkdirs();
                MainForm.logText.append(fileDirectory+" "+"Created \n");
            }     
            
            fos = new FileOutputStream (fileName);
            fos.write(pkt.getFile());
            fos.close();
        } catch (FileNotFoundException  ex) {
            Logger.getLogger(FileServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileServices.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
