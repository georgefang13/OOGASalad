package oogasalad.frontend.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import oogasalad.frontend.panels.libraryPanels.HeaderPanel;
import oogasalad.frontend.panels.libraryPanels.LibraryGridPanel;
import oogasalad.frontend.panels.libraryPanels.SortedGamesPanel;

public class GamePlayerLibraryScene extends AbstractScene {
  private BorderPane root;
  private LibraryGridPanel libraryGridPanel;
  private HeaderPanel gameLibraryHeader;
  private SortedGamesPanel sortedGamesPanel;


  public GamePlayerLibraryScene(SceneMediator sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    root = new BorderPane();
    root.setTop(gameLibraryHeader = new HeaderPanel(panelController, "library"));
    root.setLeft(sortedGamesPanel = new SortedGamesPanel(panelController));
    root.setCenter(libraryGridPanel = new LibraryGridPanel(panelController));
    setScene(new Scene(root));
    setText();
    setTheme();
    return getScene();
  }
  private void refreshScene() {
    setScene(new Scene(root));
    setText();
    setTheme();
  }

  @Override
  public void setText() {

  }
}
