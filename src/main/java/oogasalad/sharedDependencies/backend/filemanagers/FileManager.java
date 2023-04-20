package oogasalad.sharedDependencies.backend.filemanagers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Rodrigo Bassi Guerreiro
 *
 * Class used to programatically store information in configuration files
 * Currently implemented to store information as JSON files (using the Gson library)
 **/
public class FileManager {
  protected static String SEPARATOR = ",";
  protected static String RESOURCES_PATH = "backend.filemanager.ValidTags";

  private JsonObject myFileInfo;
  private Collection<String> myValidTags;

  /**
   * Standard constructor
   */
  public FileManager() {
    myFileInfo = new JsonObject();
    myValidTags = new HashSet<>();
  }

  public FileManager(String validTagsKey) {
    this();
    setValidTagsFromResources(validTagsKey);
  }

  /**
   * Adds content to currently stored file structure in the specified hierarchical order
   *
   * @param object JsonObject to be modified
   * @param content String containing content to be added to file
   * @param tags arbitrary number of String specifying hierarchical sequence (from highest to lowest)
   */
  private void updateHierarchy(JsonObject object, String content, String... tags) {
    if (tags.length == 0) {
      throw new IllegalArgumentException();
    }
    if (! isValid(tags[0])) {
      // TODO: throw custom exception
    }

    if (object.has(tags[0])) {
      updateHierarchy(object.getAsJsonObject(tags[0]),
          content, Arrays.copyOfRange(tags, 1, tags.length));
    }
    else {
      object.add(tags[0], makeHierarchy(content, tags));
    }
  }

  /**
   * Makes JsonObject representing hierarchical structure
   * @param content text content to be added at end of hierarchy
   * @param tags arbitrary number of tags in order of hierarchy (from highest to lowest)
   * @return JsonObject representing hierarchical structure
   */
  private JsonObject makeHierarchy(String content, String... tags) {
    JsonObject object = new JsonObject();
    if (tags.length == 1) {
      object.add(tags[0], new JsonPrimitive(content));
    }
    else {
      object.add(tags[0], makeHierarchy(content, Arrays.copyOfRange(tags, 1, tags.length)));
    }
    return object;
  }

  /**
   * Add information that will be stored in file
   *
   * @param tag     key name in JSON file where data should go
   * @param content information to be stored in file
   */
  protected void addContent(String tag, JsonElement content) {
    if (!myValidTags.isEmpty() && !isValid(tag)) {
      // TODO: maybe make this into a custom exception
      throw new RuntimeException("Invalid tag!");
    }
    if (myFileInfo.has(tag)) {
      JsonArray array;
      if (myFileInfo.get(tag).isJsonArray()) {
        array = myFileInfo.getAsJsonArray(tag);
        array.add(content);
      } else {
        array = new JsonArray();
        array.add(myFileInfo.get(tag));
        array.add(content);
        myFileInfo.add(tag, array);
      }
    } else {
      myFileInfo.add(tag, content);
    }
  }

  ;

  /**
   * Saves currently stored JSON content into a file in the system
   *
   * @param path file and directory where JSON file should be stored
   */
  public void saveToFile(String path) {
    try (Writer writer = new FileWriter(path)) {
      writer.write(myFileInfo.toString());
    } catch (IOException e) {
      // TODO: maybe make this into a custom exception for Controller to handle
      throw new RuntimeException(e);
    }
  }

  /**
   * Define collection of acceptable tags for FileManager instance
   *
   * @param tags Collection of Strings containing all valid tags
   */
  protected void setValidTags(Collection<String> tags) {
    myValidTags = tags;
  }

  public void setValidTagsFromResources(String key) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PATH);
    String[] validTags = resources.getString(key).split(SEPARATOR);
    this.setValidTags(new HashSet<>(List.of(validTags)));
  }

  /**
   * Finds element in a Json object based on given key, checks whether it exists and is a String,
   * and returns its content
   *
   * @param object Json object to be searched into
   * @param key    identifier inside Json object
   * @return value associated with given key
   */
  public static String getStringByKey(JsonObject object, String key) {
    if (!object.get(key).isJsonPrimitive() || !object.get(key).getAsJsonPrimitive().isString()) {
      // TODO: throw custom exception
    }
    return object.get(key).getAsJsonPrimitive().toString();
  }

  /**
   * Directly access stored information in Json format
   *
   * @return JsonObject containing saved information
   */
  public JsonObject getJson() {
    return myFileInfo;
  }

  /**
   * Check whether tag is valid
   *
   * @param tag String containing tag to be checked
   * @return Returns true if tag is valid, else false
   */
  protected boolean isValid(String tag) {
    if (myValidTags == null || myValidTags.isEmpty()) {
      return true;
    }
    return myValidTags.contains(tag);
  }
}
