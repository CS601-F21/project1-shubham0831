package cs601.project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//we will use the FileOperator class to do operations on the file

public class Test {
    public static void main(String[] args) {
        String file1 = "/home/shubham/IdeaProjects/project1-shubham0831/Cell_Phones_and_Accessories_5.json";
        String file2 = "/home/shubham/IdeaProjects/project1-shubham0831/qa_Cell_Phones_and_Accessories.json";

        ReviewList r = new ReviewList("ISO-8859-1");
        r.addToIndex(file1);
        HashMap<String, String> idToReviews = r.getIdToReviews();
        InvertedIndex i = r.getIndex();
        for (String key : idToReviews.keySet()){
            System.out.println(key + " --> " + idToReviews.get(key));
        }
//        HashMap<String, HashSet<String>> index = i.getInvertedIndex();
        System.out.println("==============");
//        r.searchIndex("it");
        System.out.println(r.searchIndex("its"));
    }
}
