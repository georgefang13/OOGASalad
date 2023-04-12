package oogasalad.frontend.panels.subPanels;

import io.github.eckig.grapheditor.core.view.GraphEditorContainer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.VisualPanel;
import oogasalad.frontend.nodeEditor.GraphEditorTutorial;

public class EnvironmentPanel extends VisualPanel {

  /**
   * Constructor for the environment panel
   */
  public EnvironmentPanel() {
    super();
  }
  public Pane createEnvironment() {
    Pane environment = new Pane();
    GraphEditorTutorial graphEditorTutorial = new GraphEditorTutorial();
    GraphEditorContainer container = graphEditorTutorial.getContainer();
    container.prefHeightProperty().bind(environment.heightProperty());
    container.prefWidthProperty().bind(environment.widthProperty());
    environment.getChildren().add(container);
//    environment.setStyle("-fx-background-color: #0000FF;"); //TODO: delete, here just so we can see the pane
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
