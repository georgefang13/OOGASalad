package oogasalad.gameeditor.backend;

public enum ObjectParameter {

  //FOR OWNABLES
  /**
   * Any concrete subclass of Ownable, accessible through reflection. Ex: Variable, DropZone, etc.
   * If not, an existing class, default GameObject will be created
   */
  OWNABLE_TYPE,

  /**
   * A Map of ObjectParameters to Strings, containing the parameters for the Ownable
   */
  CONSTRUCTOR_ARGS,

  //region FOR CONSTRUCTOR_ARGS map

  /**
   * The ID of the Object. For IdManagables, this is the instantiated ID.
   */
  ID,

  /**
   *  For noting who the Owner of Onwables is. This should be "1", "2", etc. for the Players; anything else assumes GameWorld
   */
  OWNER,

  /**
   * The ID of the Parent Ownable.
   */
  PARENT_OWNABLE_ID,

  /**
   * The value for a Variable
   */
  VALUE,

  /**
   * The Board Creator params
   */
  BOARD_TYPE,
  BOARD_ROWS,
  BOARD_COLS,
  BOARD_LENGTH,
  BOARD_FORWARD,
  BOARD_BACKWARD,

  /**
   * Rules
   */
  RULE_STR,
  RULE_CLS,
  RULE_NAME,

  /**
   * Destination DropZone ID for a new GameObject
   */
  DEST_DROPZONE_ID,

  /**
   * The name of a link between dropzones to create or remove
   */
  LINK_NAME,

  /**
   * The IDs of the dropzones to link to and from
   */
  TARGET_DROPZONE_ID_FROM,
  TARGET_DROPZONE_ID_TO,



  //endregion FOR CONSTRUCTOR_ARGS

}
