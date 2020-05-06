package Project2;

import java.math.BigInteger;
import java.util.ArrayList;

public class ListOfVoters {
    ArrayList<Voter> listOfVoters;

    public ListOfVoters() throws Exception{
        listOfVoters  = new ArrayList<Voter>();
        //list of validation numbers and recipients is voters
        //All information will be revolve around the "SSN"
        listOfVoters.add(new Voter("Voter1", "One", "123456789"));
        listOfVoters.add(new Voter("Voter2", "Two", "123456780"));
        listOfVoters.add(new Voter("Voter3", "Three",  "123456781"));


    }

}
