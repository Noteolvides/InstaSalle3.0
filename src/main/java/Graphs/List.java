package Graphs;

public class List<E> {
    // TODO: 2019-04-12 Hacer lista de c de apuntes de primero 
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public List() {
        this.head = new Node<E>(null);
        this.tail = head;
        this.size = 0;
    }

    public void add(E element) {
        tail = tail.next = new Node<E>(element);
        size++;
    }

    public boolean remove(E element) {
        boolean done = false;
        Node<E> prev = head;
        Node<E> actual = head;
        if (isEmpty()) {
            while (actual.next != null && actual != tail) {
                if (actual.element == element) {
                    prev.next = actual.next;
                    size--;
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
        return element;
    }

    public int size(){
         return size;
    }
}
