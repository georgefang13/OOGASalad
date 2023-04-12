package oogasalad.gameeditor.backend.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonPrimitive;
import java.io.File;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing FileManager and other JSON data file-related actions
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class FilesTest {
  private static final String FILE_FOLDER = System.getProperty("user.dir") + "/data/testfiles";
  private static final String TEST_FILE_NAME = "test.json";
  private static final String EMPTY_FILE_NAME = "empty.json";
  private static final String SINGLE_TAG_FILE_NAME = "single.json";
  private static final String DIFFERENT_TAG_FILE_NAME = "difftags.json";
  private static final String SAME_TAG_FILE_NAME = "sametag.json";
  FileManager fileManager;
  Gson gson;

  @BeforeEach
  void initialize() {
    fileManager = new FileManager();
    gson = new Gson();
    File file = new File(FILE_FOLDER + "/" + TEST_FILE_NAME);
    if (file.exists()) {
      file.delete();
    }
  }

  @Test
  void emptyFileTest() throws FileNotFoundException {
    saveAndCompare(EMPTY_FILE_NAME);
  }

  @Test
  void singleTagTest() {
    fileManager.addContent("name", new JsonPrimitive("Rodrigo"));
    saveAndCompare(SINGLE_TAG_FILE_NAME);
  }

  @Test
  void differentTagTest() {
    fileManager.addContent("name", new JsonPrimitive("Rodrigo"));
    fileManager.addContent("nickname", new JsonPrimitive("Hot Rod"));
    saveAndCompare(DIFFERENT_TAG_FILE_NAME);
  }

  @Test
  void sameTagTest() {
    fileManager.addContent("name", new JsonPrimitive("Rodrigo"));
    fileManager.addContent("name", new JsonPrimitive("Hot Rod"));
    saveAndCompare(SAME_TAG_FILE_NAME);
  }

  @Test
  void validTagTest() {
    fileManager.setValidTagsFromResources("General");
    fileManager.addContent("name", new JsonPrimitive("Rodrigo"));
    assertThrows(RuntimeException.class, () -> {
      fileManager.addContent("invalid tag", new JsonPrimitive("oops"));
    });
    fileManager.addContent("name", new JsonPrimitive("Hot Rod"));
    saveAndCompare(SAME_TAG_FILE_NAME);
  }

  private void saveAndCompare(String sameTagFileName) {
    fileManager.saveToFile(FILE_FOLDER + "/" + TEST_FILE_NAME);
    JsonElement singleTag = getJsonFromFile(FILE_FOLDER + "/" + sameTagFileName);
    JsonElement test = getJsonFromFile(FILE_FOLDER + "/" + TEST_FILE_NAME);
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
