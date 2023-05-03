package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.Node;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.Controller.GameController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
/**
 * @author Owen MacKenzie
 */
public abstract class GameRunnerObject extends GameObject implements GameRunnerComponent {
    protected GameController gameRunnerController;
    protected AbstractSelectableVisual selectableVisual;
    protected boolean playable;
    protected boolean active;

    /**
     * Constructor for GameRunnerObject
     * @param ID
     * @param gameRunnerController
     */
    public GameRunnerObject(String ID, GameController gameRunnerController) {
        super(ID);
        this.gameRunnerController = gameRunnerController;
        playable = false;
        active = false;
        setDraggable(false);
    }

    /**
     * Gets the node of the object
     * @return
     */
    @Override
    public Node getNode(){
        return selectableVisual;
    }

    /**
     * Makes the object active
     */
    @Override
    public void makePlayable(){
        playable = true;
        selectableVisual.showClickable();
        setDraggable(playable);
    }

    /**
     * Makes the object inactive
     */
    @Override
    public void makeUnplayable(){
        playable = false;
        selectableVisual.showUnclickable();
        setDraggable(playable || active);
    }

    /**
     * Checks if the object is playable
     * @return
     */
    @Override
    public boolean getPlayable(){
        return playable;
    }
}
