package DatasetGenerator;

import Data.User;
import List.List;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class GenerateUser {
    public static void main(String[] args) {
        System.out.println("Number of users: ");
        Scanner sc = new Scanner(System.in);
        int numUsers = sc.nextInt();
        List<User> users = new List<User>();

        Gson gson = new Gson();
        String jsonString = "";

        for (int i = 0; i < numUsers; i++) {
            Date date = new Date();
            users.add(new User(createName(), date.toString()));
        }
        for (int i = 0; i < users.size(); i++) {
            jsonString += gson.toJson(users.get(i)) + "\n";
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

    private static String createName() {
        Random rdm = new Random();
        String name = "";
        for (int i = 0; i < rdm.nextInt(10); i++) {
            name += (char) (rdm.nextInt(26) + 'a');
        }
        return name;
    }

}
