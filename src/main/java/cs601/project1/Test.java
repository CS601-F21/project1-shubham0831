package cs601.project1;

import java.util.HashMap;
import java.util.Scanner;


//we will use the FileOperator class to do operations on the file

public class Test {
    public static void main(String[] args) {
        String file1 = "/home/shubham/IdeaProjects/project1-shubham0831/Cell_Phones_and_Accessories_5.json";
        String file2 = "/home/shubham/IdeaProjects/project1-shubham0831/qa_Cell_Phones_and_Accessories.json";

        ReviewList r = new ReviewList("ISO-8859-1");
        QAList qa = new QAList("ISO-8859-1");

        r.addToIndex(file1);
        qa.addToIndex(file2);

        qa.findAsin("1466736038");

    }
}
