/*
 Author Name : Shubham Pareek
 Class function : Main // more details below
 Project Number : 1
*/

/*
    This is the main class from where the user will create inverted index and perform actions on it as they desire.
*/

package cs601.project1;

import java.util.ArrayList;

public class AmazonSearch {

    public static void main(String[] args) {
        String [] a = {"a", "b", "c"};
        Review r;
        ArrayList<Object> b = testMethod("aa");
        ArrayList<Review> c = (ArrayList<Review>)(ArrayList<?>)(b);
        for (Review d : c){
            System.out.println(d.toString());
        }
    }

    public static ArrayList<Object> testMethod (String something){
        ArrayList<Object> a = new ArrayList<>();
        String[] b = {"0", "0"};
        a.add(new Review("a", "b", "c", b,"e", "f", "g", "h", "i" ));
        a.add(new Review("j", "k", "l", b,"m", "n", "o", "p", "q" ));

        return a;
    }

}
