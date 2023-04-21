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

    public GameRunnerObject(int ID, Node container, GameRunnerController gameRunnerController) {
        super(ID, container);
        this.gameRunnerController = gameRunnerController;
    }

    public GameRunnerObject(int ID, Map<String, String> map, GameRunnerController gameRunnerController) {
        super(ID, map);
        this.gameRunnerController = gameRunnerController;
    }

    @Override
    public void followMouse() {
        getNode().setOnMousePressed(e -> {
            XOffset = e.getSceneX() - (getNode().getTranslateX());
            YOffset = e.getSceneY() - (getNode().getTranslateY());

        });
        getNode().setOnMouseDragged(e -> {
            getNode().setTranslateX(e.getSceneX() - XOffset);
            getNode().setTranslateY(e.getSceneY() - YOffset);
        });
        getNode().setOnDragOver(e -> {
            onDragDropped();
        });
    }
}
