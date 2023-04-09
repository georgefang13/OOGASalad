package oogasalad.frontend.panels.subPanels;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.HBoxPanel;
import oogasalad.frontend.scenes.SceneController;
import oogasalad.frontend.windows.GameEditorWindow;

public class HeaderMenuPanel extends HBoxPanel {
  ButtonFactory buttonFactory = new ButtonFactory();
  SceneController sceneController;

  /**
   * Constructor for HeaderMenu
   */
  public HeaderMenuPanel(SceneController sceneController) {
    super();
    this.sceneController = sceneController;
  }
  /**
   * Creates the menu for the header
   * @return
   */
  public HBox createMenu() {
    HBox menu = new HBox();
    Button rulesbutton = buttonFactory.createDefaultButton("RulesEditor"); //TODO: export these
    rulesbutton.setOnAction(e-> openLogicSceneOnClick());
    Button visualbutton = buttonFactory.createDefaultButton("VisualEditor"); //TODO: export these
    menu.getChildren().addAll(visualbutton, rulesbutton);

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
    sceneController.addAndLinkScene(GameEditorWindow.WindowScenes.LOGIC_SCENE,newsceneID);
    sceneController.switchToScene(newsceneID);
  }
}

