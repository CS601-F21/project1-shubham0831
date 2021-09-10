package cs601.project1;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

//carefully see the json file and have according file type

public class Test {
    public static void main(String[] args) {
        String file1 = "/home/shubham/IdeaProjects/project1-shubham0831/Cell_Phones_and_Accessories_5.json";
        String file2 = "/home/shubham/IdeaProjects/project1-shubham0831/qa_Cell_Phones_and_Accessories.json";

        FileParser fp = new FileParser();
        ArrayList <String> doc1 = fp.parseFile(file1, "Review");
        ArrayList <String> doc2 = fp.parseFile(file2, "QuestionAnswer");

        //now pass both the docs into the invertedIndex.
        InvertedIndex index = new InvertedIndex();
        index.addDocument(doc1, "d1");
        index.addDocument(doc2, "d2");

        /*
            The data which we pass into our invertedIndex will just be and ArrayList of HashMap (tbd) plain string, or string representation of the the objects. Ie. object.toString()
            This will ensure that our InvertedIndex remains agnostic of the input files.

            The HashMap can contain the keyValue pair {documentID, ArrayList<document.toString()>} making it easier for the inverted index to index the file.

            Thought -> While making it easier to index, when we will search the document for the relevant keywords, we might have to brute force search through all the elements in the arrayList
        */



    }

}
