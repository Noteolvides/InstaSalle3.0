package HashTable;

public class Data<K,E> {
    int hash;
    K key;
    E element;

    Data(int hash, K key, E element) {
        this.hash = hash;
        this.key = key;
        this.element = element;
    }

    K getKey() {
        return key;
    }

    E getElement() {
        return element;
    }
}
