package oogasalad.gamerunner.backend;

import oogasalad.Controller.GameRunnerController;

import java.io.FileNotFoundException;
import java.util.List;

public interface GameController {

    void addDropZone(GameRunnerController.DropZoneParameters params);

    void addPiece(String id, String image, String dropZoneID, double size) throws FileNotFoundException;

    void setClickable(List<String> ids);

    void movePiece(String id, String dropZoneID);

    void removePiece(String id);

    void setObjectImage(String id, String imagePath);
}
