/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import Messages.Packet;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import securechat_client.Info;
import securechat_client.SenderThread;

/**
 *
 * @author Mohammed Muayad
 */
public class MainForm extends javax.swing.JFrame {
    public static Socket clientSocket;
    private Packet sendPkt;
    public static HashMap <String,String> tempOnlineList  ;   // has the online clients  
    
    public static String getclientID(String name){ // search for clientID by Its name       
        for (Map.Entry <String , String> entry : tempOnlineList.entrySet()) {
                if (entry.getValue().equals(name)) {
                    System.out.println(entry.getKey());
                    return entry.getKey();
                } 
        }
            return null;
    }

    
    public MainForm() {
        initComponents();
        clientSocket=Info.getMainClientSocket();
        tempOnlineList.remove(Info.getClientID()); // remove current user from online
        onlineList.setListData(tempOnlineList.values().toArray()); // add online users
        this.setTitle(Info.getClientID());
        
    }
    public static void updateList(){
        onlineList.setListData(tempOnlineList.values().toArray()); // add oline users
        for(Map.Entry <String,String> e : tempOnlineList.entrySet()){
            System.out.println("LIST: "+e.getKey() +"   "+e.getValue());
        }
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
        onlineList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        newWindowBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        RequestHistoryBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(100, 200));

        onlineList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        onlineList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        onlineList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                onlineListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(onlineList);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ONLINE USERS");

        newWindowBtn.setText("New Conversation");
        newWindowBtn.setEnabled(false);
        newWindowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newWindowBtnActionPerformed(evt);
            }
        });

        logoutBtn.setText("Logout");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });

        RequestHistoryBtn.setText("Request History");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                        .addGap(40, 40, 40))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newWindowBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(logoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(RequestHistoryBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newWindowBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RequestHistoryBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(logoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void newWindowBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newWindowBtnActionPerformed

        String destID;
            destID=getclientID((String) onlineList.getSelectedValue()); // get Client Main ID
            System.out.println("DEST ID:"+destID);
            sendPkt = new Packet();
            sendPkt.setClientID(Info.getClientID());
            sendPkt.setType((short)3); // Request SessionID pkt
            sendPkt.setSessionID("0");    
            sendPkt.addDestID(destID);
            new SenderThread(sendPkt,clientSocket).start();//create new senderThread... send packet

    }//GEN-LAST:event_newWindowBtnActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
       System.exit(0);
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void onlineListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_onlineListValueChanged
          if(onlineList.getSelectedIndex()==-1)
              newWindowBtn.setEnabled(false);
          else
              newWindowBtn.setEnabled(true);
    }//GEN-LAST:event_onlineListValueChanged

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RequestHistoryBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton newWindowBtn;
    public static javax.swing.JList onlineList;
    // End of variables declaration//GEN-END:variables
}