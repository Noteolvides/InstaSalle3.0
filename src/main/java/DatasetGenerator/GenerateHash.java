package DatasetGenerator;

import HashTable.HashTable;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GenerateHash {
    public static void main(String[] args) throws IOException {
        System.out.println("Number of hashtags: ");
        Scanner sc = new Scanner(System.in);
        int hashtags = sc.nextInt();

        HashTable<Integer, String> hashTable = new HashTable<Integer, String>(hashtags);

        for (int i = 0; i < hashtags; i++) {
            hashTable.insert(Utils.createName());
        }

        Gson gson = new Gson();
        String jsonString;
        jsonString = gson.toJson(hashTable);
        FileWriter fw = new FileWriter("datasets/generated/datasetHash" + hashtags + ".json");
        fw.write(jsonString);
        fw.close();
    }
}
