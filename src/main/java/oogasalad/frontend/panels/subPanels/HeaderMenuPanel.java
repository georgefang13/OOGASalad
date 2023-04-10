package oogasalad.frontend.panels.subPanels;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.HBoxPanel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.windows.GameEditorWindow;
import oogasalad.frontend.windows.GameEditorWindow.WindowScenes;

public class HeaderMenuPanel extends HBoxPanel {
  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle("frontend/properties/text/english");
//  private static final String SAVE_COMMAND = "SaveCommand"; //example
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle("frontend/properties/StylingIDs/CSS_ID");
//  private static final String CANVAS_PANE_ID = "CanvasPaneID"; //example


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

