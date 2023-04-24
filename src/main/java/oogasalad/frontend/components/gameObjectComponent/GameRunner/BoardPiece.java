package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.Controller.GameRunnerController;

import java.util.Map;

public class BoardPiece extends GameRunnerObject{
    private double lastTranslateX;
    private double lastTranslateY;
    public BoardPiece(int ID, GameRunnerController gameRunnerController) {
        super(ID, gameRunnerController);
    }

    public BoardPiece(int ID, Map<String, String> map, GameRunnerController gameRunnerController) {
        super(ID, map, gameRunnerController);
    }

    @Override
    public void onDragDropped() {
        gameRunnerController.updatePieceMove(ID);
    }

    @Override
    public void acceptDrag() {
        Node node = getNode();
        lastTranslateX = node.getTranslateX();
        lastTranslateY = node.getTranslateY();
    }

    @Override
    public void goBack() {
        Node node = getNode();
        node.setTranslateX(lastTranslateX);
        node.setTranslateY(lastTranslateY);
    }
}
