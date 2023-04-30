package oogasalad.Controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneFE;

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
    void passGameId(String code);

    record DropZoneParameters(String id, DropZoneFE.selectableVisualParams unselected, DropZoneFE.selectableVisualParams selected, int x, int y, int height, int width){}

}
