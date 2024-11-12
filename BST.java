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
public class BST <T> {
    
    BSTNode<T> root, current;
/** Creates a new instance of BST */
public BST() {
    root = current = null;
}
public boolean empty() {
    return root == null;
}
public boolean full() {
    return false;
}
public T retrieve () {
    return current.data;
}

    public BSTNode<T> getRoot() {
        return root;
    }

    public BSTNode<T> getCurrent() {
        return current;
    }

public boolean findkey(String tkey) {
    BSTNode<T> p = root, q = root;

    if (empty()) {
        return false;
    }
    while (p != null) {
        q = p;
        if (p.key.equals(tkey)) { // Use equals() to compare strings
            current = p;
            return true;
        } else if (tkey.compareTo(p.key) < 0) { // Use compareTo() for comparison
            p = p.left;
        } else {
            p = p.right;
        }
    }
    current = q;
    return false;
}

public boolean insert(String k, T val) {
    BSTNode<T> p, q = current;
    if (findkey(k)) {
        current = q; // findkey() modified current
        return false; // Key already in the BST
    }
    p = new BSTNode<T>(k, val);
    if (empty()) {
        root = current = p;
        return true;
    } else {
        // current is pointing to parent of the new key
        if (k.compareTo(current.key) < 0) { // Use compareTo() for comparison
            current.left = p;
        } else {
            current.right = p;
        }
        current = p;
        return true;
    }
}

public void inOrder() {
    inOrderTraversal(root);
    System.out.println();
}

private void inOrderTraversal(BSTNode<T> node) {
    if (node == null) {
        return;
    }
    inOrderTraversal(node.left); // Visit the left subtree
    System.out.print(node.data + " "); // Visit the current node
    inOrderTraversal(node.right); // Visit the right subtree
}

public void preOrder() {
    preOrderTraversal(root);
    System.out.println();
}

private void preOrderTraversal(BSTNode<T> node) {
    if (node == null) {
        return;
    }
    System.out.print(node.data + " "); // Visit the current node
    preOrderTraversal(node.left); // Visit the left subtree
    preOrderTraversal(node.right); // Visit the right subtree
}

public void postOrder() {
    postOrderTraversal(root);
    System.out.println();
}

private void postOrderTraversal(BSTNode<T> node) {
    if (node == null) {
        return;
    }
    postOrderTraversal(node.left); // Visit the left subtree
    postOrderTraversal(node.right); // Visit the right subtree
    System.out.print(node.data + " "); // Visit the current node
}

}
