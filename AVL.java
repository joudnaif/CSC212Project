/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc212project;

/**
 *
 * @author joudnaif
 */
public class AVL<T extends Comparable<T>> {
    private AVLNode<T> root;

    public AVL() {
        this.root = null;
    }

    public boolean empty() {
        return root == null;
    }

    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private int getBalance(AVLNode<T> node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private AVLNode<T> rightRotate(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private AVLNode<T> leftRotate(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(T element) {
        root = insert(root, element);
    }

    private AVLNode<T> insert(AVLNode<T> node, T element) {
        if (node == null) {
            return new AVLNode<>(element);
        }

        if (element.compareTo(node.element) < 0) {
            node.left = insert(node.left, element);
        } else if (element.compareTo(node.element) > 0) {
            node.right = insert(node.right, element);
        } else {
            return node; // Duplicate elements are not inserted
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && element.compareTo(node.left.element) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && element.compareTo(node.right.element) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && element.compareTo(node.left.element) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && element.compareTo(node.right.element) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public boolean findkey(T element) {
        return findkey(root, element);
    }

    private boolean findkey(AVLNode<T> node, T element) {
        if (node == null) {
            return false;
        }

        if (element.compareTo(node.element) < 0) {
            return findkey(node.left, element);
        } else if (element.compareTo(node.element) > 0) {
            return findkey(node.right, element);
        } else {
            return true;
        }
    }
    
    public T get(T element) {
        return get(root, element);
    }

    private T get(AVLNode<T> node, T element) {
        if (node == null) {
            return null;
        }
        if (element.compareTo(node.element) < 0) {
            return get(node.left, element);
        } else if (element.compareTo(node.element) > 0) {
            return get(node.right, element);
        } else {
            return node.element;
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(AVLNode<T> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.element);
            inOrder(node.right);
        }
    }
}

