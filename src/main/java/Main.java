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
        Scanner sc = new Scanner(System.in);
        System.out.println("Importació de fitxers\n" +
                "1. Exportació de fitxers en format usuaris i post\n" +
                "2. Exportació de fitxers en format totes les estructures" +
                "> ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a usuaris\n");
                System.out.println("> ");
                userspath = sc.next();
                System.out.println("Especifiqui ruta del fitxer a importar corresponent a posts\n");
                System.out.println("> ");
                postspath = sc.next();
                Gson gson = new Gson();
                long startTime = System.currentTimeMillis();
                users = gson.fromJson(new FileReader(userspath), User[].class);
                posts = gson.fromJson(new FileReader(postspath), Post[].class);
                long endTime = System.currentTimeMillis();
                int elements = users.length + posts.length;
                long time = (endTime - startTime);
                System.out.println("Exportacio realitzada amb èxit!" +
                         elements + " elements exportats en " + time + "ms");
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
                        "Quina estructura dessitja visualitzar?" +
                        "\t1. Trie" +
                        "\t2. R-Tree" +
                        "\t3. AVL Tree" +
                        "\t4. Taula de Hash" +
                        "\t5. Graph");
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
                        "Quin tipus d'informació vol inserir?" +
                        "\t1. Nou Uusuari" +
                        "\t2. Nou Post");
                break;
            case 3:
                System.out.println("Esborrar informació" +
                        "Quin tipus de informació vol esborrar?" +
                        "\t1. Nou Usuari" +
                        "\t2. Nou Post");
                break;
            case 4:
                System.out.println("Cercar informació" +
                        "Quin tipus de informació vol cercar" +
                        "\t1. Usuari" +
                        "\t2. Post" +
                        "\t3. Segons hashtag" +
                        "\t4. Segons ubicació");
                break;
        }
    }
}
