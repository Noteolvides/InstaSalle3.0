package DatasetGenerator;

import Graphs.Graph;
import Graphs.Vertex;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GenerateGraph {
    public static void main (String[] args) throws IOException {
        System.out.println("Number of vertex: ");
        Scanner sc = new Scanner(System.in);
        int numUsers = sc.nextInt();
        System.out.println("Number of connections: ");
        int numConn = sc.nextInt();

        Random rdm = new Random();
        Graph graph = new Graph();

        int j = 0;
        Vertex v1 = new Vertex(Utils.createName(), rdm.nextLong());
        graph.insertVertex(v1);
        Vertex v2;
        for (int i = 0; i < numUsers-1; i++) {
            v2 = new Vertex(Utils.createName(), rdm.nextLong());
            graph.insertVertex(v2);
            if (j < numConn) {
                if (rdm.nextBoolean()) {
                    graph.insertEdge(v1, v2);
                    if (rdm.nextBoolean()) {
                        graph.insertEdge(v2, v1);
                    }
                } else {
                    graph.insertEdge(v2, v1);
                }
            }
            v1 = new Vertex(Utils.createName(), rdm.nextLong());
            graph.insertVertex(v1);
        }

        Gson gson = new Gson();
        String jsonString;
        jsonString = gson.toJson(graph);
        FileWriter fw = new FileWriter("datasets/generated/datasetGraph" + numUsers + ".txt");
        fw.write(jsonString);
        fw.close();
    }
}
