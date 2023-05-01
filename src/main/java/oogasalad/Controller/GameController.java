package oogasalad.Controller;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneFE;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;

import java.util.List;

public interface GameController {

    /**
     * Adds a drop zone to the game
     * @param params the parameters of the drop zone
     */
    void addDropZone(DropZoneParameters params);

    /**
     * Adds a piece to the game
     * @param id the id of the piece
     * @param imagePath the string path for the image of the piece
     * @param dropZoneID the id of the drop zone the piece is in
     * @param hasSelectImage if the piece has a selected image
     * @param param the name of the image or color for the selected image
     * @param height the height of the piece
     * @param width the width of the piece
     */
    void addPiece(String id, String imagePath, String dropZoneID, boolean hasSelectImage, String param, int height, int width);

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
     * Clicks a piece
     * @param dropID the id of the piece
     */
    void select(String dropID);

    /**
     * See if a piece is clickable / draggable
     * @param id the id of the piece
     * @return if the piece is clickable / draggable
     */
    boolean isObjectPlayable(String id);

    /**
     * Get all the components to put them in the JavaFX root
     * @return the components
     */
    ObservableList<Node> getGameObjectVisuals();

    /**
     * Used by the Game backend to pass the game code to the front end
     * @param code the game code
     */
    void passGameId(String code);

    ObjectProperty<Boolean> getEndGameStatus();

    void addTextObject(String id, String text, String DropZoneID);

    void updateTextObject(String id, String text);

    void assignUndoButtonAction(Button undoButton);

    void setPieceHighlight(String id, String img);

    void endGame(int player);

    record DropZoneParameters(String id, AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected, int x, int y, int height, int width, DropZoneFE.DropZoneDistribution distribution){}

}
