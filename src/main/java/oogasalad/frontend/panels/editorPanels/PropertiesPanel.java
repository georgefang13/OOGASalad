package oogasalad.frontend.panels.editorPanels;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;

public class PropertiesPanel extends HBox implements Panel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String PROPERTIES_BUTTON_TEXT_ID = "Properties";
  private static final String PROPERTIES_BUTTON_CSS_ID = "PropertiesButtonID";
  private static final String POPOUT_BUTTON_BOX_ID = "PopoutButtonBoxID";
  private static final String PROPERTIES_BOX_CLOSED_ID = "PropertiesBoxClosedID";
  private static final String PROPERTIES_BOX_OPEN_ID = "PropertiesBoxOpenID";
  private static final String PROPERTIES_TAB_PANE_ID = "PropertiesTabPaneID";

  ButtonFactory buttonFactory = new ButtonFactory();

  /**
   * Constructor for PropertiesPanel
   */
  public PropertiesPanel() {
    super();
    this.makePanel();
  }

  /**
   * Creates the Hbox that holds the pop out button and the tab pane
   *
   * @return
   */
  @Override
  public Panel makePanel() {
    this.getStyleClass().add(ID_BUNDLE.getString(PROPERTIES_BOX_OPEN_ID));
    this.getChildren().addAll(createPopOutButtonBox(), createPropertiesTabPane());
    return this;
  }

  private VBox createPopOutButtonBox() {
    VBox popOutButtonBox = new VBox();
    popOutButtonBox.getStyleClass().add(ID_BUNDLE.getString(POPOUT_BUTTON_BOX_ID));
    Button popOutButton = new Button(
        "P" + "\n" + "R" + "\n" + "O" + "\n" + "P" + "\n" + "E" + "\n" + "R" + "\n" + "T" + "\n"
            + "I" + "\n" + "E" + "\n"
            + "S"); //TODO: put in properties file so you can make it a button factory again
    popOutButton.getStyleClass().add(ID_BUNDLE.getString(PROPERTIES_BUTTON_CSS_ID));
    popOutButton.setOnAction(e -> toggleStyleSheets());
    popOutButtonBox.getChildren().add(popOutButton);
    return popOutButtonBox;
  }

  private TabPane createPropertiesTabPane() {
    TabPane propertiesTabPane = new TabPane();
    propertiesTabPane.getStyleClass().add(ID_BUNDLE.getString(PROPERTIES_TAB_PANE_ID));
    Tab tab1 = new Tab(
        "Tab 1"); // TODO: export these texts, and also make the creating of tabs come from properties files
    Tab tab2 = new Tab("Tab 2");
    Tab tab3 = new Tab("Tab 3");
    tab1.setClosable(false);
    tab2.setClosable(false);
    tab3.setClosable(false); // TODO: this will be part of the
    propertiesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    propertiesTabPane.getTabs().addAll(tab1, tab2, tab3);
    return propertiesTabPane;
  }

  private void toggleStyleSheets() {
    if (this.getStyleClass().contains(ID_BUNDLE.getString(PROPERTIES_BOX_CLOSED_ID))) {
      this.getStyleClass().remove(ID_BUNDLE.getString(PROPERTIES_BOX_CLOSED_ID));
      this.getStyleClass().add(ID_BUNDLE.getString(PROPERTIES_BOX_OPEN_ID));
    } else {
      this.getStyleClass().remove(ID_BUNDLE.getString(PROPERTIES_BOX_OPEN_ID));
      this.getStyleClass().add(ID_BUNDLE.getString(PROPERTIES_BOX_CLOSED_ID));
    }
  }
  public Node asNode() {
    return this;
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
