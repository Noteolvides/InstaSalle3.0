package Trie;

import java.util.HashMap;

public class Node<T extends Comparable> {
    HashMap<Character,Node<Character>> children = null;
    boolean isWord;
    T data;


    public Node(T data) {
        this.children =  new HashMap<Character, Node<Character>>();
        this.data = data;
        isWord = false;
    }


}
