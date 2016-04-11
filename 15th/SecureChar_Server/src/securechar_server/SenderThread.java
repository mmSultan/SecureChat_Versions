/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import Forms.MainForm;
import java.io.*;
import java.net.*;
import Messages.Packet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mohammed Muayad
 */
public class SenderThread extends Thread {

    private Socket clientSocekt;
    private ObjectOutputStream outStream;
    private Packet sendPkt;

    public SenderThread(Packet sendPkt) {
        this.sendPkt = sendPkt;
    }
    
    
    @Override
    public void run()  {
        switch (sendPkt.getType()){
            
            case 1: // Authentication
                try{ 
                   clientSocekt=Queue.mainSocketList.get(sendPkt.getClientID()); // get socket from list
                   outStream= new ObjectOutputStream (clientSocekt.getOutputStream());
                   outStream.writeObject(sendPkt);// send 
                   MainForm.logText.append("AuthPkt Sent :"+sendPkt.getData1()+"\n");
                   if(sendPkt.getData1().equals("INVALID")){
                       Queue.removeMainSocket(sendPkt.getClientID());
                   }
                 
                }catch(IOException ex){
                   MainForm.logText.append("AuthPkt Sending Faild 1 \n");
                }
                break;
            case 2: // text pkt
                 try{
                 clientSocekt=Queue.sessionSocketList.get(sendPkt.getClientID()+"-"+sendPkt.getSessionID()); // get socket
                 outStream= new ObjectOutputStream (clientSocekt.getOutputStream());
                 outStream.writeObject(sendPkt);
                 MainForm.logText.append("text sent  \n");
                } catch (IOException ex) {
                Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
                 MainForm.logText.append("Sending faild  2 \n");
                }
            break;
                
            case 3:
            try {
                // newWindow or connection
                clientSocekt=Queue.mainSocketList.get(sendPkt.getClientID()); // get socket from list
                outStream= new ObjectOutputStream (clientSocekt.getOutputStream());
                outStream.writeObject(sendPkt);
                MainForm.logText.append("rply3 sent  \n");
                
            } catch (IOException ex) {
                Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
                 MainForm.logText.append("Sending faild  3\n");
            }
            break;
                
            case 5:// update Chat List
                try{
                 System.out.println("Closing 5: "+sendPkt.getClientID()+" type "+sendPkt.getType());
                clientSocekt=Queue.sessionSocketList.get(sendPkt.getClientID()+"-"+sendPkt.getSessionID()); // get socket
                outStream= new ObjectOutputStream (clientSocekt.getOutputStream());
                outStream.writeObject(sendPkt);
                MainForm.logText.append("pkt-5  sent  \n");
                } catch (IOException ex) {
                  System.out.println("Faild 5  -"+sendPkt.getSessionID());
                 Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
                 MainForm.logText.append("Sending faild  5 \n");
                }
                
                break;
                        
           case 6:
            try {
                // newWindow or connection
                clientSocekt=Queue.mainSocketList.get(sendPkt.getClientID()); // get socket from list
                outStream= new ObjectOutputStream (clientSocekt.getOutputStream());
                outStream.writeObject(sendPkt);
                MainForm.logText.append("pkt-6  sent  \n");
                
            } catch (IOException ex) {
                MainForm.logText.append("Sending faild  6\n");
                Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
                 
            }
            break;
            
             case 7: // text pkt
                 try{
                 clientSocekt=Queue.fileSocketList.get(sendPkt.getClientID()+"-FILE"); // get socket
                 outStream= new ObjectOutputStream (clientSocekt.getOutputStream());
                 outStream.writeObject(sendPkt);
                 MainForm.logText.append("7 sent  \n");
                } catch (IOException ex) {
                       MainForm.logText.append("Sending faild  7 \n");
                Logger.getLogger(SenderThread.class.getName()).log(Level.SEVERE, null, ex);
              
                }
            break;
            
             case 9:
                FileServices file = new FileServices();
                Packet tmpPkt;
                byte[] fileArray;
                FileInputStream inStream;
                File[] histList = file.getFile(sendPkt.getClientID());
                
                for (File histFile : histList) {
                    if (histFile.isFile()) {
                        try {
                            System.out.println("|| "+histFile.getName());
                            
                            clientSocekt=Queue.fileSocketList.get(sendPkt.getClientID()+"-FILE"); // get socket
                            
                            fileArray= new byte[(int)histFile.length()];
                            inStream = new FileInputStream(histFile);
                            inStream.read(fileArray);
                            
                            tmpPkt= new Packet();
                            tmpPkt.setType((short)9);
                            tmpPkt.setData1(histFile.getName());
                            tmpPkt.setClientID(sendPkt.getClientID());
                            tmpPkt.setFile(fileArray);
                            outStream= new ObjectOutputStream (clientSocekt.getOutputStream());
                            outStream.writeObject(tmpPkt);
                            MainForm.logText.append("9 sent  \n");
                            
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(ServerCore.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(ServerCore.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                 
                 break;

    }//switch   
       
    }//run
    
}
