package oogasalad.frontend.panels.subPanels;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.VisualPanel;
import oogasalad.frontend.windows.NodeWindow;

public class EnvironmentPanel extends VisualPanel {

  /**
   * Constructor for the environment panel
   */
  public EnvironmentPanel() {
    super();
  }

  public Pane createEnvironment() {
    Pane environment = new Pane();
    environment.setStyle(
        "-fx-background-color: #24252e;"); //TODO: delete, here just so we can see the pane
    NodeWindow nodeWindow = new NodeWindow();
    //GraphEditorContainer container = graphEditorTutorial.getContainer();
    //container.prefHeightProperty().bind(environment.heightProperty());
    //container.prefWidthProperty().bind(environment.widthProperty());
    //environment.getChildren().add(container);
    return environment;
  }

  @Override
  public Panel makePanel() {
    return null;
  }

  @Override
  public Panel refreshPanel() {
    return null;
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void save() {

  }
}
