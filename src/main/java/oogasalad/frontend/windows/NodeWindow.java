package oogasalad.frontend.windows;

import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.scenes.AbstractScene;


public class NodeWindow extends Stage {

  private PropertyManager propertyManager = StandardPropertyManager.getInstance();

  public NodeWindow() {
    setResizable(false);
    setTitle("Node Editor");
    setWidth(propertyManager.getNumeric("WindowWidth"));
    setHeight(propertyManager.getNumeric("WindowHeight"));
  }

  public void showScene(AbstractScene scene) {
    setScene(scene.makeScene());
    show();
  }
}
