package Graphs;

import Data.User;
import List.List;

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

    public void insertUsers(User[] users) {
        for (User user: users) {
            insertVertex(new Vertex(user.username, user.creation));
        }
        for (User user: users) {
            User relation = null;
            for (String toFollow : user.toFollow) {
                for (User found: users) {
                    if (found.username.equals(toFollow)) {
                        relation = found;
                    }
                }
                if (relation != null) {
                    insertEdge(new Vertex(user.username, user.creation), new Vertex(relation.username, relation.creation));
                }
            }
        }
    }
}
