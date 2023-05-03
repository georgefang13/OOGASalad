package oogasalad.frontend.panels.editorPanels;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import oogasalad.Controller.FilesController;
import oogasalad.frontend.factories.ButtonFactory;
import oogasalad.frontend.modals.ModalController;
import oogasalad.frontend.modals.subInputModals.CreateNewModal;
import oogasalad.frontend.panels.ModalPanel;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.windows.GameEditorWindow.WindowScenes;
import org.checkerframework.checker.units.qual.C;

public class HeaderMenuPanel extends HBox implements Panel, ModalPanel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String LOGIC_EDITOR = "LogicEditor";
  private static final String VISUAL_EDITOR = "VisualEditor";
  private static final String RULE_EDITOR = "RuleEditor";
  private static final String SAVE_EDITOR = "SaveEditor";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String MENU_HBOX_ID = "MenuHboxID";
  private static final String DESELECTED_HEADER_MENU_BUTTON_ID = "DeselectedHeaderMenuButtonID";
  private static final String SELECTED_HEADER_MENU_BUTTON_ID = "SelectedHeaderMenuButtonID";
  private static final String logic = "logic";
  private static final String editor = "visual";
  private ButtonFactory buttonFactory = new ButtonFactory();
  private PanelController panelController;
  private ModalController modalController;
  private static String sceneID;
  private Pane root;
  private FilesController files;

  /**
   * Constructor for HeaderMenu
   */
  public HeaderMenuPanel(PanelController panelController, String sceneID) {
    // TODO: fix dependencies
    super();
    this.panelController = panelController;
    this.sceneID = sceneID;
    this.modalController = new ModalController(this);
    this.makePanel();
  }

  /**
   * Creates the menu for the header
   *
   * @return
   */
  public Panel makePanel() {
    this.getStyleClass().add(ID_BUNDLE.getString(MENU_HBOX_ID));
    Button logicButton = buttonFactory.createDefaultButton(LOGIC_EDITOR);
    Button visualButton = buttonFactory.createDefaultButton(VISUAL_EDITOR);
//    Button ruleButton = buttonFactory.createDefaultButton(RULE_EDITOR);
    Button compileButton = buttonFactory.createDefaultButton(SAVE_EDITOR);

    selectSceneButtonSettings(logicButton, visualButton);
    logicButton.setOnAction(e -> {
      panelController.newSceneFromPanel(logic, WindowScenes.LOGIC_SCENE);
    });

    visualButton.setOnAction(e -> {
      panelController.newSceneFromPanel(editor, WindowScenes.EDITOR_SCENE);
    });
//    ruleButton.setOnAction(e -> {
//      panelController.newSceneFromPanel()//TODO: JOAO LIKE ABOVE
//    });
    compileButton.setOnAction(e -> {
      modalController.getFilecontroller().saveToFile();
    });
    this.getChildren().addAll(visualButton, logicButton, compileButton);
//    CreateNewModal creator = new CreateNewModal("save", modalController.dropzoneList());
//    creator.attach(modalController);
//    creator.showAndWait();
//    this.panelController.getSceneController().setFilesController(modalController.getFilecontroller());
    return this;
  }

  public void setFiles(FilesController file){
    files = file;
    modalController.setFileController(files);
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

  /**
   * Should allow for new components to be added from this bar, planned functionality
   * @param name
   * @param objectType
   */
  @Override
  public void addComponentTemplate(String name, String objectType) {

  }
}

