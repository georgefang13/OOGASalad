package oogasalad.sharedDependencies.backend.ownables.gameobjects.piece;

import com.google.gson.JsonObject;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Owner;

public abstract class Piece extends GameObject {

  /**
   * Creates a new GameObject.
   *
   * @param owner
   */
  public Piece(Owner owner) {
    super(owner);
  }

  @Override
  public void buildFromJson(JsonObject element) {
    // TODO: update ID + set initial dropbox (probably through IdManager?)
  }

  @Override
  public JsonObject getAsJson() {
    return null;
  }
}
