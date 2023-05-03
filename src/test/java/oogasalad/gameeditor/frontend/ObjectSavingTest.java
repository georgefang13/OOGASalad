package oogasalad.gameeditor.frontend;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import oogasalad.Controller.FilesController;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ObjectSavingTest extends DukeApplicationTest {

  @Override
  public void start(Stage stage){
    FilesController filesController = new FilesController("Files");
    Map<String, String> map = new HashMap<>();
    map.put("x", "50");
    map.put("y", "50");
    map.put("width", "50");
    map.put("height", "50");
    map.put("unselectedhasImage", "50");
    ComponentsFactory factory = new ComponentsFactory();
    Component c = factory.create("GameObject", map);
    filesController.addComponent(c);
    filesController.saveToFile();
  }
  @Test
  public void creatFile(){
    FilesController 
  }
}
