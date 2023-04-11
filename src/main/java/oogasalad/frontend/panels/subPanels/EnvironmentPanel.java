package oogasalad.frontend.panels.subPanels;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    environment.setStyle("-fx-background-color: #24252e;"); //TODO: delete, here just so we can see the pane
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
