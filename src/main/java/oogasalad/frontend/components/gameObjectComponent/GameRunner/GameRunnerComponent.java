package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.frontend.components.Component;

public interface GameRunnerComponent extends Component {
    void setSelectableVisual();
    void makePlayable();
    void makeUnplayable();
    boolean getPlayable();
}
