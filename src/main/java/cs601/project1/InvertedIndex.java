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
    private HashMap <String, HashSet<String>> invertedIndex = new HashMap<>(); //is the inverted index

    //this hashmap will have keys as the docId, and the value will be the HashMap which has the key as the word and the value as the number of
    //times the word has occurred in the document.
    private HashMap <String, HashMap<String, Integer>> wordCountInDoc = new HashMap<>(); //keep this


    public HashMap<String, HashMap<String, Integer>> getWordCountInDoc() {
        return wordCountInDoc;
    }

    public void addDocument (String line, String docId){
        //method to add documents to the invertedIndex

        String [] words = line.split(" ");
        for (String word : words){
            word = word.toLowerCase().strip();

            //first we update the invertedIndex
            if (!invertedIndex.containsKey(word) && word != ""){
                //if our invertedIndex does not have the word, we create the set which stores all the document the
                //word is in then we enter the word-set pair into the invertedIndex hashmap
                HashSet<String> docSet = new HashSet<>();
                docSet.add(docId);
                invertedIndex.put(word, docSet);
            }
            else if (word != ""){
                invertedIndex.get(word).add(docId); //get the set of docs which contain the word, and add current docs
                                                    //to that set
            }

            //now we update the word count
            if (!wordCountInDoc.containsKey(docId) && word != ""){
                HashMap <String, Integer> wordCount = new HashMap<>();
                wordCount.put(word, 1);
                wordCountInDoc.put(docId, wordCount);
            }
            else if (word != ""){
                //if wordCountInDoc does contain docId, then check if the given doc has seen the word before or not
                if (!wordCountInDoc.get(docId).containsKey(word)){
                    //if this is the first instance of the word in our document, then we put the word in along with its initial count
                    //as one
                    wordCountInDoc.get(docId).put(word, 1);
                }
                else {
                    int newCount = wordCountInDoc.get(docId).get(word) + 1; //updating the count
                    wordCountInDoc.get(docId).put(word, newCount); //putting the new count
                }
            }
        }
    }


    public HashSet<String> find (String key){
        key = key.toLowerCase(); //all indexes are in lowerCase
        HashSet<String> docs = searchIndex(key, true); // is the set of doc ID which contains the given string
        return docs;
    }

    public HashSet<String> partialFind (String key){
        key = key.toLowerCase(); //all indexes are in lowerCase
        HashSet<String> docs = searchIndex(key, false); // is the set of doc ID which contains the given string
        return docs;
    }

    private HashSet<String> searchIndex (String word, boolean fullWord){
        /*
            params -> word -> word to be searched for
                      fullWord -> is a boolean which if true means we search for a full word
                                  else we search for partial word

            This method returns a set of docId which contains the given word, that set can then be used to get access to the full doc

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
