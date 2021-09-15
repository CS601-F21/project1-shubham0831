/*
     Reference:
        https://stackoverflow.com/a/38194881 to learn how to convert an Object ArrayList to that of a specified class
*/
package cs601.project1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

public class ReviewList {

    private String charSet;

    private ArrayList<Review> reviewList; //is the list containing all the reviews
    private ArrayList<String> reviewsToBeIndexed; //these are the list of reviews to be indexed
    private HashMap<String, String> idToReviews; // map containing mapping from docId to reviewText
    private HashMap<String, ArrayList<String>> asinToReview; //map containing mapping from ASIN to a list of reviews about that ASIN
    private String idPrefix = "R";
    private int idNum = 0;
    private InvertedIndex index; //is where object where we will be indexing our reviews

    public ReviewList (String charset){
        this.charSet = charset;
        reviewList = new ArrayList<>();
        reviewsToBeIndexed = new ArrayList<>();
        idToReviews = new HashMap<>();
        asinToReview = new HashMap<>();
        index = new InvertedIndex();
    }

    public InvertedIndex getIndex (){
        return index;
    }


    private void populateReviewList (String fileLoc){
        File f = new File(fileLoc);
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charSet))) {
//            String line;
//            while ((line = br.readLine()) != null){
//                try {
//                    Review review = gson.fromJson(line, Review.class);
//                    reviewList.add(review);
//                }
//                catch (JsonSyntaxException jse){
//                    continue;
//                }
//            }
                for (int i = 0; i < 10; i++){
                    Review r = gson.fromJson(br.readLine(), Review.class);
                    reviewList.add(r);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        ArrayList<String> objToBeReturned = (ArrayList<String>)(ArrayList<?>)(jsonObjects);
        //        return objToBeReturned;
    }

    public void addToIndex (String fileLoc){
        //method the user will call to add the reviews file to the index
        populateReviewList(fileLoc); //first we populate the reviewList
        populateReviewsToBeIndexed(); //then we populate the list of reviews we want to index

        for (String review : reviewsToBeIndexed){
            idNum++; //our id starts from R1
            String docId = idPrefix+idNum;
            index.addDocument(review, docId);
            idToReviews.put(docId, review);
        }
    }

    private void populateReviewsToBeIndexed (){
        //method to obtain the reviewText and populate the reviewsToBeIndexed list
        for (Review r : reviewList){
            String review = r.getReviewText();
            reviewsToBeIndexed.add(cleanString(review));
        }
    }

    private void populateAsinToReviews () {
        for (Review r : reviewList){
            String asin = r.getAsin();
            String review = r.getReviewText();

            if (!asinToReview.containsKey(asin)){
                //if this is the first time we encounter the asin key, add it to the map along with the review
                ArrayList<String> reviews = new ArrayList<>();
                reviews.add(review);
                asinToReview.put(asin, reviews);
            }
            else {
                asinToReview.get(asin).add(review);
            }
        }
    }

    private String cleanString (String line){
        //given a string, method will remove all alphaNumerics from the string
        String cleanLine = line.replaceAll("[^a-zA-Z0-9\\s]", "");
        cleanLine = cleanLine.toLowerCase();
        /*
            regex explanation
                   the ^ is inside the bracket meaning that replace anything around alphanumeric charcaters with ""
                   \\s is for space, since we dont want to remove space right now
        */
        return cleanLine;
    }

    public HashSet<String> searchIndex (String word){
        //method to search the index for a given key
        boolean validQuery = validateQuery(word);
        if (!validQuery){
            //if query contains alphanumerics then it is not a valid query
            throw new InvalidParameterException("Key should not contain alphanumerics");
        }
        return index.find(word);

    }

    public HashSet<String> partialSearchIndex (String word){
        boolean validQuery = validateQuery(word);
        if (!validQuery){
            //if query contains alphanumerics then it is not a valid query
            throw new InvalidParameterException("Key should not contain alphanumerics");
        }
        return index.partialFind(word);
    }

    private boolean validateQuery (String word){
        //method to validate whether the query is valid or not
        // regex explained ^ -> beginning of string
        // [a-zA-Z0-9] any char which is alphanumeric
        //*$ end of string
        // ^ is outside the square brackets, meaning consider the whole string and not just where the alphanumeric characters start
        Pattern p = Pattern.compile("^[a-zA-Z0-9]*$");
        return p.matcher(word).find();
    }

    public ArrayList<String> findAsin (String asin){
        populateAsinToReviews();
        ArrayList<String> reviews = asinToReview.get(asin);
        return reviews;
    }

    public void printKey (String word){
        //method to call when you want to print all the reviews that have given word
        HashSet<String>  docList = searchIndex(word);

        if (docList.size() == 0){
            System.out.println("No such element exists");
            return;
        }

        for (String id : docList){
            System.out.println(idToReviews.get(id));
        }

        return;
    }

    public void printPartialKey (String word){
        //method to call when you want to print all reviews that contain the given partial word
        HashSet<String> docList = partialSearchIndex(word);

        if (docList.size() == 0){
            System.out.println("No such element found");
            return;
        }

        for (String id : docList){
            System.out.println(idToReviews.get(id));
        }

        return;
    }

    public HashMap<String, String> getIdToReviews() {
        return idToReviews;
    }
}
