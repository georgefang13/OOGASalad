package oogasalad.sharedDependencies.backend;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.goals.Goal;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.EmptyGameObject;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;

/**
 * A factory class for ownables.
 * This is used to choose which type of ownable to instantiate in createOwnable method.
 * @author Max Meister
 */
public class ObjectFactory {

  private GameWorld gameWorld;
  private ArrayList<Player> players;

  private IdManager<Ownable> ownableIdManager;

  /**
   * The constructor for the ObjectFactory.
   * @param gameWorld the GameWorld of the game
   * @param ownableIdManager the IdManager for the Ownables
   * @param players the Players of the game
   */
  public ObjectFactory(GameWorld gameWorld, IdManager<Ownable> ownableIdManager, ArrayList<Player> players) {
    this.gameWorld = gameWorld;
    this.ownableIdManager = ownableIdManager;
    this.players = players;
  }

  /**
   * The null types that will result in a default Object for the given type.
   */
  private final String[] nullTypes = {"", "null", "NULL", "Null", "none", "NONE", "None"};

  private final String playerIdentifier = "Player"; //Independent of language, should not be changed

  /**
   * Checks if the given type is a null type.
   * @param type the type to check
   * @return true if the type is a null type, false otherwise
   */
  private boolean isNullType(String type) {
    for (String nullType : nullTypes) {
      if (type.equals(nullType)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Access a parameter from the map, returning null if the parameter is not present or is a null type.
   * @param params the map of parameters
   * @param param the parameter to get
   * @return
   */
  private String getWithNull(Map<ObjectParameter, String> params, ObjectParameter param) {
    String result = params.get(param);
    if (result == null || isNullType(result)) {
      return null;
    } else {
      return result;
    }
  }

  public Ownable createOwnable(String ownableType, Owner owner) {
    try {
      String basePackage = "oogasalad.sharedDependencies.backend.ownables."; //TODO make less ugly
      if (ownableType.contains("Variable")) {
        basePackage += "variables.";
      } else {
        basePackage += "gameobjects.";
      }
      Class<?> clazz = Class.forName(basePackage + ownableType);
      System.out.println(clazz.getName());
      if (Ownable.class.isAssignableFrom(clazz)) {
        Constructor<?> constructor = clazz.getConstructor(Owner.class);
        return (Ownable) constructor.newInstance(owner);
      } else {
        throw new IllegalArgumentException(
            "Class " + ownableType + " is not a subclass of Ownable"); //TODO add to properties file
      }
    } catch (Exception e) {
      throw new RuntimeException("Error instantiating " + ownableType, e); //TODO add to properties file
    }
  } //TODO


  /**
   * Creates an ownable from the given parameters and adds it to the IdManager. If OWNABLE_TYPE is not specified, adds a GameObject.
   * @param params
   * @return
   */
  public void createOwnable(Map<ObjectParameter, String> params) {
    String ownableType = getWithNull(params, ObjectParameter.OWNABLE_TYPE);
    String parentOwnerName = getWithNull(params, ObjectParameter.OWNER);
    String id = getWithNull(params, ObjectParameter.ID);
    String parentOwnableName = getWithNull(params, ObjectParameter.PARENT_OWNABLE);
    Ownable parentOwnable;
    try{
      parentOwnable = ownableIdManager.getObject(parentOwnableName);
    }
    catch (Exception e){
      parentOwnable = null;
    }
    //if null or does not contain playerIdentifier, then it is the gameWorld
    Owner Owner;
    if (parentOwnerName == null || !parentOwnerName.contains(playerIdentifier)) {
      Owner = gameWorld;
    } else {
      //get player from player list
      try {
        Owner = players.get(Integer.parseInt(parentOwnerName.substring(playerIdentifier.length())) - 1); //Because player numbers start at 1
      } catch (NumberFormatException e) {
        Owner = gameWorld;
      }
    }
    Ownable newOwnable = createOwnable(ownableType, Owner);
    ownableIdManager.addObject(newOwnable, id, parentOwnable);
  }

  public static Rule createRule(Map<ObjectParameter, String> params) {
    return null; //TODO move to shared dependencies
  }

  public static Goal createGoal(Map<ObjectParameter, String> params) {
    return null; //TODO move to shared dependencies
  }


}
