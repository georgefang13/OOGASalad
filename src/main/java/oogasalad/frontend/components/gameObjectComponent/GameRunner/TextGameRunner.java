package oogasalad.frontend.components.gameObjectComponent.GameRunner;

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
    public TextGameRunner(String id) {
        super(id);
    }

    /**
     * Makes the object playable
     */
    @Override
    public void makePlayable() {

    }

    /**
     * Makes the object unplayable
     */
    @Override
    public void makeUnplayable() {

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
