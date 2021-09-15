package cs601.project1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class QAList {

    String charSet;

    ArrayList<QuestionAnswer> qaList; //is the list containing all the reviews
    HashMap<String, String> indexedText; //is a hashmap which contains the id, the the text we have indexed
    String idPrefix = "Q";
    int idNum = 0;

    public QAList (String charSet){
        this.charSet = charSet;
    }

    public void populateReviewList (String fileLoc){
        //this methods takes the fileLoc as input, and parses the file, then stores the individual QuestionAnswer Object in the reviewList arrayList
        qaList = parseFile(fileLoc);
    }

    public ArrayList<QuestionAnswer> parseFile (String fileLoc){
        ArrayList<QuestionAnswer> jsonObjects = new ArrayList<>();
        File f = new File(fileLoc);
        Gson gson = new Gson();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charSet))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    QuestionAnswer questionAndAnswers = gson.fromJson(line, QuestionAnswer.class);
                    jsonObjects.add(questionAndAnswers);
                } catch (JsonSyntaxException jse) {
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        ArrayList<String> objToBeReturned = (ArrayList<String>)(ArrayList<?>)(jsonObjects);
        //        return objToBeReturned;

        return jsonObjects;

    }

    private String cleanString (String line){
        //given a string, method will remove all alphaNumerics from the string
        String cleanLine = line.replaceAll("[^a-zA-Z0-9\\s]", "");
        cleanLine = cleanLine.toLowerCase();
        /*
            regex explanation
                   the ^ is inside the bracket meaning that replace anything around alphanumeric charcaters with ""
                   \\s is for space, since we dont want to remove space right now
        */
        return cleanLine;
    }


}
