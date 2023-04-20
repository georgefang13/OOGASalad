package oogasalad.sharedDependencies.backend.ownables.gameobjects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.owners.Owner;

public class DropZone extends GameObject {

  private final String id;
  private final HashMap<String, DropZone> edges;
  private final HashMap<String, Object> holding;

  public DropZone(String nodeId) {
    this(nodeId, null);
  }

  public DropZone(String nodeId, Owner owner) {
    super(owner);
    this.id = nodeId;
    edges = new HashMap<>();
    holding = new HashMap<>();
  }

  /**
   * Get the id of the node.
   *
   * @return the id of this node
   */
  public String getId() {
    return id;
  }

  /**
   * Adds an object to the node.
   *
   * @param key   the key of the object
   * @param value the object to add
   */
  public void putObject(String key, Object value) {
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
  public List<String> findSpotsUntilBlocked(List<String> path, Predicate<DropZone> isBlocked) {
    DropZone currentNode = this;
    List<String> spots = new ArrayList<>();
    while (true) {
      currentNode = currentNode.followPath(path);
      if (currentNode == null || spots.contains(currentNode.getId()) || isBlocked.test(
          currentNode)) {
        break;
      }
      spots.add(currentNode.getId());
    }
    return spots;
  }

  @Override
  public void fromConfigFile(String object) {
    // TODO: make validation check, likely as static method of FileManager
    // TODO: pass ID into IdManager (maybe change constructor?)
    // this.id = FileManager.getStringByKey(object, "id");

    for (JsonElement edgeEntry : object.get("connections").getAsJsonArray()) {
      JsonObject edge = edgeEntry.getAsJsonObject();
//            edges.put(FileManager.getStringByKey(edge, "edgeId"));
    }

    for (JsonElement objectEntry : object.get("starterObjects").getAsJsonArray()) {
      // TODO: get gameObject by Id and add it to holding
    }


  }

  @Override
  public String getAsJson() {
    FileManager fileManager = new FileManager();
    fileManager.addContent("id", new JsonPrimitive(id));
    for (String edgeId : edges.keySet()) {
      JsonObject edge = new JsonObject();
      edge.add("edgeId", new JsonPrimitive(edgeId));
      edge.add("nodeId", new JsonPrimitive(edges.get(edgeId).getId()));
      fileManager.addContent("connections", edge);
    }
    return fileManager.getJson();
  }


  @Override
  public boolean equals(Object o) {
    if (o instanceof DropZone b) {
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