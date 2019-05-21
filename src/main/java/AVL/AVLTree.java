package AVL;


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

    public boolean search(T element) {
        return searchInside(root, element);
    }

    private boolean searchInside(Node<T> node, T element) {
        if (node.data == element) {
            return true;
        }
        if (node.leftChild != null) {
            return searchInside(node.leftChild, element);
        }
        if (node.rightChild != null) {
            return searchInside(node.rightChild, element);
        }
        return false;
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
        Logical act = new Logical();
        try {
            root = insert(data, root, act);
        } catch (Exception e) {
            System.out.println("Eroor al añadir un item");
        }
    }

    //Implementacion de la insercion


    private Node<T> insert(T data, Node<T> node, Logical act) throws Exception {
        if (node == null) {
            node = new Node<T>(data);
            act.bool = true;
        } else {
            int cmp = node.compareTo(data);
            if (cmp == 0) {
                throw new Exception("No se pueden introducir nodos repetidos");
            }
            if (cmp > 0) {
                node.leftChild = insert(data, node.leftChild, act);
                if (act.bool) {
                    if (node.factor == +1) {
                        node.factor = 0;
                        act.bool = false;
                    } else if (node.factor == 0) {
                        node.factor = -1;
                        act.bool = true;
                    } else if (node.factor == -1) {
                        if (node.leftChild.factor == -1) {
                            node = rotateLL(node);
                        } else {
                            node = rotateLR(node);
                        }
                        act.bool = false;
                    }
                }
            }
            if (cmp < 0) {
                node.rightChild = insert(data, node.rightChild, act);
                if (act.bool) {
                    if (node.factor == -1) {
                        node.factor = 0;
                        act.bool = false;
                    } else if (node.factor == 0) {
                        node.factor = +1;
                        act.bool = true;
                    } else if (node.factor == +1) {
                        if (node.rightChild.factor == +1) {
                            node = rotateRR(node);
                        } else {
                            node = rotateRL(node);
                        }
                        act.bool = false;
                    }
                }
            }
        }
        return node;
    }

    //TODO : Implementacion de la eliminacion
    public void delete(T element) throws Exception {
        root = deleteInside(root, element, new Logical());
    }

    private Node<T> deleteInside(Node<T> node, T element, Logical changeOfHeight) throws Exception {
        int cmp;
        if (node == null) {
            throw new Exception("Item not Found");
        } else {
            cmp = node.compareTo(element);
        }
        if (cmp > 0) {
            node.leftChild = deleteInside(node.leftChild, element, changeOfHeight);
            if (changeOfHeight.bool) {
                node = balanceLeftBranch(node, changeOfHeight);
            }
        }
        if (cmp < 0) {
            node.rightChild = deleteInside(node.rightChild, element, changeOfHeight);
            if (changeOfHeight.bool) {
                node = balanceRightBranch(node, changeOfHeight);
            }
        }
        if (cmp == 0) {
            Node<T> delete = node;
            if (delete.leftChild == null) {
                node = delete.rightChild;
                changeOfHeight.bool = true;
            } else if (delete.rightChild == null) {
                node = delete.leftChild;
                changeOfHeight.bool = true;
            } else {
                node.leftChild = replaze(node, node.leftChild, changeOfHeight);
                if (changeOfHeight.bool) {
                    node = balanceLeftBranch(node, changeOfHeight);
                }
            }
            delete = null;
        }
        return node;
    }


    private Node<T> replaze(Node<T> node, Node<T> actual, Logical changeOfHeight) {
        if (actual.rightChild != null) {
            actual.rightChild = replaze(node, actual.rightChild, changeOfHeight);
            if (changeOfHeight.bool) {
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
