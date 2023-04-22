package oogasalad.gameeditor.backend.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ResourceBundle;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing FileManager functionalities of adding information to configuration files
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class FileAddTest {

  private static final String FILE_FOLDER = System.getProperty("user.dir") + "/data/testfiles";
  private static final String RESOURCES_PATH = "TestConfigFiles";
  FileManager fileManager;
  Gson gson;
  ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PATH);

  @BeforeEach
  void initialize() {
    fileManager = new FileManager();
    gson = new Gson();
    File file = new File(FILE_FOLDER + "/" + resources.getString("TEST"));
    if (file.exists()) {
      file.delete();
    }
  }

  @Test
  void emptyFileTest() throws FileNotFoundException {
    saveAndCompare("EMPTY");
  }

  @Test
  void singleTagTest() {
    fileManager.addContent("Rodrigo", "name");
    saveAndCompare("SINGLE_TAG");
  }

  @Test
  void differentTagTest() {
    fileManager.addContent("Rodrigo", "name");
    fileManager.addContent("Hot Rod", "nickname");
    saveAndCompare("DIFFERENT_TAG");
  }

  @Test
  void sameTagTest() {
    fileManager.addContent("Rodrigo", "name");
    fileManager.addContent("Hot Rod", "name");
    saveAndCompare("SAME_TAG");
  }

  @Test
  void validTagTest() {
    fileManager.setValidTagsFromResources("General");
    fileManager.addContent("Rodrigo", "name");
    assertThrows(RuntimeException.class, () -> {
      fileManager.addContent( "oops", "invalid tag");
    });
    fileManager.addContent( "Hot Rod", "name");
    saveAndCompare("SAME_TAG");
  }

  @Test
  void hierarchyTest() {
    fileManager.addContent("Rodrigo", "person1", "name");
    fileManager.addContent("Hot Rod", "person1", "nickname");
    saveAndCompare("SINGLE_HIERARCHY");

    fileManager.addContent("Ethan", "person2", "name");
    fileManager.addContent("EeEEe", "person2", "nickname");
    saveAndCompare("DOUBLE_HIERARCHY");
  }

  @Test
  void hierarchyListTest() {
    fileManager.addContent("Rodrigo", "person1", "name");
    fileManager.addContent("Hot Rod", "person1", "name");
    saveAndCompare("HIERARCHY_LIST");
  }

  private void saveAndCompare(String key) {
    fileManager.saveToFile(FILE_FOLDER + "/" + resources.getString("TEST"));
    JsonElement singleTag = getJsonFromFile(FILE_FOLDER + "/" + resources.getString(key));
    JsonElement test = getJsonFromFile(FILE_FOLDER + "/" + resources.getString("TEST"));
    assertEquals(singleTag, test);
  }

  private JsonElement getJsonFromFile(String path) {
    try {
      return gson.fromJson(
          new FileReader(path), JsonElement.class);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean inDirectory(String directoryPath, String fileName) {
    File directory = new File(directoryPath);
    File[] files = directory.listFiles();
    if (files == null) {
      return false;
    }
    for (File file : files) {
      if (file.isFile() && file.getName().equals(fileName)) {
        return true;
      }
    }
    return false;
  }
}
