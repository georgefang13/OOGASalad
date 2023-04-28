package oogasalad.frontend.panels.libraryPanels;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.windows.AbstractWindow;
import oogasalad.frontend.windows.GameEditorWindow;
import oogasalad.frontend.windows.LibraryWindow;
import oogasalad.frontend.windows.WindowTypes;
import oogasalad.frontend.windows.WindowTypes.WindowType;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class LibraryGridPanel extends GridPane implements Panel {
  private static final String GAMES_FILEPATH = "data/games/";
  private static final String GAMES_FILEPATH_WITH_FILE = "file:data/games/";
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String GAME_BOX_ID = "GameBoxID";
  private static final String GAME_BOX_IMAGE_ID = "GameBoxImageID";
  private static final String GAME_NAME_LABEL_BOX_ID = "GameNameLabelBoxID";
  private static final String GAME_NAME_LABEL_ID = "GameNameLabelID";
  private static final String GAME_BOX_EDIT_ICON_BOX_ID = "GameBoxEditIconBoxID";
  private static final String GAME_BOX_EDIT_ICON_ID = "GameBoxEditIconID";
  private static final String LIBRARY_GRID_PANE_ID = "LibraryGridPaneID";
  private final int IMAGE_WIDTH = 212;
  private final int IMAGE_HEIGHT = 150;
  private final int IMAGE_RADIUS = 20;
  PanelController panelController;
  private Map<String, String> gameNames;
  private static final String JSON_NAME = "name";
  private static final String JSON_TAGS = "tags";
  private static final String JSON_GENERAL_PATH = "/general.json";
  private static final String GAME_IMAGE_PATH = "/display.png";
  private static final String DEFAULT_TAG = "board game"; //TODO: CHANGE TO "default" and put that in every general.json
  /**
   * Constructor for the environment panel
   */
  public LibraryGridPanel(PanelController panelController) {
    super();
    this.makePanel();
    this.panelController = panelController;
    this.getStyleClass().add(ID_BUNDLE.getString(LIBRARY_GRID_PANE_ID));
  }
  /**
   * Creates the components that populate the panel for the grid panel
   *
   * @return Panel
   */
  public Panel makePanel() {
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(25);
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(25);
    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(25);
    ColumnConstraints column4 = new ColumnConstraints();
    column4.setPercentWidth(25);
    this.getColumnConstraints().addAll(column1, column2, column3, column4);

    gameNames = getGameNamesWithTag(DEFAULT_TAG);
    int rowIndex = 0;
    int columnIndex = 0;
    for (String game : gameNames.keySet()) {
      this.add(createGameBox(game, gameNames.get(game)), columnIndex, rowIndex);
      columnIndex++;
      if (columnIndex > 3) {
        columnIndex = 0;
        rowIndex++;
      }
    }
    return this;
  }
  private VBox createGameBox(String realGameName, String directoryName) {
    VBox gameBox = new VBox();
    gameBox.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_ID));
    gameBox.getChildren().addAll(createImageView(directoryName), createTextIconHBox(realGameName));
    return gameBox;
  }
  private ImageView createImageView(String gameName) {
    ImageView gameImage = new ImageView(GAMES_FILEPATH_WITH_FILE + gameName + GAME_IMAGE_PATH);
    gameImage.setPreserveRatio(false);
    gameImage.setFitHeight(IMAGE_HEIGHT);
    gameImage.setFitWidth(IMAGE_WIDTH);
    Rectangle clip = new Rectangle();
    clip.setWidth(gameImage.getFitWidth());
    clip.setHeight(gameImage.getFitHeight());
    clip.setArcWidth(IMAGE_RADIUS);
    clip.setArcHeight(IMAGE_RADIUS);
    gameImage.setClip(clip);
    gameImage.setOnMouseClicked(
        e -> {
          panelController.getSceneController().getWindowController().passData(gameName);
          panelController.getSceneController().getWindowController().registerAndShow(
              WindowTypes.WindowType.GAME_WINDOW);
        });
    gameImage.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_IMAGE_ID));
    return gameImage;
  }
  private HBox createTextIconHBox(String gameName) {
    HBox gameTextBox = new HBox();
    gameTextBox.getStyleClass().add(ID_BUNDLE.getString(GAME_NAME_LABEL_BOX_ID));
    Label gameNameLabel = new Label(gameName);
    gameNameLabel.setOnMouseClicked(
        e -> panelController.newSceneFromPanel("OWEN FIX THIS", LibraryWindow.WindowScenes.PLAY_SCENE));
    gameNameLabel.getStyleClass().add(ID_BUNDLE.getString(GAME_NAME_LABEL_ID));
    gameTextBox.getChildren().add(gameNameLabel);

    HBox iconBox = new HBox();
    iconBox.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_EDIT_ICON_BOX_ID));
    FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
    editIcon.setOnMouseClicked(
        e -> {
          AbstractWindow newWindow = panelController.newWindowFromPanel(WindowType.EDIT_WINDOW);
          AbstractScene editScene = newWindow.addNewScene(GameEditorWindow.WindowScenes.EDITOR_SCENE);
          newWindow.showScene(editScene);
        });
    editIcon.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_EDIT_ICON_ID));
    iconBox.getChildren().add(editIcon);

    HBox gameNameEditBox = new HBox();
    gameNameEditBox.getChildren().addAll(gameTextBox, iconBox);
    return gameNameEditBox;
  }

  private Map<String, String> getGameNamesWithTag(String tag) {
    Map<String, String> gameNamesAndFolderNames = new HashMap<>();
    File gamesDirectory = new File(GAMES_FILEPATH);
    File[] gameDirectories = gamesDirectory.listFiles(File::isDirectory);
    FileManager fm;

    for (File gameDirectory : Objects.requireNonNull(gameDirectories)) {
      try {
        fm = new FileManager(gameDirectory.getPath() + JSON_GENERAL_PATH);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
      Iterable<String> currentTags = fm.getArray(JSON_TAGS);
      for (String s : currentTags) {
        if (s.equals(tag)) {
          gameNamesAndFolderNames.put(fm.getString(JSON_NAME), gameDirectory.getName());
        }
      }
    }
    return gameNamesAndFolderNames;
  }

  public Node asNode(){
    return (Node) this;
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
