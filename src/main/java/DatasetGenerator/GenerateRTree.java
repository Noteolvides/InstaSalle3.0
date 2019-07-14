package DatasetGenerator;

import Data.Post;

import R_Tree.Point;
import R_Tree.RTree;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GenerateRTree {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Number of posts: ");
        int numPosts = sc.nextInt();
        System.out.println("Number of users: ");
        int numUsers = sc.nextInt();

        RTree rTree = new RTree();
        Random rdm = new Random();

        for (int i = 0; i < numPosts; i++) {
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
            Post post = new Post(i, likedBy, rdm.nextLong(), Utils.createName(), (Double[]) location, hashtags);
            Random rd = new Random();
            rTree.insert(new Point(rd.nextInt(RTree.WIDTH_SCREEN), rd.nextInt(RTree.HEIGHT_SCREEN)));        }

        Gson gson = new Gson();
        String jsonString;
        jsonString = gson.toJson(rTree);
        FileWriter fw = new FileWriter("datasets/generated/datasetRTree" + numPosts + ".json");
        fw.write(jsonString);
        fw.close();
    }
}
