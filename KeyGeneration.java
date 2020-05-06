package Project2;

import java.math.BigInteger;
import java.util.*;



/*
here is the changes we have to make
none at the moment
 */



public class KeyGeneration {
    private BigInteger p;
    private BigInteger q;
    protected BigInteger n;
    private BigInteger totientN;
    protected BigInteger e;
    protected BigInteger d;


    private void pickLargePrime(int bitLength){
        p = BigInteger.probablePrime(bitLength, new Random());//bitlength is what the user wants it to be
        //  System.out.println("p: " + p);
        q = BigInteger.probablePrime(bitLength, new Random());//
        //  System.out.println("q: " +q);

    }

    private void calculateN(){//multiply p and q
        n = p.multiply(q);
        System.out.println("n:" +n);

    }

    private void calculateTotientN(){//since we know p and q are prime numbers we just (p-1)*(q-1)
        totientN = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        //System.out.println("toientN: " +totientN);

    }

    private void selectE(int bitLength) {//e must be an integer between 1 and totientN
        e = BigInteger.probablePrime(bitLength-1, new Random());//we want to use a bit lenth less than 1024 so we divide bitlength in half to help out
        while ((!e.gcd(totientN).equals(BigInteger.valueOf(1))) && e.compareTo(BigInteger.valueOf(1)) == 1 && e.compareTo(totientN) == -1) {//
            e = BigInteger.probablePrime(bitLength/2, new Random());
        }
        System.out.println("e:" +e);


    }

    private void calculateD(){
        d = e.modInverse(totientN);
        System.out.println("d = " + d);

    }

    public KeyGeneration(int bitLength){
        pickLargePrime(bitLength);
        calculateN();
        calculateTotientN();
        selectE(bitLength);
        calculateD();

    }


}