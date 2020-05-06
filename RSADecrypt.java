package Project2;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

public class RSADecrypt {

    protected BigInteger d;
    protected BigInteger n;
    protected String message;
    protected String finalAnswer;
    private byte[] temp;

    public RSADecrypt(String message, BigInteger privateKey, BigInteger privateN ){
        this.d = privateKey;
        this.n = privateN;
        this.message = message;

        try {
            decrypt(message, d, n);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void decrypt(String message, BigInteger d, BigInteger n) {
        /*
            Lets decode the string back to original form
         */
        byte[] msgbytes = Base64.getDecoder().decode(message);
        temp = (new BigInteger (msgbytes)).modPow(d,n).toByteArray();//found on sanfoundry.com only part

        finalAnswer = new String(temp);
        System.out.println("*******you Got This**********");
//        System.out.println("decrypted string: " + finalAnswer);

    }




}
