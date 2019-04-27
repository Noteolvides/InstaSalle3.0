package DatasetGenerator;

import Data.User;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class GenerateUser {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numUsers = sc.nextInt();

        Gson gson = new Gson();
        String jsonString = "";

        for (int i = 0; i < numUsers; i++) {
            Random rdm = new Random();
            Date date = new Date();
            jsonString += gson.toJson(new User(Integer.toString(rdm.nextInt(100)), date.toString()));
        }
        try {
            System.out.println(jsonString);
            FileWriter fw = new FileWriter("datasetUsers_" + numUsers + ".txt");
            fw.write(jsonString);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
