/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package securechat_client;

import java.io.Serializable;

/**
 *
 * @author Mohammed Muayad
 */
public class Packet implements Serializable  {
    private int type;
    private String data1;
    private String data2;
    private String clientID;
    private String destID;

    public Packet(int type, String data1, String data2, String clientID, String destID) {
        this.type = type;
        this.data1 = data1;
        this.data2 = data2;
        this.clientID = clientID;
        this.destID = destID;
    }

    public Packet() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getDestID() {
        return destID;
    }

    public void setDestID(String destID) {
        this.destID = destID;
    }

  

    
}
