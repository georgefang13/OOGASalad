package oogasalad.frontend.nodeEditor.main;

import javafx.application.Application;
import javafx.stage.Stage;
import oogasalad.frontend.nodeEditor.NodeController;
import oogasalad.frontend.windows.NodeWindow;

/**
 * Scrolling/panning based on
 * https://stackoverflow.com/questions/61195436/javafx-pan-and-zoom-with-draggable-nodes-inside
 */

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */

public class NodeExperiment extends Application {

  @Override
  public void start(Stage stage) {
    NodeWindow nodeWindow = new NodeWindow();
    NodeController nodeController = new NodeController(nodeWindow);
  }

}