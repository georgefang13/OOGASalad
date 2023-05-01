package oogasalad.frontend.panels.editorPanels;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.frontend.panels.Panel;

public class EnvironmentPanel extends Pane implements Panel {
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String THE_ENVIRONMENT_ID = "TheEnvironmentID";

  /**
   * Constructor for the environment panel
   */
  public EnvironmentPanel() {
    super();
    this.makePanel();
  }

  public Panel makePanel() {
    this.getStyleClass().add(ID_BUNDLE.getString(THE_ENVIRONMENT_ID));
    return this;
  }

  public Node asNode(){
    return (Node) this;
  }
  @Override
  public void refreshPanel() {}

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void save() {

  }
}
