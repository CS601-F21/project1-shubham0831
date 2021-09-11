package cs601.project1;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//carefully see the json file and have according file type

public class Test {
    public static void main(String[] args) {

        String file1 = "/home/shubham/IdeaProjects/project1-shubham0831/Cell_Phones_and_Accessories_5.json";
        String file2 = "/home/shubham/IdeaProjects/project1-shubham0831/qa_Cell_Phones_and_Accessories.json";

        FileParser fp = new FileParser();
        ArrayList <String> doc1 = fp.parseFile(file1, "Review");
        ArrayList <String> doc2 = fp.parseFile(file2, "QuestionAnswer");


//        for (String s : doc1){
//            System.out.println(s);
//        }
//
//        System.out.println("+====+");

//        for (String s : doc2){
//            System.out.println(s);
//        }
//
//        System.out.println("+=====+");


        //now pass both the docs into the invertedIndex.
        InvertedIndex index = new InvertedIndex();
        index.addDocument(doc1, "d1");
        index.addDocument(doc2, "d2");

        HashMap<String, HashSet<String>> invertedIndex = index.getInvertedIndex();
        index.find("120401325x");
//        System.out.println(invertedIndex.get(key));



//        for (String s: invertedIndex.keySet()){
//            System.out.println(s + " ===> " + invertedIndex.get(s));
//        }

        /*
            For the review dataset you will index the text in the field reviewText. For the Q&A dataset
            you will index both the question and answer fields. This will allow you to search for a term and
            get a list of reviews where the review text contains the term and a list of Q&A results where the
            question or answer contains the term.

            todo right now we are indexing every word, we only have to index the question and answer from the QA Dataset
                 and only the reviews from the review Dataset
        */


        /*
            The data which we pass into our invertedIndex will just be and ArrayList of HashMap (tbd) plain string, or string representation of the the objects. Ie. object.toString()
            This will ensure that our InvertedIndex remains agnostic of the input files.

            The HashMap can contain the keyValue pair {documentID, ArrayList<document.toString()>} making it easier for the inverted index to index the file.

            Thought -> While making it easier to index, when we will search the document for the relevant keywords, we might have to brute force search through all the elements in the arrayList
        */



    }

}
