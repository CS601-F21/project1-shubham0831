package cs601.project1;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

public abstract class ItemList {
    protected ArrayList<String> itemsToBeIndexed; //these are the list of items in string format to be indexed
    protected HashMap<String, String> idToItems; // map containing mapping from docId to the Item
    protected HashMap<String, ArrayList<String>> asinToItem; ////map containing mapping from ASIN to a list of items having that ASIN
    protected InvertedIndex index;

    public ItemList (){
        this.itemsToBeIndexed = new ArrayList<>();
        this.idToItems = new HashMap<>();
        this.asinToItem = new HashMap<>();
        this.index = new InvertedIndex();
    }

    public abstract void populateItemList (String fileLoc);
    public abstract void populateItemsToBeIndexed ();
    public abstract void addToIndex (String fileLoc);
    public abstract void populateAsinToItems ();

    public String cleanString(String line){
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

    public ArrayList<String> searchIndex (String word){
        //method to search the index for a given key
        boolean validQuery = validateQuery(word);
        if (!validQuery){
            //if query contains alphanumerics then it is not a valid query
            throw new InvalidParameterException("Key should only contain alphanumerics");
        }
        return index.find(word);
    }

    public HashSet<String> partialSearchIndex (String word){
        boolean validQuery = validateQuery(word);
        if (!validQuery){
            //if query contains alphanumerics then it is not a valid query
            throw new InvalidParameterException("Key should contain only alphanumerics");
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

    public HashMap<String, ArrayList<String>> getAsinToItem() {
        return asinToItem;
    }

    public ArrayList<String> findAsin (String asin){
        ArrayList<String> items = asinToItem.get(asin);
        if (items == null){
            System.out.println("No such review found");
            return new ArrayList<String>();
        }
        System.out.println(items.size());
        return items;
    }

    public void printKey (String word){
        //method to call when you want to print all the reviews that have given word
        ArrayList<String>  docList = searchIndex(word);

        if (docList.size() == 0){
            System.out.println("No such document exists");
            return;
        }

        for (String id : docList){
            System.out.println(idToItems.get(id));
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
            System.out.println(idToItems.get(id));
        }

        return;
    }

    public void printAsin (String asin){
        ArrayList<String> docs = findAsin(asin);
        for (String review : docs){
            System.out.println(review);
        }
    }

    public HashMap<String, String> getIdToItems() {
        return idToItems;
    }

    public InvertedIndex getIndex (){
        return index;
    }
}
