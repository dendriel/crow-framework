package com.rozsa.crow.game.path.astar;

public interface AStarNode extends Comparable {
    /**
     * Object unique identifier.
     * @return
     */
    Object getKey();

    /**
     * Previous node.
     */
    AStarNode getPrevNode();

    /**
     * Total cost to get in this node. (path cost + other heuristics cost).
     */
    int getTotalCost();
}
