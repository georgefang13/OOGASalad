package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import javafx.scene.paint.Color;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
import oogasalad.frontend.components.textObjectComponent.TextObject;
/**
 * @author Owen MacKenzie
 */
public class TextGameRunner extends TextObject implements GameRunnerComponent {

    /**
     * Constructor for TextGameRunner
     * @param id
     */
    public TextGameRunner(String id, GameRunnerController gameRunnerController) {
        super(id, gameRunnerController);
    }

    /**
     * Makes the object playable
     */
    @Override
    public void makePlayable() {
        setTextColor(Color.RED);
    }

    /**
     * Makes the object unplayable
     */
    @Override
    public void makeUnplayable() {
        setTextColor(Color.BLACK);
    }

    /**
     * Gets the playable status
     * @return
     */
    @Override
    public boolean getPlayable() {
        return false;
    }

    /**
     * Sets the selectable visual
     * @param unselected
     * @param selected
     */
    @Override
    public void setSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected) {

    }

    /**
     * Sets the select visual
     * @param selected
     */
    @Override
    public void setSelectVisual(AbstractSelectableVisual.SelectableVisualParams selected) {

    }
}
