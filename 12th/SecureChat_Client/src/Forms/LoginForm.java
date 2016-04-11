/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Forms;

import java.net.Socket;
import securechat_client.ReceiverThread;
import Messages.Packet;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import securechat_client.FileServices;
import securechat_client.SenderThread;
import securechat_client.Info;

/**
 *
 * @author Mohammed Muayad
 */
public class LoginForm extends javax.swing.JFrame {
    public static Socket clientSocket;
    private Packet sendPkt;
    private  FileServices file = new FileServices();
    private BufferedImage tmpImg;
    private ImageIcon logo;
    
    public LoginForm() {
        initComponents();
        this.getRootPane().setDefaultButton(jButton1);
        this.setLocationRelativeTo(null);
        errorLbl.setVisible(false);
        try {
           // tmpImg= ImageIO.read(new File("src/Img/3.png"));
//            Image img= tmpImg.getScaledInstance(logoLbl.getWidth(), logoLbl.getHeight(), Image.SCALE_SMOOTH);
//            logo=new ImageIcon(img);
//            logoLbl.setIcon(logo);
            
            file.getInfo(); // get server info
            clientSocket= new Socket(Info.getServerIP(),Info.getServerPort()); // MAIN SOCKET CREATION
            new ReceiverThread(clientSocket).start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            System.out.println(ex);
            errorLbl.setText("Cant Find Server.... check server Info");
            errorLbl.setVisible(true);
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

        jButton1 = new javax.swing.JButton();
        userText = new javax.swing.JTextField();
        passText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        errorLbl = new javax.swing.JLabel();
        logoLbl = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 255));
        setLocation(new java.awt.Point(400, 200));
        setPreferredSize(new java.awt.Dimension(520, 440));
        setResizable(false);
        setSize(new java.awt.Dimension(520, 440));
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 204));
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(190, 310, 160, 40);

        userText.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        getContentPane().add(userText);
        userText.setBounds(190, 170, 168, 28);

        passText.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        getContentPane().add(passText);
        passText.setBounds(190, 220, 168, 28);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setText("User ID");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(110, 170, 80, 30);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 255));
        jLabel2.setText("Password");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(110, 220, 73, 22);

        errorLbl.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        errorLbl.setForeground(new java.awt.Color(255, 0, 0));
        errorLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLbl.setText("..............");
        getContentPane().add(errorLbl);
        errorLbl.setBounds(110, 260, 320, 41);

        logoLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        logoLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/l1.png"))); // NOI18N
        getContentPane().add(logoLbl);
        logoLbl.setBounds(40, 50, 360, 90);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/b8.jpg"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, -20, 540, 440);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

       // Random rnd = new Random();
       // String clientID ="A "+ rnd.nextInt(1000);
        String clientID=userText.getText();
        Info.setClientID(clientID);
        Info.setMainClientSocket(clientSocket); // add it to Info class 
        
        sendPkt= new Packet();
        sendPkt.setType((short)1);
        sendPkt.setClientID(clientID);
        sendPkt.setSessionID("0");
        sendPkt.setData1(userText.getText());
        sendPkt.setData2(passText.getText());
 
        new SenderThread(sendPkt,clientSocket).start();
        

       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        jLabel1.setOpaque(true);
        jLabel1.setBackground(Color.red);
      
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        // TODO add your handling code here:
   
    }//GEN-LAST:event_jLabel1MousePressed

 
    
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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel errorLbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel logoLbl;
    private javax.swing.JTextField passText;
    private javax.swing.JTextField userText;
    // End of variables declaration//GEN-END:variables
}
