/*
    Author Name : Shubham Pareek
    Class function : Blueprint of a review object and everything that it contains
    Project Number : 1
*/
package cs601.project1;

//this is the class to instantitate the UI and is where we will be making all our queries from
import java.util.Scanner;

/*
    Different terms -> venezuela (getting 39, others 40)
*/

public class ProjectUI {
    private ReviewList r;
    private QAList qa;
    private String reviewFile;
    private String qaFile;

    private boolean running = true;

    public ProjectUI (ReviewList r, String reviewFile, QAList qa, String qaFile){
        this.r = r;
        this.qa = qa;
        this.reviewFile = reviewFile;
        this.qaFile = qaFile;
        createIndex();
    }

    private void createIndex (){
        r.addToIndex(reviewFile);
        qa.addToIndex(qaFile);
    }

    public void startUI (){
        while (running){
            getCommand();
        }
    }

    private void getCommand (){
        System.out.println("Enter command, anything else will exit the code");

        Scanner sc = new Scanner(System.in, "ISO-8859-1");
        String command = sc.nextLine();
        String instruction = command.split(" ")[0].strip();
        String word = command.split(" ")[1].strip();

        if (instruction.equals("find")){
            printAsin(word);
        }
        else if (instruction.equals("reviewsearch")){
            reviewSearch(word);
        }
        else if (instruction.equals("qasearch")){
            qaSearch(word);
        }
        else if (instruction.equals("reviewpartialsearch")){
            reviewPartialSearch(word);
        }
        else if (instruction.equals("qapartialsearch")){
            qaPartialSearch(word);
        }
        else {
            running = false;
        }
        return;
    }


    private void printAsin (String word){
        System.out.println("reviews : ");
//        r.printAsin(word);
        r.findAsin(word);
        System.out.println("QAs : ");
//        qa.printAsin(word);
        qa.findAsin(word);
    }

    private void reviewSearch (String word){
//        r.printKey(word);
        r.searchIndex(word);
    }

    private void qaSearch (String word){
//        qa.printKey(word);
        qa.searchIndex(word);
    }

    private void reviewPartialSearch (String word){
//        r.printPartialKey(word);
        r.partialSearchIndex(word);
    }

    private void qaPartialSearch (String word){
//        qa.printPartialKey(word);
        qa.partialSearchIndex(word);
    }


}
