package org.example;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Vehicle;

import java.time.LocalDateTime;
import java.util.Iterator;

@Getter
@Setter
public class Garage<T extends Vehicle> implements Iterable<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public T get(int index) {
        Node<T> node = getNode(index);
        return node.element;
    }

    public void delete(int index) {
        if (index == 0) {
            first = first.next;
            first.prev = null;

        } else if (index == size - 1) {
            last = last.prev;
            last.next = null;
        } else {
            Node<T> beforeDeleted = getNode(index - 1);
            Node<T> afterDeleted = beforeDeleted.next.next;
            beforeDeleted.next = afterDeleted;
            afterDeleted.prev = beforeDeleted;
        }
        --size;
    }

    public void replace(int index, T element) {
        Node<T> foundNode = getNode(index);
        foundNode.element = element;
        foundNode.numberRestyling++;
        foundNode.updatedAt = LocalDateTime.now();
    }

    public int getLastRestyling(int index) {
        Node<T> foundedNode = getNode(index);
        return foundedNode.numberRestyling;
    }

    public LocalDateTime getLastUpdateAt(int index) {
        Node<T> foundedNode = getNode(index);
        return foundedNode.updatedAt;
    }

    public LocalDateTime getFirstCreatedAt(int index) {
        Node<T> foundedNode = getNode(index);
        return foundedNode.createdAt;
    }

    public void add(T element) {
        if (first == null) {
            Node<T> newNode = new Node<>(null, null, element);
            first = last = newNode;
        } else {
            Node<T> newNode = new Node<>(null, last, element);
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Your index overflows array bounds");
        }
        Node<T> currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }
        return currentNode;
    }

    public void add(int index, T element) {
        if (index == 0) {
            Node<T> newNode = new Node<>(first, null, element);
            first.prev = newNode;
            first = newNode;
            size++;
        } else if (index == size - 1) {
            add(element);
        } else {
            Node<T> beforeNewInserted = getNode(index - 1);
            Node<T> afterNewInserted = beforeNewInserted.next;
            Node<T> newNode = new Node<>(afterNewInserted, beforeNewInserted, element);
            beforeNewInserted.next = newNode;
            afterNewInserted.prev = newNode;
            size++;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new GarageIterator();
    }

    private class GarageIterator implements Iterator<T> {

        Node<T> current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the iteration.");
            }
            T element = current.element;
            current = current.next;
            return element;
        }

    }


    private static class Node<T> {
        Node<T> next;
        Node<T> prev;

        int numberRestyling;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
        T element;

        public Node(Node<T> next, Node<T> prev, T element) {
            this.next = next;
            this.prev = prev;
            this.element = element;
            createdAt = LocalDateTime.now();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[").append(System.lineSeparator());
        for (Node<T> current = first; current != null; current = current.next) {
            stringBuilder
                    .append(current.element)
                    .append(",")
                    .append(System.lineSeparator());
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

