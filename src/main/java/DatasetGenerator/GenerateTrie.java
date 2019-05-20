package DatasetGenerator;

import Trie.TrieTree;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GenerateTrie {
    public static void main(String[] args) throws IOException {
        System.out.println("Number of users: ");
        Scanner sc = new Scanner(System.in);
        int numUsers = sc.nextInt();

        TrieTree trie = new TrieTree();
        for (int i = 0; i < numUsers; i++) {
            trie.insert(Utils.createName());
        }
        Gson gson = new Gson();
        String jsonString;
        jsonString = gson.toJson(trie);
        FileWriter fw = new FileWriter("datasets/generated/datasetAVL" + numUsers + ".txt");
        fw.write(jsonString);
        fw.close();
    }
}
