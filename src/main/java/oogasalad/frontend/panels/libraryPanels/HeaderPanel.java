package oogasalad.frontend.panels.libraryPanels;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.modals.ModalController;
import oogasalad.frontend.modals.subInputModals.CreateNewModal;
import oogasalad.frontend.panels.ModalPanel;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.GameEditorWindow;
import oogasalad.frontend.windows.WindowTypes;
import oogasalad.frontend.windows.WindowTypes.WindowType;

public class HeaderPanel extends HBox implements Panel, ModalPanel {
  private static final String PUZZLE_ICON = "puzzle_piece";
  private static final String UPLOAD_ICON = "upload";
  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String GAME_LIBRARY = "GameLibrary";
  private static final String NEW_GAME = "NewGame";
  private static final String IMPORT_GAME = "ImportGame";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String LEFT_HEADER_BOX_ID = "LeftHeaderBoxID";
  private static final String RIGHT_HEADER_BOX_ID = "RightHeaderBoxID";
  private static final String GAME_LIBRARY_TITLE_ID = "GameLibraryTitleID";
  private Button importGame;
  private Button newGame;
  ButtonFactory buttonFactory = new ButtonFactory();
  PanelController panelController;
  private ModalController modalController;
  private static String sceneID;

  /**
   * Constructor for HeaderMenu
   */
  public HeaderPanel(PanelController panelController, String sceneID) {
    super();
    this.panelController = panelController;
    this.modalController = new ModalController(this);
    this.sceneID = sceneID;
    this.makePanel();
  }

  /**
   * Creates the menu for the header
   *
   * @return
   */
  public Panel makePanel() {
    this.getChildren().addAll(makeLeftBox(), makeRightBox());
    return this;
  }
  private HBox makeLeftBox() {
    HBox leftBox = new HBox();
    Label gameLibraryTitle = new Label(ELEMENT_LABELS.getString(GAME_LIBRARY));
    gameLibraryTitle.getStyleClass().add(ID_BUNDLE.getString(GAME_LIBRARY_TITLE_ID));
    leftBox.getChildren().add(gameLibraryTitle);
    leftBox.getStyleClass().add(ID_BUNDLE.getString(LEFT_HEADER_BOX_ID));
    return leftBox;
  }
  private HBox makeRightBox() {
    HBox rightBox = new HBox();
    newGame = buttonFactory.createIconButton(NEW_GAME, PUZZLE_ICON);
    newGame.setOnMouseClicked(
        e -> {
          AbstractWindow newWindow = panelController.newWindowFromPanel(WindowType.EDIT_WINDOW);
          AbstractScene editScene = newWindow.addNewScene(GameEditorWindow.WindowScenes.EDITOR_SCENE);
          newWindow.showScene(editScene);
        });
    importGame = buttonFactory.createIconButton(IMPORT_GAME, UPLOAD_ICON);
    rightBox.getChildren().addAll(importGame, newGame);
    rightBox.getStyleClass().add(ID_BUNDLE.getString(RIGHT_HEADER_BOX_ID));
    return rightBox;
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

  @Override
  public void addComponentTemplate(String name, String objectType) {

  }

  @Override
  public Node getStyleableNode() {
    return super.getStyleableNode();
  }
}

