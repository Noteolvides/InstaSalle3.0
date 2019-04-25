import Graphs.*;

public class Main {

    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.insertVertex(new Vertex("n", "6"));
        graph.insertVertex(new Vertex("neil", "20/4/2019"));
        graph.insertVertex(new Vertex("alex", "13/5/2019"));
        graph.insertEdge(new Vertex("neil", "20/4/2019"),new Vertex("alex", "13/5/2019"));
        graph.insertEdge(new Vertex("n", "6"), new Vertex("alex", "13/5/2019"));
        graph.insertEdge( new Vertex("alex", "13/5/2019"), new Vertex("n", "6"));
        graph.removeEdge(new Vertex("n", "6"), new Vertex("alex", "13/5/2019"));
        graph.removeVertex(new Vertex("n", "6"));
    }
}
