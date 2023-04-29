package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.gamerunner.backend.GameController;
import oogasalad.gamerunner.backend.interpreter.commands.False;

public abstract class GameRunnerObject extends GameObject implements GameRunnerComponent {
    protected GameController gameRunnerController;
    protected AbstractSelectableVisual selectableVisual;
    protected boolean playable;
    protected boolean active;
    public GameRunnerObject(String ID, GameController gameRunnerController) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
        playable = false;
        active = false;
        setDraggable(false);
    }
    @Override
    public Node getNode(){
        return selectableVisual;
    }
    @Override
    public void makePlayable(){
        playable = true;
        selectableVisual.showClickable();
        setDraggable(playable);
    }
    @Override
    public void makeUnplayable(){
        playable = false;
        selectableVisual.showUnclickable();
        setDraggable(playable || active);
    }
}
