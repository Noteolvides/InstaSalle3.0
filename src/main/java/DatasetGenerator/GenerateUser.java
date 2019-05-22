package DatasetGenerator;

import Data.User;
import List.List;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
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

        Random rdm = new Random();
        for (int i = 0; i < numUsers; i++) {
            users.add(new User(Utils.createName(), rdm.nextLong()));
        }
        for (int i = 0; i < users.size(); i++) {
            int arraysize = rdm.nextInt(users.size());
            String[] userrelation = new String[arraysize];
            for (int j = 0; j < arraysize; j++) {
                int rdmuser = rdm.nextInt(users.size());
                while (rdmuser == i || inRelation(userrelation, users.get(rdmuser).username)) {
                    rdmuser = rdm.nextInt(users.size());
                }
                userrelation[j] = users.get(rdmuser).username;
            }
            users.get(i).setToFollow(userrelation);
        }

        User[] userjson = new User[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userjson[i] = users.get(i);
        }

        jsonString = gson.toJson(userjson);
        try {
            System.out.println(jsonString);
            FileWriter fw = new FileWriter("datasets/generated/datasetUsers" + numUsers + ".json");
            fw.write(jsonString);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean inRelation(String[] userrelation, String rdmuser) {
        for (String user : userrelation) {
            if (rdmuser.equals(user)) {
                return true;
            }
        }
        return false;
    }
}
