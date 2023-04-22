package oogasalad.frontend.panels.libraryPanels;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import oogasalad.frontend.modals.InputModal;
import oogasalad.frontend.modals.ModalController;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;

public class SortedGamesPanel extends VBox implements Panel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String COMPONENT_LIBRARY_ACCORDION_LABEL = "ComponentLibrary";
  private static final String ACTIVE_COMPONENTS_ACCORDION_LABEL = "ActiveComponents";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  PanelController panelController;

  /**
   * Constructor for HeaderMenu
   */
  public SortedGamesPanel() {
    super();
    this.makePanel();

  }

  /**
   * Creates the VBox that contains the two accordions and the labels for each accordion
   *
   * @return
   */
  public Panel makePanel() {
    return this;
  }

  public Node asNode() {
    return this;
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
