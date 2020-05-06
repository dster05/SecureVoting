package Project2;
import java.math.BigInteger;

import java.util.*;


public class RSAEncrypt {
    protected BigInteger e;
    protected BigInteger n;
    protected String message;
    protected byte [] temp;
    protected String encrypted;

    public RSAEncrypt( String message, BigInteger publicKey, BigInteger publicN){
        this.message = message;
        this.e = publicKey;
        this.n = publicN;
        try {
            encrypt(e, n, message);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    protected void encrypt(BigInteger e, BigInteger n, String message) {
        /*
        msgbytes takes the conversion of the String message to a byte array of type charset UTF 16 Big
         */
        //this takes the string message and converts to a byte array named msgbytes
        byte []  msgbytes = message.getBytes();

        temp = (new BigInteger(msgbytes)).modPow(e,n).toByteArray();//this allows us to add the modPow to the byte array then back to a byte array found online www.sanfoundry.com

//        encrypted = new String(temp);// wrongway
        /*
            The basic encoder keeps things simple and encodes
            the input as is - without any line separation.
            The output is mapped to a set of characters in A-Z
         */
        encrypted = Base64.getEncoder().encodeToString(temp);
        System.out.println("encrypted string: " + encrypted);
    }

}
