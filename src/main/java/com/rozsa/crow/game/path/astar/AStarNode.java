package com.rozsa.crow.game.path.astar;

public interface AStarNode {
    /**
     * Previous node.
     */
    AStarNode getPrevNode();

    /**
     * Total cost to get in this node. (path cost + other heuristics cost).
     */
    int getTotalCost();
}
