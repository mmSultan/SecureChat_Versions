/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption;

/**
 *
 * @author Kareem
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public final class RSA {
    
    private static KeyPair myKeys;
    private static  PublicKey publicKey;

    public RSA() {
    }
 

    public  PublicKey getPublicKey() {
        return publicKey;
    }



    //Generate Public/Private KeyPair using RSA Algorithm
    public  void generateKey() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
             keyGen.initialize(1024);
             myKeys = keyGen.generateKeyPair();
             publicKey=myKeys.getPublic();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    //Encrypt plain text using Public Key
    public  byte[] encrypt(String text, PublicKey serverPublickey) {
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, serverPublickey);
            cipherText = cipher.doFinal(text.getBytes());
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e);
        }
        return cipherText;
    }

    //Decrypt Cipher Text using Private Key
    public  String decrypt(byte[] text) throws FileNotFoundException, IOException, ClassNotFoundException {
        final PrivateKey myPrivateKey = myKeys.getPrivate();
        byte[] dectyptedText = null;
        try {

            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, myPrivateKey);
            dectyptedText = cipher.doFinal(text);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            System.out.println(ex);
        }

        return new String(dectyptedText);
    }
    

}
