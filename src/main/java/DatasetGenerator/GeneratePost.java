package DatasetGenerator;

import Data.Post;
import List.List;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class GeneratePost {
    public static void main(String[] args) {
        System.out.println("Number of posts: ");
        Scanner sc = new Scanner(System.in);
        int numPosts = sc.nextInt();
        System.out.println("Number of users: ");
        int numUsers = sc.nextInt();
        List<Post> posts = new List<Post>();

        Gson gson = new Gson();
        String jsonString = "";

        Random rdm = new Random();

        for (int i = 0; i < numPosts; i++) {
            int arraysize = rdm.nextInt(numUsers);
            String[] likedBy = new String[arraysize];
            for (int j = 0; j < arraysize; j++){
                likedBy[j] = createName();
            }
            Long[] location = new Long[2];
            location[0] = rdm.nextLong();
            location[1] = rdm.nextLong();
            arraysize = rdm.nextInt(numUsers);
            String[] hashtags = new String[arraysize];
            for (int j = 0; j < arraysize; j++){
                hashtags[j] = createName();
            }
            posts.add(new Post(i, likedBy, new Date().toString(), createName(), location, hashtags));
        }

        for (int i = 0; i < posts.size(); i++) {
            jsonString += gson.toJson(posts.get(i)) + "\n";
        }
        try {
            System.out.println(jsonString);
            FileWriter fw = new FileWriter("datasetPosts" + numPosts + ".txt");
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
