package Trie;

import HashTable.HashTable;

import java.util.HashMap;

public class Node<T extends Comparable> {
    HashTable<Character,Node<Character>> children = null;
    boolean isWord;
    T data;


    public Node(T data) {
        this.children =  new HashTable<Character, Node<Character>>(255);
        this.data = data;
        isWord = false;
    }


}
