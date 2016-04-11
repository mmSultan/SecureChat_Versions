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
   private static Socket fileClientSocket; 
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
        this.setLocationRelativeTo(null);
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
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(100, 200));
        setMaximumSize(new java.awt.Dimension(566, 440));
        setMinimumSize(new java.awt.Dimension(566, 440));
        setPreferredSize(new java.awt.Dimension(566, 440));
        setSize(new java.awt.Dimension(566, 440));
        getContentPane().setLayout(null);

        onlineList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        onlineList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        onlineList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                onlineListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(onlineList);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 314, 355);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ONLINE USERS");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 10, 258, 26);

        newWindowBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        newWindowBtn.setForeground(new java.awt.Color(0, 0, 255));
        newWindowBtn.setText("New Conversation");
        newWindowBtn.setEnabled(false);
        newWindowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newWindowBtnActionPerformed(evt);
            }
        });
        getContentPane().add(newWindowBtn);
        newWindowBtn.setBounds(360, 30, 165, 80);

        logoutBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        logoutBtn.setForeground(new java.awt.Color(0, 51, 204));
        logoutBtn.setText("Logout");
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });
        getContentPane().add(logoutBtn);
        logoutBtn.setBounds(360, 320, 165, 62);

        RequestHistoryBtn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        RequestHistoryBtn.setForeground(new java.awt.Color(0, 51, 204));
        RequestHistoryBtn.setText("Request History");
        RequestHistoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RequestHistoryBtnActionPerformed(evt);
            }
        });
        getContentPane().add(RequestHistoryBtn);
        RequestHistoryBtn.setBounds(360, 170, 165, 58);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/b6.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 570, 410);

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

    private void RequestHistoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RequestHistoryBtnActionPerformed
      //  fileClientSocket=Info.getFileClientSocket();
        sendPkt = new Packet();
        sendPkt.setClientID(Info.getClientID());
        sendPkt.setType((short)9); // request history

        new SenderThread(sendPkt, clientSocket).start(); // send the request
       
    }//GEN-LAST:event_RequestHistoryBtnActionPerformed

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton newWindowBtn;
    public static javax.swing.JList onlineList;
    // End of variables declaration//GEN-END:variables
}
