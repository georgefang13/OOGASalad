package oogasalad.gameeditor.backend.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import oogasalad.gameeditor.backend.filemanagers.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilesTest {
  private static final String FILE_FOLDER = System.getProperty("user.dir") + "/data/testfiles";
  private static final String TEST_FILE_NAME = "test.json";
  private static final String EMPTY_FILE_NAME = "empty.json";
  FileManager fileManager;
  Gson gson;

  @BeforeEach
  void initialize() {
    fileManager = new FileManager();
    gson = new Gson();
  }

  @AfterEach
  void removeTestFile() {
    File file = new File(FILE_FOLDER + "/" + TEST_FILE_NAME);
    if (file.exists()) {
      boolean inferno = file.delete();
    }
  }

  @Test
  void emptyFileTest() throws FileNotFoundException {
    fileManager.saveToFile(FILE_FOLDER + "/" + TEST_FILE_NAME);
    JsonElement empty = getJsonFromFile(FILE_FOLDER + "/" + EMPTY_FILE_NAME);
    JsonElement test = getJsonFromFile(FILE_FOLDER + "/" + TEST_FILE_NAME);
    assertTrue(inDirectory(FILE_FOLDER, TEST_FILE_NAME));
    assertEquals(empty, test);
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
