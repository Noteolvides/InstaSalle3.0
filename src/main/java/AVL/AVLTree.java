package AVL;


import Data.Post;
import com.google.gson.Gson;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AVLTree<T> {
    Node<T> root;

    private class Logical {
        Boolean bool = false;
    }

    //Implementacion de la busqueda

    public T search(T element) {
        return searchElement(root, element);
    }

    private T searchElement(Node<T> node, T element){
        int cmp = node.compareTo(element);
        if (cmp == 0) {
            return node.data;
        }
        if (cmp > 0){
            return searchElement(node.leftChild,element);
        }
        return searchElement(node.rightChild,element);
    }

    private T searchInside(Node<T> node, T element) {
        if (node.data.getClass() == Post.class) {
            if (((Post) node.data).id == (Integer) element) {
                return node.data;
            }
        } else {
            if (node.data == element) {
                return node.data;
            }
        }
        if (node.leftChild != null) {
            return searchInside(node.leftChild, element);
        }
        if (node.rightChild != null) {
            return searchInside(node.rightChild, element);
        }
        return null;
    }


    //Implementacion de las rotaciones

    private Node<T> rotateLL(Node<T> root) {
        Node<T> leftBranchOfRoot = root.leftChild;
        root.leftChild = leftBranchOfRoot.rightChild;
        leftBranchOfRoot.rightChild = root;
        if (leftBranchOfRoot.factor == -1){
            leftBranchOfRoot.factor = 0;
            root.factor = 0;
        }else{
            leftBranchOfRoot.factor = 1;
            root.factor = -1;
        }
        return leftBranchOfRoot;
    }

    private Node<T> rotateRR(Node<T> root) {
        Node<T> rightBranchOfRoot = root.rightChild;
        root.rightChild = rightBranchOfRoot.leftChild;
         rightBranchOfRoot.leftChild = root;
        if (rightBranchOfRoot.factor == +1){
            rightBranchOfRoot.factor = 0;
            root.factor = 0;
        }else{
            rightBranchOfRoot.factor = -1;
            root.factor = 1;
        }
        return rightBranchOfRoot;
    }


    private Node<T> rotateLR(Node<T> root) {
        Node<T> leftBranchOfRoot = root.leftChild;
        Node<T> leftBranchOfRootRightBranch = leftBranchOfRoot.rightChild;
            root.leftChild = leftBranchOfRootRightBranch.rightChild;
        leftBranchOfRootRightBranch.rightChild = root;

            leftBranchOfRoot.rightChild = leftBranchOfRootRightBranch.leftChild;
        leftBranchOfRootRightBranch.leftChild = leftBranchOfRoot;

        if (leftBranchOfRootRightBranch.factor == +1) {
            leftBranchOfRoot.factor = -1;
        } else {
            leftBranchOfRoot.factor = 0;
        }
        if (leftBranchOfRootRightBranch.factor == -1) {
             root.factor = +1;
        } else {
            root.factor = 0;
        }
        leftBranchOfRootRightBranch.factor = 0;
        return leftBranchOfRootRightBranch;
    }

    private Node<T> rotateRL(Node<T> root) {
        Node<T> rightBranchOfRoot = root.rightChild;
        Node<T> rightBranchOfRootLeftBranch = rightBranchOfRoot.leftChild;
        root.rightChild = rightBranchOfRootLeftBranch.leftChild;
        rightBranchOfRootLeftBranch.leftChild = root;

            rightBranchOfRoot.leftChild = rightBranchOfRootLeftBranch.rightChild;
        rightBranchOfRootLeftBranch.rightChild = rightBranchOfRoot;

        if (rightBranchOfRootLeftBranch.factor == +1) {
               root.factor = -1;
        } else {
            root.factor = 0;
        }
        if (rightBranchOfRootLeftBranch.factor == -1) {
            rightBranchOfRoot.factor = +1;
        } else {
            rightBranchOfRoot.factor = 0;
        }
        rightBranchOfRootLeftBranch.factor = 0;
        return rightBranchOfRootLeftBranch;
    }


    public void insert(T data) {
        //Si tenemos que actualizar el arbol porque alguno de los casos de desbalanceo se han dado utilizaremos el siguiente flag
        //Lo utilizamos en modo objeto para que java no de problemas
        Logical act = new Logical();
        try {
            root = insert(data, root, act);
        } catch (Exception e) {
            System.out.println("Eroor al añadir un item");
        }
    }

    //Implementacion de la insercion


    private Node<T> insert(T data, Node<T> node, Logical act) throws Exception {
        //En el caso trivial el nodo root no existe o tenemos que ponerle en al final del arbol
        if (node == null) {
                node = new Node<T>(data);
            act.bool = true; //que hemos terminao de insertar un nodo y es la primera insercion y tenemos que requilibrar
        } else {
            int cmp = node.compareTo(data);//Este compare es por el diamond operator
            if (cmp == 0) {                     // si se da el caso de que esta repetido avisamos que no se puede insertar y salimos de golpe de la inserccion
                throw new Exception("No se pueden introducir nodos repetidos");
            }
            if (cmp < 0) {
                //Vamos moviendonos de derecha e izquierda por las ramas
                node.leftChild = insert(data, node.leftChild, act);
                if (act.bool) {                     //En el caso de que menos insertado un nodo o una actualizacion
                    if (node.factor == 0) {            //Si es la primera vez que se inserta como nos habiamos movido por la rama de izquierda lo ponemos a -1
                        node.factor = -1;
                        act.bool = true;            //Decimos que queremos decir que actualizen los indeces
                    } else {
                        if (node.factor == +1) {        //Si al regresar por la recursividad el ya tenia un nodo en la derecha
                            node.factor = 0;            //La rama esta balanceada :) que bien
                            act.bool = false;            // Ya no tenemos que hacer nada mas
                        }
                        if (node.factor == -1) {        //Si al regresar por la recusividad el ya tenia un nodo en la izquieda es mal asunto tenemos que rotar
                            if (node.leftChild.factor == -1) {    //Primero tenemos que ver que tipo de rotacion es
                                node = rotateLL(node);
                                //En el caso que mi hijo izquiero ya tiene un -1 es una rotacion a la izquierda
                            }
                            if (node.rightChild.factor == +1) {
                                    node = rotateLR(node);            //En el caso que tenga un +1 rotacion LR
                            }
                            act.bool = false;                    //Y ya hemos parado
                        }
                    }
                }
                if (cmp > 0) {
                    node.rightChild = insert(data, node.rightChild, act);
                    if (act.bool) {
                        if (node.factor == 0) {
                            //En el la primera iteracion cambiamos el factor al poner un nodo en la derecha ponemos a +1
                            node.factor = +1;
                            act.bool = true;            //Todavia no hemos terminao de ver si esta equilibrado faltaria una iteracion
                        } else {
                            if (node.factor == -1) {        //Si el tenia un hermano pues ya esta equilibrado
                                node.factor = 0;
                                act.bool = false;
                            }
                            if (node.factor == +1) {
                                //En el caso que no tenga +1 en la 3 subida tenemos que actulizar
                                if (node.rightChild.factor == +1) {    //si mi hijo tiene un nodo con factor 3 eso es que ha sido un inserccion RR
                                    node = rotateRR(node);
                                }
                                if (node.rightChild.factor == -1) {
                                    //En el caso contrario es una inserccion RL
                                    node = rotateRL(node);
                                }
                                act.bool = false;						//Ya hemos terminado
                            }
                        }
                    }
                }
            }
        }
        return node;
    }


    public void delete(T element) throws Exception {
        root = deleteInside(root, element, new Logical());
    }
    //Tenemos dos casos claros primero si el nodo es una hoja se eliminar se sustituira por otro nodo
    //En el caso contrario si tiene subarboles buscaremos el nodo mas a la derecha del primero de la izquierda
    //Lo complicado es que despues de hacer este proceso tiene que ver los factores e ir equilibrando vamos a ir explicando paso por paso la busqueda y sustitucion
    private Node<T> deleteInside(Node<T> node, T element, Logical changeOfHeight) throws Exception {
        int cmp;
        //Primero tenemos que buscar el nodo este proceso ya nos lo sabemos buscamos derechas o irquierdas para ir llamando recursivamente
        if (node == null) {
            throw new Exception("Item not Found");
        } else {
            cmp = node.compareTo(element);
        }
        if (cmp < 0) {
            node.leftChild = deleteInside(node.leftChild, element, changeOfHeight);
            if (changeOfHeight.bool) {//En el caso de que el booleano sea true significa que al terminar la eliminacion ocurrio in desequilibrio
                node = balanceLeftBranch(node, changeOfHeight);
            }
        }
        if (cmp > 0) {
            node.rightChild = deleteInside(node.rightChild, element, changeOfHeight);
            if (changeOfHeight.bool) { //Igualmente si es true un desequilibrio ocurrio
                node = balanceRightBranch(node, changeOfHeight);
            }
        }
        if (cmp == 0) { //Okey hemos encontrado el nodo.
            //Ahora dependiendo de el nodo que queremos buscar si tiene hijos es hoja o no ocurrira un caso u otro :)
            Node<T> delete = node;
            if (delete.leftChild == null) {
                node = delete.rightChild;//Si no tiene hijo sustituimos por el hijo derecho y un desbalanceo ocurre
                changeOfHeight.bool = true;
            } else if (delete.rightChild == null) {
                node = delete.leftChild;//Igualmente si no tiene un hijo derecho sustituimos por el hijo izquiero y un desbalanceo ocurre
                changeOfHeight.bool = true;
            } else {
                node.leftChild = replaze(node, node.leftChild, changeOfHeight);//En este caso tiene dos hijos :( pues tenemos que buscar el numeor mas alto del arbol izquierdo
                if (changeOfHeight.bool) { // Y  si al salir nos desiquilibrmos pues equilibramos
                    node = balanceLeftBranch(node, changeOfHeight);
                }
            }
            delete = null;
        }
        return node;
    }


    private Node<T> replaze(Node<T> node, Node<T> actual, Logical changeOfHeight) {
        if (actual.rightChild != null) {
            actual.rightChild = replaze(node, actual.rightChild, changeOfHeight); // Hasta que llegemos al final seguimos llamando recursivamente
            if (changeOfHeight.bool) { //Igualmente hay que equilbrar si el replaze modifica algo
                actual = balanceRightBranch(actual, changeOfHeight);
            }
        } else {
            node.data = actual.data;
            node = actual;
            actual = actual.leftChild;
            node = null;
            changeOfHeight.bool = true;
        }
        return actual;
    }
    private Node<T> balanceLeftBranch(Node<T> node, Logical changeOfHeight) {
        switch (node.factor) {
            case -1:
                node.factor = 0;
                break;
            case 0:
                node.factor = +1;
                changeOfHeight.bool = false;
                break;
            case +1:
                if (node.rightChild.factor >= 0) {
                    if (node.rightChild.factor == 0) {
                        changeOfHeight.bool = false;
                    }
                    node = rotateRR(node);
                } else {
                    node = rotateRL(node);
                }
                break;
        }
        return node;
    }

    private Node<T> balanceRightBranch(Node<T> node, Logical changeOfHeight) {
        switch (node.factor) {
            case +1:
                node.factor = 0;
                break;
            case 0:
                node.factor = -1;
                changeOfHeight.bool = false;
                break;
            case -1:
                if (node.leftChild.factor <= 0) {
                    if (node.leftChild.factor == 0) {
                        changeOfHeight.bool = false;
                    }
                    node = rotateLL(node);
                } else {
                    node = rotateLR(node);
                }
                break;
        }
        return node;
    }


    //Implementacion checkBalanceo

    public boolean balanced(Node<T> node) {
        int leftHeight;

        int rightHeight;

        if (node == null) {
            return true;
        }

        leftHeight = height(node.leftChild);
        rightHeight = height(node.rightChild);

        return Math.abs(leftHeight - rightHeight) <= 1 && balanced(node.leftChild) && balanced(node.rightChild);

    }

    public int height(Node<T> node) {
        if (node == null)
            return 0;

        return 1 + Math.max(height(node.leftChild), height(node.rightChild));
    }


    // Implementacion de Visualizaciones


    public void preOrder() {
        preOrderPriv(root);
    }

    private void preOrderPriv(Node<T> node) {
        System.out.println(node.data);
        if (node.leftChild != null) {
            preOrderPriv(node.leftChild);
        }
        if (node.rightChild != null) {
            preOrderPriv(node.rightChild);
        }
    }

    public void inOrder() {
        inOrderPriv(root);
    }

    private void inOrderPriv(Node<T> node) {
        System.out.println(node.data);
        if (node.leftChild != null) {
            inOrderPriv(node.leftChild);
        }
        if (node.rightChild != null) {
            inOrderPriv(node.rightChild);
        }
    }

    public void postOrder() {
        postOrderPriv(root);
    }

    private void postOrderPriv(Node<T> node) {
        System.out.println(node.data);
        if (node.leftChild != null) {
            postOrderPriv(node.leftChild);
        }
        if (node.rightChild != null) {
            postOrderPriv(node.rightChild);
        }
    }

    private static void visualize(AVLTree<Integer> tree, ArrayList insertioTime) {
        Gson gson = new Gson();
        try {
            FileWriter fw = new FileWriter("src/main/Visualizador/VisualizadorDeArbolAVL/index.html");
            String treeString = gson.toJson(tree.root);
            fw.write("<!DOCTYPE html>\n" +
                    "<html lang=\"\">\n" +
                    "  <head>\n" +
                    "    <meta charset=\"utf-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>p5.js example</title>\n" +
                    "    <style> body {padding: 0; margin: 0;} </style>\n" +
                    "    <script src=\"../p5.min.js\"></script>\n" +
                    "    <script src=\"../addons/p5.dom.min.js\"></script>\n" +
                    "    <script src=\"../addons/p5.sound.min.js\"></script>\n" +
                    "    <script src=\"sketch.js\"></script>\n" +
                    "    <p id=\"tree\" >" + treeString + "</p>\n" +
                    "    <p id=\"insertioTime\" >" + insertioTime.toString() + "</p>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "  </body>\n" +
                    "</html>");
            fw.close();
            File file = new File("src/main/Visualizador/VisualizadorDeArbolAVL/index.html");
            Desktop.getDesktop().browse(file.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
