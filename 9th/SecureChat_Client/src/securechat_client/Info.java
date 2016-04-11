/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechat_client;

import java.net.Socket;

/**
 *
 * @author Mohammed Muayad
 */
public class Info {
   private static final int serverPort=10000; 
   private static final String serverIP="192.168.0.16";
   private  static String clientID;
   private static Socket mainClientSocket;
   private static Socket fileClientSocket;

    public static Socket getFileClientSocket() {
        return fileClientSocket;
    }

    public static void setFileClientSocket(Socket fileClientSocket) {
        Info.fileClientSocket = fileClientSocket;
    }



   

    public static Socket getMainClientSocket() {
        return mainClientSocket;
    }

    public static void setMainClientSocket(Socket mainClientSocket) {
        Info.mainClientSocket = mainClientSocket;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static String getServerIP() {
        return serverIP;
    }

    public static String getClientID() {
        return clientID;
    }

    public static void setClientID(String clientID) {
        Info.clientID = clientID;
    }
    
   
}
