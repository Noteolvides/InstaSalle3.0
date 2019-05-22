package HashTable;

import List.List;


class Table {
    List<Data> list;
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

    public Object search(E element) {
        for (int i = 0; i < hashTable.length; i++) {
            for (int j = 0; j < hashTable[i].list.size(); j++) {
                if (hashTable[i].list.get(i).element.equals(element)) {
                    return hashTable[i].list.get(i).element;
                }
            }
        }
        return null;
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

    public boolean contains(E element) {
        for (int i = 0; i < hashTable.length; i++) {
            for (int j = 0; j < hashTable[i].list.size(); j++) {
                if (hashTable[i].list.get(i).element.equals(element.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void delete(K key) {
        int hash = hashCode(getKey(key));
        //TODO SEARCH
        
       // hashTable[hash].list.remove(new Data(hash, key, element));
    }

    public void deleteElement(E element) {

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
                }
            }
            System.out.println(listhash);
        }
    }
}
