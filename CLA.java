package Project2;

import java.io.File;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLA {
    protected Integer numOfVoters;// counter
    protected ArrayList<Voter> voters; //this should be dynamic data structure array not good for this
    protected Integer validationNumber; //
    protected ArrayList<Integer> previousNumbers;//holds the previous issued validation number


   public CLA()throws Exception{//constructor instantiates numOfVoters and voters arraylist
       numOfVoters = 0;
       ListOfVoters listOfVoters = new ListOfVoters();



   }
    protected void votersToCTF(){

    }

//    protected Integer getValidationNumber(Integer validationNumber){
//                    for(Voter x : voters){
//                        if(x.getValidationNum() == validationNumber)
//
//
//                    }
//
//    }

    protected void readKeyesCLA()throws Exception{

       BigInteger e;

       BigInteger n;

       BigInteger d;

       File file = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\CLAPublicKey.txt");

       Scanner sc = new Scanner(file);

       e = new BigInteger(sc.nextLine());

       n = new BigInteger(sc.nextLine());

       File file2 = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\CLAPrivateKey.txt");

       Scanner sc2 = new Scanner(file2);

       d = new BigInteger(sc2.nextLine());


        System.out.println("e: " + e );
        System.out.println("n: " + n );
//        System.out.println("d: " + d );



    }

    protected void readKeyesCTF()throws Exception{

        BigInteger e;

        BigInteger n;

        BigInteger d;

        File file = new File("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\CTFPublicKey.txt");

        Scanner sc = new Scanner(file);

        e = new BigInteger(sc.nextLine());

        n = new BigInteger(sc.nextLine());


        System.out.println("CTF");
        System.out.println("e: " + e );
        System.out.println("n: " + n );



    }



    public void certifyVoters(String ssn){

       validationNumber = createValidationNumber();//call validation method

       //number of certified voters
       numOfVoters++;
    }

    protected Integer createValidationNumber(){
       //validation number needs to be random
       //need to store previous validation numbers to avoid duplicates need a an array list of numbers
       //assign number which means just return it.
        previousNumbers = new ArrayList<Integer>();//made this a global variable so we can use it later
        previousNumbers.add(0);
        boolean doesNotExist = false;//makes sure the validation number doesnt exist
        do {
            Integer validationNumber = (int) (100 * Math.random()) + 1;//create validation number
            for (Integer x : previousNumbers) {//enhanced for loop will traverse the Arraylist to check numbers
                if (validationNumber.equals(x)) {//checks to see if number already exists if it does break out of loop
                    break;
                } else {
                    previousNumbers.add(validationNumber);//Add validation number
                    doesNotExist = true;//make doesNotExist True
                    return validationNumber;//return validationNumber;
                }
            }
        }while(doesNotExist == false);
       return 0;
    }



}
