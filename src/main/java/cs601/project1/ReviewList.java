/*
     Reference:
        https://stackoverflow.com/a/38194881 to learn how to convert an Object ArrayList to that of a specified class
*/
package cs601.project1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.ArrayList;

/*
    ReviewList and QAList will have alot of the same methods. So we make an abstract class ItemList which will have those common methods as well as abstract methods for when we
    need our own implementation.

    common methods -> cleanString, searchIndex, partialSearchIndex, validateQuery, findASIN, printKey, printPartialKey, printAsin
    abstract methods -> populateReviewList ( will become populateList), populateReviewsToBeIndexed ( -> populateListToBeIndexed), populateAsinToReviews ( -> populateAsinToItems), addToIndex

    common variables -> reviewsToBeIndexed (-> itemToBeIndexed), idToReviews (-> idToItems), asinToReview (-> asinToItem), invertedIndex
*/

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

        for (String review : itemsToBeIndexed){
            idNum++; //our id starts from R1
            String docId = idPrefix+idNum;
            index.addDocument(review, docId);
            idToItems.put(docId, review);
        }
    }

    @Override
    public void populateAsinToItems() {
        for (Review r : reviewList){
            String asin = r.getAsin();
            String review = r.getReviewText();

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
