/*
 Author Name : Shubham Pareek
 Class function : Blueprint of an inverted index, and how it will store data. // more details below
 Project Number : 1
*/

//references //https://stackoverflow.com/questions/11796985/java-regular-expression-to-remove-all-non-alphanumeric-characters-except-spaces to get regex for removing non-alphanumeric characters

package cs601.project1;

import java.security.InvalidParameterException;
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
    private HashMap <String, ArrayList<String>> documents = new HashMap<>(); //is the hashmap which stores the documents
                                                                             //the key will be the document id, and the value is the document.

    public void addDocument (ArrayList<String> doc, String docId){
        //method to add documents to the invertedIndex
        if (documents.containsKey(docId)){ //this method only allows new documents
            throw new InvalidParameterException("Key already exists, if you want to update an existing document you InvertedIndex.update() method");
        }

        documents.put(docId, doc); //putting the document in the documents HashMap

        for (String line : doc){
            line = line.replaceAll("[^a-zA-Z0-9\\s]", "");
            String[] words = line.split(" ");
            for (String word : words){
                if (!invertedIndex.containsKey(word)){
                    HashSet<String> docSet = new HashSet<>();
                    docSet.add(docId);
                }
                else {
                    invertedIndex.get(word).add(docId);
                }
            }
        }



    }


}
