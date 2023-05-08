package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
/**
 * @author Owen MacKenzie
 */
public interface GameRunnerComponent extends Component {
    /**
     * updates the dropZone or piece to be selectable and highlighted
     */
    void makePlayable();
    /**
     * updates the dropZone or piece to be unselectable and unhighlighted
     */
    void makeUnplayable();
    /**
     * returns whether the piece is playable
     * important for seeing if it is possible to drop a piece
     * on the drop zone in question
     */
    boolean getPlayable();
    /**
     * uses the parameters to create the selectable visual class that
     * specifies both the highlighted and unhighlighted node visual
     */
    void setSelectableVisual(AbstractSelectableVisual.SelectableVisualParams unselected, AbstractSelectableVisual.SelectableVisualParams selected);
    /**
     * sets only the highlighted visual because the backend could only
     * update one at a time
     */
    void setSelectVisual(AbstractSelectableVisual.SelectableVisualParams selected);
}
