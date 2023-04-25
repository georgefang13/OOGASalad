package oogasalad.frontend.components.gameObjectComponent.GameRunner;

import oogasalad.frontend.components.Component;

public interface GameRunnerComponent extends Component {
    void onDragDropped();
    void onClick();
    void acceptDrag();
    void goBack();

}
