package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.Controller.GameController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;

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
        if (!active) {
            selectableVisual.showUnclickable();
        }
        setDraggable(playable || active);
    }
    @Override
    public boolean getPlayable(){
        return playable;
    }
}
