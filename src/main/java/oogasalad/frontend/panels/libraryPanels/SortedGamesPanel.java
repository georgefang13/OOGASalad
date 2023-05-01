package oogasalad.frontend.panels.libraryPanels;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;
import org.checkerframework.checker.units.qual.C;

public class SortedGamesPanel extends VBox implements Panel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final String ALL_GAMES = ELEMENT_LABELS.getString("AllGames");
  private static final String BOARD_GAMES = ELEMENT_LABELS.getString("BoardGames");
  private static final String CARD_GAMES = ELEMENT_LABELS.getString("CardGames");
  private static final String GRID_GAMES = ELEMENT_LABELS.getString("GridGames");
  private static final String USER_GAMES = ELEMENT_LABELS.getString("UserGames");
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String SORTED_GAMES_VBOX_ID = "SortedGamesVBoxID";
  private static final String SORTED_GAMES_TEXT_ID = "SortedGamesTextID";
  private static final String SORTED_GAMES_TEXT_ALL_ID = "SortedGamesTextAllID";
  PanelController panelController;


  /**
   * Constructor for HeaderMenu
   */
  public SortedGamesPanel(PanelController panelController) {
    super();
    this.makePanel();
    this.panelController = panelController;
  }

  /**
   * Calls createSortedGamesVBox to create the VBox for the sorted games. Sets up the panel to do
   * this
   *
   * @return
   */
  public Panel makePanel() {
    this.getChildren().addAll(createSortedGamesVBox());
    return this;
  }

  private VBox createSortedGamesVBox() {
    VBox sortedGamesVBox = new VBox();
    sortedGamesVBox.getStyleClass().add(ID_BUNDLE.getString(SORTED_GAMES_VBOX_ID));
    Hyperlink allGames = new Hyperlink(ALL_GAMES);
    allGames.setOnMouseClicked(e -> {
          panelController.getSceneController().getWindowController().passData("all");
          panelController.updatePanel("library");
        });
    allGames.getStyleClass().add(ID_BUNDLE.getString(SORTED_GAMES_TEXT_ALL_ID));
    Hyperlink boardGames = new Hyperlink(BOARD_GAMES);
    boardGames.getStyleClass().add(ID_BUNDLE.getString(SORTED_GAMES_TEXT_ID));
    boardGames.setOnMouseClicked(e -> {
      panelController.getSceneController().getWindowController().passData("board game");
      panelController.updatePanel("library");
    });
    Hyperlink cardGames = new Hyperlink(CARD_GAMES);
    cardGames.getStyleClass().add(ID_BUNDLE.getString(SORTED_GAMES_TEXT_ID));
    cardGames.setOnMouseClicked(e -> {
      panelController.getSceneController().getWindowController().passData("card game");
      panelController.updatePanel("library");
    });
    Hyperlink gridGames = new Hyperlink(GRID_GAMES);
    gridGames.getStyleClass().add(ID_BUNDLE.getString(SORTED_GAMES_TEXT_ID));
    gridGames.setOnMouseClicked(e -> {
      panelController.getSceneController().getWindowController().passData("grid game");
      panelController.updatePanel("library");
    });
    Hyperlink userGames = new Hyperlink(USER_GAMES);
    userGames.getStyleClass().add(ID_BUNDLE.getString(SORTED_GAMES_TEXT_ID));
    userGames.setOnMouseClicked(e -> {
      panelController.getSceneController().getWindowController().passData("user"); //TODO: MAKE IT THE USERNAME
      panelController.updatePanel("library");
    });
    sortedGamesVBox.getChildren().addAll(allGames, boardGames, cardGames, gridGames, userGames);
    return sortedGamesVBox;
  }

  public Node asNode() {
    return this;
  }

  @Override
  public void refreshPanel() {
  }

  @Override
  public String getTitle() {
    return null;
  }

  @Override
  public void save() {

  }
}
