package oogasalad.sharedDependencies.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
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

  private final GameWorld gameWorld;
  private final ArrayList<Player> players;

  private final IdManager<Ownable> ownableIdManager;

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

  private void handleBoardCreator(Map<Object, Object> params) {
    String type = params.get(ObjectParameter.BOARD_TYPE) != null ? params.get(ObjectParameter.BOARD_TYPE).toString() : null;
    List<DropZone> dropZones;
    String boardRows = params.get(ObjectParameter.BOARD_ROWS) != null ? params.get(ObjectParameter.BOARD_ROWS).toString() : null;
    String boardCols = params.get(ObjectParameter.BOARD_COLS) != null ? params.get(ObjectParameter.BOARD_COLS).toString() : null;
    String boardLength = params.get(ObjectParameter.BOARD_LENGTH) != null ? params.get(ObjectParameter.BOARD_LENGTH).toString() : null;
    String boardForward = params.get(ObjectParameter.BOARD_FORWARD) != null ? params.get(ObjectParameter.BOARD_FORWARD).toString() : null;
    String boardBackward = params.get(ObjectParameter.BOARD_BACKWARD) != null ? params.get(ObjectParameter.BOARD_BACKWARD).toString() : null;

    switch (Objects.requireNonNull(type)) {
      case "createGrid" -> {
        assert boardRows != null;
        assert boardCols != null;
        dropZones = BoardCreator.createGrid(Integer.parseInt(boardRows), Integer.parseInt(boardCols));
      }
      case "createSquareLoop" -> {
        assert boardRows != null;
        assert boardCols != null;
        dropZones = BoardCreator.createSquareLoop(Integer.parseInt(boardRows), Integer.parseInt(boardCols));
      }
      case "create1DLoop" -> {
        assert boardLength != null;
        if (boardForward == null || boardBackward == null) {
          dropZones = BoardCreator.create1DLoop(Integer.parseInt(boardLength));
        } else {
          dropZones = BoardCreator.create1DLoop(Integer.parseInt(boardLength), boardForward, boardBackward);
        }
      }
      default -> throw new RuntimeException("Unsupported BoardCreator type: " + type);
    }

    //we have all the dropzones, now we need to add them to game manager (with owner as game world)
    for (DropZone dropZone : dropZones) {
      dropZone.setOwner(gameWorld);
//      System.out.println("ADDING ID: " + dropZone.getId());
      ownableIdManager.addObject(dropZone);
    }
  }

  public Ownable constructOwnable(String ownableType, Owner owner, Map<Object, Object> constructorParams) throws IllegalArgumentException {

    //Assume that only Variables and GameObjects will be created in this way
    //Could be changed to use reflection to create any type of Ownable, but only Variables and GameObjects are needed
    if(ownableType.contains("Variable")) {
      Object value = constructorParams.get("value");
      //if neither value nor owner are null, then we can create the variable
      if(value != null && owner != null) {
        return new Variable<>(value, owner);
      } else if(value == null && owner != null) {
        //if value is null, then we need to create a variable with a default value
        return new Variable<>(owner);
      } else if(value != null) {
        //if owner is null, then we need to create a variable with a default owner
        return new Variable<>(value, gameWorld);
      } else {
        //if both value and owner are null, then we need to create a variable with a default value and owner
        return new Variable<>(gameWorld);
      }

    } else if(ownableType.contains("GameObject")){
      return new GameObject(owner);
    } else {
      throw new IllegalArgumentException("Invalid ownableType: " + ownableType);
    }
  }

  /**
   * check if owner id is numeric value
   * @param str owner id
   * @return owner id if numeric, null otherwise
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
   * @param params the parameters for the ownable
   */
  public void createOwnable(Map<ObjectParameter, Object> params) throws IllegalArgumentException {
    String ownableType = params.get(ObjectParameter.OWNABLE_TYPE) != null ? params.get(ObjectParameter.OWNABLE_TYPE).toString() : null;
    Map<Object, Object> constructorParams = (Map<Object, Object>) params.get(ObjectParameter.CONSTRUCTOR_ARGS);
    String ownerNum = params.get(ObjectParameter.OWNER) != null ? params.get(ObjectParameter.OWNER).toString() : null;
    Integer ownerInt = isNumeric(ownerNum);
    String parentOwnableId = params.get(ObjectParameter.PARENT_OWNABLE_ID) != null ? params.get(ObjectParameter.PARENT_OWNABLE_ID).toString() : null;
    String id = params.get(ObjectParameter.ID) != null ? params.get(ObjectParameter.ID).toString() : null;
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
    assert ownableType != null;
    if (ownableType.equals("BoardCreator")) {
      //below method also adds dropzones to ownableIdManager
      handleBoardCreator(constructorParams);
    } else {
      try {
        Ownable newOwnable = constructOwnable(ownableType, owner, constructorParams);
        ownableIdManager.addObject(newOwnable, id, parentOwnable);
      } catch (IllegalArgumentException e) {
        throw new RuntimeException("Error creating ownable", e);
      }
    }
  }
}
