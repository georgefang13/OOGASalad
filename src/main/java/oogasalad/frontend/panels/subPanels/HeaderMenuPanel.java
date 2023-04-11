package oogasalad.frontend.panels.subPanels;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.HBoxPanel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.windows.GameEditorWindow.WindowScenes;

public class HeaderMenuPanel extends HBoxPanel {
  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle("frontend/properties/text/english");
  private static final String RULES_EDITOR = "RulesEditor";
  private static final String VISUAL_EDITOR = "VisualEditor";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String MENU_HBOX_ID = "MenuHboxID";
  private static final String DESELECTED_HEADER_MENU_BUTTON_ID = "DeselectedHeaderMenuButtonID";
  private static final String SELECTED_HEADER_MENU_BUTTON_ID = "SelectedHeaderMenuButtonID";


  ButtonFactory buttonFactory = new ButtonFactory();
  PanelController panelController;

  /**
   * Constructor for HeaderMenu
   */
  public HeaderMenuPanel(PanelController panelController) {
    super();
    this.panelController = panelController;
  }
  /**
   * Creates the menu for the header
   * @return
   */
  public HBox createMenu() {
    HBox menu = new HBox();
    menu.getStyleClass().add(ID_BUNDLE.getString(MENU_HBOX_ID));
    Button rulesButton = buttonFactory.createDefaultButton(RULES_EDITOR);
    Button visualButton = buttonFactory.createDefaultButton(VISUAL_EDITOR);
    rulesButton.getStyleClass().add(ID_BUNDLE.getString(DESELECTED_HEADER_MENU_BUTTON_ID));
    rulesButton.setOnAction(e -> {
      visualButton.getStyleClass().remove(SELECTED_HEADER_MENU_BUTTON_ID);
      visualButton.getStyleClass().add(DESELECTED_HEADER_MENU_BUTTON_ID);
      rulesButton.getStyleClass().add(SELECTED_HEADER_MENU_BUTTON_ID);
      panelController.newSceneFromPanel("logic", WindowScenes.LOGIC_SCENE);
    });
    visualButton.getStyleClass().add(ID_BUNDLE.getString(SELECTED_HEADER_MENU_BUTTON_ID));
    visualButton.setOnAction(e -> {
      rulesButton.getStyleClass().remove(SELECTED_HEADER_MENU_BUTTON_ID);
      rulesButton.getStyleClass().add(DESELECTED_HEADER_MENU_BUTTON_ID);
      visualButton.getStyleClass().add(SELECTED_HEADER_MENU_BUTTON_ID);
      panelController.newSceneFromPanel("visual", WindowScenes.EDITOR_SCENE);
    });
    menu.getChildren().addAll(visualButton, rulesButton);
    return menu;
  } //TODO: i dont know what "visual" and "logic" do here, does this actaully work as the Scene ID?
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

