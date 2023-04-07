package oogasalad.gameeditor.backend.filemanagers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Rodrigo Bassi Guerreiro
 *
 * Abstract implementation for class used to save JSON files as user is creating games
 **/
public abstract class FileManager {
  JsonObject myFileInfo;
  JsonParser myParser;

  public FileManager() {
    myFileInfo = new JsonObject();
    myParser = new JsonParser();
  }

  /**
   * Add information that will be stored in file
   * @param tag key name in JSON file where data should go
   * @param content information to be stored in file
   */
  public abstract void addContent(String tag, String content);

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
   * Internally accessible modifier method that updates JSONObject
   * @param tag String specifying key inside JSON file where info should be added
   * @param content JsonElement containing information to be added to JSON file
   */
  protected void addToContent(String tag, JsonElement content) {
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
  }
}
