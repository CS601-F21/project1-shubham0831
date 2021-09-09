/*
 Author Name : Shubham Pareek
 Class function : Blueprint of an inverted index, and how it will store data. // more details below
 Project Number : 1
*/

/*
 Reference:
    https://stackoverflow.com/a/38194881 to learn how to convert an Object ArrayList to that of a specified class
*/

package cs601.project1;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class InvertedIndex {
    /*
        Constructor -> takes an ArrayList of file locations as the argument.

        We need to have a data structure for each unique document for search and retrieval

        Algorithm for populating the invertedIndex -> 1) Read all the files in said array list and create list of the objects said file contains
                                                      2) Once we have the list of the objects, for every object in that list use the toString method
                                                      3) Once we have used the toString method in said object, we then tokenize and clean the strings
                                                      4) Then we add those strings to our index HashMap with the string being the key, the docName (or) docID (tbd) being the value

        Algorithm for search and retrieval -> 1) Given the word we have to search, look up from our inverted index the documents it is present in
                                              2) Once we have the list/set of documents, we need to search the document for the given word //todo



    */
    private HashMap<String, HashSet<String>> invertedIndex = new HashMap<>(); //is the inverted index
    private ArrayList<String> fileLocations;
    private HashMap<Integer, String> classKeys = new HashMap<>(); //this contains the type of objects our program can handle
                                                                  //and the key of those object. Will be used when we parse files and need
                                                                  //to know what object the given file contains

    private void fillClassKeys() {
        //method to populate the classKeys HashMap, which contains the key,value pair which lets us know which key is used to represent which class
        //benefit -> can now use same method to parse different files containing different objects
        classKeys.put(0, "Review");
        classKeys.put(1, "QuestionAnswer");
    }

    public InvertedIndex (ArrayList<String> fileLocations, HashSet<String> objectType){
        /*
            fileLocations -> is the list of different files which we will be used to create the inverted index
        */
        this.fileLocations = fileLocations;
        fillClassKeys();
    }

    private ArrayList<Object> parseFile (String fileLoc, int key){
        /*
            Arguments -> fileLoc : is the location of the file which has to be parsed
                         key : is key to the type of object the given file contains
                               key = 0 if file contains a Review
                               key = 1 if file contains Question and Answers
            Method function ->  given a location of a file, this method will read the file
                                create appropriate objects based on what the file contains
                                and return a list of those objects.
            Big picture -> Once someone calls this method and gets the list of objects, they will then change the list
                           type to the object the wanted the list to be.
                           Benefit of using ArrayList of Object is that our method can be generalized, and we do not have to
                           create separate methods for getting arraylist of different object

        */
        ArrayList<Object> jsonObjects = new ArrayList<>();
        File f = new File(fileLoc);
        Gson gson = new Gson();
        if (classKeys.get(key).equals("Review")) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "ISO-8859-1"))) {
                //temp for loop for testing purpose, we will use while loop in the actual code
                for (int i = 0; i < 5; i++){
                    Review review = gson.fromJson(br.readLine(), Review.class);
                    jsonObjects.add(review);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (classKeys.get(key).equals("QuestionAnswer")) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "ISO-8859-1"))) {
                //temp for loop for testing purpose, we will use while loop in the actual code
                for (int i = 0; i < 5; i++){
                    QuestionAnswer qa = gson.fromJson(br.readLine(), QuestionAnswer.class);
                    jsonObjects.add(qa);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObjects;
    }
}
