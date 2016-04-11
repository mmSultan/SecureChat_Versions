/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechar_server;

import java.io.IOException;

/**
 *
 * @author Mohammed Muayad
 */
public class SecureChat_Server {

    /**
     * @param args the command line arguments
     */
    private static Server server ;
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        DbConnection a = new DbConnection();
        FileServices f = new FileServices();
        if(!f.getInfo())
            System.exit(0);
        
        
        server =new Server();
        server.strat();
    }
    
}
