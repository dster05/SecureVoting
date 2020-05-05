package demo;

import java.io.*;
import java.net.*;

// Server class
public class CTF
{
    public static void main(String[] args) throws IOException
    {
        // CLA is listening to Voter on port 1234
        ServerSocket CLAsocket = new ServerSocket(5678);
        ServerSocket Votersocket = new ServerSocket(9012);
        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket CLAs = null;
            Socket Voters = null;

            try{
                // socket object to receive incoming client requests
                CLAs = CLAsocket.accept();
                System.out.println("CLA is sending Data: ");
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(CLAs.getInputStream());
                DataOutputStream dos = new DataOutputStream(CLAs.getOutputStream());
                System.out.println("Assigning new thread for this CLA Request");
                // create a new thread object
                Thread t = new CLAClient(CLAs, dis, dos);
                // Invoking the start() method
                t.start();
            }
            catch (Exception e){
                CLAs.close();
                e.printStackTrace();
            }
            try
            {
                // socket object to receive incoming client requests
                Voters = Votersocket.accept();
                System.out.println("Voter is sending Vote: ");
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(Voters.getInputStream());
                DataOutputStream dos = new DataOutputStream(Voters.getOutputStream());
                System.out.println("Assigning new thread for this Voter Request");
                // create a new thread object
                Thread t = new VoterClient(Voters, dis, dos);
                // Invoking the start() method
                t.start();
            }
            catch (Exception e){
                CLAs.close();
                e.printStackTrace();
            }

        }
    }
}

// ClientHandler class
class CLAClient extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public CLAClient(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        try {
            received = dis.readUTF();
            System.out.println("Received From CLA: " + received);
            this.dis.close();
            this.dos.close();
            this.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

// ClientHandler class
class VoterClient extends Thread {

    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;


    // Constructor
    public VoterClient(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        try {
            received = dis.readUTF();
            System.out.println("Received From Voter: " + received);
            dos.writeUTF("Hello Voter");
            this.dis.close();
            this.dos.close();
            this.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

