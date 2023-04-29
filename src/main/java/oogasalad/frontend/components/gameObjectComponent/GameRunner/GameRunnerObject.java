package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.gamerunner.backend.GameController;

public abstract class GameRunnerObject extends GameObject implements GameRunnerComponent {
    protected GameController gameRunnerController;
    protected AbstractSelectableVisual selectableVisual;
    public GameRunnerObject(String ID, GameController gameRunnerController) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
    }
    @Override
    public Node getNode(){
        return selectableVisual;
    }
    abstract void setSelectableVisual();
}
