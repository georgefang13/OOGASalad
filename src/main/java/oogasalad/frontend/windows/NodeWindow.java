package oogasalad.frontend.windows;

import javafx.stage.Stage;
import oogasalad.frontend.managers.PropertyManager;
import oogasalad.frontend.managers.StandardPropertyManager;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.logging.MainLogger;


public class NodeWindow extends Stage {

  private PropertyManager propertyManager = StandardPropertyManager.getInstance();
  private static final MainLogger logger = MainLogger.getInstance(NodeWindow.class);

  public NodeWindow() {
    setResizable(false);
    setTitle("Node Editor");
    setWidth(propertyManager.getNumeric("WindowWidth"));
    setHeight(propertyManager.getNumeric("WindowHeight"));

    //logger.setLogLevel(Level.ALL); // uncomment if you want to add lover level logs specific to this class
    logger.trace("Created a new instance of NodeWindow");
  }

  public void showScene(AbstractScene scene) {
    setScene(scene.makeScene());
    show();
    logger.trace("Showed a new scene in NodeWindow");
  }
}
