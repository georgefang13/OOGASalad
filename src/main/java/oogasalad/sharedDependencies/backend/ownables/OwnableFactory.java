package oogasalad.sharedDependencies.backend.ownables;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.EmptyGameObject;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

/**
 * A factory class for ownables.
 * This is used to choose which type of ownable to instantiate in createOwnable method.
 * @author Max Meister
 */
public class OwnableFactory {

  /**
   * @param ownableType string that represents which ownable to use
   * @param idManager idmanager used for param of ownable
   * @return
   */
  public Ownable OwnableFactory(String ownableType, IdManager idManager) {
    if (ownableType.equals("GameObject")) {
      return new EmptyGameObject(idManager);
    } else if (ownableType.equals("Variable")) {
      return new Variable(idManager);
    } else {
      throw new IllegalArgumentException("Invalid ownable type: " + ownableType);
    }
  }
}
