package me.dibdin.adventofcode.year2021;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.dibdin.adventofcode.util.TreeNode;

import me.dibdin.adventofcode.AbstractChallenge;

/**
 * Advent of Code Challenge 2021 - Day 18: Snailfish.
 * https://adventofcode.com/2021/day/18
 */
public class Day18 extends AbstractChallenge {

    // puzzle input data
    ArrayList<String> puzzle = null;
    boolean readyToSolve = false;

    /**
     * Constructor
     */
    public Day18() {
        super("Snailfish", 2021, 18);
    }

    /**
     * Recusively search for the first node at the specified level
     * 
     * @param node  the node
     * @param level the current level
     * @return
     */
    private TreeNode<Integer> findFirstNodeAtLevel(TreeNode<Integer> node, int level) {
        if (node.isLeaf()) {
            return null;
        } else {
            if (level == 0) {
                return node;
            } else {
                TreeNode<Integer> n = findFirstNodeAtLevel(node.getLeft(), level - 1);
                if (n != null) {
                    return n;
                } else {
                    return findFirstNodeAtLevel(node.getRight(), level - 1);
                }
            }
        }
    }

    /**
     * Calculate the magnitude of the Snailfish sum
     * 
     * @param node the node the calculate the magnitude of
     * @return the result of this node and all sub-nodes
     */
    long calculateMagnitude(TreeNode<Integer> node) {
        if (node.isLeaf()) {
            return node.getValue();
        } else {
            return (3 * calculateMagnitude(node.getLeft())) + (2 * calculateMagnitude(node.getRight()));
        }
    }

    /**
     * Reduce the Snailfish number
     * 
     * @param root the root node to start reducing
     */
    void reduce(TreeNode<Integer> root) {

        // loop until we've finished reducing the nodes
        boolean reduced;

        do {
            reduced = false;

            // get a list of the leaves
            ArrayList<TreeNode<Integer>> leaves = new ArrayList<TreeNode<Integer>>();
            root.asList(leaves);

            // can we explode a pair?
            TreeNode<Integer> explodingPair = findFirstNodeAtLevel(root, 4);
            if (explodingPair != null) {

                // find the nodes to the left and to the right
                int leftIndex = leaves.indexOf(explodingPair.getLeft());
                int rightIndex = leaves.indexOf(explodingPair.getRight());

                // explode to the left
                if ((leftIndex - 1) >= 0) {
                    leaves.get(leftIndex - 1).setValue(
                            leaves.get(leftIndex - 1).getValue() +
                                    explodingPair.getLeft().getValue());
                }

                // explode to the right
                if ((rightIndex + 1) < leaves.size()) {
                    leaves.get(rightIndex + 1).setValue(
                            leaves.get(rightIndex + 1).getValue() +
                                    explodingPair.getRight().getValue());
                }
                explodingPair.setValue(0);

                reduced = true;
            } else {
                // search for a pair to split
                Iterator<TreeNode<Integer>> iterator = leaves.iterator();
                while (iterator.hasNext()) {
                    TreeNode<Integer> node = iterator.next();
                    Integer value = node.getValue();
                    if (value >= 10) {
                        // split this pair
                        if (value % 2 == 0) {
                            // even
                            node.setLeft(new TreeNode<Integer>(value / 2));
                            node.setRight(new TreeNode<Integer>(value / 2));
                        } else {
                            // odd
                            node.setLeft(new TreeNode<Integer>(value / 2));
                            node.setRight(new TreeNode<Integer>((value / 2) + 1));
                        }
                        reduced = true;
                        break;
                    }
                }
            }
        } while (reduced);
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        // parse the first input line
        Iterator<String> puzzleIterator = puzzle.iterator();
        TreeNode<Integer> root = TreeNode.parseTreeNode(puzzleIterator.next());

        while (puzzleIterator.hasNext()) {
            // add this number with the next, then reduce
            root = new TreeNode<Integer>(root, TreeNode.parseTreeNode(puzzleIterator.next()));
            reduce(root);
        }

        return calculateMagnitude(root);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        long max = Long.MIN_VALUE;

        // search for the largest magnitude off all possible sums
        for (int i = 0; i < puzzle.size(); i++) {
            for (int j = 0; j < puzzle.size(); j++) {
                if (i != j) {
                    TreeNode<Integer> root = new TreeNode<Integer>(
                            TreeNode.parseTreeNode(puzzle.get(i)),
                            TreeNode.parseTreeNode(puzzle.get(j)));
                    reduce(root);
                    long magnitude = calculateMagnitude(root);
                    if (magnitude > max) {
                        max = magnitude;
                    }
                }
            }
        }

        return max;
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        puzzle = input.filter(str -> !str.equals("")).collect(Collectors.toCollection(ArrayList<String>::new));

        readyToSolve = true;
    }
}