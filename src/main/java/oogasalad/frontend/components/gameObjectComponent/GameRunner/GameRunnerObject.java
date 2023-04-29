package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.gamerunner.backend.GameController;
import oogasalad.gamerunner.backend.interpreter.commands.False;

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
    @Override
    public void makeClickable(){
        setDraggable(true);
        selectableVisual.showClickable();
    }
    @Override
    public void makeUnclickable(){
        setDraggable(false);
        selectableVisual.showUnclickable();
    }
}
