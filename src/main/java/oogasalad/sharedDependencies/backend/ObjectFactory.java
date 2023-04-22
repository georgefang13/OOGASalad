package oogasalad.sharedDependencies.backend;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.gameeditor.backend.rules.Rule;
import oogasalad.gamerunner.backend.interpretables.Goal;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;

/**
 * A factory class for ownables. This is used to choose which type of ownable to instantiate in
 * createOwnable method.
 *
 * @author Max Meister
 */
public class ObjectFactory {

  private GameWorld gameWorld;
  private ArrayList<Player> players;

  private IdManager<Ownable> ownableIdManager;

  /**
   * The constructor for the ObjectFactory.
   *
   * @param gameWorld        the GameWorld of the game
   * @param ownableIdManager the IdManager for the Ownables
   * @param players          the Players of the game
   */
  public ObjectFactory(GameWorld gameWorld, IdManager<Ownable> ownableIdManager,
      ArrayList<Player> players) {
    this.gameWorld = gameWorld;
    this.ownableIdManager = ownableIdManager;
    this.players = players;
  }

  /**
   * The null types that will result in a default Object for the given type.
   */
  private final String[] nullTypes = {"", "null", "NULL", "Null", "none", "NONE", "None"};

  private final String playerIdentifier = "Player"; //Independent of language, should not be changed

//  /**
//   * Checks if the given type is a null type.
//   *
//   * @param type the type to check
//   * @return true if the type is a null type, false otherwise
//   */
//  private boolean isNullType(String type) {
//    for (String nullType : nullTypes) {
//      if (type.equals(nullType)) {
//        return true;
//      }
//    }
//    return false;
//  }
//
//  /**
//   * Access a parameter from the map, returning null if the parameter is not present or is a null
//   * type.
//   *
//   * @param params the map of parameters
//   * @param param  the parameter to get
//   * @return
//   */
//  private String getWithNull(Map<ObjectParameter, String> params, ObjectParameter param) {
//    String result = params.get(param);
//    if (result == null || isNullType(result)) {
//      return null;
//    } else {
//      return result;
//    }
//  }

  private void handleBoardCreator(Map<ObjectParameter, Object> params) {
    Owner owner = gameWorld;
    String type = params.get(ObjectParameter.BOARD_TYPE).toString();
    List<DropZone> dropZones = new ArrayList<>();
    String boardRows = params.get(ObjectParameter.BOARD_ROWS).toString();
    String boardCols = params.get(ObjectParameter.BOARD_COLS).toString();
    String boardLength = params.get(ObjectParameter.BOARD_LENGTH).toString();
    String boardForward = params.get(ObjectParameter.BOARD_FORWARD).toString();
    String boardBackward = params.get(ObjectParameter.BOARD_BACKWARD).toString();
    try {
      // Get the method from BoardCreator matching type
      Class<?> boardCreatorClass = Class.forName("oogasalad/gameeditor/backend/ownables/gameobjects/BoardCreator.java");
      Method boardCreatorMethod;
      Object[] args;

      switch (type) {
        case "createGrid":
          args = new Object[]{Integer.parseInt(boardRows), Integer.parseInt(boardCols)};
          boardCreatorMethod = BoardCreator.class.getMethod(type, int.class, int.class);
          break;
        case "createSquareLoop":
          args = new Object[]{Integer.parseInt(boardRows), Integer.parseInt(boardCols)};
          boardCreatorMethod = boardCreatorClass.getMethod(type, int.class, int.class);
          break;
        case "create1DLoop":
          if (boardForward == null || boardBackward == null) {
            args = new Object[]{Integer.parseInt(boardLength)};
            boardCreatorMethod = boardCreatorClass.getMethod(type, int.class);
          } else {
            args = new Object[]{Integer.parseInt(boardLength), boardForward, boardBackward};
            boardCreatorMethod = boardCreatorClass.getMethod(type, int.class, String.class, String.class);
          }
          break;
        default:
          throw new RuntimeException("Unsupported BoardCreator type: " + type);
      }

      dropZones = (List<DropZone>) boardCreatorMethod.invoke(null, args);
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    } catch (Exception e) {
      throw new RuntimeException("Error creating game board", e);
    }

    //we have all the dropzones, now we need to add them to game manager (with owner as game world)
    for (DropZone dropZone : dropZones) {
      dropZone.setOwner(owner);
      ownableIdManager.addObject(dropZone);
      //get id of dropZone and print
      System.out.println("DropZone ID: " + dropZone.getId());
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
      throw new RuntimeException("Error instantiating " + ownableType,
          e); //TODO add to properties file
    }
  } //TODO

  /**
   * check if owner id is numeric value
   * @param str
   * @return
   */
  public Integer isNumeric(String str) {
    if (str == null) {
      return null;
    }
    try {
      return  Integer.parseInt(str);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  /**
   * Creates an ownable from the given parameters and adds it to the IdManager. If OWNABLE_TYPE is
   * not specified, adds a GameObject.
   *
   * @param params
   * @return
   */
  public void createOwnable(Map<ObjectParameter, Object> params) {
    String ownableType = params.get(ObjectParameter.OWNABLE_TYPE).toString();
    Map<ObjectParameter, Object> constructor_params = (Map<ObjectParameter, Object>) params.get(ObjectParameter.CONSTRUCTOR_ARGS);
    String ownerNum = constructor_params.get(ObjectParameter.OWNER).toString();
    Integer ownerInt = isNumeric(ownerNum);
    String parentOwnableId = constructor_params.get(ObjectParameter.PARENT_OWNABLE_ID).toString();
    String id = constructor_params.get(ObjectParameter.ID).toString();
    Owner owner;
    Ownable parentOwnable;
    //owner from constructor params
    if (ownerInt != null && players.size() >= ownerInt){
      owner = players.get(ownerInt - 1);
    } else {
      owner = gameWorld;
    }
    //parent ownable from constructor params
    if (parentOwnableId != null){
      parentOwnable = ownableIdManager.getObject(parentOwnableId);
    } else {
      parentOwnable = null;
    }
    //if ownableType is BoardCreator
    if (ownableType.equals("BoardCreator")) {
      handleBoardCreator(constructor_params);
    } else {
      Ownable newOwnable = createOwnable(ownableType, owner);
      ownableIdManager.addObject(newOwnable, id, parentOwnable);
    }


  }

  public static Rule createRule(Map<ObjectParameter, Object> params) {
    return null; //TODO move to shared dependencies
  }

  public static Goal createGoal(Map<ObjectParameter, Object> params) {
    return null; //TODO move to shared dependencies
  }


}
