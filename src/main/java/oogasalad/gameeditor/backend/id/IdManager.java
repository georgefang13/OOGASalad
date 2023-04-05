package oogasalad.gameeditor.backend.id;

import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the ids of all Objects of type T (such as an Ownable).
 * For example, an IdManager<Ownable> would manage the ids of all Ownables
 * and an IdManager<Rule> would manage the ids of all Rules.
 * Supports adding SubIds to ids (such as a Player owning a Variable. In this case, the id would be OwnableId.VariableId).
 * @author Michael Bryant
 */
public class  IdManager<T extends IdManageable> {

  /**
   * Map of ids to Objects. Includes subIds, but not with the full id.
   * Ex. If the id is "Player1.Variable1", then the key is the object and the value is the Variable1.
   * Since the key is a SubObject, it is included in the subIds map.
   */
  private final Map<String, T> simpleIds = new HashMap<>();

  /**
   * Map of SubObjects to Objects (owners). Example, the key is the Variable1 and the value is the Player1 if the id is "Player1.Variable1".
   * Enables tree traversal (up the tree) to find the owner of a SubObject.
   * If the key points to null, then the key is the root of the tree.
   * Dependency loops are not allowed per the below methods.
   */
  private final Map<T, T> ownershipMap = new HashMap<>();

  /**
   * Map of default ids to number generators for naming ids
   */
  private final HashMap<String, NumberGenerator> idGenerators = new HashMap<>();


  /**
   * @param id the id to check
   * @return true if the simple id is already in use, false otherwise
   */
  public boolean isIdInUse(String id) {
    return simpleIds.containsKey(id);
  }

  /**
   * Tests if a given obejct is already stored in the id manager.
   * @param obj the object to test
   * @return true if the object is already stored in the id manager, false otherwise
   */
  public boolean isObjectInUse(Object obj) {
    return simpleIds.containsValue(obj);
  }

  /**
   * Returns the T with the given id.
   * Accepts simple ids (ex. "Player1") and full ids (ex. "Player1.Variable1").
   * @param id the id of the ownable to return
   * @return the T with the given id
   * @throws IllegalArgumentException if the id is not in the set of ids
   */
  public T getObject(String id) throws IllegalArgumentException {
    //searchId is everything after the last "." in the id, to handle full ids
    String searchId = id.substring(id.lastIndexOf(".") + 1);
    if(!simpleIds.containsKey(searchId)) {
      throw new IllegalArgumentException("Id " + searchId + " not found"); //TODO resource bundle
    }
    return simpleIds.get(searchId);
  }

  /**
   * Finds the simple id of the given T.
   * Ex. If the T is a Variable, then the returned id could be "Variable1".
   * Throws exception if the T is not in the set of ids.
   * @param obj the T to get the id of
   * @return the id of the given T
   * @throws IllegalArgumentException if the T is not in the set of ids
   */
  private String getSimpleId(T obj) throws IllegalArgumentException{
    for (Map.Entry<String, T> entry : simpleIds.entrySet()) {
      if (entry.getValue().equals(obj)) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("Id " + simpleIds.get(obj) + " not found"); //TODO resource bundle
  }

  /**
   * Returns the (full) id of the given T.
   * Ex. If the T is a Variable, then the returned id could be "Player1.Variable1".
   * Throws exception if the T is not in the set of ids.
   * @param obj the T to get the id of
   * @return the id of the given T
   * @throws IllegalArgumentException if the T is not in the set of ids
   */
  public String getId(T obj) throws IllegalArgumentException {
    String fullId = null;
    StringBuilder idBuilder = new StringBuilder();
    // Traverse up the ownership hierarchy to find the root object and build the id
    T currentObj = obj;
    while (ownershipMap.containsKey(currentObj)) {
      T parentObj = ownershipMap.get(currentObj);
      String subId = getSimpleId(currentObj);
      //add subId and "." to the beginning of the id if the current is not null
      idBuilder.insert(0, ".").insert(0, subId);
      if (parentObj == null) { // Found the root object
        fullId = idBuilder.substring(0, idBuilder.length() - 1); // Remove the last "." from the id
        break;
      }
      currentObj = parentObj;
    }
    // If the root object was not found, the object is not in the IdManager
    if (fullId == null) {
      throw new IllegalArgumentException("Object not found in IdManager: " + obj); //TODO resource bundle
    }
    return fullId;
  }


  /**
   * Generates a default simple id for an Object of type T and adds it to the set of ids.
   * @param obj the T to generate an id for
   * @param parent the parent of the T to be logged in the ownership map
   * @throws IllegalArgumentException if the id is already in use
   */
  public void addObject(T obj, T parent) throws IllegalArgumentException{

    if (obj == null) {
      throw new IllegalArgumentException("Object cannot be null"); //TODO resource bundle
    }

    //avoid adding the same object twice
    if (simpleIds.containsValue(obj)) {
      throw new IllegalArgumentException("Object with same id already exists in IdManager: " + getId(obj)); //TODO resource bundle
    }
    String defaultId = obj.getDefaultId();
    if (!idGenerators.containsKey(defaultId)) {
      idGenerators.put(defaultId, new NumberGenerator());
    }
    String itemNum = idGenerators.get(defaultId).next();
    String id;
    do {
      try {
        id = defaultId + itemNum;
        addId(id, obj, parent);
        break;
      } catch (Exception e) {
        // Handle exception, e.g. generate a new itemNum and try again
        itemNum = idGenerators.get(defaultId).next();
      }
    } while (true);
  }

  /**
   * Generates a default simple id for an Object of type T and adds it to the set of ids.
   * Adds as a root object.
   * @param obj the T to generate an id for
   * @throws IllegalArgumentException if the id is already in use
   */
  public void addObject(T obj) throws IllegalArgumentException{
    addObject(obj, null);
  }

  /**
   * Adds an id to the set of ids along with the given T and parent.
   * @param id the id to add
   * @param obj the T to add
   * @param parent the parent of the T to be logged in the ownership map
   * @throws IllegalArgumentException if the id is already in use
   */
  private void addId(String id, T obj, T parent) throws IllegalArgumentException{
    //if id is in use throw exception
    if (isIdInUse(id)) {
      throw new IllegalArgumentException("Id already in use"); //TODO put in resource bundle
    }
    simpleIds.put(id, obj);
    ownershipMap.put(obj, parent);
  }

  /**
   * Changes an id to a new id.
   * @param oldId the old id
   * @param newId the new id
   * @throws IllegalArgumentException if the new id is already in use or the old id is not in use
   */
  public void changeId(String oldId, String newId) throws IllegalArgumentException{
    //if new id is in use throw exception
    if (isIdInUse(newId)) {
      throw new IllegalArgumentException("Id already in use"); //TODO put in resource bundle
    }
    //if old id is not in use throw exception
    if (!isIdInUse(oldId)) {
      throw new IllegalArgumentException("Given Id not found: " + oldId); //TODO put in resource bundle
    }
    T obj = simpleIds.get(oldId);
    simpleIds.remove(oldId);
    simpleIds.put(newId, obj);
  }


  /**
   * Recursively removes the given T and all of its sub-objects from the IdManager //TODO include frontend warning about this behavior
   * (including Ids but not NumberGenerators).
   * @param obj the T to remove
   * @throws IllegalArgumentException if the T is not in the set of ids
   */
  public void removeObject(T obj) throws IllegalArgumentException {
    // Check if the object is present in the simpleIds map
    if (!simpleIds.containsValue(obj)) {
      throw new IllegalArgumentException("Object not found in IdManager: " + obj);
    }

    // Get the simpleId of the object
    String simpleId = getSimpleId(obj);

    // Remove the object from the simpleIds map
    simpleIds.remove(simpleId);

    // Check if the object is present in the ownershipMap map
    if (ownershipMap.containsKey(obj)) {
      // Remove the object from the ownershipMap map
      ownershipMap.remove(obj);

      // Recursively delete any child objects of the removed object
      deleteChildren(obj);
    }
  }

  /**
   * Helper method to recursively delete child objects of a removed object.
   */
  private void deleteChildren(T obj) {
    // Get a list of child objects
    Map<T, T> children = new HashMap<>();
    for (Map.Entry<T, T> entry : ownershipMap.entrySet()) {
      if (entry.getValue() == obj) {
        children.put(entry.getKey(), entry.getValue());
      }
    }

    // Recursively delete each child object
    for (Map.Entry<T, T> entry : children.entrySet()) {
      T child = entry.getKey();
      ownershipMap.remove(child);
      simpleIds.remove(getSimpleId(child));
      deleteChildren(child);
    }
  }

  /**
   * Returns an unmodifiable set of all ids.
   * Useful for displaying all ids in a list.
   */
  public Map getSimpleIds() {
    return Collections.unmodifiableMap(simpleIds);
  }


  /**
   * Clears everything in the IdManager.
   */
  public void clear() {
    simpleIds.clear();
    idGenerators.clear();
    ownershipMap.clear();
  }

  //TODO return multiple maps based on ownership (recursive)

  //TODO manipulate graph

  //TODO check for loops in graph

  //TODO get an unmodifiable list of all things owned by a given object
}
