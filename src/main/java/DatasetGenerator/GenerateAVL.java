package DatasetGenerator;

import AVL.AVLTree;
import Data.Post;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GenerateAVL {
    public static void main(String[] args) throws IOException {
        System.out.println("Number of posts: ");
        Scanner sc = new Scanner(System.in);
        int numPosts = sc.nextInt();

        AVLTree<Post> avltree = new AVLTree<>();
        for (int i = 0; i < numPosts; i++) {
            avltree.insert(new Post());
        }

        Gson gson = new Gson();
        String jsonString;
        jsonString = gson.toJson(avltree);
        FileWriter fw = new FileWriter("datasets/generated/datasetAVL" + numPosts + ".txt");
        fw.write(jsonString);
        fw.close();
    }
}
