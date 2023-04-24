package oogasalad.gamerunner.backend;

import oogasalad.Controller.GameRunnerController;

import java.util.List;

public interface GameController {

    void addDropZone(GameRunnerController.DropZoneParameters params);

    void addPiece(String id, String image, String dropZoneID, double size);

    void setClickable(List<String> ids);

    void movePiece(String id, String dropZoneID);
}
