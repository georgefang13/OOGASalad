package oogasalad.sharedDependencies.backend.ownables.gameobjects;

import com.google.gson.JsonObject;
import oogasalad.sharedDependencies.backend.filemanagers.JsonSaveable;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * A GameObject is an Ownable that can be owned by an Owner
 * Represents an object such as a game piece
 * @author Michael Bryant
 */
public class GameObject extends Ownable implements JsonSaveable {

  /**
   * Creates a new GameObject.
   */
  public GameObject(Owner owner) {
    super(owner);
  }

  @Override
  public void buildFromJson(JsonObject element) {
    // Does nothing
  }

  @Override
  public JsonObject getAsJson() {
    return null;
  }
}

