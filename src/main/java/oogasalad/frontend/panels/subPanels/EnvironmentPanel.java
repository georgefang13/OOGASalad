package oogasalad.frontend.panels.subPanels;

import javafx.scene.layout.Pane;
import oogasalad.frontend.nodeEditor.GraphEditorTutorial;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.VisualPanel;

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
    GraphEditorTutorial graphEditorTutorial = new GraphEditorTutorial();
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
