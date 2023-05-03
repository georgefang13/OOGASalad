package oogasalad.gameeditor.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.stage.Stage;
import oogasalad.Controller.ConvertingStrategy;
import oogasalad.Controller.FilesController;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.frontend.modals.ModalController;
import oogasalad.frontend.panels.ModalPanel;
import oogasalad.frontend.panels.editorPanels.ComponentPanel;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ObjectLoadingTest extends DukeApplicationTest {
  private static final String PATH = System.getProperty("user.dir") + "/data/games/Test/frontend/objects.json";
  private FilesController filesController;
  private FileManager fileManager;
  private ModalController modalController;
  @Override
  public void start(Stage stage){
    filesController = new FilesController();
    filesController.setGameName("checker");
    modalController = new ModalController(new ComponentPanel());
  }

  @Test
  public void makeComponent() throws FileNotFoundException {
    List<Component> comp = filesController.loadFromFile();
    ConvertingStrategy strat = new ConvertingStrategy();
    for(Component c: comp){
      modalController.createObjectInstance("GameObject", strat.paramsToMap(c));
    }
  }
}
