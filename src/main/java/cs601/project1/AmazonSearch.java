/*
 Author Name : Shubham Pareek
 Class function : Main
 Project Number : 1
*/

/*
    This file contains the main method, what this method does is given a valid input, it will create a reviewList and QAList object and then pass that object
    alongside the fileLoc to the ProjectUI object. Then it will start the whole UI.

    Validation of input is done with the help of validateParameters method.
*/

package cs601.project1;

import java.security.InvalidParameterException;

public class AmazonSearch {

    public static void main(String[] args) {
        boolean validParameter = validateParameters(args); //validating params
        if (!validParameter){
            throw new InvalidParameterException("The input paramters are not in the expected format\n" +
                                                "The correct format for entering the parameters is -reviews <review_file_name> -qa <qa_file_name>");
        }

        //getting file arg
        String reviewFile = args[1];
        String qaFile = args[3];

        ReviewList reviewList = new ReviewList("ISO-8859-1"); //creating ReviewList
        QAList qaList = new QAList("ISO-8859-1"); //creating QAList

        ProjectUI ui = new ProjectUI(reviewList, reviewFile, qaList, qaFile);
        ui.startUI(); //starting ui

    }

    private static boolean validateParameters(String[] parameter) {
        //currently we only accept json file, so if file is in another format, we don't allow it
        if (parameter.length != 4){
            return false; //incorrect number of arguments
        }
        if (!parameter[0].equals("-reviews") && !parameter[2].equals("-qa")){
            return false; //incorrect order
        }
        if (!parameter[1].endsWith(".json") && !parameter[3].endsWith(".json")){
            return false; //incorrect file type
        }
        return true;
    }
}
