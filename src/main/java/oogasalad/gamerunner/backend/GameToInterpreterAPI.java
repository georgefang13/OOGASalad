package oogasalad.gamerunner.backend;

import oogasalad.sharedDependencies.backend.id.IdManageable;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.Owner;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;

/**
 * An API for the interpreter to call to access and move game assets
 */
public interface GameToInterpreterAPI {

    /**
     * Returns the player with the given number. Returns the GameWorld if -1
     * @param playerNum the number of the player to return
     * @return the player with the given number
     */
    Owner getPlayer(int playerNum);

    /**
     * Returns the DropZone that a piece is in
     * @param piece the piece to find the location of
     * @return the DropZone that the piece is in
     */
    DropZone getPieceLocation(Ownable piece);

    /**
     * Moves a GameObject to the selected DropZone
     * @param piece the piece to move
     * @param dz the DropZone to move the piece to
     */
    void movePiece(GameObject piece, DropZone dz);

    /**
     * Removes a piece from the game
     * @param piece the piece to remove
     */
    void removePiece(GameObject piece);

    /**
     * Adds a piece to the game
     * @param element
     * @param dropZone
     */
    void putInDropZone(Ownable element, DropZone dropZone);

    void increaseTurn();

    void setTurn(double turn);

    void setObjectImage(Ownable obj, String image);

    void addObject(Ownable obj, DropZone dz, String image, double size);
    void addDropZone(DropZone dz, DropZone location, String image, String highlight, double width, double height);
    RuleManager getRules();

}
