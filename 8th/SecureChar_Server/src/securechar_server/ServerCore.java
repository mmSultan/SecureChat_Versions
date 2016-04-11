/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import Forms.MainForm;
import Messages.Packet;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mohammed Muayad
 */
public class ServerCore extends Thread {
    private final Packet rcvPkt;
    private final Socket clientSocket;
    private Packet sendPkt;
    private Packet tempPkt;
    private String clientID;
    private static int SessionCouter;
    private String sessionID;
    private boolean mainThread;
    public static int count;
    private String newClientID;
    private FileServices file;

    public ServerCore(Packet rcvPkt, Socket clientSocket) {
        this.rcvPkt = rcvPkt;
        this.clientSocket = clientSocket;
    }
    
    
    
    
    
    @Override
    public void run(){
 
        clientID=rcvPkt.getClientID();
        sessionID=rcvPkt.getSessionID();
        
        switch(rcvPkt.getType()){
            case 1: // Authintication
                    sendPkt=new Packet();
                    sendPkt.setType((short)1);
                    sendPkt.setClientID(clientID);
                    MainForm.logText.append("Auth Rcvd \n");
                    if(rcvPkt.getData1().equals("a")){  // if user is true 
                        sendPkt.setData1("VALID");   
                        sendPkt.setOnlineList(Queue.onlineList); // add the whole online list
                        Queue.addMainSocket(clientID, clientSocket);
                        Queue.addOnline(clientID);
                        new SenderThread(sendPkt).start(); // send replay valid
                        
                        //send update OnlineList
                        HashMap <String,String> tempList = new HashMap<>();
                        tempList.put(clientID,Queue.onlineList.get(clientID)); // get the new user name added to online List
                        
                        //send update to all online users
                         for(Map.Entry<String, Socket> mainSocketList : Queue.mainSocketList.entrySet()){
                            if(!mainSocketList.getKey().equals(clientID)) { // if it is not the current user send update
                                sendPkt=new Packet();
                                sendPkt.setType((short)6);
                                sendPkt.setOnlineList(tempList);
                                sendPkt.setClientID(mainSocketList.getKey());
                                sendPkt.setAddToList(true);
                                MainForm.logText.append("Sent-6 to "+mainSocketList.getKey()+" \n");
                                new SenderThread(sendPkt).start();
                            }// if
                         }// for     
                    } else { // invalid user or pass
                        Queue.addMainSocket(clientID, clientSocket);
                        sendPkt.setData1("INVALID");
                        new SenderThread(sendPkt).start();
                    }
                    break;

            case 2: //DATA
                // send the text packet to all the users in the destinationList
                for(String destID : rcvPkt.getDestIDList()){
                    System.out.println("2- from "+rcvPkt.getClientID());
                    sendPkt= new Packet();
                    sendPkt.setType((short)2);
                    sendPkt.setData2(rcvPkt.getClientID());
                    sendPkt.setClientID(destID);
                    sendPkt.setSessionID(rcvPkt.getSessionID());
                    sendPkt.setData1(rcvPkt.getData1());
                    new SenderThread(sendPkt).start();
                }
                mainThread=false;
                break;

            case 3: //3-ReqSessionID
                MainForm.logText.append("Request SessionID Pkt received \n");
                SessionCouter++;
                sessionID=Integer.toString(SessionCouter);
                sendPkt = new Packet();
                sendPkt.setClientID(clientID);
                sendPkt.setType((short)3); //create New window Pkt
                sendPkt.setDestIDList(rcvPkt.getDestIDList()); // put the Dest User ID
                sendPkt.setSessionID(sessionID);

                new SenderThread(sendPkt).start(); // sendback sessionID with NewWindow Pkt to the user to open newWindow

                List <String> destIDList=rcvPkt.getDestIDList();
                sendPkt = new Packet();
                sendPkt.setClientID(destIDList.get(0));
                sendPkt.addDestID(clientID);
                sendPkt.setType((short)3);
                sendPkt.setSessionID(sessionID);
                new SenderThread(sendPkt).start(); // send to the other client. that the first one wants to chat with him to open new window in his app 

                break;
            case 4://4-newChat
                 newClientID=clientID+"-"+rcvPkt.getSessionID();
                 Queue.addSessionSocket((newClientID), clientSocket); // save socket with new ID
                 mainThread=false;
                break;
            case 5://5-UpdatechatList,
                sessionID=rcvPkt.getSessionID();

                 for(String destID : rcvPkt.getDestIDList()){
                     if(!destID.equals(rcvPkt.getData1())){ // send to all except the invited or removed user
                        sendPkt= new Packet();
                         System.out.println("5 sent to...."+rcvPkt.getData1()+" "+destID);
                        //sendPkt=rcvPkt;
                        sendPkt.setType((short)5);
                        sendPkt.setClientID(destID);
                        sendPkt.setData1(rcvPkt.getData1());
                        sendPkt.setSessionID(sessionID);
                        sendPkt.setAddToList(rcvPkt.isAddToList());
                        sendPkt.setData2(Queue.onlineList.get(clientID)); // person who sent the request
                        new SenderThread(sendPkt).start();
                     }// if
                 }//for
                 if(rcvPkt.isAddToList()){// if it is invitink pkt
                // send 3-pkt to invited user to open new chat window              
                  sendPkt = new Packet();
                  sendPkt.setType((short)3);
                  sendPkt.setClientID(rcvPkt.getData1());// get the ID of invited user
                  sendPkt.setSessionID(sessionID);
                  sendPkt.setDestIDList(rcvPkt.getDestIDList());// put the Dist List
                  sendPkt.addDestID(rcvPkt.getClientID()); // add the ID of the inviter user
                  sendPkt.removeDestId(rcvPkt.getData1());// remoce the ID of the invited user
                  new SenderThread(sendPkt).start();
                 }else { // it is remove pkt         
                  sendPkt = new Packet();
                  sendPkt.setType((short)5);
                  sendPkt.setClientID(rcvPkt.getData1());// get the ID of invited user
                  sendPkt.setSessionID(sessionID);
                  sendPkt.setData1(rcvPkt.getData1());//
                  sendPkt.setAddToList(false);// remove type
                  sendPkt.setData2(Queue.onlineList.get(clientID)); // the user who removed you from chat
                  System.out.println("Remove PKT sent to "+rcvPkt.getData1());
                  new SenderThread(sendPkt).start();
                  
                 }
                  mainThread=false;

                break;
            case 7:
              if(rcvPkt.isCreateFileSocket()){
                    Queue.addFileSocketList(clientID+"-FILE", clientSocket); 
              }else{
                file = new FileServices();
                file.saveFile(rcvPkt);
                System.out.println("File recvd");
                for(String destID : rcvPkt.getDestIDList()){
                    System.out.println("7- from "+rcvPkt.getClientID());
                    sendPkt= new Packet();
                    sendPkt.setClientID(destID);
                    sendPkt.setType((short)7);
                    sendPkt.setData2(rcvPkt.getClientID());
                    sendPkt.setSessionID(rcvPkt.getSessionID());
                    sendPkt.setData1(rcvPkt.getData1());
                    sendPkt.setFile(rcvPkt.getFile());
                    new SenderThread(sendPkt).start();
                    
                }
              }
                mainThread=false;
                break;
            case 8://receiving chat history from client
                 System.out.println("8 Rcvd from "+rcvPkt.getClientID() );
                 file = new FileServices();
                 file.saveChat(rcvPkt);
                 mainThread=false;
            break;
            case 9:
                System.out.println("9- rcvd");
                if(rcvPkt.isCreateFileSocket()){
                   // Queue.addFileSocketList(clientID+"-FILE", clientSocket); 
                }else{
                    
                }
                break;
            
        } // switch
    }// run
    
}//class
