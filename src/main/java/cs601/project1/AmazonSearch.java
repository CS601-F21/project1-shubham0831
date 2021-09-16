/*
 Author Name : Shubham Pareek
 Class function : Main // more details below
 Project Number : 1
*/

/*
    This is the main class from where the user will create inverted index and perform actions on it as they desire.
*/

package cs601.project1;

public class AmazonSearch {

    public static void main(String[] args) {
        String file1 = "/home/shubham/IdeaProjects/project1-shubham0831/Cell_Phones_and_Accessories_5.json";
        String file2 = "/home/shubham/IdeaProjects/project1-shubham0831/qa_Cell_Phones_and_Accessories.json";

        ReviewList r = new ReviewList("ISO-8859-1");
        QAList qa = new QAList("ISO-8859-1");
//
//        qa.addToIndex(file2);
//
//        HashMap<String, ArrayList<String>> aToI= qa.getAsinToItem();
//        System.out.println(aToI.get("1466736038").size());

        ProjectUI ui = new ProjectUI(r, file1, qa, file2);
        ui.startUI();

//        qa.findAsin("1466736038");
    }
}
