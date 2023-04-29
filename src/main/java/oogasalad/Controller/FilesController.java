package oogasalad.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;

import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

/**
 * @author Han This class is made to update any File information from the Front End to the backend
 */
public class FilesController {
  private final String GAMES_PATH = "src/main/resources/";
  private final String gameFolder;
  private final String FILES_NAMES = "Controller/FilesConfig.properties";

  private List<Component> components = new ArrayList<>();

  /**
   * Sets up the FileController
   *
   * @param name        Game Name
   */
  public FilesController(String name) {
    gameFolder = GAMES_PATH + name;
    File directory = new File(gameFolder);
    boolean valid = directory.mkdir();
    if (valid) {
      //TODO log file made successfully
    } else {
      //TODO log file not made successfully
    }
  }

  /**
   * Add a component to be saves
   * @param comp the component to be saved
   */
  public void addComponent(Component comp){
    components.add(comp);
  }

  /**
   * Remove a component
   * @param comp the component to be removed
   */
  public void removeComponent(Component comp){
    components.remove(comp);
  }

  /**
   * Saves components to frontend file
   */
  public void saveToFile(){
    ConvertingStrategy strategy = new ConvertingStrategy();
    FileManager manager = new FileManager();

    int count = 0;
    for (Component comp : components){
      String className = comp.getClass().getSimpleName();
      Map<String, String> map = strategy.paramsToMap(comp);
      manager.addContent(map, "components", String.valueOf(count), "map");
      manager.addContent(className, "components", String.valueOf(count), "className");
      count++;
    }

    manager.saveToFile(gameFolder + "/frontend/components.json");
  }

  /**
   * Loads components from frontend file
   * @return a list of components
   * @throws FileNotFoundException if the file is not found
   */
  public List<Component> loadFromFile() throws FileNotFoundException {
    FileManager fm = new FileManager(gameFolder + "/frontend/components.json");
    ComponentsFactory factory = new ComponentsFactory();
    List<Component> comps = new ArrayList<>();
    fm.getTagsAtLevel("components").forEach(key -> {
      String classType = fm.getString(key, "className");
      HashMap<String, String> map = fm.getObject(HashMap.class, key, "map");
      comps.add(factory.create(classType, map));
    });

    return comps;
  }
}
