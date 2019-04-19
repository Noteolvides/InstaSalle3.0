package Graphs;

public class List<E> {
    // TODO: 2019-04-12 Hacer lista de c de apuntes de primero 
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public List() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(E element) {
        Node<E> aux = tail;
        if (!isEmpty()) {
            tail = new Node<E>(element);
            aux.next = tail;
        } else {
            tail = new Node<E>(element);
            head = tail;
        }
        size++;
    }

    public boolean remove(E element) {
        boolean done = false;
        Node<E> prev = head;
        Node<E> actual = head;
        if (!isEmpty()) {
            while (actual.next != null && actual != tail && !done) {
                if (actual.element.equals(element)) {
                    prev.next = actual.next;
                    size--;
                    done = true;
                }
                prev = actual;
                actual = actual.next;
            }
        }
        return done;
    }

    private boolean isEmpty() {
        return head == null;
    }

    public E get(E element) {
        E found = null;
        Node<E> actual = head;
        if (!isEmpty()) {
            while (actual != null && found == null) {
                if (actual.element.equals(element)) {
                    found = actual.element;
                }
                actual = actual.next;
            }
        }
        return found;
    }

    public int size(){
         return size;
    }

    public E get(int index) {
        int jumps = 0;
        E found = null;
        Node<E> actual = head;
        if (!isEmpty()) {
            while (actual != null && found == null) {
                if (jumps == index) {
                    found = actual.element;
                }
                jumps++;
                actual = actual.next;
            }
        }
        return found;
    }
}
