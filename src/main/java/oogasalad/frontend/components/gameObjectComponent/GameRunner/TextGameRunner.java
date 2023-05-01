package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
import oogasalad.frontend.components.textObjectComponent.TextObject;

public class TextGameRunner extends TextObject implements GameRunnerComponent {
    public TextGameRunner(String id) {
        super(id);
    }

    @Override
    public void makePlayable() {

    }

    @Override
    public void makeUnplayable() {

    }

    @Override
    public boolean getPlayable() {
        return false;
    }

    @Override
    public void setSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected) {

    }

    @Override
    public void setSelectVisual(AbstractSelectableVisual.SelectableVisualParams selected) {

    }
}
