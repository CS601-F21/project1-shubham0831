/*
    Author Name : Shubham Pareek
    Class function : Blueprint of a review object and everything that it contains as well as has mapping to and fro from docId to review object
    Project Number : 1
     Reference:
        https://stackoverflow.com/a/38194881 to learn how to convert an Object ArrayList to that of a specified class
*/
package cs601.project1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.*;
import java.util.ArrayList;


public class ReviewList extends ItemList {

    private String charSet;

    private ArrayList<Review> reviewList; //is the list containing all the reviewsd
    private String idPrefix = "R";
    private int idNum = 0;

    public ReviewList (String charset){
        super();
        this.charSet = charset;
        reviewList = new ArrayList<>();
    }

    @Override
    public void populateItemList(String fileLoc) {
        File f = new File(fileLoc);
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charSet))) {
            String line;
            while ((line = br.readLine()) != null){
                try {
                    Review review = gson.fromJson(line, Review.class);
                    reviewList.add(review);
                }
                catch (JsonSyntaxException jse){
                    continue;
                }
            }
//            for (int i = 0; i < 10; i++){
//                Review r = gson.fromJson(br.readLine(), Review.class);
//                reviewList.add(r);
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void populateItemsToBeIndexed() {
        //method to obtain the reviewText and populate the reviewsToBeIndexed list
        int i = 0;
        for (Review r : reviewList){
            String review = r.getReviewText();
            itemsToBeIndexed.add(cleanString(review));
        }
    }

    @Override
    public void addToIndex(String fileLoc) {
        //method the user will call to add the reviews file to the index
        populateItemList(fileLoc); //first we populate the reviewList
        populateItemsToBeIndexed(); //then we populate the list of reviews we want to index
        populateAsinToItems(); //we also populate the asin to item HashMap

        for (String review : itemsToBeIndexed){
            idNum++; //our id starts from R1
            String docId = idPrefix+idNum;
            index.addDocument(review, docId);
            idToItems.put(docId, review);
        }
    }

    @Override
    public void populateAsinToItems() {
        int i = 0;
        for (Review r : reviewList){
            String asin = r.getAsin().toLowerCase();
            String review = r.getReviewText().toLowerCase();

            if (!asinToItem.containsKey(asin)){
                //if this is the first time we encounter the asin key, add it to the map along with the review
                ArrayList<String> reviews = new ArrayList<>();
                reviews.add(review);
                asinToItem.put(asin, reviews);
            }
            else {
                asinToItem.get(asin).add(review);
            }
        }
    }
}
