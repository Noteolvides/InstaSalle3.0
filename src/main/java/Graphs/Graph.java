package Graphs;

import List.List;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class Graph {
    private List<Vertex> adjacencyList;

    public Graph() {
        adjacencyList = new List<Vertex>();
    }

    public void insertVertex (Vertex newvertex) {
        adjacencyList.add(newvertex);
    }

    public void insertEdge (Vertex v1, Vertex v2) {
        adjacencyList.get(v1).relations.add(v2);
    }

    public void removeVertex (Vertex vertex) {
        adjacencyList.remove(vertex); // TODO: 2019-04-19 revisar que tots el edges que es comunicaven amb aquest siguin eliminats
        for (int i = 0; i < adjacencyList.size(); i++) {
            for (int j = 0; j < adjacencyList.get(i).relations.size(); j++) {
                if (adjacencyList.get(i).relations.get(j).equals(vertex)){
                    adjacencyList.get(i).relations.remove(vertex);
                }
            }
        }
    }

    public void removeEdge (Vertex v1, Vertex v2) {
        adjacencyList.get(v1).relations.remove(v2);
    }

    public void visualize() {

    }
    //List<List<Vertex>> graph;
    //directed graph implemented via Adjacency list

    public List<Vertex> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<Vertex> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public static void main (String[] args) throws IOException {
        Graph graph = new Graph();
        graph.insertVertex(new Vertex("n", "6"));
        graph.insertVertex(new Vertex("neil", "20/4/2019"));
        graph.insertVertex(new Vertex("alex", "13/5/2019"));
        graph.insertEdge(new Vertex("neil", "20/4/2019"), new Vertex("alex", "13/5/2019"));
        graph.insertEdge(new Vertex("n", "6"), new Vertex("alex", "13/5/2019"));
        graph.insertEdge(new Vertex("alex", "13/5/2019"), new Vertex("n", "6"));
        graph.removeEdge(new Vertex("n", "6"), new Vertex("alex", "13/5/2019"));
        graph.removeVertex(new Vertex("n", "6"));
        Gson gson = new Gson();
        String jsonString = null;
        jsonString = gson.toJson(graph);
        FileWriter fw = new FileWriter("graphjson.txt");
        fw.write(jsonString);
        fw.close();
    }
}
