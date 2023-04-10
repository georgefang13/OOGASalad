package oogasalad.frontend.panels.subPanels;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.HBoxPanel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.windows.GameEditorWindow;
import oogasalad.frontend.windows.GameEditorWindow.WindowScenes;

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
    Button rulesbutton = buttonFactory.createDefaultButton("RulesEditor"); //TODO: export these
    rulesbutton.setOnAction(e-> panelController.newSceneFromPanel("logic", WindowScenes.LOGIC_SCENE));
    Button visualbutton = buttonFactory.createDefaultButton("VisualEditor"); //TODO: export these
    visualbutton.setOnAction(e-> panelController.newSceneFromPanel("visual", WindowScenes.EDITOR_SCENE));
    menu.getChildren().addAll(visualbutton, rulesbutton);
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

