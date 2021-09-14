package cs601.project1;

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

        FileParser fp = new FileParser("ISO-8859-1");
        ArrayList <String> doc1 = fp.parseFile(file1, "Review");
        ArrayList <String> doc2 = fp.parseFile(file2, "QuestionAnswer");


//        for (String s : doc1){
//            System.out.println(s);
//        }
//
//        System.out.println("+====+");
//
//        for (String s : doc2){
//            System.out.println(s);
//        }



        //now pass both the docs into the invertedIndex.
        InvertedIndex index1 = new InvertedIndex();
        InvertedIndex index2 = new InvertedIndex();
        index1.addFile(doc1);
        index2.addFile(doc2);

        System.out.println("Index built +++++++++++++++++");

        HashMap<String, String> invertedIndex = index1.getDocuments();
//        for (String key : invertedIndex.keySet()){
//            System.out.println(key + " --> " +invertedIndex.get(key));
//        }
        System.out.println("Full search ");
        String key = "these";
        index1.find(key);
        System.out.println("=======++++");
    }
}
