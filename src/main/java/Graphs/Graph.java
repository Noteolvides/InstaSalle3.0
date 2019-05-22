package Graphs;

import Data.User;
import Graphs.visualización.Vista_Graph;
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
    public void visualizeCommandLine(){
        for (int i = 0; i < adjacencyList.size(); i++) {
            Vertex v =  adjacencyList.get(i);
            System.out.print(v.username + ":");
            for (int j = 0; j < v.getRelations().size(); j++) {
                Vertex vPrima = v.getRelations().get(j);
                System.out.print( ", " + vPrima.username);
            }
            System.out.println();
        }
    }

    public void removeEdge (Vertex v1, Vertex v2) {
        adjacencyList.get(v1).relations.remove(v2);
    }

    @Deprecated
    public void visualize() {
        new Vista_Graph(adjacencyList);
    }

    public User searchUser(String name) {
        Vertex found = null;
        for (int i = 0; i < adjacencyList.size(); i++) {
            if (adjacencyList.get(i).username.equals(name)) {
                found = adjacencyList.get(i);
            }
        }
        User user = new User(found.username, found.creation);
        String[] toFollow = new String[found.relations.size()];
        for (int i = 0; i < found.relations.size(); i++) {
            toFollow[i] = found.relations.get(i).username;
        }
        user.setToFollow(toFollow);
        return user;
    }

    public List<Vertex> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(List<Vertex> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }
}
