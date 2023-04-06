package oogasalad.sharedDependencies.backend.ownables;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.EmptyGameObject;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * A factory class for ownables.
 * This is used to choose which type of ownable to instantiate in createOwnable method.
 * @author Max Meister
 */
public class OwnableFactory {

  /**
   * @param ownableType string that represents which ownable to use
   * @param idManager   idmanager used for param of ownable
   * @param owner the owner for the ownable
   * @return Ownable
   */
  public static Ownable createOwnable(String ownableType, IdManager idManager, Owner owner) {
    if (ownableType.equals("GameObject")) {
      return new EmptyGameObject(idManager, owner);
    }

    try {
      Class<?> clazz = Class.forName(ownableType);
      if (Ownable.class.isAssignableFrom(clazz)) {
        Constructor<?> constructor = clazz.getConstructor(IdManager.class, Owner.class);
        return (Ownable) constructor.newInstance(idManager, owner);
      } else {
        throw new IllegalArgumentException(
            "Class " + ownableType + " is not a subclass of Ownable"); //TODO add to properties file
      }
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("Invalid ownable type: " + ownableType, e);
    } catch (NoSuchMethodException | SecurityException | InstantiationException |
             IllegalAccessException | IllegalArgumentException |
             InvocationTargetException e) {
      throw new RuntimeException("Error instantiating " + ownableType, e);
    }
  }
}
