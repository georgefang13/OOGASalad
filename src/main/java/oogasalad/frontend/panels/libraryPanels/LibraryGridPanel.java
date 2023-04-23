package oogasalad.frontend.panels.libraryPanels;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import oogasalad.frontend.nodeEditor.GraphEditorTutorial;
import oogasalad.frontend.panels.Panel;

public class LibraryGridPanel extends GridPane implements Panel {
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String GAME_BOX_ID = "GameBoxID";
  private final List<String> libraryGames = new ArrayList<>(
      Arrays.asList("Tic-Tak-Toe", "Fortnite", "Backgammon", "Chess", "Checkers")); //TODO: TEMPORARY

  /**
   * Constructor for the environment panel
   */
  public LibraryGridPanel() {
    super();
    this.makePanel();
    this.setPadding(new Insets(10));
    this.setHgap(10);
    this.setVgap(10);

    // set column constraints to evenly distribute the available width
    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(25);
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(25);
    ColumnConstraints column3 = new ColumnConstraints();
    column3.setPercentWidth(25);
    ColumnConstraints column4 = new ColumnConstraints();
    column4.setPercentWidth(25);
    this.getColumnConstraints().addAll(column1, column2, column3, column4);

    // populate the grid with labels
//    String[] items = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};
//    int numRows = (int) Math.ceil((double) items.length / 4);
//    int rowIndex = 0;
//    int columnIndex = 0;
//    for (String item : items) {
//      Label label = new Label(item);
//      GridPane.setHgrow(label, Priority.ALWAYS);
//      GridPane.setVgrow(label, Priority.ALWAYS);
//      this.add(label, columnIndex, rowIndex);
//      columnIndex++;
//      if (columnIndex > 3) {
//        columnIndex = 0;
//        rowIndex++;
//      }
//    }
    this.add(createGameBox("UploadImage"), 0, 0);
    this.add(createGameBox("UploadImage"), 1, 0);

  }

  public Panel makePanel() {
    //TODO: make this a grid of buttons
    return this;
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
  public VBox createGameBox(String gameName) {
    VBox gameBox = new VBox();
    ImageView gameImage = new ImageView("file:src/main/resources/frontend/images/GameLibrary/" + gameName + ".png"); //make this a vairable and have the game name find the image name from a helper function
    gameBox.getChildren().add(gameImage);
    gameBox.getStyleClass().add(ID_BUNDLE.getString(GAME_BOX_ID));


    return gameBox;
  }
}
