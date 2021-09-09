package cs601.project1;

import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

//carefully see the json file and have according file type

public class Test {
    public static void main(String[] args) {
        Gson gson = new Gson();

        String fileLoc = "/home/shubham/IdeaProjects/project1-shubham0831/Cell_Phones_and_Accessories_5.json";
        ArrayList<String> jsonObjects = readFile(fileLoc);
        for (String s : jsonObjects){
            Review r = gson.fromJson(s, Review.class);
        }

        fileLoc = "/home/shubham/IdeaProjects/project1-shubham0831/qa_Cell_Phones_and_Accessories.json";
        jsonObjects = readFile(fileLoc);
        for (String s : jsonObjects){
            QuestionAnswer q = gson.fromJson(s, QuestionAnswer.class);
            System.out.println(q);
            System.out.println("+===============+");
        }

    }

    public static ArrayList<String> readFile (String fileLoc){
        ArrayList<String> jsonObjects = new ArrayList<>();
        File f = new File(fileLoc);
        Gson gson = new Gson();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "ISO-8859-1"))) {
            for (int i = 0; i < 5; i++){
                String line = br.readLine();
                jsonObjects.add(line);
            }
//            while (br.readLine() != null){
//
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObjects;
    }
}
