package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.gameObjectComponent.GameObject;

import java.util.Map;

public abstract class GameRunnerObject extends GameObject implements GameRunnerComponent {
    GameRunnerController gameRunnerController;
    public GameRunnerObject(int ID, GameRunnerController gameRunnerController) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
    }

    public GameRunnerObject(int ID, Map<String, String> map, GameRunnerController gameRunnerController) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
    }

    @Override
    public void followMouse() {
        super.followMouse();
        getNode().setOnMouseReleased(e -> {
            onDragDropped();
        });
    }
}
