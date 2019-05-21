package HashTable;


import Data.Post;
import List.List;

class Table {
    List<String> list;
}

public class HashTable<K,E> {
    private Table[] hashTable;
    private int size;

    public HashTable(int length) {
        if (length < 53) {
            size = 53;
        } else if (length <= 193) {
            size = 193;
        } else if (length <= 769) {
            size = 769;
        } else if (length <= 1543) {
            size = 1543;
        } else if (length <= 3079) {
            size = 3079;
        } else if (length <= 6151) {
            size = 6151;
        } else if (length <= 12289) {
            size = 12289;
        } else if (length <= 49157) {
            size = 49157;
        } else if (length <= 196613) {
            size = 196613;
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

    public void visualize() {
        for (int i = 0; i < hashTable.length; i++) {
            for (int j = 0; j < hashTable[i].list.size(); j++) {
                if (hashTable[i].list.get(j) != null) {
                    System.out.println(hashTable[i].list.get(j));
                } else {
                    System.out.println("-- empty --");
                }
            }
        }
    }

    public void insertHashTags(List<String> hashtags) {
        for (int i = 0; i < hashtags.size(); i++) {
            insert((E) hashtags.get(i));
        }
    }
}
