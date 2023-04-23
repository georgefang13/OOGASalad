package oogasalad.sharedDependencies.backend.filemanagers;

import com.google.gson.JsonObject;

/**
 * Interface that should be implemented by all objects in the program that can be saved in the
 * format of a JSON file
 *
 * @author Rodrigo Bassi Guerreiro
 */
public interface JsonSaveable {

  /**
   * Sets internal information of instance according to information in JSON object
   *
   * @param element JsonObject (from Gson package) containing information
   */
  public void buildFromJson(JsonObject element) throws ClassNotFoundException;

  /**
   * Saves internal information of instance into JSON object
   *
   * @return JsonObject (from Gson package) containing information
   */
  public JsonObject getAsJson();
}
