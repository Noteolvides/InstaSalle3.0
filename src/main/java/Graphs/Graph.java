package Graphs;

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
        adjacencyList.remove(vertex);
    }

    public void removeEdge (Vertex v1, Vertex v2) {
        adjacencyList.get(v1).relations.remove(v2);
    }

    public void visualize() {

    }
    //List<List<Vertex>> graph;
    //directed graph implemented via Adjacency list
}
