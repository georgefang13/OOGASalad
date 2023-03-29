package oogasalad.backend.ownables.gameobjects.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import oogasalad.backend.ownables.gameobjects.GameObject;
import oogasalad.backend.owners.Owner;
import oogasalad.backend.owners.GameWorld;


public class BoardGraph extends GameObject {
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
        if (nodeId.isEmpty()) throw new IllegalArgumentException("Node name cannot be empty");
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

    /**
     * @see oogasalad.backend.ownables.Ownable#canBeOwnedBy(oogasalad.backend.owners.Owner)
     * BoardGraphNodes can only be owned by the game.
     */
    public boolean canBeOwnedBy(Owner potentialOwner) {
        return potentialOwner instanceof GameWorld;
    }

    /**
     * Follows a path of edges from a starting node.
     * @param startNode the id of the node to start from
     * @param path the list of labels of edges to follow
     * @return the node at the end of the path
     */
    public BoardGraphNode followPath(String startNode, List<String> path) {
        BoardGraphNode currentNode = getNode(startNode);
        for (String s : path) {
            if (currentNode == null) return null;
            currentNode = currentNode.getEdges().get(s);
        }
        return currentNode;
    }

    /**
     * Follows a path of edges from a starting node, tests to see if any node along the path is blocked.
     * @param startNode the id of the node to start from
     * @param path the list of labels of edges to follow
     * @param isBlocked a function that takes a node and returns true if it is blocked
     * @return true if the path is blocked, false otherwise
     */
    public boolean isPathBlocked(String startNode, List<String> path, Predicate<BoardGraphNode> isBlocked) {
        BoardGraphNode currentNode = getNode(startNode);
        if (currentNode == null) return true;
        for (String s : path) {
            currentNode = currentNode.getEdges().get(s);
            if (currentNode == null) return true;
            if (isBlocked.test(currentNode)) return true;
        }
        return false;
    }

    // TODO: put text in properties file
    /**
     * Creates a grid graph with the given number of rows and columns. Edges are Up, Down, Left, Right, UpLeft, UpRight, DownLeft, DownRight.
     * Node IDs start with 0,0 at the top left and increase to the right and down, with the format "y,x"
     * <br>
     * example:
     * <br><br>
     * grid with 3 rows, 5 columns:
     * <table>
     *      <tr>
     *          <td>0,0</td> <td>↔</td> <td>0,1</td> <td>↔</td> <td>0,2</td> <td>↔</td> <td>0,3</td> <td>↔</td> <td>0,4</td>
     *      </tr>
     *      <tr>
     *          <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td></td>
     *      </tr>
     *      <tr>
     *          <td>1,0</td> <td>↔</td> <td>1,1</td> <td>↔</td> <td>1,2</td> <td>↔</td> <td>1,3</td> <td>↔</td> <td>1,4</td>
     *      </tr>
     *      <tr>
     *          <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td></td>
     *      </tr>
     *      <tr>
     *          <td>2,0</td> <td>↔</td> <td>2,1</td> <td>↔</td> <td>2,2</td> <td>↔</td> <td>2,3</td> <td>↔</td> <td>2,4</td>
     *      </tr>
     * </table>
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return a grid graph with the given number of rows and columns
     */
    public static BoardGraph createGrid(int rows, int cols) {
        BoardGraph graph = new BoardGraph();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String id = i + "," + j;
                graph.addNode(id);
            }
        }

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                String id = i + "," + j;
                if (i > 0)          graph.addConnection(id, (i - 1) + "," + j, "Up");
                if (i < rows - 1)   graph.addConnection(id, (i + 1) + "," + j, "Down");
                if (j > 0)          graph.addConnection(id, i + "," + (j - 1), "Left");
                if (j < cols - 1)   graph.addConnection(id, i + "," + (j + 1), "Right");
                if (i > 0 && j > 0) graph.addConnection(id, (i - 1) + "," + (j - 1), "UpLeft");
                if (i > 0 && j < cols - 1)  graph.addConnection(id, (i - 1) + "," + (j + 1), "UpRight");
                if (i < rows - 1 && j > 0)  graph.addConnection(id, (i + 1) + "," + (j - 1), "DownLeft");
                if (i < rows - 1 && j < cols - 1)   graph.addConnection(id, (i + 1) + "," + (j + 1), "DownRight");
            }
        }

        return graph;
    }

    /**
     * Follows a path of edges completely and checks to see if the end node is blocked. Does this continuously until the path is repeated, blocked, or the edge of the board is reached.
     * @param startNode the id of the node to start from
     * @param path the list of labels of edges to follow
     * @param isBlocked a function that takes a node and returns true if it is blocked
     * @return a list of all the open nodes that can be reached with that path
     */
    public List<String> findSpotsUntilBlocked(String startNode, List<String> path, Predicate<BoardGraphNode> isBlocked) {
        BoardGraphNode currentNode = getNode(startNode);
        List<String> spots = new ArrayList<>();
        while (!spots.contains(currentNode.getId())){
            currentNode = followPath(currentNode.getId(), path);
            if (currentNode == null) return spots;
            if (isBlocked.test(currentNode)) return spots;
            spots.add(currentNode.getId());
        }
        return spots;
    }

    public static class BoardGraphNode {
        private final String id;
        private final HashMap<String, BoardGraphNode> edges;

        private final HashMap<String, Object> holding;

        public BoardGraphNode(String nodeId) {
            this.id = nodeId;
            edges = new HashMap<>();
            holding = new HashMap<>();
        }

        public void putObject(String key, Object value) {
            holding.put(key, value);
        }

        public Object removeObject(String key) {
            return holding.remove(key);
        }

        public Object getObject(String key) {
            return holding.get(key);
        }

        public boolean hasObject(String key) {
            return holding.containsKey(key);
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
