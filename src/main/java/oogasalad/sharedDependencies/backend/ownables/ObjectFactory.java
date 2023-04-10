package oogasalad.sharedDependencies.backend.ownables;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import oogasalad.gameeditor.backend.goals.Goal;
import oogasalad.gameeditor.backend.ownables.gameobjects.EmptyGameObject;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * A factory class for ownables.
 * This is used to choose which type of ownable to instantiate in createOwnable method.
 * @author Max Meister
 */
public class ObjectFactory {

  private GameWorld gameWorld;

  public ObjectFactory(GameWorld gameWorld) {
    this.gameWorld = gameWorld;
  }

  /**
   * The null types that will result in a default Object for the given type.
   */
  private String[] nullTypes = {"", "null", "NULL", "Null", "none", "NONE", "None"};

  public Ownable createOwnable(String ownableType, Owner owner) {
    try {
      //get class from string (potentially in different package)
      Class<?> clazz = Class.forName("oogasalad.sharedDependencies.backend.ownables.gameobjects.piece." + ownableType);
      //print clazz name
      System.out.println("class: " + clazz.getName());

      if (Ownable.class.isAssignableFrom(clazz)) {
        Constructor<?> constructor = clazz.getConstructor(Owner.class);
        return (Ownable) constructor.newInstance(owner);
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
  } //TODO


  public Ownable createOwnable(Map<String, String> params) {
    if(params == null) {
      return new EmptyGameObject(gameWorld);
    }
    String ownableType = params.get("OWNABLE_TYPE");
    //TODO, see google doc
    return null;
  }

  public static Rule createRule(Map<String, String> params) {
    return null;
  }

  public static Goal createGoal(Map<String, String> params) {
    return null; //TODO
  }


}
