/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import java.net.*;
import java.io.*;
import Messages.Packet;
import Forms.MainForm;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Mohammed Muayad
 */
public class ReceiverThread extends Thread {
    private final Socket clientSocket;
    private ObjectInputStream inStream;
    private Packet rcvPkt;
    private Packet sendPkt;
    private String clientID;
    private String sessionID;
    private boolean mainThread;
    public static int count;

    public ReceiverThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.mainThread=true;
    }

    @Override
    public void run() {
        System.out.println("Receiving Thread Started");
        count++;
        try {
            while (true){
            rcvPkt = new Packet(); // create new packet
            inStream = new ObjectInputStream(clientSocket.getInputStream());// new input
            rcvPkt=(Packet) inStream.readObject(); // listen to the socket for incoming packets 
            sessionID= rcvPkt.getSessionID();
            clientID=rcvPkt.getClientID();

            new ServerCore(rcvPkt,clientSocket).start();// send the packet to new thread to be processed 
            
            if(rcvPkt.getType() != 1 && rcvPkt.getType() != 3 && rcvPkt.getType() != 9)
                mainThread=false;
            }//while
            
        }catch (ClassNotFoundException | IOException ex) {
        //  Logger.getLogger(ReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
        }  
        try {
           // inStream.close();
            clientSocket.close();
           System.out.println("Closing socket "+ clientSocket);
        } catch (IOException ex) {
            Logger.getLogger(ReceiverThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        count--;
        MainForm.logText.append("Active thread:"+count +" \n");
        if(mainThread==true){
         
        HashMap <String,String> removedList = new HashMap<>();
        removedList.put(clientID,Queue.onlineList.get(clientID)); // get the offline user name be removed to online List
            System.out.println("REMOVING ONLINE");
        Queue.removeSessionSocket(clientID+"-"+sessionID); 
        Queue.removeMainSocket(clientID);
        Queue.removeOnline(clientID);
        Queue.removeFileSocketList(clientID+"-FILE");
        Queue.removePublikKeysList(clientID);
        
           //send update to all online users
        for(Map.Entry<String, Socket> mainSocketList : Queue.mainSocketList.entrySet()){
           if(!mainSocketList.getKey().equals(clientID)) { // if it is not the current user send update
               sendPkt=new Packet();
               sendPkt.setType((short)6);
               sendPkt.setOnlineList(removedList);
               sendPkt.setClientID(mainSocketList.getKey());
               sendPkt.setAddToList(false);
               MainForm.logText.append("Sent to "+mainSocketList.getKey()+" \n");
               new SenderThread(sendPkt).start();
              }
            }
        }else{
            Queue.removeSessionSocket(clientID+"-"+sessionID); 
        }
        
        System.out.println("Receive Thread Closed");
        
        
    }// run
    
}
