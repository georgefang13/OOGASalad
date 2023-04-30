package oogasalad.frontend.panels.libraryPanels;

import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import oogasalad.frontend.panels.Panel;
import oogasalad.frontend.panels.PanelController;

public class SortedGamesPanel extends VBox implements Panel {

  private static final ResourceBundle ELEMENT_LABELS = ResourceBundle.getBundle(
      "frontend/properties/text/english");
  private static final ResourceBundle ID_BUNDLE = ResourceBundle.getBundle(
      "frontend/properties/StylingIDs/CSS_ID");
  private static final String SORTED_GAMES_VBOX_ID = "SortedGamesVBoxID";

  /**
   * Constructor for HeaderMenu
   */
  public SortedGamesPanel() {
    super();
    this.makePanel();
  }

  /**
   * Calls createSortedGamesVBox to create the VBox for the sorted games. Sets up the panel to do this
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
    Hyperlink allGames = new Hyperlink("Game 1");
    Hyperlink boardGames = new Hyperlink("Game 2");
    Hyperlink cardGames = new Hyperlink("Game 3");
    Hyperlink gridGames = new Hyperlink("Game 4");
    Hyperlink userGames = new Hyperlink("Game 5");
    sortedGamesVBox.getChildren().addAll(allGames, boardGames, cardGames, gridGames, userGames);
    return sortedGamesVBox;
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
