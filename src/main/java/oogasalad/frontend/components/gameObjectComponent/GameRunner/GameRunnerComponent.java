package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
/**
 * @author Owen MacKenzie
 */
public interface GameRunnerComponent extends Component {
    void makePlayable();
    void makeUnplayable();
    boolean getPlayable();
    void setSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected);
    void setSelectVisual(AbstractSelectableVisual.SelectableVisualParams selected);
}
