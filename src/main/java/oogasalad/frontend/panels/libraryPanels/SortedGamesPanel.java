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

public class SortedGamesPanel extends GridPane implements Panel {

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
    this.setPadding(new Insets(10));
    this.setHgap(10);
    this.setVgap(10);

    // set column constraints to evenly distribute the available width
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(25);
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(25);
    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(25);
    ColumnConstraints column4 = new ColumnConstraints();
    column4.setPercentWidth(25);
    this.getColumnConstraints().addAll(column1, column2, column3, column4);

    // populate the grid with labels
    String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
    int numRows = (int) Math.ceil((double) items.length / 4);
    int rowIndex = 0;
    int columnIndex = 0;
    for (String item : items) {
      Label label = new Label(item);
      GridPane.setHgrow(label, Priority.ALWAYS);
      GridPane.setVgrow(label, Priority.ALWAYS);
      this.add(label, columnIndex, rowIndex);
      columnIndex++;
      if (columnIndex > 3) {
        columnIndex = 0;
        rowIndex++;
      }
    }
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
