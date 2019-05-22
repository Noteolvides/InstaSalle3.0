package HashTable;

import List.List;


class Table {
    List<Data> list;
}

class Data<K,E> {
    int hash;
    K key;
    E element;

    Data(int hash, K key, E element) {
        this.hash = hash;
        this.key = key;
        this.element = element;
    }
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
            hashTable[i].list = new List<Data>();
        }
    }

    private int hashCode(int key) {
        return key % size;
    }

    private int getKey(K key) {
        if (key instanceof Character) {
            return (int)(Character) key;
        } else if (key instanceof String) {
            int stringkey = 0;
            char[] toCharArray = ((String) key).toCharArray();
            for (char c : toCharArray) {
                stringkey += (int) c;
            }
            return stringkey;
        } else if (key instanceof Integer) {
            return (int) (Integer)key;
        }
        return 0;
    }

    private int getKeyFromElement(E element) {
        int key = 0;
        if (element instanceof String) {
            char[] toCharArray = ((String) element).toCharArray();
            for (char c : toCharArray) {
                key += (int) c;
            }
        }
        if (element instanceof Character) {
            key = (int) (Character) element;
        } else if (element instanceof Integer) {
            key = (int) (Integer) element;
        }
        return key;
    }

    public void insert(E element) {
        int key = getKeyFromElement(element);
        int hash = hashCode(key);
        hashTable[hash].list.add(new Data(hash, key, element));
    }

    public String search(E element) {
        String found = null;
        int index = hashCode(getKeyFromElement(element));
        for (int i = 0; i < hashTable[index].list.size() && found == null; i++) {
            if (hashTable[index].list.get(i).equals(element.toString())) {
                found = hashTable[index].list.get(i).element.toString();
            }
        }
        return found;
    }

    public void put(K key, E element) {
        int hash = hashCode(getKey(key));
        hashTable[hash].list.add(new Data(hash, key, element));
    }

    public E get(K key) {
        List<Data> data = hashTable[hashCode(getKey(key))].list;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).key.equals(key)) {
                return (E) data.get(i).element;
            }
        }
        return null;
    }

    public void delete(E element) {
        int key = getKeyFromElement(element);
        int hash = hashCode(key);
        hashTable[hash].list.remove(new Data(hash, key, element));
    }

    public void visualize() {
        for (int i = 0; i < hashTable.length; i++) {
            String listhash = i + " : ";
            for (int j = 0; j < hashTable[i].list.size(); j++) {
                if (hashTable[i].list.get(j) != null) {
                    if (j != 0) {
                        listhash += ", ";
                    }
                    listhash += hashTable[i].list.get(j).element;
                    System.out.println(get((K) "#sonyalpha"));
                }
            }
            System.out.println(listhash);
        }
    }
}
