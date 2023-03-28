package oogasalad.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapted from of ChatGPT response to the following prompt:
 * I want to program a class in Java called BoardGraph. It's a directed graph that contains nodes with IDs, whose connections can have labels from one node to another. Each node should be findable within the BoardGraph by its id. Write the code for BoardGraph
 */

public class BoardGraph {
    private final Map<String, BoardGraphNode> nodeMap;

    public BoardGraph() {
        nodeMap = new HashMap<>();
    }

    public List<String> getNodes() {
        return new ArrayList<>(nodeMap.keySet());
    }

    /**
     * Returns the node with the given id.
     * @param nodeId the id of the node to get
     * @return the node with the given id, or null if it does not exist
     */
    public BoardGraphNode getNode(String nodeId) {
        return nodeMap.get(nodeId);
    }

    /**
     * Adds a node to the graph if it does not already exist.
     * @param nodeId the id of the node to add
     * @return true if the node was added, false if it already existed
     */
    public boolean addNode(String nodeId) {
        if (nodeId == null) throw new IllegalArgumentException("Node name cannot be null");
        return nodeMap.putIfAbsent(nodeId, new BoardGraphNode(nodeId)) == null;
    }

    /**
     * Adds a connection from one node to another with a label.
     * @param fromNodeId the id of the node to connect from
     * @param toNodeId the id of the node to connect to
     * @param label the label of the connection
     */
    public boolean addConnection(String fromNodeId, String toNodeId, String label) {
        BoardGraphNode fromNode = getNode(fromNodeId);
        BoardGraphNode toNode = getNode(toNodeId);

        if (fromNode == null || toNode == null) throw new IllegalArgumentException("Node name cannot be null");
        if (label == null) throw new IllegalArgumentException("Edge name cannot be null");

        return fromNode.addOutgoingConnection(toNode, label);
    }

    public static class BoardGraphNode {
        private final String id;
        private final HashMap<String, BoardGraphNode> edges;

        public BoardGraphNode(String nodeId) {
            this.id = nodeId;
            edges = new HashMap<>();
        }

        /**
         * Adds an outgoing connection from this node to another node with a label.
         * @param toNode the node to connect to
         * @param label the label of the connection
         * @return true if the connection was added, false if it already existed
         */
        public boolean addOutgoingConnection(BoardGraphNode toNode, String label) {
            if (edges.containsValue(toNode)) return false;
            return edges.putIfAbsent(label, toNode) == null;
        }

        /**
         * Returns a map of the outgoing edges of this node.
         * [ label : toNodeId ]
         * @return a map of the outgoing edges of this node
         */
        public Map<String, BoardGraphNode> getEdges() {
            return new HashMap<>(edges);
        }

        /**
         * Get the id of the node.
         * @return the id of this node
         */
        public String getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof BoardGraphNode b) {
                return id.equals(b.id);
            }
            return false;
        }

        @Override
        public String toString() {
            return id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }
}
