package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

import java.io.FileInputStream;
import java.util.Map;

public abstract class GameRunnerObject extends GameObject implements GameRunnerComponent {
    GameRunnerController gameRunnerController;
    public GameRunnerObject(String ID, GameRunnerController gameRunnerController, FileInputStream imagepath) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
        setImage(imagepath.toString());
    }
    /*
    public GameRunnerObject(int ID, Map<String, String> map, GameRunnerController gameRunnerController) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
    }

     */

    @Override
    public void followMouse() {
        super.followMouse();
        getNode().setOnMouseReleased(e -> {
            onDragDropped();
        });
    }
}
