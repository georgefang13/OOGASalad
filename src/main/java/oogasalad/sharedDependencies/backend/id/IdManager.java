package oogasalad.sharedDependencies.backend.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * Manages the ids of all Objects of type T (such as an Ownable). For example, an IdManager<Ownable>
 * would manage the ids of all Ownables and an IdManager<Rule> would manage the ids of all Rules.
 * Supports adding SubIds to ids (such as a Player owning a Variable. In this case, the id would be
 * OwnableId.VariableId).
 *
 * @author Michael Bryant
 */
public class IdManager<T extends IdManageable> implements Iterable<Map.Entry<String, T>> {

  /**
   * Map of ids to Objects. Includes subIds, but not with the full id. Ex. If the id is
   * "Player1.Variable1", then the key is the object and the value is the Variable1. Since the key
   * is a SubObject, it is included in the subIds map.
   */
  private final Map<String, T> simpleIds = new HashMap<>();

  /**
   * Map of SubObjects to Objects (owners). Example, the key is the Variable1 and the value is the
   * Player1 if the id is "Player1.Variable1". Enables tree traversal (up the tree) to find the
   * owner of a SubObject. If the key points to null, then the key is the root of the tree.
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
   *
   * @param obj the object to test
   * @return true if the object is already stored in the id manager, false otherwise
   */
  public boolean isObjectInUse(Object obj) {
    return simpleIds.containsValue(obj);
  }

  /**
   * Returns the T with the given id. Accepts simple ids (ex. "Player1") and full ids (ex.
   * "Player1.Variable1").
   *
   * @param id the non-null id of the ownable to return
   * @return the T with the given id
   * @throws IllegalArgumentException if the id is not in the set of ids
   */
  public T getObject(String id) throws IllegalArgumentException {
    //searchId is everything after the last "." in the id, to handle full ids
    String searchId = id.substring(id.lastIndexOf(".") + 1);
    if (!simpleIds.containsKey(searchId)) {
      throw new IllegalArgumentException("Id \"" + id + "\" not found."); //TODO resource bundle
    }
    return simpleIds.get(searchId);
  }

  /**
   * Finds the simple id of the given T. Ex. If the T is a Variable, then the returned id could be
   * "Variable1". Throws exception if the T is not in the set of ids.
   *
   * @param obj the T to get the id of
   * @return the id of the given T
   * @throws IllegalArgumentException if the T is not in the set of ids
   */
  public String getSimpleId(T obj) throws IllegalArgumentException {
    for (Map.Entry<String, T> entry : simpleIds.entrySet()) {
      if (entry.getValue().equals(obj)) {
        return entry.getKey();
      }
    }
    throw new IllegalArgumentException("Id for " + obj + " not found"); //TODO resource bundle
  }

  /**
   * Returns the (full) id of the given T. Ex. If the T is a Variable, then the returned id could be
   * "Player1.Variable1". Throws exception if the T is not in the set of ids.
   *
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
      throw new IllegalArgumentException(
          "Object \"" + obj + "\" not found in IdManager"); //TODO resource bundle
    }
    return fullId;
  }

  /**
   * Generates a default simple id for an Object of type T and adds it to the set of ids.
   *
   * @param obj    the T to generate an id for
   * @param parent the parent of the T to be logged in the ownership map
   * @throws IllegalArgumentException if the id is already in use
   */
  public void addObject(T obj, T parent) throws IllegalArgumentException {
    addObject(obj, obj.getDefaultId(), parent);
  }

  /**
   * Adds an object with a given id to the set of ids.
   *
   * @param obj    the T to add
   * @param parent the parent of the T to be logged in the ownership map
   * @param id     the id to add the T to
   * @throws IllegalArgumentException if the id or object is already in use
   */
  public void addObject(T obj, String id, T parent) throws IllegalArgumentException {
    if (id == null) {
      id = obj.getDefaultId();
    }
    logIdName(obj, parent, id);
  }

  /**
   * Helper method for addObject.
   *
   * @param obj       the T to add
   * @param parent    the parent of the T to be logged in the ownership map
   * @param defaultId the default id to add the T to. Non-null.
   */
  private void logIdName(T obj, T parent, String defaultId) {
    if (obj == null) {
      throw new IllegalArgumentException("Object cannot be null"); //TODO resource bundle
    }
    //if defaultId contains a ".", then it is not allowed
    if (defaultId.contains(".")) {
      throw new IllegalArgumentException(
          "Id \"" + defaultId + "\" cannot contain a '.'"); //TODO resource bundle
    }
    //check if obj is already in the id manager
    if (simpleIds.containsValue(obj)) {
      throw new IllegalArgumentException(
          "Object \"" + obj + "\" already in IdManager"); //TODO resource bundle
    }
    if (!idGenerators.containsKey(defaultId)) {
      idGenerators.put(defaultId, new NumberGenerator());
    }
    String itemNum = simpleIds.containsKey(defaultId) ? idGenerators.get(defaultId).next() : "";
    String fullId;
    do {
      try {
        fullId = defaultId + itemNum;
        addId(fullId, obj, parent);
        break;
      } catch (Exception e) {
        // Handle exception, e.g. generate a new itemNum and try again
        itemNum = idGenerators.get(defaultId).next();
      }
    } while (true);
  }

  /**
   * Generates a default simple id for an Object of type T and adds it to the set of ids. Adds as a
   * root object.
   *
   * @param obj the T to generate an id for
   * @throws IllegalArgumentException if the id is already in use
   */
  public void addObject(T obj) throws IllegalArgumentException {
    logIdName(obj, null, obj.getDefaultId());
  }

  /**
   * Adds an id to the set of ids along with the given T.
   *
   * @param obj the T to add
   * @param id  the id to add
   * @throws IllegalArgumentException if the id is already in use
   */
  public void addObject(T obj, String id) throws IllegalArgumentException {
    throwExceptionIfAlreadyInUse(id);
    logIdName(obj, null, id);
  }

  /**
   * Adds an id to the set of ids along with the given T and parent.
   *
   * @param id     the id to add
   * @param obj    the T to add
   * @param parent the parent of the T to be logged in the ownership map
   * @throws IllegalArgumentException if the id is already in use
   */
  private void addId(String id, T obj, T parent) throws IllegalArgumentException {
    if (simpleIds.containsKey(id)) {
      throw new IllegalArgumentException("Id \"" + id + "\" already in use"); //TODO resource bundle
    }
    simpleIds.put(id, obj);
    ownershipMap.put(obj, parent);
    //If T is Ownable, remap the owner of the Object to the Owner of the parent
    if (obj instanceof Ownable) {
      Ownable ownable = (Ownable) obj;
      if (parent != null) {
        ownable.setOwner(((Ownable) parent).getOwner());
      }
    }

  }

  /**
   * Adds an Object to the set of ids along with the given T and parent id.
   *
   * @param obj      the T to add
   * @param id       the id to add
   * @param parentId the id of the parent of the T to be logged in the ownership map
   * @throws IllegalArgumentException if the id is already in use
   */
  public void addObject(T obj, String id, String parentId) throws IllegalArgumentException {
    throwExceptionIfAlreadyInUse(id);
    T parent = null;
    if (parentId != null) {
      parent = simpleIds.get(parentId);
    }
    logIdName(obj, parent, id);
  }

  /**
   * Changes an id to a new id.
   *
   * @param oldId the old id
   * @param newId the new id
   * @throws IllegalArgumentException if the new id is already in use or the old id is not in use
   */
  public void changeId(String oldId, String newId) throws IllegalArgumentException {
    //if new id is in use throw exception
    if (isIdInUse(newId)) {
      throw new IllegalArgumentException(
          "Id \"" + newId + "\" already in use"); //TODO resource bundle
    }
    //if old id is not in use throw exception
    if (!isIdInUse(oldId)) {
      throw new IllegalArgumentException(
          "Given id \"" + oldId + "\" not found."); //TODO put in resource bundle
    }
    T obj = simpleIds.get(oldId);
    simpleIds.remove(oldId);
    simpleIds.put(newId, obj);
  }


  /**
   * Recursively removes the given T and all of its sub-objects from the IdManager //TODO include
   * frontend warning about this behavior (including Ids but not NumberGenerators).
   *
   * @param obj the T to remove
   * @throws IllegalArgumentException if the T is not in the set of ids
   */
  public void removeObject(T obj) throws IllegalArgumentException {
    // Check if the object is present in the simpleIds map
    if (!simpleIds.containsValue(obj)) {
      throw new IllegalArgumentException("Object \"" + obj + "\" not found in IdManager");
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
   * Helper method to check if an id is already in use. Checks iff the callee came from inside the
   * class, if so, throws an exception as this means we don't care if we add a number to the end of
   * the id.
   *
   * @param id the id to check
   * @throws IllegalArgumentException if the id is already in use
   */
  private void throwExceptionIfAlreadyInUse(String id) throws IllegalArgumentException {
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace(); //TODO refactor
    if (stackTraceElements[3].getClassName().equals(this.getClass().getName())) {
      if (isIdInUse(id)) {
        throw new IllegalArgumentException(
            "Id \"" + id + "\" already in use"); //TODO resource bundle
      }
    }
  }

  /**
   * Recursively removes the T with the given id and all of its sub-objects from the IdManager
   *
   * @param id the id of the T to remove
   * @throws IllegalArgumentException if the T is not in the set of ids
   */
  public void removeObject(String id) throws IllegalArgumentException {
    removeObject(getObject(id));
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
   * Returns an unmodifiable set of all ids. Useful for displaying all ids in a list.
   */
  public Map getSimpleIds() {
    return Collections.unmodifiableMap(simpleIds);
  }

  /**
   * Get all objects owned by the given object
   * @param owner the object to get the owned objects of
   * @return a list of all objects owned by the given object
   */
  public List<T> getObjectsOwnedBy(T owner) {
    List<T> ownedObjects = new ArrayList<>();
    for (Map.Entry<T, T> entry : ownershipMap.entrySet()) {
      if (entry.getValue() == owner) {
        ownedObjects.add(entry.getKey());
      }
    }
    return ownedObjects;
  }

  /**
   * Set one object to be owned by another object
   * @param obj the object to set the owner of
   * @param owner the object to set as the owner
   */
  public void setObjectOwner(T obj, T owner) {
    if (ownershipMap.containsKey(obj)) {
      ownershipMap.put(obj, owner);
      //If T is Ownable, remap the owner of the Object to the Owner of the parent
      if (obj instanceof Ownable) {
        Ownable ownable = (Ownable) obj;
        if (owner != null) {
          ownable.setOwner(((Ownable) owner).getOwner());
        }
      }

      for (T ownable : getObjectsOwnedBy(obj)) {
        setObjectOwner(ownable, obj);
      }

    }
  }

  /**
   * Sets the player owner of the given object to the given owner.
   * @param obj the object to set the owner of
   * @param owner the owner to set the object to
   */
  public void setPlayerOwner(Ownable obj, Owner owner){
    obj.setOwner(owner);
    for (T ownable : getObjectsOwnedBy((T) obj)) {
      setPlayerOwner((Ownable) ownable, owner);
    }
  }

  /**
   * Clears everything in the IdManager.
   */
  public void clear() {
    simpleIds.clear();
    idGenerators.clear();
    ownershipMap.clear();
  }


  /**
   * Returns the iterator for the simpleIds map.
   *
   * @return an iterator of type Entry<String, T>
   */
  @Override
  public Iterator<Entry<String, T>> iterator() {
    return simpleIds.entrySet().iterator();
  }

  /**
   * Returns a List of all ids of objects of the given classes in the IdManager.
   * Returns only classes that use all the given classes.
   *
   * @param classNames the classes to check for
   * @return a List of all ids of objects of the given class in the IdManager
   */
  public List<String> getIdsOfObjectsOfClass(String... classNames) {
    List<String> ids = new ArrayList<>();
    for (Map.Entry<String, T> entry : simpleIds.entrySet()) {
      boolean allClassesMatch = true;
      for (String className : classNames) {
        if (!entry.getValue().usesClass(className)) {
          allClassesMatch = false;
          break;
        }
      }
      if (allClassesMatch) {
        ids.add(getId(getObject(entry.getKey())));
      }
    }
    return ids;
  }

  /**
   * Returns a Steam of all objects in the IdManager.
   * @return a Stream of all objects in the IdManager
   */
  public Stream<T> objectStream() {
    return simpleIds.values().stream();
  }

  /**
   * Changes the ownership hierarchy of an object within the idManager.
   * @param id the id of the object to change. Can be the simple id or the full id.
   * @param newParentId the id of the new parent of the object (within the idManager)
   */
  public void changeParentId(String id, String newParentId) {
    T obj = getObject(id);
    T newParent = getObject(newParentId);
    setObjectOwner(obj, newParent);
  }

  /**
   * Removes all objects owned by the given owner.
   * @param owner the owner to remove all objects of
   */
  public void removeObjectsOwnedByOwner(Owner owner) {
    //if T is Ownable, remove all objects with owner as owner avoiding ConcurrentModificationException
    List<T> ownedObjects = new ArrayList<>();
    for (Map.Entry<T, T> entry : ownershipMap.entrySet()) {
      Ownable ownable = (Ownable) entry.getKey();
      if (ownable.getOwner() == owner) {
        ownedObjects.add(entry.getKey());
      }
    }
    for (T ownedObject : ownedObjects) {
      removeObject(ownedObject);
    }
  }

}
