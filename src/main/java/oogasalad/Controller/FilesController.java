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
  private final String SEPARATOR = ",";
  private final String GAMES_PATH = "data\\games\\";
  private String gameFolder;
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
  public FilesController() {
  }

  /**
   * sets the game name for the backend to make files
   * @param name
   */
  public void setGameName(String name){
    gameFolder = GAMES_PATH + name;
//    game = new GameInator(name);
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
//    File directory = new File(gameFolder);
//    boolean valid = directory.mkdir();
//    File frontend = new File(gameFolder + "\\frontend");
//    boolean valid1 = frontend.mkdir();

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
        String id = map.get("ID");
        for(String key : map.keySet()){
          String remove = "unselected,hasImage";

          currentManager.addContent(map.get(key), makeTagsArray(id, key));
        }
        currentManager.addContent(map, "components", String.valueOf(count), "map");
//        currentManager.addContent(className, "components", String.valueOf(count), "className");
//        count++;
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

  public GameInator getGame() {
    return game;
  }

  private String[] makeTagsArray(String id, String tags) {
    String[] inputTags = tags.split(SEPARATOR);
    String[] allTags = new String[inputTags.length + 1];
    allTags[0] = id;
    System.arraycopy(inputTags, 0, allTags, 1, inputTags.length);
    return allTags;
  }
}
