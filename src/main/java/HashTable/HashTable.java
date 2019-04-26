package HashTable;


import List.List;

public class HashTable<K,E> {
    private List[] hashTable;
    private int size;

    public HashTable(int lenght) {
        hashTable = new List[lenght];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new List<E>();
        }

        if (lenght < 2999) {
            size = 2999;
        } else {
            size = 10069;
        }
    }

    public int hashCode(int key) {
        return key % size;
    }

    public int getKey(E element) {
        int key = 0;
        if (element instanceof String) {
            char[] toCharArray = ((String) element).toCharArray();
            for (char c : toCharArray) {
                key += (int) c;
            }
        }
        if (element instanceof Character) {
            key = (int) (Character) element;
        }
        return key;
    }


}
