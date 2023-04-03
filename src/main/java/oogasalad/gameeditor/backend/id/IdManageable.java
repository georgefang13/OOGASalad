package oogasalad.gameeditor.backend.id;

public abstract class IdManageable {

  private final IdManager idManager;

  /**
   * Constructs an IdManageable using the given IdManager.
   * @param idManager the IdManager to use
   * #NOTE: dependency injection
   */
  public IdManageable(IdManager idManager) {
    this.idManager = idManager;
  }

  /**
   * @return the id of the object
   */
  public String getDefaultId() {
    return this.getClass().getSimpleName();
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
