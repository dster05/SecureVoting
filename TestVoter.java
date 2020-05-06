package Project2;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestVoter {



    static ListOfVoters voters;
    static BigInteger e;
    static BigInteger n;
    static BigInteger d;
    static OutputStream out;
    static Socket socket;
    static ObjectOutput oOut;
    static InputStream in;
    static ObjectInput oln;





    public static synchronized void sendMessage(String s){
        try{
            RSAEncrypt encrypt = new RSAEncrypt(s,e,n);
            oOut.writeObject(encrypt.encrypted);
            oOut.flush();
        }catch(Exception ex){}
    }

// receive message

    public static synchronized String receiveMessage(){
        while(true){
            try{
                String inputString = (String)oln.readObject();
                RSADecrypt encrypt = new RSADecrypt(inputString, d, n);
                return "This is decrypted: " + encrypt.finalAnswer;
            }catch(Exception e){}
        }

    }




    public static void main(String[] args)throws Exception{
        voters = new ListOfVoters();

        for(Voter x: voters.listOfVoters){
            if(x.getSSN().equals("123456789")){
                e = new BigInteger(x.e.toString());
                n = new BigInteger(x.n.toString());
                d = new BigInteger(x.d.toString());
            }
        }
        try{
            socket = new Socket("127.0.0.1", 1234);

            out = socket.getOutputStream();
            oOut = new ObjectOutputStream(out);

            sendMessage("this is the message");


            //receive message

            in = socket.getInputStream();
            oln = new ObjectInputStream(in);

            String s = receiveMessage();
            System.out.println("From CLA: "+s);




            socket.close();

        }catch(Exception e){}



    }
}
