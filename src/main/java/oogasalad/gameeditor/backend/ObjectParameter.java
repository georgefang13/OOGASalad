package oogasalad.gameeditor.backend;

public enum ObjectParameter {

  //FOR OWNABLES
  /**
   * Any concrete subclass of Ownable, accessible through reflection. Ex: Variable, DropZone, etc.
   * If contained in {"", "null", "NULL", "Null", "none", "NONE", "None"} or not present in the map,
   * a default GameObject will be created
   */
  OWNABLE_TYPE,

  /**
   * The owner of the Ownable. Ex: Player1, Player2, GameWorld, etc. This is of type Owner Note:
   * Players cannot be named to anything other than "Player1", "Player2", etc. This is independent
   * of the language configuration Any other name will result in the owner being set to the
   * GameWorld
   */
  OWNER,

  /**
   * The ID of the Ownable. If not present in the map, a new ID will be generated. (Ex: Variable2)
   */
  ID,

  /**
   * The name of parent of the Ownable. If not present in the map, no parent Ownable will be set.
   * Ex. the parent ownable for a variable representing the value of a card is the card itself
   */
  PARENT_OWNABLE,

  BOARD_CREATOR_TYPE,
  BOARD_CREATOR_PARAM_1,
  BOARD_CREATOR_PARAM_2,
  BOARD_CREATOR_PARAM_3,

  /**
   * The value of the variable that will be set in the updateObjectProperties api call
   */
  VALUE

  //FOR RULES

  //FOR GOALS

  //FOR PLAYERS
  //N/A
}
