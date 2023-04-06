package oogasalad.sharedDependencies.backend.ownables.gameobjects.piece;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.owners.Owner;

/**
 * A PlayerPiece is a Piece that is owned by a Player (rather than a piece that is played into the game)
 * For example, this can be the piece that a player moves around the board
 */
public class PlayerPiece extends Piece{

  public PlayerPiece(IdManager idManager, Owner owner) {
    super(idManager, owner);
  }
}
