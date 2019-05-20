package DatasetGenerator;

import AVL.AVLTree;
import Data.Post;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GenerateAVL {
    public static void main(String[] args) throws IOException {
        System.out.println("Number of posts: ");
        Scanner sc = new Scanner(System.in);
        int numPosts = sc.nextInt();
        System.out.println("Number of users: ");
        int numUsers = sc.nextInt();

        //AVLTree<Post> avltree = new AVLTree<>();
        for (int i = 0; i < numPosts; i++) {
            Random rdm = new Random();
            int arraysize = rdm.nextInt(numUsers);
            String[] likedBy = new String[arraysize];
            for (int j = 0; j < arraysize; j++){
                likedBy[j] = Utils.createName();
            }
            Double[] location = new Double[2];
            location[0] = (double) rdm.nextInt((1200) + 1);
            location[1] = (double) rdm.nextInt((700) + 1);
            arraysize = rdm.nextInt(numUsers);
            String[] hashtags = new String[arraysize];
            for (int j = 0; j < arraysize; j++){
                hashtags[j] = Utils.createName();
            }
            //avltree.insert(new Post());
        }

        Gson gson = new Gson();
        String jsonString;
        //jsonString = gson.toJson(avltree);
        FileWriter fw = new FileWriter("datasets/generated/datasetAVL" + numPosts + ".txt");
        //fw.write(jsonString);
        fw.close();
    }
}
