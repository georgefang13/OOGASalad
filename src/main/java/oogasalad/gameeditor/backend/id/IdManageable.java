package oogasalad.gameeditor.backend.id;

import java.util.HashSet;

public abstract class IdManageable {

  /**
   * The classes that this Ownable can be.
   * Note: these are not Java classes, but rather the names of the classes in the game.
   * For example, if the game has a class called "Rook", then this set would contain "Rook".
   */
  private HashSet<String> classes = new HashSet<>();

  /**
   * @return the id of the object
   */
  public String getDefaultId() {
    return this.getClass().getSimpleName();
  }

  /**
   * Adds the given class to this Ownable.
   * @param className the name of the class to add
   */
  public void addClass(String className) {
    classes.add(className);
  }

  /**
   * Removes the given class from this Ownable, if it exists.
   * @param className the name of the class to remove
   */
  public void removeClass(String className) {
    if(classes.contains(className)) {
      classes.remove(className);
    }
  }

  /**
   * Clears all classes from this Ownable.
   */
  public void clearClasses() {
    classes.clear();
  }


  /**
   * Checks if this Ownable is of the given class.
   * @param className the name of the class to check
   * @return true if this Ownable is of the given class, false otherwise
   */
  public boolean usesClass(String className) {
    return classes.contains(className);
  }

  //TODO decide if we want methods like these to access the IdManager from the class
  //also need to ensure non-recursive behavior by changing method names here or in IdManager
//  /**
//   * Gets the id of the this.
//   * @return the id of this
//   */
//  public String getId() {
//    return idManager.getId(this);
//  }
//
//  /**
//   * Changes the id of this.
//   * @param newId the new id of this
//   */
//  public void changeId(String newId) {
//    idManager.changeId(this.getId(), newId);
//  }
//
//  /**
//   * @return the IdManager used by this
//   */
//  protected IdManager getIdManager() {
//    return idManager;
//  }

}
