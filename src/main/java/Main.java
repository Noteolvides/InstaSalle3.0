import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String userspath;
        String postspath;
        String triepath;
        String rtreepath;
        String avltreepath;
        String hashpath;
        String graphpath;
        Scanner sc = new Scanner(System.in);
        System.out.println("Importació de fitxers\n" +
                "1. Exportació de fitxers en format usuaris i post" +
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
                System.out.println("Visualització de l'estructura" +
                        "Quina estructura dessitja visualitzar?" +
                        "\t1. Trie" +
                        "\t2. R-Tree" +
                        "\t3. AVL Tree" +
                        "\t4.Taula de Hash" +
                        "5. Graph");

                break;
            case 2:
                System.out.println("Inserció d'informació" +
                        "Quin tipus d'informació vol inserir?" +
                        "\t1. Nou Uusuari" +
                        "\t2.Nou Post");

                break;
            case 3:
                System.out.println("Esborrar informació" +
                        "Quin tipus de informació vol esborrar?" +
                        "\t1. Nou Uusuari" +
                        "\t2.Nou Post");
                break;
            case 4:
                System.out.println("Cercar informació" +
                        "Quin tipus de informació vol cercar" +
                        "\t1. Usuari" +
                        "\t2. Post" +
                        "\t3. Segons hashtag" +
                        "\t4.Segons ubicació");
                break;
        }
    }
}
