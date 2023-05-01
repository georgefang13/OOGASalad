package oogasalad.frontend.nodeEditor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joao Carvalho
 * @author Connor Wells-Weiner
 */
public class JsonNodeParser {

  public JsonNodeParser() {
  }

  /**
   * Returns a list of Command objects that represent the commands in the file
   * @param absoluteFilePath
   * @return
   */
  public List<Command> readCommands(String absoluteFilePath) {
    List<Command> commands = new ArrayList<>();
    String fileContent = "";
    try {
      fileContent = Files.readString(Paths.get(absoluteFilePath));
    } catch (IOException e) {
      e.printStackTrace();
    }
    JsonElement json = JsonParser.parseString(fileContent);
    JsonObject obj = json.getAsJsonObject();
    for (String key : obj.keySet()) {
      JsonObject value = (JsonObject) obj.get(key);
      String name = value.get("name").getAsString();
      String foler = value.get("folder").getAsString();
      JsonObject specs = value.get("specs").getAsJsonObject();
      String description = value.get("description").getAsString();
      JsonArray innerBlocksArray = specs.get("innerBlocks").getAsJsonArray();
      List<String> innerBlocks = new ArrayList<>();
      for (JsonElement element : innerBlocksArray) {
        innerBlocks.add(element.getAsString());
      }
      String parseStr = specs.get("parse").getAsString();
      JsonArray inputs = specs.get("inputs").getAsJsonArray();
      List<String> inputList = new ArrayList<>();
      for (JsonElement inp : inputs) {
        JsonObject inpObj = inp.getAsJsonObject();
        String inpName = inpObj.get("name").getAsString();
        inputList.add(inpName);
      }
      commands.add(new Command(name, foler, description, innerBlocks, parseStr, inputList));
    }
    return commands;
  }
}

