package Graphs;


import List.List;

public class Vertex {
    String username;
    String creation;
    List<Vertex> relations;

    public Vertex(String username, String creation) {
        this.username = username;
        this.creation = creation;
        this.relations = new List<Vertex>();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != getClass()) {
            return false;
        } else {
            Vertex v = (Vertex) obj;
            return username.equals(v.username) && creation.equals(v.creation);
        }
    }

    public String getUsername() {
        return username;
    }

    public List<Vertex> getRelations() {
        return relations;
    }
}
