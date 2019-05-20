package DatasetGenerator;

import Graphs.Graph;
import Graphs.Vertex;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateGraph {
    public static void main (String[] args) throws IOException {
        Random rdm = new Random();
        Graph graph = new Graph();
        graph.insertVertex(new Vertex(Utils.createName(), rdm.nextLong()));
        graph.insertVertex(new Vertex(Utils.createName(), rdm.nextLong()));
        graph.insertVertex(new Vertex(Utils.createName(), rdm.nextLong()));
        graph.insertEdge(new Vertex(Utils.createName(), rdm.nextLong()), new Vertex(Utils.createName(), rdm.nextLong()));
        graph.insertEdge(new Vertex(Utils.createName(), rdm.nextLong()), new Vertex(Utils.createName(), rdm.nextLong()));
        graph.insertEdge(new Vertex(Utils.createName(), rdm.nextLong()), new Vertex(Utils.createName(), rdm.nextLong()));
        Gson gson = new Gson();
        String jsonString = null;
        jsonString = gson.toJson(graph);
        FileWriter fw = new FileWriter("graphjson.txt");
        fw.write(jsonString);
        fw.close();
    }
}
