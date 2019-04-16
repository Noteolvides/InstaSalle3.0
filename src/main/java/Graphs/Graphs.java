package Graphs;

public class Graphs {
    private List<Vertex> adjacencyList;
    
    public static void main(String[] args) {
        List<Vertex> adjList = new List<Vertex>();
        adjList.add(new Vertex("neil", "20/4/2019"));
        adjList.add(new Vertex("alex", "13/5/2019"));
        adjList.get(new Vertex("neil", "20/4/2019")).relations.add(new Vertex("alex", "13/5/2019"));
    }

    public void insertVertex (Vertex newvertex) {
        newvertex.relations = new List<Vertex>();
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
