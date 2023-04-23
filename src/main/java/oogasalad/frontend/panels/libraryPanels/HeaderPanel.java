package oogasalad.frontend.panels.libraryPanels;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.windows.GameEditorWindow.WindowScenes;

public class HeaderPanel extends HBox implements Panel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String HEADER_PANEL_ID = "HeaderPanelID";
  private Button importGame;
  private Button newGame;
  ButtonFactory buttonFactory = new ButtonFactory();
  PanelController panelController;
  private static String sceneID;

  /**
   * Constructor for HeaderMenu
   */
  public HeaderPanel(PanelController panelController, String sceneID) {
    super();
    this.panelController = panelController;
    this.sceneID = sceneID;
    this.makePanel();
  }

  /**
   * Creates the menu for the header
   *
   * @return
   */
  public Panel makePanel() {
//    buttonFactory.createIconButton("logic", "logic", e -> {
//      panelController.newSceneFromPanel("logic", WindowScenes.LOGIC_SCENE);
//    });
    newGame = buttonFactory.createIconButton("New Game", "file:src/main/resources/frontend/images/GameLibrary/EditIcon.png");
    importGame = buttonFactory.createIconButton("Import Game", "file:src/main/resources/frontend/images/GameLibrary/EditIcon.png");
    this.getChildren().addAll(newGame, importGame);
    this.getStyleClass().add(ID_BUNDLE.getString(HEADER_PANEL_ID));
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

