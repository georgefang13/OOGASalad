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
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.frontend.components.gridObjectComponent.GridObject;
import oogasalad.gameeditor.backend.GameInator;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

/**
 * @author Han This class is made to update any File information from the Front End to the backend
 */
public class FilesController {
  private final String GAMES_PATH = "data\\games\\";
  private final String gameFolder;
  private final String FILES_NAMES = "Controller/FilesConfig.properties";
  private Map<String, String> generalInfo = new HashMap<>();
  private List<Component> components = new ArrayList<>();
  private List<Component> componentsLater = new ArrayList<>();
  private GameInator game;
  /**
   * Sets up the FileController
   *
   * @param name        Game Name
   */
  public FilesController(String name) {
    gameFolder = GAMES_PATH + name;
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
    game = new GameInator(gameFolder);
    File directory = new File(gameFolder);
    boolean valid = directory.mkdir();
    File frontend = new File(gameFolder + "\\frontend");
    boolean valid1 = frontend.mkdir();

    ConvertingStrategy strategy = new ConvertingStrategy();
    FileManager layout = new FileManager();
    FileManager objects = new FileManager();
    FilesStrategy strat = new FilesStrategy(layout, objects);
    FileManager currentManager;
    int count = 0;
    for (Component comp : components){
      currentManager = strat.getFileLocation(comp);
      String className = comp.getClass().getSimpleName();
      Map<String, String> map = strategy.paramsToMap(comp);
      if(comp.getClass() == GridObject.class){
        List<Dropzone> newComponents = ((GridObject) comp).getDropzones();
        componentsLater.addAll(newComponents);
      }else{
        currentManager.addContent(map, "components", String.valueOf(count), "map");
        currentManager.addContent(className, "components", String.valueOf(count), "className");
        count++;
      }
    }

    for (Component comp : componentsLater){
      currentManager = strat.getFileLocation(comp);
      String className = comp.getClass().getSimpleName();
      Map<String, String> map = strategy.paramsToMap(comp);
      currentManager.addContent(map, "components", String.valueOf(count), "map");
      currentManager.addContent(className, "components", String.valueOf(count), "className");
      count++;
    }
    layout.saveToFile(gameFolder + "/frontend/layout.json");
    objects.saveToFile(gameFolder + "/frontend/objects.json");

    FileManager general = new FileManager();
    for (String tag: generalInfo.keySet()){
      if(tag.equals("tags")){
        String[] tags = generalInfo.get(tag).split(",");
        for(String individualTag: tags){
          general.addContent(individualTag, tag);
        }
      }
      else{
        general.addContent(generalInfo.get(tag), tag);
      }
    }
    general.saveToFile(gameFolder + "/general.json");
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

  /**
   * Allows for General Info to be set
   */
  public void setGeneral(Map<String, String> map){
    generalInfo = map;
  }
}
