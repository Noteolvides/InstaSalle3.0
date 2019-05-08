package HashTable;


import List.List;

class Table {
    List<String> list;
}

public class HashTable<K,E> {
    private Table[] hashTable;
    private int size;

    public HashTable(int length) {
        if (length < 2999) {
            size = 2999;
        } else {
            size = 10069;
        }
        hashTable = new Table[size];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new Table();
            hashTable[i].list = new List<String>();
        }


    }

    private int hashCode(int key) {
        return key % size;
    }

    private int getKey(E element) {
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

    public void insert(E element) {
        int index = hashCode(getKey(element));
        hashTable[index].list.add((String)element);
    }

    public String search(E element) {
        String found = null;
        int index = hashCode(getKey(element));
        for (int i = 0; i < hashTable[index].list.size() && found == null; i++) {
            if (hashTable[index].list.get(i).equals(element.toString())) {
                found = hashTable[index].list.get(i);
            }
        }
        return found;
    }

    public void delete(E element) {
        int index = hashCode(getKey(element));
        hashTable[index].list.remove(element.toString());
    }

}
