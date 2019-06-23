package exe;

import AVL.AVLTree;
import AVL.Test;
import Data.Post;
import Data.User;
import Graphs.Graph;
import Graphs.Vertex;
import HashTable.HashTable;
import HashTable.Data;
import List.List;
import R_Tree.Point;
import R_Tree.RTree;
import R_Tree.VisualizacionMenuRtree;
import Trie.TrieTree;
import com.google.gson.Gson;
import javafx.geometry.Pos;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Menu {
    String userspath;
    String postspath;
    User[] users;
    Post[] posts;
    String triepath;
    String rtreepath;
    String avltreepath;
    String hashpath;
    String graphpath;
    public static TrieTree trieTree = new TrieTree();
    public static RTree rTree = new RTree();
    public static AVLTree avlTree = new AVLTree();
    HashTable hashTable = new HashTable(100);
    Graph graph = new Graph();

    public static void main(String[] args) throws FileNotFoundException {
        Menu main= new Menu();
        int back = 0;
        do{
            try {
                back = main.Menu(args);
            }catch(FileNotFoundException e){
                System.out.println("Arxiu no trobat. Entra una ruta existent.");
                System.out.println();
                back = main.Menu(args);
            }
        }while(back == 5);
    }

    public int Menu(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        int option = 0;
        int option2 = 0;

        while (option != 5) {
                do{
                    System.out.println("Importació de fitxers");
                    System.out.println("1. Importació de fitxers en format usuaris i post");
                    System.out.println("2. Importació de fitxers en format totes les estructures");
                    System.out.println("3. Exportació de fitxers en format totes les estructures");
                    System.out.println("4. Estructures buides");
                    System.out.println("5. Exit");
                    option = sc.nextInt();
                    switch (option) {
                        case 1:
                            importacioUserPost();
                            break;
                        case 2:
                            importacioEstructures();
                            break;
                        case 3:
                            exportacioEstructures();
                            break;
                        case 4:

                            break;
                        case 5:
                            return(0);
                        default:
                            System.out.println("Opcio incorrecta!");
                            break;
                    }
                }while(option > 5);


                while (option != 3) {
                    do{
                        System.out.println("1. Visualitzar");
                        System.out.println("2. Inserir informació");
                        System.out.println("3. Esborrar informació");
                        System.out.println("4. Cercar informació");
                        System.out.println("5. Return");
                        option2 = sc.nextInt();
                        switch (option2) {
                            case 1:
                                visualizacionEstructuras(args);
                                break;
                            case 2:
                                insertEstructures();
                                break;
                            case 3:
                                deleteEstructures();
                                break;
                            case 4:
                                searchEstructures();
                                break;
                            case 5:
                                return(5);
                            default:
                                System.out.println("Opcio incorrecta!");
                                break;
                        }
                    }while((option2>4));
                }
        }
        return(0);
    }

    private void searchEstructures() {
        int option;
        do{
            Scanner sc = new Scanner(System.in);
            System.out.println("Cercar informació");
            System.out.println("Quin tipus de informació vol cercar");
            System.out.println("\t1. Usuari");
            System.out.println("\t2. Post");
            System.out.println("\t3. Segons hashtag");
            System.out.println("\t4. Segons ubicació");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Quant vols limitar la memoria: ");
                    int memori = sc.nextInt();
                    boolean exit = true;
                    do {
                        System.out.println("Quin usuari vols buscar: ");
                        String search = sc.next();
                        long timeSearchTrie;
                        long start = System.nanoTime();
                        trieTree.containsQuery(search, memori);
                        long end = System.nanoTime();
                        timeSearchTrie = end - start;
                        System.out.println("S'ha tardat " + timeSearchTrie + "ns.");
                        System.out.println("Seguir buscant?[Y/N]");
                        if (sc.next().equals("N")) {
                            exit = false;
                        }
                    } while (exit);
                    System.out.println("A qui vols buscar finalment:");
                    String search = sc.next();
                    long timeSearchGraph;
                    long start = System.nanoTime();
                    Vertex userSearch = graph.searchUser(search);
                    long end = System.nanoTime();
                    timeSearchGraph = end - start;

                    User user = new User(userSearch.getUsername(), userSearch.getCreation());
                    String[] toFollow = new String[userSearch.getRelations().size()];
                    for (int i = 0; i < userSearch.getRelations().size(); i++) {
                        toFollow[i] = userSearch.getRelations().get(i).getUsername();
                    }
                    user.setToFollow(toFollow);

                    System.out.println("Usuari trobat:");
                    System.out.println("\tNom: " + user.username);
                    System.out.println("\tData de creacio: " + user.creation);
                    System.out.println("\tToFollow: ");
                    for (String follow : user.toFollow) {
                        System.out.println("\t\tNom: " + follow);
                    }
                    System.out.println("S'ha tardat " + timeSearchGraph + "ns.");
                    break;
                case 2:
                    System.out.println("Data de creacio del post que es vol cercar:");
                    long data = sc.nextLong();
                    long timeSearchAVL;
                    start = System.nanoTime();
                    Post post = (Post) avlTree.search(data);
                    end = System.nanoTime();
                    timeSearchAVL = end - start;
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
                    System.out.println("\tpublistimeSearchAVLhed_by: " + post.publishedBy);
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
                    System.out.println("S'ha tardat " + timeSearchAVL + "ns.");
                    break;
                case 3:
                    System.out.println("Introduir hashtag:");
                    String hashtag = sc.next();
                    System.out.println("Quants pots vols mostrar d'aquest hashtag:");
                    int limit = sc.nextInt();
                    System.out.println("Post amb el hashtag " + hashtag + ":");
                    long timeSearchHash;
                    start = System.nanoTime();
                    List<Data> posts = hashTable.getAll(hashtag);
                    end = System.nanoTime();
                    timeSearchHash = end - start;
                    for (int i = 0; i < limit; i++) {
                        System.out.println(((Post) posts.get(i).getElement()).id);
                    }
                    System.out.println("\n");
                    System.out.println("S'ha tardat " + timeSearchHash + "ns.");
                    System.out.println("\n");
                    break;
                case 4:
                    System.out.println("Latitud:");
                    double lat = sc.nextDouble();
                    System.out.println("Longitud:");
                    double log = sc.nextDouble();
                    System.out.println("Radi maxim:");
                    int radi = sc.nextInt();
                    break;
                default:
                    System.out.println("Opcio incorrecta!");
                break;

            }
        }while(option > 4);
    }

    private void deleteEstructures() {
        Scanner sc = new Scanner(System.in);
        int option;
        do{
            System.out.println("Esborrar informació\n" +
                    "Quin tipus de informació vol esborrar?\n" +
                    "\t1. Usuari\n" +
                    "\t2. Post\n");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Nom d'usuari que s'esborrara:");
                    String username = sc.next();
                    System.out.println("Data de creacio del usuari");
                    long creation = sc.nextLong();

                    long timeDeleteGraph;
                    long start = System.nanoTime();
                    graph.removeVertex(new Vertex(username, creation));
                    long end = System.nanoTime();
                    timeDeleteGraph = end - start;
                    System.out.println("Eliminacio de Usuari finalitzada del Graph en " + timeDeleteGraph + "ns.");

                    long timeDeleteTrie;
                    start = System.nanoTime();
                    trieTree.delete(username);
                    end = System.nanoTime();
                    timeDeleteTrie = end - start;
                    System.out.println("Eliminacio de Usuari finalitzada del Trie en " + timeDeleteTrie + "ns.");

                    break;
                case 2:
                    System.out.println("Data de publicacio post que s'esborrara:");
                    long published = sc.nextLong();
                    try {
                        long timeDeleteAVL;
                        start = System.nanoTime();
                        avlTree.delete(published);
                        end = System.nanoTime();
                        timeDeleteAVL = end - start;
                        System.out.println("Eliminacio del Post finalitzada del AVLTree en " + timeDeleteAVL + "ns.");

                        long timeDeleteHash;
                        start = System.nanoTime();
                        hashTable.removeElement(published);
                        end = System.nanoTime();
                        timeDeleteHash = end - start;
                        System.out.println("Eliminacio del Post finalitzada del HashTable en " + timeDeleteHash + "ns.");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Opcio incorrecta!");
                break;
            }
        }while(option > 2);
    }

    private void insertEstructures() {
        Scanner sc = new Scanner(System.in);
        int option;
        do{
            System.out.println("Inserció d'informació\n" +
                    "Quin tipus d'informació vol inserir?\n" +
                    "\t1. Nou Usuari\n" +
                    "\t2. Nou Post\n");
            option = sc.nextInt();
            switch (option) {
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
                        if (sc.next().equals("Y")||sc.next().equals("y")) {
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
                    long timeInsertGraph;

                    long start = System.nanoTime();
                    graph.insertVertex(v1);
                    for (int i = 0; i < tofollowname.length; i++) {
                        graph.insertEdge(v1, graph.getAdjacencyList().get(new Vertex(tofollowname[i], tofollowcreation[i])));
                    }
                    long end = System.nanoTime();
                    timeInsertGraph = end - start;
                    System.out.println("Insercio nou Usuari del Graph finalitzada en " + timeInsertGraph + "ns.");

                    long timeInsertTrie;
                    start = System.nanoTime();
                    trieTree.insert(username);
                    end = System.nanoTime();
                    timeInsertTrie = end - start;
                    System.out.println("Insercio nou Usuari del Trie finalitzada en " + timeInsertTrie + "ns.");

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
                        if (sc.next().equals("Y")||sc.next().equals("y")) {
                            System.out.println("HashTag:");
                            likednamelist.add(sc.next());
                        } else {
                            tags = false;
                        }
                    }
                    String[] tagname = new String[tagnamelist.size()];
                    for (int i = 0; i < tagnamelist.size(); i++) {
                        tagname[i] = tagnamelist.get(i);
                    }
                    Post newpost = new Post(idPost, likedname, publishedWhen, publishedBy, location, tagname);

                    long timeInsertHash;
                    start = System.nanoTime();
                    for (String tag : tagname) {
                        hashTable.put(tag, newpost);
                    }
                    end = System.nanoTime();
                    timeInsertHash = end - start;
                    System.out.println("Insercio nou Post de la HashTable finalitzada en " + timeInsertHash + "ns.");

                    long timeInsertAVL;
                    start = System.nanoTime();
                    avlTree.insert(newpost);
                    end = System.nanoTime();
                    timeInsertAVL = end - start;
                    System.out.println("Insercio nou Post del AVLTree finalitzada en " + timeInsertAVL + "ns.");
                    long timeInsertR;
                    start = System.nanoTime();
                    rTree.insert(new Point(newpost.location[0].intValue(), newpost.location[1].intValue()));
                    end = System.nanoTime();
                    timeInsertR = end - start;
                    System.out.println("Insercio nou Post del RTree finalitzada en " + timeInsertR + "ns.");
                    break;
                default:
                    System.out.println("Opcio incorrecta!");
                    break;
            }
        }while(option > 2);
    }

    private void visualizacionEstructuras(String[] args) {
        Scanner sc = new Scanner(System.in);
        Gson gson = new Gson();
        int option;
        do{
            System.out.println("Visualització de l'estructura\n" +
                "Quina estructura dessitja visualitzar?\n" +
                "\t1. Trie\n" +
                "\t2. R-Tree\n" +
                "\t3. AVL Tree\n" +
                "\t4. Taula de Hash\n" +
                "\t5. Graph\n");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    Trie.Test trietest = new Trie.Test();
                    trietest.visualize(args);
                    break;
                case 2:
                    VisualizacionMenuRtree rvisual = new VisualizacionMenuRtree();
                    rvisual.visualize(args);
                    break;
                case 3:
                    Test avltest = new Test();
                    avltest.visualize(args);
                    break;
                case 4:
                    hashTable.visualize();
                    break;
                case 5:
                    graph.visualizeCommandLine();
                    break;
                default:
                    System.out.println("Opcio incorrecte!");
                    break;
            }
        }while(option >5 );
    }

    private void exportacioEstructures() {
        int opcio = 0;
        Gson gson = new Gson();
        Scanner sc = new Scanner(System.in);
        String jsonString;
        FileWriter fw;
        do{
            System.out.println("1.Exportació JSON Trie");
            System.out.println("2.Exportació JSON R-Tree");
            System.out.println("3.Exportació JSON AVL Tree");
            System.out.println("4.Exportació JSON Hash Table");
            System.out.println("5.Exportació JSON Graph");
            opcio = sc.nextInt();
            switch (opcio) {
                case 1:
                    jsonString = gson.toJson(trieTree);
                    try {
                        fw = new FileWriter("datasets/exported/datasetTrie.json");
                        fw.write(jsonString);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    jsonString = gson.toJson(rTree);
                    try {
                        fw = new FileWriter("datasets/exported/datasetRTree.json");
                        fw.write(jsonString);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    jsonString = gson.toJson(avlTree);
                    try {
                        fw = new FileWriter("datasets/exported/datasetAVLTree.json");
                        fw.write(jsonString);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    jsonString = gson.toJson(hashTable);
                    try {
                        fw = new FileWriter("datasets/exported/datasetHashTable.json");
                        fw.write(jsonString);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    jsonString = gson.toJson(graph);
                    try {
                        fw = new FileWriter("datasets/exported/datasetGraph.json");
                        fw.write(jsonString);
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Opcio incorrecta!");
                    break;
            }
        }while(opcio > 5);
        System.out.println("El fitxer ha sigut exportat a la carpeta datasets/exported\n");
    }

    private void importacioEstructures() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        Gson gson = new Gson();
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
        try{
            long startTime = System.nanoTime();
            trieTree = gson.fromJson(new FileReader(triepath), TrieTree.class);
            rTree = gson.fromJson(new FileReader(rtreepath), RTree.class);
            avlTree = gson.fromJson(new FileReader(avltreepath), AVLTree.class);
            hashTable = gson.fromJson(new FileReader(hashpath), HashTable.class);
            graph = gson.fromJson(new FileReader(graphpath), Graph.class);
            long endTime = System.nanoTime();
            long time = (endTime - startTime);
            System.out.println("Importació realitzada amb èxit!\n" +
                    "Elements importats en " + time + "ms\n");
        }catch(FileNotFoundException e){
            System.out.println("Rutes invàlides. Entri una ruta vàlida.");
            System.out.println("S'avançarà amb les estructures buides\n");
        }
    }

    private void importacioUserPost() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        Gson gson = new Gson();
        System.out.println("Especifiqui ruta del fitxer a importar corresponent a usuaris");
        userspath = sc.next();
        System.out.println("Especifiqui ruta del fitxer a importar corresponent a posts");
        postspath = sc.next();

        long startTime = System.nanoTime();
        users = gson.fromJson(new FileReader(userspath), User[].class);
        posts = gson.fromJson(new FileReader(postspath), Post[].class);

        graph = insertUsersGraph(users);
        hashTable = insertHashTags(posts);
        avlTree = insertPostsAVL(posts);
        trieTree = insertUsersTrie(users);
        rTree = insertPostsRTree(posts);

        long endTime = System.nanoTime();
        int elements = users.length + posts.length;
        long time = (endTime - startTime);
        System.out.println("Importació realitzada amb èxit!\n" +
                 elements + " elements importats en " + time + "ms\n");
    }

    private Graph insertUsersGraph(User[] users) {
        Graph graph = new Graph();
        Vertex[] vertices = new Vertex[users.length];
        for (int i = 0; i < users.length; i++) {
            vertices[i] = (new Vertex(users[i].username, users[i].creation));
            graph.insertVertex(vertices[i]);
        }
        for (int i = 0; i < users.length; i++) {
            for (int j = 0; j < users[i].toFollow.length; j++) {
                for (Vertex vertex : vertices) {
                    if (users[i].toFollow[j].equals(vertex.getUsername())) {
                        graph.insertEdge(vertices[i], vertex);
                    }
                }
            }
        }
        return graph;
    }

    private List<String> getHashTags(Post[] posts) {
        List<String> hashtags = new List<String>();
        for (Post post : posts) {
            for (String hastag : post.hashtags) {
                if (hashtags.get(hastag) == null || !hashtags.get(hastag).equals(hastag)) {
                    hashtags.add(hastag);
                }
            }
        }
        return hashtags;
    }

    private HashTable insertHashTags(Post[] posts) {
        //insertar los post dentro las hashtable con key los hashtags
        List<String> hastags = getHashTags(posts);
        HashTable<String, Post> hashTable = new HashTable<String, Post>(hastags.size());
        for (Post post : posts) {
            for (String hashtag : post.hashtags) {
                hashTable.put(hashtag, post);
            }
        }
        return hashTable;
    }

    private AVLTree<Post> insertPostsAVL(Post[] posts) {
        AVLTree<Post> avlTree = new AVLTree<>();
        for (Post post : posts) {
            avlTree.insert(post);
        }
        return avlTree;
    }

    private TrieTree insertUsersTrie(User[] users) {
        TrieTree trie = new TrieTree();
        for (User user : users) {
            trie.insert(user.username);
        }
        return null;
    }

    private RTree insertPostsRTree(Post[] posts) {
        RTree rtree = new RTree();
        Random rd = new Random();
        for (int i = 0; i < posts.length; i++) {
            int x = rd.nextInt(RTree.WIDTH_SCREEN);
            int y = rd.nextInt(RTree.HEIGHT_SCREEN);
            try {
                rtree.insert(new Point(x, y));
            } catch (Exception e) {
                rtree.insert(new Point(x, y));
            }
        }
        return rtree;
    }
}
