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

public class FileParser {
    //class that takes in String file location and String objectType as arguments to the constructor
    //once file has been parsed this class will return an ArrayList of objects in that file

    public ArrayList<String> parseFile (String fileLoc, String objType){
        /*
            todo Ask Sami whether we can assume that a single file can only have the same type of object
                 Currently we are only parsing json files, write code which will also be able to parse .txt and .csv
                 files at the least.
        */

        /*
            Arguments -> fileLoc => is the location of the file to be parsed
                         objType => is the type of object the given file contains.

            Given a fileLoc and the object type of the file, this method will open the file, create those objects convert them toString and then store
            the string into an arraylist.

            The arraylist is then returned.
        */
        ArrayList<Object> jsonObjects = new ArrayList<>();
        File f = new File(fileLoc);
        Gson gson = new Gson();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "ISO-8859-1"))) {
            if (objType.equals("Review")){
                String line;
                while ((line = br.readLine()) != null){
                    try {
                        String review = gson.fromJson(line, Review.class).toString();
                        jsonObjects.add(review);
                    }
                    catch (JsonSyntaxException jse){
                        continue;
                    }
                }
                //temp for loop for testing purpose, we will use while loop in the actual code
//                for (int i = 0; i < 5; i++){
//                    //removing every nonalphanumeric chars from the string
//                    String review = gson.fromJson(br.readLine(), Review.class).toString();
//                    //String review = gson.fromJson(br.readLine(), Review.class).toString().replaceAll("[^A-Za-z0-9\\s\\+]", "");;
//                    jsonObjects.add(review);
//                }
            }
            else if (objType.equals("QuestionAnswer")){
                String line;
                while ((line = br.readLine()) != null){
                    try{
                        String qa = gson.fromJson(line, QuestionAnswer.class).toString();
                        jsonObjects.add(qa);
                    }
                    catch (JsonSyntaxException jse){
                        continue;
                    }
                }
                //temp for loop for testing purpose, we will use while loop in the actual code
//                for (int i = 0; i < 5; i++){
//                    String qa = gson.fromJson(br.readLine(), QuestionAnswer.class).toString();
//                    //String qa = gson.fromJson(br.readLine(), QuestionAnswer.class).toString().replaceAll("[^A-Za-z0-9\\s]", "");;
//                    jsonObjects.add(qa);
//                }
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
