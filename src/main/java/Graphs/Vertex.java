package Graphs;

public class Vertex {
    String username;
    String creation;
    List<Vertex> relations;

    public Vertex(String username, String creation) {
        this.username = username;
        this.creation = creation;
        this.relations = new List<Vertex>();
    }
}
