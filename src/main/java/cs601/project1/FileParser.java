/*
 Author - Shubham Pareek
 Class function - class to parse the file
 Reference:
    https://stackoverflow.com/a/38194881 to learn how to convert an Object ArrayList to that of a specified class
*/

package cs601.project1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class FileParser {
    //class that takes in String file location and String objectType as arguments to the constructor
    //once file has been parsed this class will return an ArrayList of objects in that file
    String charSet;
    public FileParser(String charSet){
        this.charSet = charSet;
    }

    public ArrayList<String> parseFile (String fileLoc, String objType){
        /*
            todo Ask Sami whether we can assume that a single file can only have the same type of object
                 Currently we are only parsing json files, write code which will also be able to parse .txt and .csv
                 files at the least.
        */

        /*
            Arguments -> fileLoc => is the location of the file to be parsed
                         objType => is the type of object the given file contains.

            Given a fileLoc and the object type of the file, this method will open the file, create those objects convert the required information toString then return those strings
            in an arraylist
        */
        ArrayList<Object> jsonObjects = new ArrayList<>();
        File f = new File(fileLoc);
        Gson gson = new Gson();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charSet))) {
            if (objType.equals("Review")){
//                String line;
//                while ((line = br.readLine()) != null){
//                    try {
//                        Review r = gson.fromJson(line, Review.class);
//                        String review = r.getReviewText();
//                        review = review.toLowerCase();
//                        jsonObjects.add(review);
//                    }
//                    catch (JsonSyntaxException jse){
//                        continue;
//                    }
//                }

                for (int i = 0; i < 10; i++){
                    Review r = gson.fromJson(br.readLine(), Review.class);
                    String review = r.getReviewText();
                    review = review.toLowerCase();
                    jsonObjects.add(review);
                }
            }
            else if (objType.equals("QuestionAnswer")){
//                String line;
//                while ((line = br.readLine()) != null){
//                    try{
//                        QuestionAnswer questionAndAnswers = gson.fromJson(line, QuestionAnswer.class);
//                        String qa = questionAndAnswers.getQuestion() + " " + questionAndAnswers.getAnswer();
//                        qa = qa.toLowerCase(); //doing this in separate line, since since otherwise we lowerCase getAnswer and alternative sol with make line too long
//                        jsonObjects.add(qa);
//                    }
//                    catch (JsonSyntaxException jse){
//                        continue;
//                    }
//                }

                for (int i = 0; i < 10; i++){
                    QuestionAnswer questionAndAnswers = gson.fromJson(br.readLine(), QuestionAnswer.class);
                    String qa = questionAndAnswers.getQuestion() + " " + questionAndAnswers.getAnswer();
                    qa = qa.toLowerCase(); //doing this in separate line, since since otherwise we lowerCase getAnswer and alternative sol with make line too long
                    jsonObjects.add(qa);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> objToBeReturned = (ArrayList<String>)(ArrayList<?>)(jsonObjects);
        return objToBeReturned;
    }
}
