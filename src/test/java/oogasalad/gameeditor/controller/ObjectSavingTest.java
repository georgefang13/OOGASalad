package oogasalad.gameeditor.controller;

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

import oogasalad.frontend.components.gameObjectComponent.GameObject;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class ObjectSavingTest extends DukeApplicationTest {
  private static final String PATH = System.getProperty("user.dir") + "/data/games/Test/frontend/objects.json";
  FilesController filesController;
  FileManager fileManager;

  @Override
  public void start(Stage stage){
    System.out.println(PATH);
    filesController = new FilesController();
    filesController.setGameName("Test");
    Component c = makeComponent();
    filesController.addComponent(c);
  }

  @Test
  public void createFile() {
    assertDoesNotThrow(() -> filesController.saveToFile());
    assertDoesNotThrow(() -> fileManager = new FileManager(PATH));
    assertEquals(50.0, fileManager.getObject(Double.class, "ID", "x"));
    assertEquals(50, fileManager.getObject(Integer.class, "ID", "width"));
    assertTrue(fileManager.getObject(Boolean.class, "ID", "unselected", "hasImage"));
  }

  private static Component makeComponent() {
    Map<String, String> map = new HashMap<>();
    map.put("x", "50");
    map.put("y", "50");
    map.put("width", "50");
    map.put("height", "50");
    map.put("unselectedHasImage", "true");
    map.put("image", "frontend/images/GameObjects/Default.png");
    ComponentsFactory factory = new ComponentsFactory();
    GameObject c = (GameObject) factory.create("GameObject", map);
    c.setID("ID");
    return c;
  }
}
