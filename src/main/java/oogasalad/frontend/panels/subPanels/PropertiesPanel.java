package oogasalad.frontend.panels.subPanels;

import java.util.ResourceBundle;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.HBoxPanel;
import oogasalad.frontend.panels.Panel;

public class PropertiesPanel extends HBoxPanel {
  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle("frontend/properties/text/english");
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String PROPERTIES_BUTTON_TEXT_ID = "Properties";
  private static final String PROPERTIES_BUTTON_CSS_ID = "PropertiesButtonID";
  private static final String POPOUT_BUTTON_BOX_ID = "PopoutButtonBoxID";
  private static final String PROPERTIES_BOX = "PropertiesBoxID";

  ButtonFactory buttonFactory = new ButtonFactory();

  /**
   * Constructor for PropertiesPanel
   */
  public PropertiesPanel() {
    super();
  }

  /**
   * Creates the Hbox that holds the pop out button and the tab pane
   * @return
   */
  public HBox createPanel() {
    HBox propertiesPanel = new HBox();
    propertiesPanel.getStyleClass().add(ID_BUNDLE.getString(PROPERTIES_BOX));
    propertiesPanel.getChildren().addAll(createPopOutButtonBox());
    return propertiesPanel;
  }
  private VBox createPopOutButtonBox() {
    VBox popOutButtonBox = new VBox();
    popOutButtonBox.getStyleClass().add(ID_BUNDLE.getString(POPOUT_BUTTON_BOX_ID));
    Button popOutButton = buttonFactory.createVeritcalButton(PROPERTIES_BUTTON_TEXT_ID);
    popOutButton.getStyleClass().add(ID_BUNDLE.getString(PROPERTIES_BUTTON_CSS_ID));
    popOutButtonBox.getChildren().add(popOutButton);
    return popOutButtonBox;
  }
  private TabPane createPropertiesTabPane() {
    TabPane propertiesTabPane = new TabPane();
    Tab tab1 = new Tab("Tab 1"); // TODO: export these texts, and also make the creating of tabs come from properties files
    Tab tab2 = new Tab("Tab 2");
    Tab tab3 = new Tab("Tab 3");
    return propertiesTabPane;
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
