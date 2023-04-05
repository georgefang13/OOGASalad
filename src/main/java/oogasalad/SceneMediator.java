package oogasalad;

import java.util.HashMap;
import java.util.Map;

public interface SceneMediator {
    String registerScene();

    void switchScene(String windowID);

    void receiveMessage();
}
