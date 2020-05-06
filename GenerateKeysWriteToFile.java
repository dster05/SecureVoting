package Project2;

import java.io.FileWriter;

public class GenerateKeysWriteToFile {


    public static void main(String[] args){

        KeyGeneration key = new KeyGeneration(1024);

            try{
            FileWriter myWriter = new FileWriter("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter3PublicKey.txt");
            myWriter.write("123456781\n");
            myWriter.write(key.e.toString()+"\n");
            myWriter.write(key.n.toString());
            myWriter.close();
            FileWriter myWriter1 = new FileWriter("C:\\Users\\dster\\IdeaProjects\\OnlineVoting\\src\\Project2\\Voter3PrivateKey.txt");
            myWriter1.write("123456781\n");
            myWriter1.write(key.d.toString());
            myWriter1.close();
        }catch(Exception e){}
    }
}
