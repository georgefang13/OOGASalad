package oogasalad.gameeditor.backend.filemanagers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * @author Rodrigo Bassi Guerreiro
 *
 * Concrete implementation of FileManager for general game information
 * Creates files with content such as game name, author, and description
 */
public class GeneralFileManager extends FileManager {
  public GeneralFileManager() {
    super();
  }

  @Override
  public void addContent(String tag, String content) {
    super.addToContent(tag, new JsonPrimitive(content));
  }
}
