package Trie;


import HashTable.HashTable;

import java.util.ArrayList;
import java.util.Map;

public class TrieTree {
    public static void main(String[] args) {
        TrieTree tree = new TrieTree();
        tree.insert("hola");
        tree.insert("hola2fjkosdkj");
        tree.insert("hola3erj");
        tree.insert("hola4");
        System.out.println(tree.containsQuery("h", 4));
    }
    int counter = 0;
    Node<Character> root = null;

    public void insert(String phrase) {
        if (root == null) {
            root = new Node<Character>(null);
        }
        insertInside(phrase, 0, root);
    }

    private void insertInside(String sentence, int i, Node<Character> node) {
        Character c = sentence.charAt(i);
        if (!node.children.containsKey(c)) {
            Node<Character> aux = new Node<Character>(c);
            if (i + 1 < sentence.length()) {
                node.children.put(c, aux);
                insertInside(sentence, i + 1, aux);
            } else {
                aux.isWord = true;
                node.children.put(c, aux);
            }
        } else {
            if (i + 1 < sentence.length()) {
                insertInside(sentence, i + 1, node.children.get(c));
            } else {
                node.children.get(c).isWord = true;
            }
        }
    }

    public boolean contains(String phrase) {
        if (root == null) {
            return false;
        }
        return containsInside(phrase, 0, root);
    }

    private boolean containsInside(String phrase, int i, Node<Character> node) {
        Character c = phrase.charAt(i);
        if (!node.children.containsKey(c)) {
            return false;
        } else {
            if (i + 1 < phrase.length()) {
                return containsInside(phrase, i + 1, node.children.get(c));
            }
            return node.children.get(c).isWord;
        }
    }

    public ArrayList<String> containsQuery(String phrase, int memoriaMaxima) {
        counter = 0;
        ArrayList<String> queryWords = new ArrayList<String>();
        queryWords.clear();
        if (root == null) {
            return null;
        }
        Node<Character> aux = containsInsideQuery(phrase, 0, root);
        getAllStrings(aux, new StringBuilder(), phrase, memoriaMaxima);
        return queryWords;
    }

    private void getAllStrings(Node<Character> aux, StringBuilder stringBuilder, String start, int memoriaMaxima) {
        for (Data<Character, Node<Character>> pair : aux.children.entrySet()) {
            if (counter < memoriaMaxima) {
                stringBuilder.append(pair.getKey());
                if (pair.getValue().isWord) {
                    counter++;
                    System.out.println(start+stringBuilder.toString());
                }
                getAllStrings(pair.getValue(), stringBuilder,start, memoriaMaxima);
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
    }



    private Node<Character> containsInsideQuery(String phrase, int i, Node<Character> node) {
        Character c = phrase.charAt(i);
        if (!node.children.containsKey(c)) {
            return null;
        } else {
            if (i + 1 < phrase.length()) {
                return containsInsideQuery(phrase, i + 1, node.children.get(c));
            }
            return node.children.get(c);
        }
    }

    public boolean delete(String phrase) {
        //Si root es null, no hay nada en el árbol y por tanto es fallido
        if (root == null) {
            return (false);
        }
        //Se llama al delete. Dependiendo del resultado (int) será exitoso o no
        int result = deleteinside(phrase, 0, root);
        //Si result es 1, será una operación fallida
        if (result == 1) {
            System.out.println("Delete fallido");
            return (false);
        }
        //En caso contrario, result será 4 y será una operación exitosa
        System.out.println("Delete exitoso");
        return (true);
    }

    private int deleteinside(String phrase, int i, Node<Character> node) {
        int j = 0;
        boolean found = false;
        int casenum = 0;

        //Se comprueva que el nodo sea final de palabra
        if (node.isWord) {
            if (node.data == phrase.charAt(i - 1)) {
                //Si lo es, se comprueva si alguna palabra depende de esta letra
                if (node.children.size() == 0) {
                    //Si no tiene hijos, se quita la flag se devuelve un 2
                    node.isWord = false;
                    return (2);
                } else {
                    //Si tiene hijos, se quita la flag y se devuelve un 4
                    if (phrase.charAt(phrase.length() - 1) == node.data) {
                        node.isWord = false;
                        return (4);
                    } else {
                        casenum = deleteinside(phrase, i + 1, node.children.get(phrase.charAt(i)));
                    }
                }
            }
        } else {
            if (node.children.get(phrase.charAt(i)).data == phrase.charAt(i)) {
                found = true;
                casenum = deleteinside(phrase, i + 1, node.children.get(phrase.charAt(i)));
            }

        }
        if (node == root) {
            casenum = 3;
        }
        switch (casenum) {
            case 1:
                return (1);
            case 2:
                if (!node.children.get(phrase.charAt(i)).isWord) {
                    if (node.children.size() <= 1) {
                        node.children.remove(node.children.get(phrase.charAt(i)).data);
                        return (2);
                    } else {
                        if ((node.children.size() > 1) && (!node.children.get(phrase.charAt(i)).isWord)) {
                            node.children.remove(phrase.charAt(i));
                        }
                        return (4);
                    }
                } else {
                    return (4);
                }
            case 3:
                if ((node.children.get(phrase.charAt(i)).children.size() == 0) && (!node.children.get(phrase.charAt(i)).isWord)) {
                    node.children.delete(node.children.get(phrase.charAt(i)).data);
                }
                return (4);
            case 4:
                //Success
                return (4);
        }

        return (1);
    }
}
