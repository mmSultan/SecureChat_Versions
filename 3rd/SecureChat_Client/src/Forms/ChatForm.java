
package Forms;

import Messages.Packet;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.text.*;
import securechat_client.Info;
import securechat_client.ReceiverThread;
import securechat_client.SenderThread;

/**
 *
 * @author Mohammed Muayad
 */
public class ChatForm extends javax.swing.JFrame {

    private Socket clientSocket;
    private Packet sendPkt;
    private String clientID;
    private String sessionID;
    private ReceiverThread r1;
    private List <String> destIDList= new ArrayList<>();
    private List <String> tempChatList;

    public void setDestIDList(List<String> destIDList) {
        this.destIDList = destIDList;
       
    }
     public void removeDestId(String ID){// remoce a certain ID from the list
        for(int i=0 ;i<destIDList.size();i++){
            if(destIDList.get(i).equals(ID)){
                destIDList.remove(i);
                return;
            }
        }
    }

    private void updateChatList(){
        tempChatList= new ArrayList<>();
         for (String id :destIDList){
            tempChatList.add(MainForm.tempOnlineList.get(id));
        }
         chatList.setListData(tempChatList.toArray());
    }

    public void setSessionID(String SessionID) {
        this.sessionID = SessionID;
        
    }
    
    
    public static void updateOnlineList(){
        try{
        onlineList.setModel(MainForm.onlineList.getModel());
        }
        catch(Throwable ex){}
    }
    
    
    // Function to add text with diffrent colors and styles to chatText2
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
    
    // locla receiving thread to listen for incomming pakcets.
    private Thread localReceiver = new Thread(new Runnable() { // Receiver thread to get packet related to current chat
    @Override
    public void run() {
     ObjectInputStream inStream;
     Packet rcvPkt;
        System.out.println(Info.getClientID()+" Local Thread Sarted");
        try {
            while(true){
            inStream= new ObjectInputStream(clientSocket.getInputStream());
            rcvPkt=(Packet) inStream.readObject();
            
            switch (rcvPkt.getType()){
                
                case 2: //DATA packet
                    appendToPane(chatText2,MainForm.tempOnlineList.get(rcvPkt.getData2())+":"+
                            rcvPkt.getData1()+"\n",Color.black);
                    
                    break;
                case 5:
                     String addedOrRemovedUser=rcvPkt.getData1();
                     String inviterOrRemover=rcvPkt.getData2();
                    if(rcvPkt.isAddToList()){ // true means add to list
                     destIDList.add(rcvPkt.getData1());// add new user
                     updateChatList();
                     appendToPane(chatText2,MainForm.tempOnlineList.get(addedOrRemovedUser)+" has been invited by..."+inviterOrRemover+"\n",Color.blue);
                     System.out.println(Info.getClientID()+" Local rcvd pkt-5");
                    }else {//false means remove from list
                        if(!rcvPkt.getData1().equals(Info.getClientID())){// removed user is not me
                            removeDestId(rcvPkt.getData1()); // remove it  from chat list
                            updateChatList();// update it       
                            appendToPane(chatText2,MainForm.tempOnlineList.get(addedOrRemovedUser)+" has been removed by..."+inviterOrRemover+"\n",Color.red);

                        }else{ // removed user is  me
                            destIDList.clear();
                            updateChatList();
                            appendToPane(chatText2,"You have been removed from this session by..."+rcvPkt.getData2()+"\n",Color.red);
                        }
                    }
                    break;
                
            }//switch

            } // while
        } catch (Throwable  ex ) {
           // Logger.getLogger(ChatForm.class.getName()).log(Level.SEVERE, null, ex);
        }
     
         
    }//run
    });  
           
    
    public void visible(boolean status){
       
        try {
            this.setTitle(Info.getClientID());
            clientSocket=new Socket(Info.getServerIP(),Info.getServerPort()); // start NEW SOCKET connection
            sendPkt=new Packet();
            sendPkt.setType((short)4); // create newChat packet
            sendPkt.setClientID(Info.getClientID());
            sendPkt.setSessionID(sessionID);
            updateChatList(); // update chat list
            clientID=Info.getClientID(); //get client Id from info class
            
            
            localReceiver.start();
          //  updateOnlineList.start();
            new SenderThread(sendPkt,clientSocket).start(); // send newChat Pkt
            this.setVisible(status);
            
        } catch (IOException ex) {
            Logger.getLogger(ChatForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//visible
    
    private boolean hasUser(String id){
        for(String entry : tempChatList ){
            if (entry.equals(id))
                return true;
        }
        return false;
    }
  
    public ChatForm() {
        initComponents();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        chatList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        onlineList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        sendBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        sendText = new javax.swing.JTextArea();
        removBtn = new javax.swing.JButton();
        chatText = new javax.swing.JScrollPane();
        chatText2 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(new java.awt.Point(200, 200));
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        chatList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chatList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                chatListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(chatList);

        onlineList.setModel(MainForm.onlineList.getModel());
        onlineList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        onlineList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        onlineList.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                onlineListComponentAdded(evt);
            }
        });
        onlineList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                onlineListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(onlineList);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CHAT WITH");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ONLINE USERS");

        sendBtn.setText("Send");
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        addBtn.setText("Add To Chat");
        addBtn.setEnabled(false);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        sendText.setColumns(20);
        sendText.setRows(5);
        jScrollPane4.setViewportView(sendText);

        removBtn.setText("Remove User");
        removBtn.setEnabled(false);
        removBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removBtnActionPerformed(evt);
            }
        });

        chatText2.setDoubleBuffered(true);
        chatText.setViewportView(chatText2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                        .addComponent(sendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(chatText, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(removBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chatText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    // sending text
    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
     if(sendText.getText().length()!=0){
     sendPkt = new Packet();
     sendPkt.setType((short)2); // pkt type text
     sendPkt.setClientID(clientID); // current ID
     sendPkt.setSessionID(sessionID); // session Id for this chat, 
     sendPkt.setDestIDList(destIDList); // include all the chatList
     sendPkt.setData1(sendText.getText());// get the text
     sendPkt.setData2(clientID);
     new SenderThread(sendPkt,clientSocket).start(); // send it
     appendToPane(chatText2,"Me:"+sendText.getText()+"\n",Color.black);
     sendText.setText("");//clear it
     }
      //JOptionPane.showMessageDialog(null, "OK");
    }//GEN-LAST:event_sendBtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            //We need to add here a code for sending a packet to let know other users that u leaved this conversation
            System.out.println("CLOSING socket");
             //sending update chatList pkt to remove current user.
            if(destIDList.size()>0){
            sendPkt=new Packet();
            sendPkt.setType((short)5);
            sendPkt.setAddToList(false);
            sendPkt.setClientID(clientID);
            sendPkt.setDestIDList(destIDList);
            sendPkt.setData1(clientID);
            sendPkt.setSessionID(sessionID);
            new SenderThread(sendPkt,clientSocket).start();
                System.out.println("closing session pkt is sent");
            this.setVisible(false);
            sleep(2000);// sleep for 1/2 second to wait for sending thread finish before close the socket
            }
            localReceiver.interrupt();
            clientSocket.close();
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ChatForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
       String invitedID = MainForm.getclientID((String) onlineList.getSelectedValue());//get the clientID
        if (!hasUser((String) onlineList.getSelectedValue())){
            destIDList.add(invitedID);// add it to current chat List
            updateChatList();
            sendPkt= new Packet();
            sendPkt.setType((short)5);
            sendPkt.setAddToList(true);
            sendPkt.setClientID(Info.getClientID());
            sendPkt.setData1(invitedID); // InvitedID user
            sendPkt.setDestIDList(destIDList);
            sendPkt.setSessionID(sessionID);
            appendToPane(chatText2,"You have invited "+onlineList.getSelectedValue()+"\n",Color.blue);
            new SenderThread(sendPkt,clientSocket).start(); // send the packet
        }
        else{
            JOptionPane.showMessageDialog(null, onlineList.getSelectedValue()+" Already invited to this session " );
        }
            
    }//GEN-LAST:event_addBtnActionPerformed

   
    private void onlineListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_onlineListValueChanged
        // TODO add your handling code here:
        if(onlineList.getSelectedIndex()!=-1)
        addBtn.setEnabled(true);
        else
             addBtn.setEnabled(false);
        
//        ListModel tempOnlineList = onlineList.getModel();
//        boolean isOnline;
//        //Loop through all users in chat list    
//        System.out.println("Checking online");
//        for (int i=0 ;i<tempChatList.size();i++){
//             isOnline=false;
//            //Loop through online list to check whether the use is online or not
//            for(int j=0;j<tempOnlineList.getSize();j++){
//                if(tempChatList.get(i).equals(tempOnlineList.getElementAt(j))){
//                    System.out.println("found user");
//                    isOnline=true;
//                    break;
//                }//if
//            }//for j           
//                if(!isOnline){
//                   appendToPane(chatText2,tempChatList.get(i)+" has left chat",Color.red);
//                   tempChatList.remove(i);
//                   updateChatList();
//                }//if
//        }//for i
            
    }//GEN-LAST:event_onlineListValueChanged

    private void removBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removBtnActionPerformed
        String removeID = MainForm.getclientID((String) chatList.getSelectedValue());//get the clientID
        
        sendPkt= new Packet();
        sendPkt.setType((short)5);
        sendPkt.setAddToList(false); // set it as remove from list
        sendPkt.setClientID(Info.getClientID());
        sendPkt.setData1(removeID); // InvitedID user
        sendPkt.setDestIDList(destIDList);
        sendPkt.setSessionID(sessionID);
        new SenderThread(sendPkt,clientSocket).start(); // send the packet
        appendToPane(chatText2,"You have removed "+chatList.getSelectedValue()+"\n",Color.red);
        removeDestId(removeID);// remove from current chat list
        updateChatList();
    }//GEN-LAST:event_removBtnActionPerformed

    private void chatListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_chatListValueChanged
        if(chatList.getSelectedIndex()!=-1){
            removBtn.setEnabled(true);
        }else{
             removBtn.setEnabled(false);
        }
    }//GEN-LAST:event_chatListValueChanged

    private void onlineListComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_onlineListComponentAdded
        // TODO add your handling code here:sout    
        System.out.println("Change happend online list");
    }//GEN-LAST:event_onlineListComponentAdded

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JList chatList;
    private javax.swing.JScrollPane chatText;
    private javax.swing.JTextPane chatText2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private static javax.swing.JList onlineList;
    private javax.swing.JButton removBtn;
    private javax.swing.JButton sendBtn;
    private javax.swing.JTextArea sendText;
    // End of variables declaration//GEN-END:variables
}
