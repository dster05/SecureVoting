package Project2;

import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;

public class Voter {
    private String firstName;
    private String lastName;
    private String SSN;
    protected BigInteger e;
    protected BigInteger n;
    protected BigInteger d;

    protected Integer validationNum;

//    public Voter(String fn, String ln, String ssn, String county, String state, Integer valNum){
      public Voter(String fn, String ln, String ssn) throws Exception{
        this.firstName = fn;
        this.lastName = ln;
        this.SSN = ssn;
        getPublicKeys();
/*
    we might need this soon
 */
//        e = voterKeys.e;
//        n = voterKeys.n;
//        d = voterKeys.d;

    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSSN() {
        return SSN;
    }

    public void setValidationNum(Integer validationNum){
          this.validationNum = validationNum;
    }

    public Integer getValidationNum(){
        return validationNum;
    }
    /*
    this will associate the key pairs with the ssn
     */

    public void getPublicKeys()throws Exception{
          if(SSN == "123456789"){
              File voter1 = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter1PublicKey.txt");
              Scanner sc = new Scanner(voter1);
              e = new BigInteger(sc.nextLine());
              n = new BigInteger(sc.nextLine());
              sc.close();
              File voter1a = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter1PrivateKey.txt");
              Scanner sc1 = new Scanner(voter1a);
              d = new BigInteger(sc1.nextLine());
              sc1.close();

          }else if(SSN == "123456780"){
              File voter1 = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter2PublicKey.txt");
              Scanner sc = new Scanner(voter1);
              e = new BigInteger(sc.nextLine());
              n = new BigInteger(sc.nextLine());
              sc.close();
              File voter1a = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter2PrivateKey.txt");
              Scanner sc1 = new Scanner(voter1a);
              d = new BigInteger(sc1.nextLine());
              sc1.close();

          }else{
              File voter1 = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter3PublicKey.txt");
              Scanner sc = new Scanner(voter1);
              e = new BigInteger(sc.nextLine());
              n = new BigInteger(sc.nextLine());
              sc.close();
              File voter1a = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter3PrivateKey.txt");
              Scanner sc1 = new Scanner(voter1a);
              d = new BigInteger(sc1.nextLine());
              sc1.close();

          }

    }
}
