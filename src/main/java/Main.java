import Data.Post;
import Data.User;
import Trie.Test;
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
        /*Trie triedata;
        RTree rtreedata;
        AVLTree avldata;
        Hash hashdata;
        Graph graphdata;*/

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
                /*triedata = gson.fromJson(new FileReader(triepath), Trie[].class);
                rtreedata = gson.fromJson(new FileReader(rtreepath), RTree[].class);
                avldata = gson.fromJson(new FileReader(avltreepath), AVLTree[].class);
                hashdata = gson.fromJson(new FileReader(hashpath), Hash[].class);
                graphdata = gson.fromJson(new FileReader(graphpath), Graph[].class);
                endTime = System.currentTimeMillis();
                elements = triedata.length + rtreedata.length + avltreedata.length + hashdata.length + graphdata.length;
                time = (endTime - startTime);
                System.out.println("Exportacio realitzada amb èxit!\n" +
                        elements + " elements exportats en " + time + "ms\n");*/
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
}
