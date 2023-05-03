package oogasalad.gameeditor.frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.stage.Stage;
import oogasalad.Controller.FilesController;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ObjectSavingTest extends DukeApplicationTest {
  private static final String PATH = System.getProperty("user.dir") + "/data/games/Files/frontend/objects.json";
  FilesController filesController;
  FileManager fileManager;

  @BeforeEach
  public void start(Stage stage){
    System.out.println(PATH);
    filesController = new FilesController("Files");
    Component c = makeComponent();
    filesController.addComponent(c);
  }

  @Test
  public void createFile() {
    assertDoesNotThrow(() -> filesController.saveToFile());
    assertDoesNotThrow(() -> fileManager = new FileManager(PATH));
    assertEquals(50, Integer.valueOf(fileManager.getString("TestObject", "x")));
    assertEquals(50, Integer.valueOf(fileManager.getString("TestObject", "width")));
    assertTrue(fileManager.getObject(Boolean.class, "TestObject", "unselected", "hasImage"));
  }

  private static Component makeComponent() {
    Map<String, String> map = new HashMap<>();
    map.put("ID", "TestObject");
    map.put("x", "50");
    map.put("y", "50");
    map.put("width", "50");
    map.put("height", "50");
    map.put("unselectedhasImage", "50");
    ComponentsFactory factory = new ComponentsFactory();
    Component c = factory.create("GameObject", map);
    return c;
  }
}
