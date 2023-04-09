package oogasalad.frontend.panels.subPanels;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.HBoxPanel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.windows.GameEditorWindow;

public class HeaderMenuPanel extends HBoxPanel {
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
    Button rulesbutton = buttonFactory.createDefaultButton("RulesEditor");
    rulesbutton.setOnAction(e-> openLogicSceneOnClick());
    menu.getChildren().addAll(buttonFactory.createDefaultButton("VisualEditor"),rulesbutton); //TODO: export these
    return menu;
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

  private void openLogicSceneOnClick(){
    String newsceneID = "logic";
    panelController.newSceneFromPanel(newsceneID, GameEditorWindow.WindowScenes.LOGIC_SCENE);
  }
}

