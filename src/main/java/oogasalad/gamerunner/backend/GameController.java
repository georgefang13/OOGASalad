package oogasalad.gamerunner.backend;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.FileNotFoundException;
import java.util.List;

public interface GameController {

    void addDropZone(DropZoneParameters params);

    void addPiece(String id, String image, String dropZoneID, boolean hasSelectImage, Object param, double height, double width) throws FileNotFoundException;

    void setClickable(List<String> ids);

    void movePiece(String id, String dropZoneID);

    void removePiece(String id);

    void setObjectImage(String id, String imagePath);

    void select(String dropID);
    boolean isObjectPlayable(String id);
    ObservableList<Node> getGameObjectVisuals();

    record DropZoneParameters(String id, Node unselected, Node selected, int x, int y, int height, int width){}

}
