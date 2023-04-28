package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.Controller.GameRunnerController;

import java.util.HashMap;
import java.util.Map;

public class Piece extends GameRunnerObject{
    private double lastTranslateX;
    private double lastTranslateY;
    public Piece(String ID, GameRunnerController gameRunnerController) {
        super(ID, gameRunnerController);
    }

    //@TODO
    // Owen what is this map bruh?
    public Piece(String ID, Node container, GameRunnerController gameRunnerController) {
        super(ID, new HashMap<>(), gameRunnerController);
    }

    public Piece(String ID, Map<String, String> map, GameRunnerController gameRunnerController) {
        super(ID, map, gameRunnerController);
    }

    @Override
    public void onDragDropped() {
        gameRunnerController.updatePieceMove(Integer.parseInt(getID()));
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
