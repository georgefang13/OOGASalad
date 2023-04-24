package oogasalad.frontend.panels.libraryPanels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import oogasalad.frontend.nodeEditor.GraphEditorTutorial;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.windows.GamePlayerWindow;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class LibraryGridPanel extends GridPane implements Panel {
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
  private final List<String> libraryGames = new ArrayList<>(
      Arrays.asList("Tic-Tak-Toe", "Fortnite", "Backgammon", "Chess", "Checkers")); //TODO: TEMPORARY

  /**
   * Constructor for the environment panel
   */
  public LibraryGridPanel() {
    super();
    this.makePanel();
    this.getStyleClass().add(ID_BUNDLE.getString(LIBRARY_GRID_PANE_ID));
  }
  public VBox createGameBox(String gameName) {
    VBox gameBox = new VBox();
    gameBox.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_ID));
    gameBox.getChildren().addAll(createImageView(gameName), createTextIconHBox(gameName));
    return gameBox;
  }
  private ImageView createImageView(String gameName) {
    ImageView gameImage = new ImageView("file:src/main/resources/frontend/images/GameLibrary/" + gameName + ".png"); //make file:src/main/resources/frontend/images/GameLibrary/ a vairable and have the game name find the image name from a helper function
    gameImage.setPreserveRatio(false);
    gameImage.setFitHeight(IMAGE_HEIGHT);
    gameImage.setFitWidth(IMAGE_WIDTH);
    Rectangle clip = new Rectangle();
    clip.setWidth(gameImage.getFitWidth());
    clip.setHeight(gameImage.getFitHeight());
    clip.setArcWidth(IMAGE_RADIUS);
    clip.setArcHeight(IMAGE_RADIUS);
    gameImage.setClip(clip);
    gameImage.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_IMAGE_ID));
    return gameImage;
  }
  private HBox createTextIconHBox(String gameName) {
    HBox gameTextBox = new HBox();
    gameTextBox.getStyleClass().add(ID_BUNDLE.getString(GAME_NAME_LABEL_BOX_ID));
    Label gameNameLabel = new Label(gameName);
    gameNameLabel.getStyleClass().add(ID_BUNDLE.getString(GAME_NAME_LABEL_ID));
    gameTextBox.getChildren().add(gameNameLabel);

    HBox iconBox = new HBox();
    iconBox.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_EDIT_ICON_BOX_ID));
    FontIcon editIcon = new FontIcon(FontAwesomeSolid.EDIT);
    editIcon.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_EDIT_ICON_ID));
    iconBox.getChildren().add(editIcon);

    HBox gameNameEditBox = new HBox();
    gameNameEditBox.getChildren().addAll(gameTextBox, iconBox);
    return gameNameEditBox;
  }

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

    List<String> games = getNamesOfFilesToLoad();
    int rowIndex = 0;
    int columnIndex = 0;
    for (String game : games) {
      this.add(createGameBox(game), columnIndex, rowIndex);
      columnIndex++;
      if (columnIndex > 3) {
        columnIndex = 0;
        rowIndex++;
      }
    }
    return this;
  }
  private List<String> getNamesOfFilesToLoad() {
    File folder = new File("src/main/resources/frontend/images/GameLibrary");
    File[] listOfGameImages = folder.listFiles();

    List<String> fileNames = new ArrayList<>();
    for (int i = 0; i < listOfGameImages.length; i++) {
      if (listOfGameImages[i].isFile()) {
        fileNames.add(listOfGameImages[i].getName().substring(0, listOfGameImages[i].getName().indexOf('.')));
      }
    }
    fileNames.remove("UploadImage");
    return fileNames;
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
