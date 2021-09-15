package cs601.project1;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class QAList extends ItemList{

    String charSet;

    ArrayList<QuestionAnswer> qaList; //is the list containing all the reviews
    HashMap<String, String> indexedText; //is a hashmap which contains the id, the the text we have indexed
    String idPrefix = "Q";
    int idNum = 0;

    public QAList (String charSet){
        super();
        this.charSet = charSet;
        this.qaList = new ArrayList<>();
    }


    @Override
    public void populateItemList(String fileLoc) {
        File f = new File(fileLoc);
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), charSet))) {
            String line;
            while ((line = br.readLine()) != null){
                try {
                    QuestionAnswer qa = gson.fromJson(line, QuestionAnswer.class);
                    qaList.add(qa);
                }
                catch (JsonSyntaxException jse){
                    continue;
                }
            }
//            for (int i = 0; i < 10; i++){
//                QuestionAnswer qa = gson.fromJson(br.readLine(), QuestionAnswer.class);
//                qaList.add(qa);
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
        for (QuestionAnswer qa : qaList){
            String questionAnswer = qa.getQuestion() + " " + qa.getAnswer();
            itemsToBeIndexed.add(cleanString(questionAnswer));
        }
    }

    @Override
    public void addToIndex(String fileLoc) {
        //method the user will call to add the reviews file to the index
        populateItemList(fileLoc); //first we populate the reviewList
        populateItemsToBeIndexed(); //then we populate the list of reviews we want to index

        for (String qa : itemsToBeIndexed){
            idNum++; //our id starts from R1
            String docId = idPrefix+idNum;
            index.addDocument(qa, docId);
            idToItems.put(docId, qa);
        }
    }

    @Override
    public void populateAsinToItems() {
        for (QuestionAnswer qa : qaList){
            String asin = qa.getAsin();
            String questionAnswer = qa.getQuestion() + " " + qa.getAnswer();

            if (!asinToItem.containsKey(asin)){
                //if this is the first time we encounter the asin key, add it to the map along with the review
                ArrayList<String> questionAnswers = new ArrayList<>();
                questionAnswers.add(questionAnswer);
                asinToItem.put(asin, questionAnswers);
            }
            else {
                asinToItem.get(asin).add(questionAnswer);
            }
        }
    }



}
