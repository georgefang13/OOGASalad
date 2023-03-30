package oogasalad.backend.ownables.id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.backend.ownables.Ownable;
import oogasalad.backend.owners.Owner;

/**
 * Manages the ids of all ownables.
 * @author Michael Bryant
 */
public class IdManager {

  /**
   * Map of ids to ownables.
   */
  private HashMap<String, Ownable> ids = new HashMap<>();

  /**
   * Map of default ids to number generators for naming ids
   */
  private HashMap<String, NumberGenerator> idGenerators = new HashMap<>();


  /**
   * @param id the id to check
   * @return true if the id is already in use, false otherwise
   */
  public boolean isIdInUse(String id) {
    return ids.containsKey(id);
  }

  /**
   * Returns the Ownable with the given id.
   * @param id the id of the ownable to return
   * @return the ownable with the given id
   */
  public Ownable getOwnable(String id) {
    return ids.get(id);
  }

  /**
   * Returns the owner of the ownable with the given id.
   * @param id the id of the ownable to return the owner of
   * @return the owner of the ownable with the given id
   */
  public Owner getOwner(String id) {
    return ids.get(id).getOwner();
  }

  /**
   * Generates an id for an ownable and adds it to the set of ids.
   * @param ownable the ownable to generate an id for
   * @return the generated id
   */
  public String addOwnable(Ownable ownable) {
    String defaultId = ownable.getDefaultId();
    if (!idGenerators.containsKey(defaultId)) {
      idGenerators.put(defaultId, new NumberGenerator());
    }
    String itemNum = idGenerators.get(defaultId).next();
    String id;
    do {
      try {
        id = defaultId + itemNum;
        addId(id, ownable);
        ownable.setId(id);
        return id;
      } catch (Exception e) {
        // Handle exception, e.g. generate a new itemNum and try again
        itemNum = idGenerators.get(defaultId).next();
      }
    } while (true);
  }

  /**
   * Adds an id to the set of ids.
   * Used internally
   * @see #addOwnable(Ownable)
   * @param id the id to add
   */
  private void addId(String id, Ownable ownable) throws IllegalArgumentException{
    //if id is in use throw exception
    if (isIdInUse(id)) {
      throw new IllegalArgumentException("Id already in use"); //TODO put in resource bundle
    }
    ids.put(id, ownable);
  }

  /**
   * Changes an id to a new id.
   * @param oldId the old id
   * @param newId the new id
   */
  public void changeId(String oldId, String newId) throws IllegalArgumentException{
    //if new id is in use throw exception
    if (isIdInUse(newId)) {
      throw new IllegalArgumentException("Id already in use"); //TODO put in resource bundle
    }
    //if old id is not in use throw exception
    if (!isIdInUse(oldId)) {
      throw new IllegalArgumentException("Id not in use"); //TODO put in resource bundle
    }
    Ownable ownable = ids.get(oldId);
    ids.remove(oldId);
    ids.put(newId, ownable);
  }

  /**
   * Removes an id and Ownable from the set of ids.
   * @param id the id to remove
   */
  public void removeOwnable(String id) throws IllegalArgumentException{
    //if id is not in use throw exception
    if (!isIdInUse(id)) {
      throw new IllegalArgumentException("Id not in use");
    }
    ids.remove(id);
  }

  /**
   * Returns an uneditable set of all ids.
   */
  public Map getIds() {
    return Collections.unmodifiableMap(ids);
  }

  /**
   * Clears all ids.
   */
  public void clear() {
    ids.clear();
    idGenerators.clear();
  }

  /**
   * Loads a set of ids by adding them to the set of ids.
   * @param ids the set of ids to load
   */
  public void loadIds(Map<String, Ownable> ids) {
    //add all ids using addId method for exception handling
    for (String id : ids.keySet()) {
      addId(id, ids.get(id));
    }
  }

  /**
   * Clears all ids and loads a set of ids.
   * @param ids the set of ids to load
   */
  public void clearAndLoadIds(Map<String, Ownable> ids) {
    clear();
    loadIds(ids);
  }

  /**
   * Returns a list of all ownables owned by the given owner.
   * @param owner the owner to get ownables for
   * @return a list of all ownables owned by the given owner
   */
  public List getOwnablesForOwner(Owner owner) {
    List<Ownable> ownables = new ArrayList<>();
    for (Ownable ownable : ids.values()) {
      if (ownable.getOwner() == owner) {
        ownables.add(ownable);
      }
    }
    return ownables;
  }

}
