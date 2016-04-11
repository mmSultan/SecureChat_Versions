/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;
import java.net.*;
import java.util.HashMap;
import Messages.Packet;
import Forms.MainForm;

/**
 *
 * @author Mohammed Muayad
 */

public class Queue {
   public static HashMap <String, Socket> mainSocketList = new HashMap<>();
   public static HashMap <String, Socket> sessionSocketList = new HashMap<>();
   public static HashMap <String, Socket> fileSocketList = new HashMap<>();
   public static HashMap <String,String> onlineList = new HashMap<>();
   public static HashMap <String,Packet> packetBuffer = new HashMap<>();
   private static DbServices DB = new DbServices();
   private static UserInfo USER = new UserInfo();
   
   public static void addOnline (String clientID){
       // we need to fetch name from database
   USER=DB.SearchId(clientID);
   onlineList.put(clientID, USER.getFullName());
   MainForm.onlineList.setListData(onlineList.keySet().toArray());
   System.out.println("ID "+clientID+", NAME:"+USER.getFullName()+"Is Online");
   
//   for (String name: onlineList.keySet()){
//            String key =name.toString();
//            String value = onlineList.get(name).toString();  
//            System.out.println(key + " " + value);  
//} 
   }
   public static void addMainSocket (String clientID,Socket sock){
   mainSocketList.put(clientID, sock);
   MainForm.socketList.setListData(mainSocketList.entrySet().toArray());
   }
   public static void addSessionSocket (String clientID,Socket sock){
   sessionSocketList.put(clientID, sock);
   MainForm.sessionList.setListData(sessionSocketList.entrySet().toArray());
   }
   public static void addFileSocketList (String clientID,Socket sock){
   fileSocketList.put(clientID, sock);
   MainForm.fileSocketList.setListData(fileSocketList.entrySet().toArray());
   }
   
   public static void removeOnline(String clientID){
       onlineList.remove(clientID);
       MainForm.onlineList.setListData(onlineList.keySet().toArray());
       System.out.println("Client "+clientID+" Is Offlien");
   }
    public static void removeMainSocket(String clientID){
       mainSocketList.remove(clientID);
       MainForm.socketList.setListData(mainSocketList.entrySet().toArray());
        System.out.println("REMOVING Msocket"+clientID);

   }
    public static void removeSessionSocket(String clientID){
       sessionSocketList.remove(clientID);
       MainForm.sessionList.setListData(sessionSocketList.entrySet().toArray());
   }  
    
    public static void removeFileSocketList(String clientID){
     fileSocketList.remove(clientID);
     MainForm.fileSocketList.setListData(fileSocketList.entrySet().toArray());
   }   
    
   
   
}
