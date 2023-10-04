package com.vrozsa.crowframework.game.path.astar;

/**
 * Represents a node from the graph.
 */
public interface AStarNode extends Comparable<AStarNode> {
    /**
     * Object unique identifier.
     * @return the key.
     */
    Object getKey();

    /**
     * Previous node.
     * @return the previous node if any.
     */
    AStarNode getPrevNode();

    /**
     * Total cost to get in this node.
     * @return total cost (path cost + other heuristics cost).
     */
    int getTotalCost();
}
