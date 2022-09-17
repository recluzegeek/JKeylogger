/*Custom implementation of Doubly Linked List in java*/

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    LinkedList() {
        this.head = tail = null;
    }

    // insertion at the end
    void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) head = tail = newNode;
        else {
            tail.next = newNode;
            newNode.prvs = tail;
            tail = newNode;
        }
    }

    // clear all elements from the list
    void clear() {
        this.head = tail = null;
    }

    @Override
    public String toString() {
        Node<T> current = head;
        StringBuilder dataString = new StringBuilder();
//        StringBuilder dataString = new StringBuilder("[");
        while (current != null) {
            if(current.data.equals("[Enter]")){
                dataString.append("\n");
            }
            dataString.append(current.data);
            current = current.next;
        }
        return dataString.toString();
    }

    public LinkedListIterator<T> listIterator() {
        return new LinkedListIterator<T>();
    }

    private class LinkedListIterator<E> implements Iterator<E>{
        private Node<E> current = null;
        @Override
        public boolean hasNext() {
            if (current == null && head != null) return true;
            else return current != null && current.next != null;
        }

        @Override
        public E next() {
            if (current == null && head != null){
                current = (Node<E>) head;
                return current.data;
            } else if (current != null) {
                current = current.next;
                return current.data;
            }
            return (E) new NoSuchElementException();
        }
    }
    private static class Node<T> {
        private Node<T> next;
        private Node<T> prvs;
        private T data;

        private Node(T data) {
            this.next = this.prvs = null;
            this.data = data;
        }
    }

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("My");
        list.add("name");
        list.add("is");
        list.add("salman");
        System.out.println(list);
        list.clear();
        System.out.println(list);
    }

    // implement linked list iterator




}
