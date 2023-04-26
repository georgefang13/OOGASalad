package oogasalad.sharedDependencies.backend.ownables.gameobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import oogasalad.sharedDependencies.backend.owners.Owner;

public class DropZone extends GameObject {

  private final HashMap<String, DropZone> edges;
  private final HashMap<String, Object> holding;

  /**
   * Creates a new DropZone with null owner
   * For testing purposes only
   */
  public DropZone() {
    this(null);
  }

  public DropZone(Owner owner) {
    super(owner);
    edges = new HashMap<>();
    holding = new HashMap<>();
  }


  /**
   * Adds an object to the node. If the object is already in the dropzone, it updates the key
   *
   * @param key   the key of the object
   * @param value the object to add
   */
  public void putObject(String key, Object value) {
    if (holding.containsValue(value)){
      String prevKey = getKey(value);
      holding.remove(prevKey);
    }
    holding.put(key, value);
  }

  /**
   * Removes the object with the given key from the node.
   *
   * @param key the key of the object
   * @return the object that was removed
   */
  public Object removeObject(String key) {
    return holding.remove(key);
  }

  /**
   * Returns the object with the given key.
   *
   * @param key the key of the object
   * @return the object with the given key
   */
  public Object getObject(String key) {
    return holding.get(key);
  }

  public String getKey(Object value) {
    List<Map.Entry<String, Object>> key = holding.entrySet()
            .stream()
            .filter(entry -> value.equals(entry.getValue()))
            .toList();
    if (key.size() == 1) {
      return key.get(0).getKey();
    }
    return null;
  }

  public List<Object> getAllObjects() {
    return new ArrayList<>(holding.values());
  }

  /**
   * Returns true if the node is holding an object with the given key.
   *
   * @param key the key of the object
   * @return true if the node is holding an object with the given key
   */
  public boolean hasObject(String key) {
    return holding.containsKey(key);
  }

  /**
   * Adds an outgoing connection from this node to another node with a label.
   *
   * @param toNode the node to connect to
   * @param label  the label of the connection
   * @return true if the connection was added, false if it already existed
   */
  public boolean addOutgoingConnection(DropZone toNode, String label) {
    if (toNode == null) {
      throw new IllegalArgumentException("Cannot add null node to graph.");
    }
    if (label == null || label.isEmpty()) {
      throw new IllegalArgumentException("Edge name cannot be empty.");
    }
    if (edges.containsValue(toNode)) {
      return false;
    }
    return edges.putIfAbsent(label, toNode) == null;
  }

  /**
   * Returns a map of the outgoing edges of this node. [ label : toNodeId ]
   *
   * @return a map of the outgoing edges of this node
   */
  public Map<String, DropZone> getEdges() {
    return new HashMap<>(edges);
  }


  /**
   * Follows a path of edges from a starting node.
   *
   * @param path the list of labels of edges to follow
   * @return the node at the end of the path
   */
  public DropZone followPath(List<String> path) {
    DropZone currentNode = this;
    for (String s : path) {
      if (currentNode == null) {
        return null;
      }
      currentNode = currentNode.getEdges().get(s);
    }
    return currentNode;
  }

  /**
   * Follows a path of edges from a starting node, tests to see if any node along the path is
   * blocked.
   *
   * @param path      the list of labels of edges to follow
   * @param isBlocked a function that takes a node and returns true if it is blocked
   * @return true if the path is blocked, false otherwise
   */
  public boolean isPathBlocked(List<String> path, Predicate<DropZone> isBlocked) {
    DropZone currentNode = this;
    for (String s : path) {
      currentNode = currentNode.getEdges().get(s);
      if (currentNode == null) {
        return true;
      }
      if (isBlocked.test(currentNode)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Follows a path of edges completely and checks to see if the end node is blocked. Does this
   * continuously until the path is repeated, blocked, or the edge of the board is reached.
   *
   * @param path      the list of labels of edges to follow
   * @param isBlocked a function that takes a node and returns true if it is blocked
   * @return a list of all the open nodes that can be reached with that path
   */
  public List<DropZone> findSpotsUntilBlocked(List<String> path, Predicate<DropZone> isBlocked) {
    DropZone currentNode = this;
    List<DropZone> spots = new ArrayList<>();
    while (true) {
      currentNode = currentNode.followPath(path);
      if (currentNode == null || spots.contains(currentNode) || isBlocked.test(currentNode)) {
        break;
      }
      spots.add(currentNode);
    }
    return spots;
  }

  @Override
  public String toString() {
    return "DropZone-" + hashCode();
  }

}