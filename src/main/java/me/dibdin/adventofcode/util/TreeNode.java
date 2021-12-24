package me.dibdin.adventofcode.util;

import java.util.ArrayList;

/**
 * A simple binary tree implementation, which only allows values on the
 * leaves, not the branches
 */
public class TreeNode<T> {
    private T value = null;
    private TreeNode<T> parent = null;
    private TreeNode<T> left = null;
    private TreeNode<T> right = null;

    /**
     * Default contructor
     */
    public TreeNode() {
    }

    /**
     * Initialise a leaf none with the given value
     * 
     * @param value Value to set
     */
    public TreeNode(T value) {
        setValue(value);
    }

    /**
     * Initialise a branch node, with left and right children
     * 
     * @param left  the left child
     * @param right the right child
     */
    public TreeNode(TreeNode<T> left, TreeNode<T> right) {
        setLeft(left);
        setRight(right);
    }

    /**
     * Get the value of this node
     * 
     * @return the value of this node
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value of this node, and sets the left and right children to null
     * 
     * @param value the new value to set
     */
    public void setValue(T value) {
        this.left = null;
        this.right = null;
        this.value = value;
    }

    /**
     * Get the left child node
     * 
     * @return the node
     */
    public TreeNode<T> getLeft() {
        return left;
    }

    /**
     * Sets the left child node, and sets the this to be the child's new parent
     * 
     * @param left the new child
     */
    public void setLeft(TreeNode<T> left) {
        this.value = null;
        this.left = left;
        if (left != null) {
            left.parent = this;
        }
    }

    /**
     * Get the right child node
     * 
     * @return the node
     */
    public TreeNode<T> getRight() {
        return right;
    }

    /**
     * Sets the right child node, and sets the this to be the child's new parent
     * 
     * @param right the new child
     */
    public void setRight(TreeNode<T> right) {
        this.value = null;
        this.right = right;
        if (right != null) {
            right.parent = this;
        }
    }

    /**
     * Get the parent of this node
     * 
     * @return the parent
     */
    public TreeNode<T> getParent() {
        return parent;
    }

    /**
     * Returns true is this node is a leaf - i.e. has a value
     * 
     * @return true is this node is a leaf, otherwise false.
     */
    public boolean isLeaf() {
        return (value != null);
    }

    /**
     * Return a String representation of this node and all children nodes
     * 
     * @return the String
     */
    @Override
    public String toString() {
        if (isLeaf()) {
            return value.toString();
        } else {
            return "[" + left + "," + right + "]";
        }
    }

    /**
     * Adds the leaves of this node and it's children to the array, reading left to
     * right
     * 
     * @param list the list to add the nodes to
     */
    public void asList(ArrayList<TreeNode<T>> list) {
        if (isLeaf()) {
            list.add(this);
        } else {
            left.asList(list);
            right.asList(list);
        }
    }

    /**
     * Parses a string representation of this node
     * 
     * @param inputString the string to parse
     * @return the newly created root node
     */
    public static TreeNode<Integer> parseTreeNode(String inputString) {

        char[] input = inputString.toCharArray();

        TreeNode<Integer> root = new TreeNode<Integer>();
        TreeNode<Integer> currentNode = root;

        // for each character in the input string
        for (int i = 1; i < input.length; i++) {
            if (input[i] == '[') {
                // start of a new branch node
                TreeNode<Integer> child = new TreeNode<Integer>();
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(child);
                } else {
                    currentNode.setRight(child);
                }
                currentNode = child;
            } else if (Character.isDigit(input[i])) {
                // a child node
                TreeNode<Integer> literalNode = new TreeNode<Integer>();
                literalNode.setValue(Integer.valueOf(Character.toString(input[i])));
                if (currentNode.getLeft() == null) {
                    currentNode.setLeft(literalNode);
                } else {
                    currentNode.setRight(literalNode);
                }
            } else if (input[i] == ',') {
                // nothing to do here
            } else if (input[i] == ']') {
                // end of branch
                currentNode = currentNode.getParent();
            }
        }

        return root;
    }
}