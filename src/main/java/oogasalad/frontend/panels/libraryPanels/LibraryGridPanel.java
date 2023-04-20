package oogasalad.frontend.panels.libraryPanels;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import oogasalad.frontend.nodeEditor.GraphEditorTutorial;
import oogasalad.frontend.panels.Panel;

public class LibraryGridPanel extends Pane implements Panel {

  /**
   * Constructor for the environment panel
   */
  public LibraryGridPanel() {
    super();
    this.makePanel();
  }

  public Panel makePanel() {
    this.setStyle(
        "-fx-background-color: #24252e;"); //TODO: delete, here just so we can see the pane
    GraphEditorTutorial graphEditorTutorial = new GraphEditorTutorial();
    //GraphEditorContainer container = graphEditorTutorial.getContainer();
    //container.prefHeightProperty().bind(environment.heightProperty());
    //container.prefWidthProperty().bind(environment.widthProperty());
    //environment.getChildren().add(container);
    return this;
  }

  public Node asNode(){
    return (Node) this;
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
