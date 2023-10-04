package com.vrozsa.crowframework.game.path.astar;

import com.vrozsa.crowframework.shared.time.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class AStarSolver {
    /**
     * First node to visit when solving a maze.
     */
    protected AStarNode startingNode;

    /**
     * Open list (frontier) nodes.
     */
    protected Map<Object, AStarNode> openList;
    protected PriorityQueue<AStarNode> sortedOpenList;

    /**
     * Closed list (visited) nodes.
     */
    protected Map<Object, AStarNode> closedList;

    /**
     * Elapsed time since started solving a maze (in milliseconds).
     */
    protected long elapsedTimeInMs;

    /**
     * Current node being checked.
     */
    protected AStarNode currNode;

    /// <summary>
    /// Create a AStar solver.
    /// </summary>
    /// <param name="_startingNode">Starting node.</param>
    public AStarSolver(AStarNode startingNode) {
        this.startingNode = startingNode;
    }

    /// <summary>
    /// Find a solution for a maze. (Template Method Design Pattern).
    /// </summary>
    /// <returns>An ordered list (first to last) of nodes to be visited to reach a solution;
    /// if interrupted, returns an empty list.</returns>
    public List<AStarNode> findSolution() {
        elapsedTimeInMs = 0;

        openList = new HashMap<>();
        sortedOpenList = new PriorityQueue<>();
        closedList = new HashMap<>();

        initializeOpenList();

        AStarNode nextNode;
        long startTime;
        boolean foundSolution = false;
        do {
            startTime = TimeUtils.getCurrentTime();
            nextNode = findLightestNode();

            closedList.put(nextNode.getKey(), nextNode);
            currNode = nextNode;

            if (hasFoundASolution()) {
                foundSolution = true;
                break;
            }

            List<AStarNode> neighbors = findNeighbors();
            neighbors.forEach(n -> openList.put(n.getKey(), n));
            sortedOpenList.addAll(neighbors);

            elapsedTimeInMs += TimeUtils.getTimePassedSince(startTime);
        } while (openList.size() > 0);

        return foundSolution ? buildSolutionArray() : new ArrayList<>();
    }

    /**
     * Amount of available nodes.
     */
    public int getOpenListCount() {
        return openList.size();
    }

    /**
     * Amount of visited nodes.
     */
    public int getClosedListCount() {
        return closedList.size();
    }

    /**
     * Add the first node to be visited in the open list.
     */
    private void initializeOpenList() {
        openList.put(startingNode.getKey(), startingNode);
        sortedOpenList.add(startingNode);
    }

    /**
     * Find the node with best heuristic and retrieve it from the list.
     * (will remove its reference from the list).
     *
     * @return The node with best heuristics in the list.
     */
    private AStarNode findLightestNode() {
        AStarNode bestNode = sortedOpenList.poll();
        openList.remove(bestNode.getKey());
        return bestNode;
    }

    /**
     * Check if the current node being tested is a in a solution state. (Primitive Operation)
     *
     * @return true if found a solution; false otherwise.
     */
    protected abstract Boolean hasFoundASolution();

    /**
     * Find the valid neighbors (next states) from the current node. (Primitive Operation)
     *
     * @return A list of valid neighbors from the current node.
     */
    protected abstract List<AStarNode> findNeighbors();

    /**
     * Retrieve all nodes of a solution starting from the last node. Reverse the solution so earlier
     * nodes are in the starting positions. (Hook Operation)
     *
     * @return The nodes that compose the solution.
     */
    protected List<AStarNode> buildSolutionArray() {
        List<AStarNode> solution = new ArrayList<>();
        AStarNode nextNode = currNode;

        while (nextNode != null && nextNode.getPrevNode() != null) {
            solution.add(nextNode);
            nextNode = nextNode.getPrevNode();
        }

        Collections.reverse(solution);

        return solution;
    }
}
