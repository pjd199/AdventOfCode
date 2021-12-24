package me.dibdin.adventofcode.year2021;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import me.dibdin.adventofcode.AbstractChallenge;
import me.dibdin.adventofcode.util.InputDecoder;

/**
 * Advent of Code Challenge 2021 - Day 15: Chiton.
 * https://adventofcode.com/2021/day/15
 */
public class Day15 extends AbstractChallenge {

    int[][] puzzle = null;
    boolean readyToSolve = false;

    /**
     * Constructor.
     */
    public Day15() {
        super("Chiton", 2021, 15);
    }

    /**
     * A node in the graph
     */
    class GraphNode {
        public int x;
        public int y;
        public int weight;
        public boolean visited;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof GraphNode) {
                GraphNode n = (GraphNode) obj;
                return ((this.x == n.x) && (this.y == n.y));
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return (1009 * x) + y;
        }
    }

    class CompareNodeweight implements Comparator<GraphNode> {

        public int compare(GraphNode n1, GraphNode n2) {
            return n1.weight - n2.weight;
        }

        public boolean equals(Object obj) {
            return (obj instanceof CompareNodeweight);
        }
    }

    /**
     * Find the shortest path from top let to bottom right, using the weightings in
     * the grid.
     * Implementations use the Dijkstra's alogrithm with a priority queue.
     * 
     * @param grid the input grod
     * @return the total weight of the shortest path
     */
    private int findShortestPath(int[][] grid) {
        PriorityQueue<GraphNode> pq = new PriorityQueue<GraphNode>(grid.length * grid[0].length, new CompareNodeweight());

        GraphNode[][] nodes = new GraphNode[grid.length][grid[0].length];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                nodes[y][x] = new GraphNode();
                nodes[y][x].x = x;
                nodes[y][x].y = y;
                nodes[y][x].weight = Integer.MAX_VALUE;
                nodes[y][x].visited = false;
                pq.add(nodes[y][x]);
            }
        }

        // set the starting node to zero
        pq.remove(nodes[0][0]);
        nodes[0][0].weight = 0;
        pq.add(nodes[0][0]);

        GraphNode endNode = nodes[nodes.length -1][nodes[0].length-1];

        while (pq.size() > 0) {
            // remove the node with the smallest weight from the queue
            GraphNode current = pq.poll();
            current.visited = true;

            // have we reached the end point
            if (current.equals(endNode)) {
                return current.weight;
            }

            // look left
            if (current.x > 0) {
                GraphNode left = nodes[current.y][current.x - 1];
                if (!left.visited) {
                    int tempWeight = current.weight + grid[current.y][current.x - 1];
                    if (tempWeight < left.weight) {
                        pq.remove(left);
                        left.weight = tempWeight;
                        pq.add(left);
                    }
                }
            }

            // look right
            if (current.x < (grid[0].length - 1)) {
                GraphNode right = nodes[current.y][current.x + 1];
                if (!right.visited) {
                    int tempWeight = current.weight + grid[current.y][current.x + 1];
                    if (tempWeight < right.weight) {
                        pq.remove(right);
                        right.weight = tempWeight;
                        pq.add(right);
                    }
                }
            }

            // look up
            if (current.y > 0) {
                GraphNode up = nodes[current.y - 1][current.x];
                if (!up.visited) {
                    int tempWeight = current.weight + grid[current.y - 1][current.x];
                    if (tempWeight < up.weight) {
                        pq.remove(up);
                        up.weight = tempWeight;
                        pq.add(up);
                    }
                }
            }

            // look down
            if (current.y < (grid.length - 1)) {
                GraphNode down = nodes[current.y + 1][current.x];
                if (!down.visited) {
                    int tempWeight = current.weight + grid[current.y + 1][current.x];
                    if (tempWeight < down.weight) {
                        pq.remove(down);
                        down.weight = tempWeight;
                        pq.add(down);
                    }
                }
            }
        }

        // no path found
        return -1;
    }

    /**
     * Solve part one of the puzzle
     */
    public long solvePartOne() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        return findShortestPath(puzzle);
    }

    /**
     * Solve part two of the puzzle
     */
    public long solvePartTwo() {
        if (!readyToSolve) {
            throw new IllegalStateException("No puzzle input set");
        }

        // enlarge the puzzle
        int factor = 5;
        int[][] enlargedPuzzle = new int[puzzle.length * factor][puzzle[0].length * factor];

        for (int y = 0; y < enlargedPuzzle.length; y++) {
            for (int x = 0; x < enlargedPuzzle[y].length; x++) {
                enlargedPuzzle[y][x] = (puzzle[y % puzzle.length][x % puzzle[0].length]
                        + (y / puzzle.length) + (x / puzzle[0].length));

                while (enlargedPuzzle[y][x] > 9) {
                    enlargedPuzzle[y][x] -= 9;
                }
            }
        }

        return findShortestPath(enlargedPuzzle);
    }

    /**
     * Take the stream of strings, and decode the input into puzzle data for this
     * challenge.
     */
    public void setPuzzleInput(Stream<String> input) {
        puzzle = InputDecoder.decodeAsTwoDimensionalIntArray(input);

        readyToSolve = true;
    }
}