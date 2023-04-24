package oogasalad.sharedDependencies.backend.filemanagers;

import java.io.FileNotFoundException;

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
  void fromConfigFile(String path) throws FileNotFoundException;

  /**
   * Saves internal information into configuration file at specified path
   *
   * @param path String containing path where configuration file should be saved
   */
  void toConfigFile(String path);
}
