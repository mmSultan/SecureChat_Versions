
package Messages;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Mohammed Muayad
 */
public class Packet implements Serializable  {
    private static final long serialVersionUID  = 1L;
    private short type; //1-Authentication, 2-Text chat ,3-ReqSessionID 4-newChat  5-UpdatechatList, 6-UpdateOnLineLis, 7-FileTransfer, 8-Request Chat History, ....., 9-CloseConnecton
    private String data1;// contains any tyoe of data... message,user or password   
    private String data2;//contains any tyoe of data... message,user or password  
    private String clientID;// current client id    
    private  List <String> destIDList= new ArrayList<>();
    private String sessionID;// chat session id
    private byte[] msgOrFile ;// array of bytes that has msgOrFile 
    private HashMap <String,String> onlineList = new HashMap<>();// online users list... that server sends it for the first login   
    private boolean addToList;// flag to determain whether add or remove from online list       
    private boolean createFileSocket; // flag refers to create a socket for msgOrFile transmitting       
    private PublicKey publicKey; // public key of the message to be used to decrypt the message

    public Packet() {
        
    }

    public boolean isCreateFileSocket() {
        return createFileSocket;
    }

    public void setCreateFileSocket(boolean createFileSocket) {
        this.createFileSocket = createFileSocket;
    }
    
    public Packet(short type, String data1, String data2, String clientID, List <String> destID) {
        this.type = type;
        this.data1 = data1;
        this.data2 = data2;
        this.clientID = clientID;
        this.destIDList = destID;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

 

    public boolean isAddToList() {
        return addToList;
    }

    public void setAddToList(boolean addToList) {
        this.addToList = addToList;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
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

    public List<String> getDestIDList() {
        return destIDList;
    }

    public void setDestIDList(List<String> destIDList) {
        for (String userID : destIDList){
            this.destIDList.add(userID);
        }
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public byte[] getMsgOrFile() {
        return msgOrFile;
    }

    public void setMsgOrFile(byte[] msgOrFile) {
        this.msgOrFile = msgOrFile;
    }

    public HashMap<String, String> getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(HashMap<String, String> onlineList) {
        this.onlineList = onlineList;
    }
    
        
    public void addDestID (String item){// add ID
        destIDList.add(item);

    }
    public void removeDestId(String ID){// remoce a certain ID from the list
        for(int i=0 ;i<destIDList.size();i++){
            if(destIDList.get(i).equals(ID)){
                destIDList.remove(i);
                return;
            }
        }
    }



    
}
