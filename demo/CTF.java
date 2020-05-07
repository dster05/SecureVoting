package demo;

import java.io.*;
import java.net.*;
import java.sql.*;

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
                Voters.close();
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
    public void insertToDB(String FName, String LName, String SSN,String ValNo) throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CTF?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();

        String query1 = "insert into VoterList (FName, LName, SSN, ValNo, Voted) values (?, ?, ?, ?, ?);";
        PreparedStatement pst1 = myConn.prepareStatement(query1);
        pst1.setString(1, FName);
        pst1.setString(2, LName);
        pst1.setString(3, SSN);
        pst1.setString(4, ValNo);
        pst1.setInt(5, 0);
        pst1.execute();
    }
    @Override
    public void run() {
        String rFName;
        String rLName;
        String rSSN;
        String rValNo;
        String toreturn;
        try {
            rFName = dis.readUTF();
            rLName = dis.readUTF();
            rSSN = dis.readUTF();
            rValNo = dis.readUTF();
            System.out.println("Received FName From CLA: " + rFName);
            System.out.println("Received LName From CLA: " + rLName);
            System.out.println("Received SSN From CLA: " + rSSN);
            System.out.println("Received ValNo From CLA: " + rValNo);
            insertToDB(rFName, rLName, rSSN,rValNo);
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

    String connectDB(String ValNo, char Vote) throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CTF?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();
        String query1 = "select * from VoterList where ValNo = ? and Voted = 0";
        PreparedStatement pst1 = myConn.prepareStatement(query1);
        pst1.setString(1, ValNo);
        //pst1.setInt(2, );
        //pst1.execute();
        ResultSet rs = pst1.executeQuery();
        if(rs.next()) {
            System.out.println("New Voter");
            String query2 = "update Candidates set VotesReceived = VotesReceived+1 where id = ?; ";
            PreparedStatement pst2 = myConn.prepareStatement(query2);
            pst2.setInt(1, Character.getNumericValue(Vote));
            pst2.execute();
            String query3 = "update VoterList set Voted = 1 where ValNo = ?";
            PreparedStatement pst3 = myConn.prepareStatement(query3);
            pst3.setString(1,ValNo);
            pst3.execute();
            return "111";
        }
        else{
            System.out.println("Old Voter");

            return "000";
        }

    }

    String getResult() throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CTF?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();
        String query1 = "select Name, VotesReceived from Candidates";
        ResultSet rs = my.executeQuery(query1);
        System.out.println("Candidate Name\t\tNo of Votes");
        String winner;
        String name="";
        int votes=0;
        while(rs.next()){
            System.out.println(rs.getString(1) + "\t\t" +rs.getInt(2));
            if(rs.getInt(2)>votes){
                votes = rs.getInt(2);
                name = rs.getString(1);
            }
        }
        winner = name + " won with " + votes + " votes ";
        //System.out.println(winner);
        return winner;
        //PreparedStatement pst1 = myConn.prepareStatement(query1);
        //pst1.setString(1, ValNo);
    }

    @Override
    public void run() {
        String ValNo;
        char vote;
        String toreturn;
        try {
            ValNo = dis.readUTF();
            vote = dis.readChar();
            System.out.println("Received From Voter: " + ValNo + " " +vote);
            if( vote == 'e' || vote == 'E'){
                System.out.println("Voting Ended..");
                toreturn=getResult();
                dos.writeUTF(toreturn);
                System.exit(0);
            }
            else {
                toreturn = connectDB(ValNo, vote);
                dos.writeUTF(toreturn);
            }
            this.dis.close();
            this.dos.close();
            this.s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

