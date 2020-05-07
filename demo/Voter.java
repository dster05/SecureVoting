package demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import demo.RSA.*;

public class Voter {
	public String ssn;
	public String fname;
	public String lname;
	public String rno;
	Voter(){
		ssn="";
		fname="";
		lname="";
		rno="";
	}
	Voter(String ssn1, String fname1, String lname1){
		ssn = ssn1;
		fname = fname1;
		lname = lname1;
		rno="";
	}
	public void  Input() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Your First Name: ");
		this.fname = in.nextLine();
		System.out.println("Enter Your Last Name: ");
		this.lname = in.nextLine();
		System.out.println("Enter Your SSN: ");
		this.ssn = in.nextLine();
		
	}
	
	public void Show() {
		System.out.println("Entered Voter Details: ");
		System.out.println("Name: " + fname + " " +lname);
		System.out.println("SSN: " + ssn); 
	}
	public String connectCLA(String fname, String lname, String ssn) throws Exception{
		BigInteger[] publickey = new BigInteger[2];
		publickey[0] = new BigInteger("8698240004659325829557472844402847721341766219557628491366876596799400622251018340475165052484059607738322038126476773814930952312388433920352925123974307");
		publickey[1] = new BigInteger("27389355848173965429007265039983715712801642001937687450424405374311561080085124989885979231216670863137787185638469771487706788583712695425442586943764226614035221958869042221928954210879345804712799327079745080555023022033466792303459850674857620285874815574802371329773638433227733759575112397461043677209614526192111224394692908806923722840593441994592928018644296349048986244694395012690096195867473674608250003611166034107784830377932943829818771433027171720042248780781157674455524978431249353992937336348052340870315963289849072435291028036614550682468627620637103383883563078732207757705162947919782457146839");
		BigInteger pk = new BigInteger("22745684232701186008671616454591264943229187451754027022853213192409880971046526836318350508302766637233626175463015808748887588443791343847160614146397496272560238085751891980523945566033655183527679658031064808781327084756593369783159204499879010941032960045811093387165963417673844703677882007651984022112834166499361346757332130649719421396226108115011887751081644255141519044255182377725984093821444497930601374471851871641323332674422291298145894813035414179668325370147527644386300821219661212188115733071194714407088111829183414737317982718242614857845490646883934077347762153893825283565593656209107038602483");
		InetAddress ip = InetAddress.getByName("localhost");
		Socket s = new Socket(ip, 1234);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF(fname);
		dos.writeUTF(lname);
		dos.writeUTF(ssn);
		String ValNo = dis.readUTF();
		if(ValNo != "000") {
			System.out.println("From CLA: \n Your Validation Number:" + ValNo);
			System.out.println("Use this number to cast your Vote.");

		}
		else{
			System.out.println("Validation Number Already Assigned.");
		}
		dis.close();
		dos.close();
		s.close();
		return ValNo;
	}
	public void connectCTF(String ValNo, char vote)throws Exception{
		InetAddress ip = InetAddress.getByName("localhost");
		Socket s = new Socket(ip, 9012);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		System.out.println("Valno: " + ValNo);
		System.out.println("Vote " + vote);
		dos.writeUTF(ValNo);
		dos.writeChar(vote);
		String CTFM = dis.readUTF();
		if(CTFM == "000"){
			System.out.println("Invalid Registration Number.");
			System.out.println("Or you have already Voted");
		}
		else if(CTFM == "111"){
			System.out.println("Successfully Voted.");
		}
		if(vote == 'e' || vote == 'E'){
			System.out.println("----------Election Result--------");
			System.out.println(CTFM);
		}
		dis.close();
		dos.close();
		s.close();
	}
	public static void main(String args[]) throws Exception{
		Scanner ch = new Scanner(System.in);
		int choice;
		System.out.println("------------------------Online Voting Booth-----------------------");
		System.out.println("Candidates\nJay Tandel\nDaniel G");
		System.out.println("Press 1. to get Validation Number: ");
		System.out.println("You can vote after getting a Validation Number");
		System.out.println("Press any other key to exit ");
		System.out.print("Enter Your Choice: ");
		choice = ch.nextInt();
		if(choice == 1) {
			Voter v = new Voter();
			v.Input();
			String ValNo=v.connectCLA(v.fname, v.lname, v.ssn);
			if(ValNo != "000"){
				char cn;
				System.out.println("Press Y to Vote Now. ");
				cn = ch.next().charAt(0);
				if(cn == 'Y' || cn == 'y'){
					System.out.println("Press 1. to vote Jay Tandel");
					System.out.println("Press 2. to vote Daniel G");
					char vote=ch.next().charAt(0);
					v.connectCTF(ValNo, vote);
				}
			}
		}
		/*
		else if(choice == 2) {
			Voter v = new Voter();
			String ValNo;
			System.out.println("Enter Your Validation Number.");
			ValNo = ch.next();
			System.out.println("Press 1. to vote Jay Tandel");
			System.out.println("Press 2. to vote Daniel G");
			char vote=ch.next().charAt(0);
			v.connectCTF(ValNo, vote);
		}*/
		else {
			System.exit(0);
		}
		
	}
}