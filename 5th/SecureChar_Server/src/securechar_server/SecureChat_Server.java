/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

/**
 *
 * @author Mohammed Muayad
 */
public class SecureChat_Server {

    /**
     * @param args the command line arguments
     */
    private static Server server ;
    public static void main(String[] args) {
        // TODO code application logic here
        server =new Server();
        server.strat();
    }
    
}
