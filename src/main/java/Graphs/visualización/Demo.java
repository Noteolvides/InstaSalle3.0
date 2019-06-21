package Graphs.visualización;

import Graphs.Graph;
import Graphs.Vertex;

public class Demo {

    public static void main(String[] args) {
        Graph g = new Graph();

        g.insertVertex(new Vertex("Neil", 1345));
        g.insertVertex(new Vertex("Gustavo", 1543));
        g.insertVertex(new Vertex("Alex", 5431));
        g.insertVertex(new Vertex("Tupper", 4135));
        g.insertVertex(new Vertex("Movil", 1345));
        g.insertVertex(new Vertex("Xiaomi", 1258));
        g.insertVertex(new Vertex("Silla", 1498));
        g.insertVertex(new Vertex("Mesa", 1465));
        g.insertVertex(new Vertex("Trash", 176543));
        g.insertVertex(new Vertex("Link", 3467));

        g.insertEdge(g.searchUser("Neil"), g.searchUser("Gustavo"));
        g.insertEdge(g.searchUser("Gustavo"), g.searchUser("Neil"));
        g.insertEdge(g.searchUser("Neil"), g.searchUser("Silla"));
        g.insertEdge(g.searchUser("Xiaomi"), g.searchUser("Link"));
        g.insertEdge(g.searchUser("Link"), g.searchUser("Trash"));
        g.insertEdge(g.searchUser("Alex"), g.searchUser("Tupper"));
        g.insertEdge(g.searchUser("Xiaomi"), g.searchUser("Mesa"));
        g.insertEdge(g.searchUser("Movil"), g.searchUser("Silla"));
        g.insertEdge(g.searchUser("Tupper"), g.searchUser("Gustavo"));

        Vista v = new Vista(g.getAdjacencyList());
    }
}
