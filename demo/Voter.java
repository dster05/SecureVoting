package demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Voter {
	int ssn;
	String fname;
	String lname;
	String rno;
	Voter(){
		ssn=0;
		fname="";
		lname="";
		rno="";
	}
	Voter(int ssn1, String fname1, String lname1){
		ssn = ssn1;
		fname = fname1;
		lname = lname1;
		rno="";
	}
	public void Input() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter Your First Name: ");
		this.fname = in.nextLine();
		System.out.println("Enter Your Last Name: ");
		this.lname = in.nextLine();
		System.out.println("Enter Your SSN: ");
		this.ssn = in.nextInt();
		
	}
	
	public void Show() {
		System.out.println("Entered Voter Details: ");
		System.out.println("Name: " + fname + " " +lname);
		System.out.println("SSN: " + ssn); 
	}
	public void connectCLA()throws Exception{
		InetAddress ip = InetAddress.getByName("localhost");
		Socket s = new Socket(ip, 1234);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF("hello CLA");
		String rec = dis.readUTF();
		System.out.println("Recieved From CLA: " + rec);
		dis.close();
		dos.close();
		s.close();
	}
	public void connectCTF()throws Exception{
		InetAddress ip = InetAddress.getByName("localhost");
		Socket s = new Socket(ip, 9012);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		dos.writeUTF("hello CTF");
		String rec = dis.readUTF();
		System.out.println("Recieved From CTF: " + rec);
		dis.close();
		dos.close();
		s.close();
	}
	public static void main(String args[]) throws Exception{
		Scanner ch = new Scanner(System.in);
		int choice;
		System.out.println("------------------------Online Voting Booth-----------------------");
		System.out.println("Press 1. to get Registration Number: ");
		System.out.println("Press 2. to vote if you already have a valid Registration number: ");
		System.out.println("Press any other key to exit ");
		System.out.print("Enter Your Choice: ");
		choice = ch.nextInt();
		System.out.println("your choice is: " + choice);
		
		if(choice == 1) {
			Voter v = new Voter();
			//v.Input();
			//v.Show();
			v.connectCLA();
			v.connectCTF();
			
			//Voter nv = new Voter();
			//nv.toCLA();
		}
		else if(choice == 2) {
			;
		}
		else {
			System.exit(0);
		}
		
	}
}