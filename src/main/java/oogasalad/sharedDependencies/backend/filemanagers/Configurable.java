package oogasalad.sharedDependencies.backend.filemanagers;

/**
 * Interface that should be implemented by all objects in the program that can be loaded to / from
 * a configuration file
 * Internal implementation currently made for JSON (using Gson library)
 *
 * @author Rodrigo Bassi Guerreiro
 */
public interface Configurable {

  /**
   * Sets internal information of instance according to information in JSON object
   *
   * @param path String containing path to configuration file
   */
  void fromConfigFile(String path);

  /**
   * Saves internal information of instance into JSON object
   *
   * @return JsonObject (from Gson package) containing information
   */
  String getAsJson();
}
