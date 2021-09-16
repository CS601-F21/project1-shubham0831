package cs601.project1;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

/*
    ReviewList and QAList have a lot of the same methods. So we make an abstract class ItemList which will have those common methods as well as abstract methods for when we
    need our own implementation.

    common methods -> cleanString, searchIndex, partialSearchIndex, validateQuery, findASIN, printKey, printPartialKey, printAsin (Essentially all retrieval will be the same)
    abstract methods -> populateList, populateListToBeIndexed, populateAsinToItems, addToIndex (All populating of the datastructures for retrieval will be different)

    common variables -> itemToBeIndexed, idToItems, asinToItem, invertedIndex
*/

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
        return index.find(word); //not checking for null and returning empty arrayList if doc doesn't contain word since inverted index will do that
    }

    public HashSet<String> partialSearchIndex (String word){
        boolean validQuery = validateQuery(word);
        if (!validQuery){
            //if query contains alphanumerics then it is not a valid query
            throw new InvalidParameterException("Key should contain only alphanumerics");
        }
        return index.partialFind(word); //not checking for null and returning empty arrayList if doc doesn't contain word since inverted index will do that
    }

    public ArrayList<String> findAsin (String asin){
        ArrayList<String> items = asinToItem.get(asin);
        if (items == null){
            System.out.println("No such item found");
            return new ArrayList<String>();
        }
        System.out.println(items.size());
        return items;
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

    public void printKey (String word){
        //method to call when we want to print all the reviews that have given word
        ArrayList<String>  docList = searchIndex(word);

        if (docList.size() == 0){
            System.out.println("No such element found");
            return;
        }

        for (String id : docList){
            System.out.println(idToItems.get(id));
        }

        return;
    }

    public void printPartialKey (String word){
        //method to call when we want to print all reviews that contain the given partial word
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
        //method to call when we want to print all the Items which have the given ASIN number
        ArrayList<String> docs = findAsin(asin);

        if (docs.size() == 0){
            System.out.println("No such element found");
            return;
        }

        for (String review : docs){
            System.out.println(review);
        }
    }

    public HashMap<String, String> getIdToItems() {
        //returns mapping from the docId to the items
        return idToItems;
    }

    public InvertedIndex getIndex (){
        //returns the inverted index
        return index;
    }

    public HashMap<String, ArrayList<String>> getAsinToItem() {
        //returns the mapping from the ASIN key to the DocId
        return asinToItem;
    }
}
