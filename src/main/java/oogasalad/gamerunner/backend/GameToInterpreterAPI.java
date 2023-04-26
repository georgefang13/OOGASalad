package oogasalad.gamerunner.backend;

import oogasalad.sharedDependencies.backend.id.IdManageable;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;

/**
 * An API for the interpreter to call to access and move game assets
 */
public interface GameToInterpreterAPI {

    Player getPlayer(int playerNum);

    DropZone getPieceLocation(Ownable piece);

    void movePiece(GameObject piece, DropZone dz, String name);

    void removePiece(GameObject piece);

    void putInDropZone(Ownable element, DropZone dropZone, String name);

    void increaseTurn();

    void putClass(IdManageable obj, String name);

    void removeClass(IdManageable obj, String name);

    void setObjectImage(Ownable obj, String image);

    RuleManager getRules();

}
