/*
 Author Name : Shubham Pareek
 Class function : Blueprint of an inverted index, and how it will store data. // more details below
 Project Number : 1
*/

//references https://stackoverflow.com/questions/11796985/java-regular-expression-to-remove-all-non-alphanumeric-characters-except-spaces to get regex for removing non-alphanumeric characters
//           https://stackoverflow.com/a/64271783 to learn how to match a whole word //didn't end up using it
//           https://www.geeksforgeeks.org/iterate-treemap-in-reverse-order-in-java/ to learn how to iterate a treemap in reverse order

//implemented validateKeys to check whether the key is valid or not in the ReviewList and QAList method instead of over here, since the keys were not valid for this use case, our invertedIndex
//does infact

package cs601.project1;

import java.util.*;


public class InvertedIndex {
    /*
        Constructor -> doesn't take any argument

        We need to have a data structure for each unique document for search and retrieval

        Algorithm for populating the invertedIndex -> 1) Split document to words, then add each word to the inverted index, and the document which the word occurs in, in the hashset of documents
                                                      2) Also while we are inserting a word, we also update the count of the number of times the given word has occurred in the wordCountInDoc

        Algorithm for fullSearch -> 1) Retrieve the set of documents which have the given word
                                    2) Get count of number of times the word has appeared in the document
                                    3) Store the count and docId in a treeMap with the count being the id
                                    4) Traverse the treeMap in reverse order and keep adding the documents to the arrayList
                                    5) Return the arrayList

        Algorithm for partialSearch -> 1) For every key in inverted index, if the key contains the word, store the document in a set
                                       2) Return the set of documents. Since we do not need to implement sorting, this is enough.

    */

    private HashMap <String, HashSet<String>> invertedIndex; //is the inverted index

    //this hashmap will have docIds as the keys, and the value will be a HashMap which has the word as the key and the count of that word in the doc as the value
    private HashMap <String, HashMap<String, Integer>> wordCountInDoc; //is the hashmap we use for retrieving the count of the word in a given document

    public InvertedIndex (){
        this.invertedIndex = new HashMap<>();
        this.wordCountInDoc = new HashMap<>();
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
                    //otherwise we just update the count of the word
                    int newCount = wordCountInDoc.get(docId).get(word) + 1; //updating the count
                    wordCountInDoc.get(docId).put(word, newCount); //putting the new count
                }
            }
        }
    }


    public ArrayList<String> find (String key){
        key = key.toLowerCase(); //all indexes are in lowerCase
        HashSet<String> docs = searchIndex(key, true); // is the set of doc ID which contains the given string

        if (docs == null){
//            System.out.println("No such element found");
            return new ArrayList<String>();
        }

        //structure of this tree map is as follows
        //key -> integer value having the count of the word
        //value -> list of documents that have the word and the count of the word is the same as the key
        TreeMap<Integer, ArrayList<String>> docWordCount = new TreeMap<>(Collections.reverseOrder()); //using tree map because it sorts the input on the basis of the key
        for (String docId : docs){
            int count = wordCountInDoc.get(docId).get(key); //for every document that contains the word, get the count of the word
            if (!docWordCount.containsKey(count)){
                //using an arraylist since our docs are in a set and we do not have to worry about duplicates
                ArrayList<String> docList = new ArrayList<>();
                docList.add(docId);
                docWordCount.put(count, docList);
            }
            else {
                docWordCount.get(count).add(docId);
            }
        }

        //once we have the treemap, we can just iterate it (which will be in descending order) and add all the docs which have the given word in a sorted order by
        //term frequency
        ArrayList<String> docsInSortedOrder = new ArrayList<>();
        for (int count : docWordCount.keySet()){
            //count will be in descending order, for each count we have a bunch of documents (documents with same number of occurence of the given word)
            //we put those documents in the docsInSortedOrder arraylist  then return the arrayList
            ArrayList<String> docList = docWordCount.get(count);
            for (String doc : docList){
                docsInSortedOrder.add(doc);
            }
        }
//        System.out.println(docsInSortedOrder.size()); //temp solution to check count of docs found
        return docsInSortedOrder;
    }

    public HashSet<String> partialFind (String key){
        key = key.toLowerCase(); //all indexes are in lowerCase
        HashSet<String> docs = searchIndex(key, false); // is the set of doc ID which contains the given string
//        System.out.println(docs.size()); //temp solution to check count of docs found
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

    public HashMap<String, HashMap<String, Integer>> getWordCountInDoc() {
        return wordCountInDoc;
    }
}
