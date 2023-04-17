package oogasalad.frontend.panels.subPanels;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.HBoxPanel;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.windows.GameEditorWindow.WindowScenes;

public class HeaderMenuPanel extends HBoxPanel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String LOGIC_EDITOR = "LogicEditor";
  private static final String VISUAL_EDITOR = "VisualEditor";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String MENU_HBOX_ID = "MenuHboxID";
  private static final String DESELECTED_HEADER_MENU_BUTTON_ID = "DeselectedHeaderMenuButtonID";
  private static final String SELECTED_HEADER_MENU_BUTTON_ID = "SelectedHeaderMenuButtonID";
  private static final String logic = "logic";
  private static final String editor = "visual";
  ButtonFactory buttonFactory = new ButtonFactory();
  PanelController panelController;
  private static String sceneID;

  /**
   * Constructor for HeaderMenu
   */
  public HeaderMenuPanel(PanelController panelController, String sceneID) {
    super();
    this.panelController = panelController;
    this.sceneID = sceneID;
  }

  /**
   * Creates the menu for the header
   *
   * @return
   */
  public HBox createMenu() {
    HBox menu = new HBox();
    menu.getStyleClass().add(ID_BUNDLE.getString(MENU_HBOX_ID));
    Button logicButton = buttonFactory.createDefaultButton(LOGIC_EDITOR);
    Button visualButton = buttonFactory.createDefaultButton(VISUAL_EDITOR);
    selectSceneButtonSettings(logicButton, visualButton);
    logicButton.setOnAction(e -> {
      panelController.newSceneFromPanel(logic, WindowScenes.LOGIC_SCENE);
    });

    visualButton.setOnAction(e -> {
      panelController.newSceneFromPanel(editor, WindowScenes.EDITOR_SCENE);
    });
    menu.getChildren().addAll(visualButton, logicButton);
    return menu;
  }

  private static void selectSceneButtonSettings(Button logicButton, Button visualButton) {
    switch (sceneID) {
      case logic:
        logicButton.getStyleClass().add(ID_BUNDLE.getString(SELECTED_HEADER_MENU_BUTTON_ID));
        visualButton.getStyleClass().add(ID_BUNDLE.getString(DESELECTED_HEADER_MENU_BUTTON_ID));
        break;
      case editor:
        logicButton.getStyleClass().add(ID_BUNDLE.getString(DESELECTED_HEADER_MENU_BUTTON_ID));
        visualButton.getStyleClass().add(ID_BUNDLE.getString(SELECTED_HEADER_MENU_BUTTON_ID));
        break;
      default:
        break;
    }

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

