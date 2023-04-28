package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.gamerunner.backend.GameController;

import java.io.FileInputStream;
import java.util.Map;

public abstract class GameRunnerObject extends GameObject implements GameRunnerComponent {
    GameController gameRunnerController;
    public GameRunnerObject(String ID, GameController gameRunnerController, String imagepath, double size) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
        setSize(size);
        setImage(imagepath);
        followMouse();
    }

    @Override
    public void followMouse() {
        super.followMouse();
        getNode().setOnMouseClicked(e -> {
            onClick();
        });
        getNode().setOnMouseReleased(e -> {
            onDragDropped();
        });
    }
}
