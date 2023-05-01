package oogasalad.gamerunner.backend;

import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.TextObject;
import oogasalad.sharedDependencies.backend.owners.Owner;
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
     * @param element the piece to add
     * @param dropZone the DropZone to add the piece to
     */
    void putInDropZone(Ownable element, DropZone dropZone);

    /**
     * set the turn to be the next player
     */
    void increaseTurn();

    /**
     * set the turn to be a specific player
     * @param turn the player position to set the turn to
     */
    void setTurn(double turn);

    /**
     * Sets the image of an object
     * @param obj the object to set the image of
     * @param image the path of the image
     */
    void setObjectImage(Ownable obj, String image);

    /**
     * Adds a piece to the game
     * @param obj the piece to add
     * @param dz the DropZone to add the piece to
     * @param image the image of the piece
     * @param width the width of the piece
     * @param height the height of the piece
     */
    void addObject(Ownable obj, DropZone dz, String image, double width, double height);

    /**
     * Adds a dropzone to the game
     * @param dz the dropzone to add
     * @param location the dropzone to add the dropzone to
     * @param image the image of the dropzone
     * @param highlight the highlight of the dropzone
     * @param width the width of the dropzone
     * @param height the height of the dropzone
     */
    void addDropZone(DropZone dz, DropZone location, String image, String highlight, double width, double height);

    /**
     * Set the highlight image or color for a GameObject
     * @param piece the piece to set the highlight of
     * @param highlight the highlight to set
     */
    void setPieceHighlight(Ownable piece, String highlight);

    /**
     * Send the content of a TextObject to the controller
     * @param obj the TextObject to send
     */
    void updateTextObject(TextObject obj);

    /**
     * Add a TextObject to the game
     * @param obj the TextObject to add
     * @param dz the DropZone to add the TextObject to
     */
    void addTextObject(TextObject obj, DropZone dz);

    /**
     * Get the rules of the game
     * @return the rules of the game
     */
    RuleManager getRules();

}
