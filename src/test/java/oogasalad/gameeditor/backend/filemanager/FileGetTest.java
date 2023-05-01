package oogasalad.gameeditor.backend.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonSyntaxException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing FileManager functionalities of getting information from configuration files
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class FileGetTest {
  private static final String FILE_FOLDER = System.getProperty("user.dir") + "/data/testfiles";
  private static final String RESOURCES_PATH = "TestConfigFiles";

  FileManager fileManager;
  ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PATH);

  @BeforeEach
  void setup() {
    fileManager = new FileManager();
  }

  @Test
  void noHierarchyGetTest() throws FileNotFoundException {
    fileManager = new FileManager(getPath("SINGLE_TAG"));
    assertEquals(fileManager.getString("name"), "Rodrigo");
    assertEquals(fileManager.getArray("name"), new ArrayList<>(Arrays.asList("Rodrigo")));

    fileManager = new FileManager(getPath("DIFFERENT_TAG"));
    assertEquals(fileManager.getString("name"), "Rodrigo");
    assertEquals(fileManager.getString("nickname"), "Hot Rod");
  }

  @Test
  void hierarchyGetTest() throws FileNotFoundException {
    fileManager = new FileManager(getPath("DOUBLE_HIERARCHY"));
    assertEquals(fileManager.getString("person1", "name"), "Rodrigo");
    assertEquals(fileManager.getString("person2", "nickname"), "EeEEe");
  }

  @Test
  void arrayGetTest() throws FileNotFoundException {
    fileManager = new FileManager(getPath("SAME_TAG"));
    List<String> compare = Arrays.asList("Rodrigo", "Hot Rod");
    Iterator<String> iteratorFromFile = fileManager.getArray("name").iterator();
    Iterator<String> iteratorExpected = compare.iterator();

    while (iteratorFromFile.hasNext() && iteratorExpected.hasNext()) {
      assertEquals(iteratorFromFile.next(), iteratorExpected.next());
    }
  }

  @Test
  void tagsGetTest() throws FileNotFoundException {
    fileManager = new FileManager(getPath("MANY_TAGS"));
    List<String> tags = new ArrayList<>(Arrays.asList(
       "Rodrigo", "Max", "Ethan", "Owen", "George"
    ));
    List<String> values = new ArrayList<>(Arrays.asList(
        "Hot Rod", "The Meister", "Eh", "O-Show", "Curious"
    ));
    Iterator<String> iteratorExpected = tags.iterator();
    Iterator<String> iteratorFromFile = fileManager.getTagsAtLevel("names").iterator();
    while (iteratorFromFile.hasNext() && iteratorExpected.hasNext()) {
      assertEquals(iteratorFromFile.next(), iteratorExpected.next());
    }
    Iterator<String> iteratorTags = tags.iterator();
    Iterator<String> iteratorValues = values.iterator();
    while (iteratorExpected.hasNext()) {
      assertEquals(iteratorValues.next(), fileManager.getString("names", iteratorTags.next()));
    }
  }

  @Test
  void differentTypesGetTest() throws FileNotFoundException {
    fileManager = new FileManager(getPath("DIFFERENT_TYPES"));
    HashMap<String, Double> map = new HashMap<>();

    map.put("Rodrigo", 69.0);
    map.put("Ethan", 10.0);
    map.put("Dooval", 1000.0);
    HashMap<Integer, String> map2 = fileManager.getObject(HashMap.class,
        "OH HAROLD DO YOU WANT A WIFE THATS NOT A RAGING PUMPKIN");
    assertEquals(map, map2);
  }

  @Test
  void hasPathTest() throws FileNotFoundException {
    fileManager = new FileManager();
    assertFalse(fileManager.hasTag("rodrigo"));
    fileManager = new FileManager(getPath("DOUBLE_HIERARCHY"));
    assertTrue(fileManager.hasTag("person1"));
    assertTrue(fileManager.hasTag("name", "person1"));
  }

  @Test
  void sadTests() throws FileNotFoundException {
    fileManager = new FileManager(getPath("SAME_TAG"));
    assertThrows(IllegalArgumentException.class, () -> {
      fileManager.getString();
    });
    assertThrows(IllegalArgumentException.class, () -> {
      fileManager.getString("invalid key");
    });
    assertThrows(JsonSyntaxException.class, () -> {
      fileManager.getString("name");
    });
    assertThrows(FileNotFoundException.class, () -> {
      FileManager sadFileManager = new FileManager("bad file path");
    });
  }

  private String getPath(String key) {
    return FILE_FOLDER + "/" + resources.getString(key);
  }
}
