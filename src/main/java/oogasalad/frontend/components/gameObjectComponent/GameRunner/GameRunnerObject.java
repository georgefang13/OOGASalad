package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

import java.io.FileInputStream;
import java.util.Map;

public abstract class GameRunnerObject extends GameObject implements GameRunnerComponent {
    GameRunnerController gameRunnerController;
    public GameRunnerObject(String ID, GameRunnerController gameRunnerController, String imagepath) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
        setImage(imagepath);
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
        getNode().setOnMouseClicked(e -> {
            onClick();
        });
        getNode().setOnMouseReleased(e -> {
            onDragDropped();
        });
    }
}
