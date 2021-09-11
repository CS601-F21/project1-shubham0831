/*
 Author Name : Shubham Pareek
 Class function : Blueprint of an inverted index, and how it will store data. // more details below
 Project Number : 1
*/

//references https://stackoverflow.com/questions/11796985/java-regular-expression-to-remove-all-non-alphanumeric-characters-except-spaces to get regex for removing non-alphanumeric characters
//           https://stackoverflow.com/a/64271783 to learn how to match a whole word

package cs601.project1;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /*
        todo implement sorting as well, show results where the words appear most frequently
    */
    private HashMap<String, HashSet<String>> invertedIndex = new HashMap<>(); //is the inverted index
    private HashMap <String, ArrayList<String>> documents = new HashMap<>(); //is the hashmap which stores the documents
                                                                             //the key will be the document id, and the value is the document.

    public void cleanDoc (ArrayList<String> doc){
        //given any arraylist of strings, this method will remove all alphanumeric characters for the strings in the list
        for (int i = 0; i < doc.size(); i++){
            //here the ^ is inside the bracket meaning that replace anything around alphanumeric charcaters with "";
            // \\s is for space, since we dont want to remove space right now
            doc.set(i, doc.get(i).replaceAll("[^a-zA-Z0-9\\s]", ""));
        }
    }

    public void addDocument (ArrayList<String> doc, String docId){
        //method to add documents to the invertedIndex
        if (documents.containsKey(docId)){ //this method only allows new documents
            throw new InvalidParameterException("Key already exists, if you want to update an existing document you InvertedIndex.update() method");
        }

        cleanDoc(doc);
        documents.put(docId, doc); //putting the document in the documents HashMap


        for (String line : doc){
            String[] words = line.split(" ");
            for (String word : words){
                word = word.toLowerCase().strip();
                if (!invertedIndex.containsKey(word) && word != ""){
                    HashSet<String> docSet = new HashSet<>();
                    docSet.add(docId);
                    invertedIndex.put(word, docSet);
                    //System.out.println("1 ==>"  + invertedIndex);
                }
                else if (word != "") {
                    invertedIndex.get(word).add(docId);
                    //System.out.println(invertedIndex);
                }
            }
        }
    }

    private ArrayList<String> findKey (String key){
        key = key.toLowerCase(); //all indexes are in lowerCase
        HashSet<String> docs = searchIndex(key, true); // is the set of doc ID which contains the given string
        if (docs == null){
            //return empty list if no match found
            return new ArrayList<String>();
        }

        ArrayList<String> matches = new ArrayList<>(); // is the arraylist which will store all the lines which do have the string
        Pattern pattern = Pattern.compile("\\b"+key+"\\b"); // \\b for word end or beginning
        Matcher matcher;


        for (String docId : docs){
            ArrayList<String> document = documents.get(docId);

            for (String line : document){
                //using matches instead of contains, as it does full match
                matcher = pattern.matcher(line);
                if (matcher.find()){
                    matches.add(line);
                }
            }
        }
        return matches;
    }

    public void find (String key){
        boolean validQuery = validateQuery(key);
        if (!validQuery){
            //if query contains alphanumerics then it is not a valid query
            throw new InvalidParameterException("Key should not contain alphanumerics");
        }
        ArrayList<String> match = findKey(key);
        if (match.size() == 0){
            //cuz findKey return empty arraylist if no matches are found
            //so we output this out
            System.out.println("No matches found");
            return;
        }
        //otherwise print all the matches
        for (String line : match){
            System.out.println(line);
        }
    }


    private ArrayList<String> partialMatch (String key){
        key = key.toLowerCase(); //all indexes are in lowerCase
        //todo key is not in the searchIndex, since that does a comprehensive search, we have to iterate through all keys and check if key contains tha
        HashSet<String> docs = searchIndex(key, false); // is the set of doc ID which contains the given string
        if (docs == null){
            //return empty list if no match found
            return new ArrayList<String>();
        }

        ArrayList<String> matches = new ArrayList<>(); // is the arraylist which will store all the lines which do have the string
        for (String docId : docs){
            ArrayList<String> document = documents.get(docId);
            for (String line : document){
                if (line.contains(key)){
                    matches.add(line);
                }
            }
        }
        return matches;
    }

    public void partialFind (String key){
        boolean validQuery = validateQuery(key);
        if (!validQuery){
            throw new InvalidParameterException("Key should not contain alphanumerics");
        }
        ArrayList<String> match = partialMatch(key);
        if (match.size() == 0){
            System.out.println("No matches found");
            return;
        }

        for (String line : match){
            System.out.println(line);
        }


    }

    private boolean validateQuery (String word){
        // regex explained ^ -> beginning of string
        // [a-zA-Z0-9] any char which is alphanumeric
        //*$ end of string
        // ^ is outside the square brackets, meaning consider the whole string and not just where the alphanumeric characters start
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        return p.matcher(word).find();
    }

    private HashSet<String> searchIndex (String word, boolean fullWord){
        /*
            params -> word -> word to be searched for
                      fullWord -> is a boolean which if true means we search for a full word
                                  else we search for partial word

        */
        if (fullWord){
            return invertedIndex.get(word);
        }

        //since method has to return a set, we create one, and add documents which contain said partial word into this set
        HashSet<String> docs = new HashSet<>();
        for (String key : invertedIndex.keySet()){
            if (key.contains(word)){
                for (String d : invertedIndex.get(key)){
                    docs.add(d);
                }
            }
        }
        return docs;
    }

    public HashMap<String, HashSet<String>> getInvertedIndex() {
        return invertedIndex;
    }
}
