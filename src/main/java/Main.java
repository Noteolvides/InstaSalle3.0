import AVL.AVLTree;
import AVL.Test;
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
import java.io.FileWriter;
import java.io.IOException;
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
        TrieTree trieTree = new TrieTree();
        Tree rTree = new Tree();
        AVLTree avlTree = new AVLTree();
        HashTable hashTable = new HashTable(1);
        Graph graph = new Graph();

        Scanner sc = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Importació de fitxers\n" +
                "1. Exportació de fitxers en format usuaris i post\n" +
                "2. Exportació de fitxers en format totes les estructures\n");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a usuaris");
                userspath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a posts");
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
                triepath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a R-Trees\n");
                rtreepath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a AVL Tree\n");
                avltreepath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a Hash Table\n");
                hashpath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a Graph\n");
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
                        gson = new Gson();
                        String trieString;
                        trieString = gson.toJson(trieTree);
                        try {
                            FileWriter fw = new FileWriter("datasets/visualize/jsonForVisualize.json");
                            fw.write(trieString);
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Trie.Test trietest = new Trie.Test();
                        trietest.visualize(args);
                        break;
                    case 2:
                        gson = new Gson();
                        String rString;
                        rString = gson.toJson(rTree);
                        try {
                            FileWriter fw = new FileWriter("datasets/visualize/jsonForVisualize.json");
                            fw.write(rString);
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /*R_Tree.Test rtest = new R_Tree.Test();
                        rtest.visualize(rTree, args);*/
                        break;
                    case 3:
                        gson = new Gson();
                        String avlString;
                        avlString = gson.toJson(avlTree);
                        try {
                            FileWriter fw = new FileWriter("datasets/visualize/jsonForVisualize.json");
                            fw.write(avlString);
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        AVL.Test avltest = new Test();
                        avltest.visualize(args);
                        break;
                    case 4:
                        hashTable.visualize();
                        break;
                    case 5:
                        graph.visualize();
                        break;
                }
                break;
            case 2:
                System.out.println("Inserció d'informació\n" +
                        "Quin tipus d'informació vol inserir?\n" +
                        "\t1. Nou Uusuari\n" +
                        "\t2. Nou Post\n");
                switch (sc.nextInt()) {
                    case 1:
                        System.out.println("Nom d'usuari:");
                        String username = sc.next();
                        System.out.println("Data de creacio:");
                        long datacreation = sc.nextLong();

                        boolean follow = true;
                        List<String> tofollownamelist = new List<String>();
                        List<Long> tofollowcreationlist = new List<Long>();
                        while (follow) {
                            System.out.println("Usuaris que seguira[Y/N]");
                            if (sc.next().equals("Y")) {
                                System.out.println("Nom:");
                                tofollownamelist.add(sc.next());
                                System.out.println("Data de creacio:");
                                tofollowcreationlist.add(sc.nextLong());
                            } else {
                                follow = false;
                            }
                        }

                        String[] tofollowname = new String[tofollownamelist.size()];
                        Long[] tofollowcreation = new Long[tofollowcreationlist.size()];
                        for (int i = 0; i < tofollownamelist.size(); i++) {
                            tofollowname[i] = tofollownamelist.get(i);
                            tofollowcreation[i] = tofollowcreationlist.get(i);
                        }

                        Vertex v1 = new Vertex(username, datacreation);
                        graph.insertVertex(v1);
                        for (int i = 0; i < tofollowname.length; i++) {
                            graph.insertEdge(v1, graph.getAdjacencyList().get(new Vertex(tofollowname[i], tofollowcreation[i])));
                        }

                        trieTree.insert(username);
                        break;
                    case 2:
                        System.out.println("ID:");
                        int idPost = sc.nextInt();
                        System.out.println("Usuaris a qui li ha agradat[Y/N]");
                        boolean liked = true;
                        List<String> likednamelist = new List<String>();
                        while (liked) {
                            System.out.println("Usuaris a qui li ha agradat[Y/N]");
                            if (sc.next().equals("Y")) {
                                System.out.println("Nom:");
                                likednamelist.add(sc.next());
                            } else {
                                liked = false;
                            }
                        }
                        String[] likedname = new String[likednamelist.size()];
                        for (int i = 0; i < likednamelist.size(); i++) {
                            likedname[i] = likednamelist.get(i);
                        }

                        System.out.println("Data de publicacio:");
                        long publishedWhen = sc.nextLong();
                        System.out.println("Publicat per:");
                        String publishedBy = sc.next();
                        System.out.println("Location:");
                        Double[] location = new Double[2];
                        System.out.println("\tLatitud:");
                        location[0] = sc.nextDouble();
                        System.out.println("\tLogitud:");
                        location[1] = sc.nextDouble();

                        boolean tags = true;
                        List<String> tagnamelist = new List<String>();
                        while (tags) {
                            System.out.println("Tags del post[Y/N]");
                            if (sc.next().equals("Y")) {
                                System.out.println("HashTag:");
                                likednamelist.add(sc.next());
                            } else {
                                tags = false;
                            }
                        }
                        String[] tagname = new String[tagnamelist.size()];
                        for (int i = 0; i < tagnamelist.size(); i++) {
                            tagname[i] = tagnamelist.get(i);
                            if (hashTable.search(tagname[i]) == null || !hashTable.search(tagname[i]).equals(tagname[i])) {
                                hashTable.insert(tagname[i]);
                            }
                        }
                        Post newpost = new Post(idPost, likedname, publishedWhen, publishedBy, location, tagname);
                        //rTree.insertion(newpost, root);
                        avlTree.insert(newpost);
                        break;
                }
                break;
            case 3:
                System.out.println("Esborrar informació\n" +
                        "Quin tipus de informació vol esborrar?\n" +
                        "\t1. Usuari\n" +
                        "\t2. Post\n");
                switch (sc.nextInt()) {
                    case 1:
                        System.out.println("Nom d'usuari que s'esborrara:");
                        String username = sc.next();
                        System.out.println("Data de creacio del usuari");
                        long creation = sc.nextLong();

                        graph.removeVertex(new Vertex(username, creation));
                        trieTree.delete(username);
                        break;
                    case 2:
                        System.out.println("Id post que s'esborrara:");
                        try {
                            avlTree.delete(avlTree.search(sc.nextInt()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
            case 4:
                System.out.println("Cercar informació\n" +
                        "Quin tipus de informació vol cercar\n" +
                        "\t1. Usuari\n" +
                        "\t2. Post\n" +
                        "\t3. Segons hashtag\n" +
                        "\t4. Segons ubicació\n");
                switch (sc.nextInt()) {
                    case 1:
                        String search = sc.next();
                        trieTree.contains(search);
                        break;
                    case 2:
                        System.out.println("ID del post que es vol cercar:");
                        Post post = (Post) avlTree.search(sc.nextInt());
                        System.out.println("Post trobat:");
                        System.out.println("\tid: " + post.id);
                        String likedby = "[";
                        for (int i = 0; i < post.likedBy.length ; i++) {
                            if (i != 0) {
                                likedby += ", ";
                            }
                            likedby += post.likedBy[i];
                        }
                        likedby += "]";
                        System.out.println("\tliked_by: " + likedby);
                        System.out.println("\tpublished_by: " + post.publishedBy);
                        System.out.println("\tpublished_when: " + post.publishedWhen);
                        System.out.println("\tlocation: [" + post.location[0] + ", " + post.location[1] + "]");
                        String tags = "[";
                        for (int i = 0; i < post.hashtags.length ; i++) {
                            if (i != 0) {
                                tags += ", ";
                            }
                            tags += post.hashtags[i];
                        }
                        tags += "]";
                        System.out.println("\thashtags: " + tags);
                        break;
                    case 3:
                        System.out.println("Introduir hashtag:");
                        String hastag = hashTable.search(sc.next());

                        break;
                    case 4:
                        System.out.println("Latitud:");
                        double lat = sc.nextDouble();
                        System.out.println("Longitud:");
                        double log = sc.nextDouble();
                        System.out.println("Radi maxim:");
                        int radi = sc.nextInt();
                        break;
                }
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
                if (hashtags.get(hastag) == null || !hashtags.get(hastag).equals(hastag)) {
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
