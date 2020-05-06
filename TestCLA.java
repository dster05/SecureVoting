package Project2;

import Project2.CLA;

import javax.print.DocFlavor;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestCLA {

    static ListOfVoters voters;
    static BigInteger e;
    static BigInteger n;
    static BigInteger d;
    static ServerSocket server;
    static Socket socket;
    static InputStream in;
    static ObjectInput oln;
    static OutputStream out;
    static ObjectOutput oOut;


    public static synchronized String receiveMessage(){
        while(true){
            try{
                String inputString = (String)oln.readObject();
                RSADecrypt encrypt = new RSADecrypt(inputString, d, n);
                return "This is decrypted: " + encrypt.finalAnswer;
            }catch(Exception e){}
        }

    }
    //send message

    public static synchronized void sendMessage(String s){
        try{
            RSAEncrypt encrypt = new RSAEncrypt(s,e,n);
            oOut.writeObject(encrypt.encrypted);
            oOut.flush();
        }catch(Exception ex){}
    }






    public static void main(String[] args)throws Exception {
        voters = new ListOfVoters();

        for(Voter x: voters.listOfVoters){
            if(x.getSSN().equals("123456789")){
                e = new BigInteger(x.e.toString());
                d = new BigInteger(x.d.toString());
                n = new BigInteger(x.n.toString());
            }
        }

        try{
            server = new ServerSocket(1234);
            //create socket server
            socket = server.accept();
            //put server into waiting state
            //listen to connection
            in = socket.getInputStream();
            oln = new ObjectInputStream(in);

            String s = receiveMessage();
            System.out.println("From Voter: "+s);
//send message
            out = socket.getOutputStream();
            oOut = new ObjectOutputStream(out);

            sendMessage("CLA's message : this is the message");

            oln.close();
            socket.close();
            server.close();

        }catch(Exception e){}



    }
}


