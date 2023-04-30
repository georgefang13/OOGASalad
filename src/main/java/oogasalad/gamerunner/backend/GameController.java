package oogasalad.gamerunner.backend;

import oogasalad.Controller.GameRunnerController;

import java.util.List;

public interface GameController {

    /**
     * Adds a drop zone to the game
     * @param params the parameters of the drop zone
     */
    void addDropZone(GameRunnerController.DropZoneParameters params);

    /**
     * Adds a piece to the game
     * @param id the id of the piece
     * @param image the image of the piece
     * @param dropZoneID the id of the drop zone the piece is in
     * @param size the size of the piece
     */
    void addPiece(String id, String image, String dropZoneID, double size);

    /**
     * Sets pieces to be clickable
     * @param ids the id of the piece
     */
    void setClickable(List<String> ids);

    /**
     * Moves a piece to a dropzone
     * @param id the id of the piece
     * @param dropZoneID the id of the drop zone the piece is in
     */
    void movePiece(String id, String dropZoneID);

    /**
     * Removes a piece from the game
     * @param id the id of the piece
     */
    void removePiece(String id);

    /**
     * Sets the image of an object
     * @param id the id of the object
     * @param imagePath the path of the image
     */
    void setObjectImage(String id, String imagePath);

    /**
     * Used by the Game backend to pass the game code to the front end
     * @param code the game code
     */
    void passGameId(String code);
}
