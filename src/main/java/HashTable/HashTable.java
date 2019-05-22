package HashTable;

import List.List;

import java.util.Objects;


public class HashTable<K,E> {
    private Table[] hashTable;
    private int space;
    private int size;

    public HashTable(int length) {
        if (length < 53) {
            space = 53;
        } else if (length <= 193) {
            space = 193;
        } else if (length <= 769) {
            space = 769;
        } else if (length <= 1543) {
            space = 1543;
        } else if (length <= 3079) {
            space = 3079;
        } else if (length <= 6151) {
            space = 6151;
        } else if (length <= 12289) {
            space = 12289;
        } else if (length <= 49157) {
            space = 49157;
        } else if (length <= 196613) {
            space = 196613;
        }
        size = 0;
        hashTable = new Table[space];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new Table();
            hashTable[i].list = new List<Data>();
        }
    }

    private int hashCode(int key) {
        return key % space;
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
        size++;
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
                if (hashTable[i].list.get(i).element.equals(element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsKeys(K key) {
        int hash = hashCode(getKey(key));
        for (int i = 0; i < hashTable[hash].list.size(); i++) {
            if (hashTable[hash].list.get(i).key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public Data[] dataSet() {
        List<Data> set = new List<Data>();
        for (int i = 0; i < hashTable.length; i++) {
            for (int j = 0; j < hashTable[i].list.size(); j++) {
                if (hashTable[i].list.get(j) != null) {
                    set.add(hashTable[i].list.get(j));
                }
            }
        }
        Data[] dataset = new Data[set.size()];
        for (int i = 0; i < set.size(); i++) {
            dataset[i] = set.get(i);
        }
        return dataset;
    }

    public K[] keySet() {
        List<Object> set = new List<Object>();
        for (int i = 0; i < hashTable.length; i++) {
            for (int j = 0; j < hashTable[i].list.size(); j++) {
                if (hashTable[i].list.get(j) != null) {
                    set.add(hashTable[i].list.get(j).key);
                }
            }
        }
        Object[] keyset = new Object[set.size()];
        for (int i = 0; i < set.size(); i++) {
            keyset[i] = set.get(i);
        }
        return (K[]) keyset;
    }

    public void delete(K key) {
        int hash = hashCode(getKey(key));
        for (int i = 0; i < hashTable[hash].list.size(); i++) {
            if (hashTable[hash].list.get(i).key.equals(key)) {
                hashTable[hash].list.remove(hashTable[hash].list.get(i));
            }
        }
        size--;
    }

    public void deleteElement(E element) {
        for (int i = 0; i < hashTable.length; i++) {
            for (int j = 0; j < hashTable[i].list.size(); j++) {
                if (hashTable[i].list.get(j).element.equals(element)) {
                    hashTable[i].list.remove(hashTable[i].list.get(j));
                    if (hashTable[i].list.size() == 0) {
                        size--;
                    }
                }
            }
        }
    }

    public int size() {
        return size;
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
