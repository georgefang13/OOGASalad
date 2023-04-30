package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;

public interface GameRunnerComponent extends Component {
    void setSelectableVisual();
    void makePlayable();
    void makeUnplayable();
    boolean getPlayable();
    void updateSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected);
}
