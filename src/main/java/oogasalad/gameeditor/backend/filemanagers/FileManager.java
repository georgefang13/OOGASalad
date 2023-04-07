package oogasalad.gameeditor.backend.filemanagers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Rodrigo Bassi Guerreiro
 *
 * Abstract implementation for class used to save JSON files as user is creating games
 **/
public class FileManager {
  protected static String SEPARATOR = ",";
  protected static String RESOURCES_PATH = "backend/filemanager/ValidTags";

  private JsonObject myFileInfo;
  private JsonParser myParser;
  private Collection<String> myValidTags;

  public FileManager() {
    myFileInfo = new JsonObject();
    myParser = new JsonParser();
    myValidTags = new ArrayList<>();
  }

  /**
   * Add information that will be stored in file
   * @param tag key name in JSON file where data should go
   * @param content information to be stored in file
   */
  public void addContent(String tag, JsonElement content) {
    if (! myValidTags.isEmpty() && ! isValid(tag)) {
      // TODO: make custom exception for this
    }
    if (myFileInfo.has(tag)) {
      JsonArray array;
      if (myFileInfo.get(tag).isJsonArray()) {
        array = myFileInfo.getAsJsonArray(tag);
        array.add(content);
      }
      else {
        array = new JsonArray();
        array.add(content);
        myFileInfo.add(tag, new JsonArray());
      }
    }
    else {
      myFileInfo.add(tag, content);
    }
  };

  /**
   * Saves currently stored JSON content into a file in the system
   * @param path file and directory where JSON file should be stored
   */
  public void saveToFile(String path) {
    try {
      Writer writer = new FileWriter(path);
      writer.write(myFileInfo.toString());
    }
    catch (IOException e) {
      // TODO: maybe make this into a custom exception for Controller to handle
      throw new RuntimeException(e);
    }
  }

  /**
   * Define collection of acceptable tags for FileManager instance
   * @param tags Collection of Strings containing all valid tags
   */
  protected void setValidTags(Collection<String> tags) {
    myValidTags = tags;
  }

  protected void setValidTagsFromResources(String key) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PATH);
    String[] validTags = resources.getString(key).split(SEPARATOR);
    this.setValidTags(new HashSet<>(List.of(validTags)));
  }

  /**
   * Check whether tag is valid
   * @param tag String containing tag to be checked
   * @return Returns true if tag is valid, else false
   */
  protected boolean isValid(String tag) {
    return myValidTags.contains(tag);
  }
}
