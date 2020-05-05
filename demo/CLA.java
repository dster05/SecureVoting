package demo;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.net.*;

public class CLA
{

    public static void main(String[] args) throws IOException
    {
        // CLA is listening to Voter on port 1234
        ServerSocket ss = new ServerSocket(1234);
        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;

            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                System.out.println("A Voter is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread t = new CLAClientHandler(s, dis, dos);

                // Invoking the start() method
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class
class CLAClientHandler extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public CLAClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
    public int GenerateValNo(){
        return ((int)((Math.random() * 9000000)+1000000));
    }
    @Override
    public void run() {
        String received;
        String toreturn;
        try {
            received = dis.readUTF();
            System.out.println("Received From Voter: " + received);
            //dos.writeUTF("Hello Voter");
            int tovoter = GenerateValNo();
            dos.writeUTF(Integer.toString(tovoter));
            this.dis.close();
            this.dos.close();
            this.s.close();

            InetAddress ip = InetAddress.getByName("localhost");
            Socket ctfs = new Socket(ip, 5678);
            DataOutputStream dctf = new DataOutputStream(ctfs.getOutputStream());
            dctf.writeUTF("CLA");
            dctf.close();
            ctfs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

