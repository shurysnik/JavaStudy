package org.example;


//Добавить возможность подсчета стоимости правой и левой ветки нод относительно корня

import org.example.model.Vehicle;

import java.math.BigDecimal;
import java.util.Comparator;

public class Tree<T extends Vehicle> {
    private Node<T> root;
    private int size;
    private Comparator<T> comparator;

    public Tree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public boolean add(T value) {
        if (value == null) {
            return false;
        }
        root = add(value, root);
        size++;
        return true;
    }

    private Node<T> add(T value, Node<T> current) {
        if (current == null) {
            return new Node<>(value);
        }
        int compare = comparator.compare(value, current.element);
        if (compare > 0) {
            current.right = add(value, current.right);
        } else if (compare < 0) {
            current.left = add(value, current.left);
        }
        return current;
    }

    public void print() {
        print(root);
    }

    public BigDecimal getLeftTreePrice() {
        return calculateSummaryPrice(root.left);
    }

    public BigDecimal getRightTreePrice() {
        return calculateSummaryPrice(root.right);

    }

    private BigDecimal calculateSummaryPrice(Node<T> current) {
        if (current == null) {
            return BigDecimal.ZERO;
        }
        return calculateSummaryPrice(current.left)
                .add(current.element.getPrice())
                .add(calculateSummaryPrice(current.right));
    }

    private void print(Node<T> current) {
        if (current == null) {
            return;
        }
        print(current.left);
        System.out.println(current.element.getModel());
        print(current.right);
    }

    @Override
    public String toString() {
        return "Tree{" +
                "root=" + root +
                ", size=" + size +
                ", comparator=" + comparator +
                '}';
    }

    private static class Node<T> {
        private T element;
        private Node<T> right;
        private Node<T> left;

        public Node(T element) {
            this.element = element;
        }
    }

}
