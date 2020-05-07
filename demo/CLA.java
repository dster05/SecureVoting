package demo;

import java.io.*;
import java.math.BigInteger;
import java.sql.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.net.*;
import demo.RSA.*;


public class CLA
{

    public static void main(String[] args) throws Exception
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

                System.out.println("Assigning new thread for this Voter");

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
    public String GenerateValNo(){
        return (Integer.toString((int)((Math.random() * 9000000)+1000000)));
    }

    public String connectDB(String FName, String LName, String SSN) throws Exception{
        Connection myConn = null;
        myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/CLA?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","12345678");
        Statement my =myConn.createStatement();
        String query = "select * from VoterList where SSN = ?";
        PreparedStatement pst = myConn.prepareStatement(query);
        pst.setString(1, SSN);
        ResultSet rs = pst.executeQuery();
        if(!rs.next()) {
            System.out.println("New Voter: ");
            String ValNo = GenerateValNo();
            //String query1 = "update VoterList set ValNo = ?,  where SSN = ?";
            String query1 = "insert into VoterList (FName, LName, SSN, ValNo) values (?, ?, ?, ?);";
            PreparedStatement pst1 = myConn.prepareStatement(query1);
            pst1.setString(1, FName);
            pst1.setString(2, LName);
            pst1.setString(3, SSN);
            pst1.setString(4, ValNo);
            pst1.execute();
            return ValNo;
        }
        return "000";
    }
    @Override
    public void run() {
        String rSSN;
        String rFName;
        String rLName;
        String toVoter;
        //BigInteger[] publickey = new BigInteger[2];
        BigInteger[] privatekey = new BigInteger[2];
        //privatekey[0] = new BigInteger("");
        //privatekey[1] = new BigInteger("");
        privatekey[1] = new BigInteger("27389355848173965429007265039983715712801642001937687450424405374311561080085124989885979231216670863137787185638469771487706788583712695425442586943764226614035221958869042221928954210879345804712799327079745080555023022033466792303459850674857620285874815574802371329773638433227733759575112397461043677209614526192111224394692908806923722840593441994592928018644296349048986244694395012690096195867473674608250003611166034107784830377932943829818771433027171720042248780781157674455524978431249353992937336348052340870315963289849072435291028036614550682468627620637103383883563078732207757705162947919782457146839");
        privatekey[0] = new BigInteger("22745684232701186008671616454591264943229187451754027022853213192409880971046526836318350508302766637233626175463015808748887588443791343847160614146397496272560238085751891980523945566033655183527679658031064808781327084756593369783159204499879010941032960045811093387165963417673844703677882007651984022112834166499361346757332130649719421396226108115011887751081644255141519044255182377725984093821444497930601374471851871641323332674422291298145894813035414179668325370147527644386300821219661212188115733071194714407088111829183414737317982718242614857845490646883934077347762153893825283565593656209107038602483");
        try {

            //rFName = dis.readUTF();
            //int r = dis.read();
            //byte[] r = new byte[256];
            //dis.readFully(r);
            //String r = dis.readUTF();
            rFName = dis.readUTF();
            rLName = dis.readUTF();
            rSSN = dis.readUTF();
          //  byte[] r = rFName.getBytes();
            //System.out.println("Received FName: " + r);
            //System.out.println("FNAME IN BYTES: " + r.getBytes());
            //System.out.println("No String: " +RSA.bytesToString(r.getBytes()));
            //System.out.println("USING VALUE OF: " + String.valueOf(r));
            //byte[] b = RSA.decrypt(r.getBytes(),privatekey[0],privatekey[1]);
            //rFName = new String(b);
            //System.out.println("Decrypt: " + rFName);
            System.out.println("Received FName: " + rFName);
            System.out.println("Received LName: " + rLName);
            System.out.println("Received SSN: " + rSSN);
            //dos.writeUTF("Hello Voter");
            //toVoter=connectDB(rFName, rLName, rSSN);
            toVoter= "all received";
            String db = connectDB(rFName, rLName, rSSN);
            dos.writeUTF(db);
            this.dis.close();
            this.dos.close();
            this.s.close();

            if(db !="000") {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket ctfs = new Socket(ip, 5678);
            DataOutputStream dctf = new DataOutputStream(ctfs.getOutputStream());
            dctf.writeUTF(rFName);
            dctf.writeUTF(rLName);
            dctf.writeUTF(rSSN);
            dctf.writeUTF(db);
            dctf.close();
            ctfs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

