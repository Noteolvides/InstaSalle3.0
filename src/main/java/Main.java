import AVL.AVLTree;
import Data.Post;
import Data.User;
import Graphs.Graph;
import Graphs.Vertex;
import HashTable.HashTable;
import List.List;
import R_Tree.Tree;
import Trie.TrieTree;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String userspath;
        String postspath;
        User[] users;
        Post[] posts;
        String triepath;
        String rtreepath;
        String avltreepath;
        String hashpath;
        String graphpath;
        TrieTree trieTree;
        Tree rTree;
        AVLTree avlTree;
        HashTable hashTable = null;
        Graph graph = null;

        Scanner sc = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Importació de fitxers\n" +
                "1. Exportació de fitxers en format usuaris i post\n" +
                "2. Exportació de fitxers en format totes les estructures\n" +
                "> ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a usuaris");
                System.out.println("> ");
                userspath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a posts");
                System.out.println("> ");
                postspath = sc.next();

                long startTime = System.currentTimeMillis();
                users = gson.fromJson(new FileReader(userspath), User[].class);
                posts = gson.fromJson(new FileReader(postspath), Post[].class);

                graph = insertUsersGraph(users);
                hashTable = insertHashTags(posts);
                avlTree = insertPostsAVL(posts);
                trieTree = insertUsersTrie(users);
                rTree = insertPostsRTree(posts);

                long endTime = System.currentTimeMillis();
                int elements = users.length + posts.length;
                long time = (endTime - startTime);
                System.out.println("Exportacio realitzada amb èxit!\n" +
                         elements + " elements exportats en " + time + "ms\n");
                break;
            case 2:
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a Tries\n");
                System.out.println("> ");
                triepath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a R-Trees\n");
                System.out.println("> ");
                rtreepath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a AVL Tree\n");
                System.out.println("> ");
                avltreepath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a Hash Table\n");
                System.out.println("> ");
                hashpath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a Graph\n");
                System.out.println("> ");
                graphpath = sc.next();
                startTime = System.currentTimeMillis();
                trieTree = gson.fromJson(new FileReader(triepath), TrieTree.class);
                rTree = gson.fromJson(new FileReader(rtreepath), Tree.class);
                avlTree = gson.fromJson(new FileReader(avltreepath), AVLTree.class);
                hashTable = gson.fromJson(new FileReader(hashpath), HashTable.class);
                graph = gson.fromJson(new FileReader(graphpath), Graph.class);
                endTime = System.currentTimeMillis();
                //elements = triedata. + rtreedata.length + avldata.length + hashdata.length + graphdata.length;
                time = (endTime - startTime);
                System.out.println("Exportacio realitzada amb èxit!\n" +
                        /*elements + */" elements exportats en " + time + "ms\n");
                break;
        }

        System.out.println("1. Visualitzar");
        System.out.println("2. Inserir informació");
        System.out.println("3. Esborrar informació");
        System.out.println("4. Cercar informació");
        option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Visualització de l'estructura\n" +
                        "Quina estructura dessitja visualitzar?\n" +
                        "\t1. Trie\n" +
                        "\t2. R-Tree\n" +
                        "\t3. AVL Tree\n" +
                        "\t4. Taula de Hash\n" +
                        "\t5. Graph\n");
                switch (sc.nextInt()) {
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        //hashTable.visualize();
                        break;
                    case 5:
                        //graph.visualize();
                        break;
                }
                break;
            case 2:
                System.out.println("Inserció d'informació\n" +
                        "Quin tipus d'informació vol inserir?\n" +
                        "\t1. Nou Uusuari\n" +
                        "\t2. Nou Post\n");
                break;
            case 3:
                System.out.println("Esborrar informació\n" +
                        "Quin tipus de informació vol esborrar?\n" +
                        "\t1. Nou Usuari\n" +
                        "\t2. Nou Post\n");
                break;
            case 4:
                System.out.println("Cercar informació\n" +
                        "Quin tipus de informació vol cercar\n" +
                        "\t1. Usuari\n" +
                        "\t2. Post\n" +
                        "\t3. Segons hashtag\n" +
                        "\t4. Segons ubicació\n");
                break;
        }
    }

    private static Graph insertUsersGraph(User[] users) {
        Graph graph = new Graph();
        for (User user: users) {
            graph.insertVertex(new Vertex(user.username, user.creation));
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
                    graph.insertEdge(new Vertex(user.username, user.creation), new Vertex(relation.username, relation.creation));
                }
            }
        }
        return graph;
    }

    private static HashTable insertHashTags(Post[] posts) {
        List<String> hashtags = getHashTags(posts);
        HashTable<Integer, String> hashTable = new HashTable<Integer, String>(hashtags.size());
        for (int i = 0; i < hashtags.size(); i++) {
            hashTable.insert(hashtags.get(i));
        }
        return hashTable;
    }

    private static List<String> getHashTags(Post[] posts) {
        List<String> hashtags = new List<String>();
        for (Post post: posts) {
            for (String hastag: post.hashtags) {
                if (hashtags.get(hastag) != null && !hashtags.get(hastag).equals(hastag)) {
                    hashtags.add(hastag);
                }
            }
        }
        return hashtags;
    }

    private static AVLTree<Post> insertPostsAVL(Post[] posts) {
        AVLTree<Post> avlTree = new AVLTree<>();
        for (Post post : posts) {
            avlTree.insert(post);
        }
        return avlTree;
    }

    private static TrieTree insertUsersTrie(User[] users) {
        TrieTree trie = new TrieTree();
        for (User user : users) {
            trie.insert(user.username);
        }
        return trie;
    }

    private static Tree insertPostsRTree(Post[] posts) {
        Tree rtree = new Tree();
        for (Post post: posts) {
            //rtree.insertion(post, root);
        }
        return rtree;
    }
}
