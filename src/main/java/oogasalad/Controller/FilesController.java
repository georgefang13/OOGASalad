package oogasalad.Controller;

import java.io.File;
import java.util.ResourceBundle;
import oogasalad.gameeditor.backend.filemanagers.FileManager;

/**
 * @author Han
 * This class is made to update any File information from the Front End to the backend
 */
public class FilesController {

  private FileManager manager;
  private final String GAMES_PATH = "src/main/resources/";
  private String gameFolder;
  private final String FILES_NAMES = "Controller/FilesConfig.properties";
  private final ResourceBundle filesBundle = ResourceBundle.getBundle(FILES_NAMES);
  /**
   * Sets up the FileController
   * @param fileManager This is the fileManager that controls this fileCOntroller, actually doing the work
   * to work on everything
   * @param name Game Name
   */
  public FilesController(FileManager fileManager, String name){
    manager = fileManager;
    gameFolder = GAMES_PATH + name;
    File directory = new File(gameFolder);
    boolean valid = directory.mkdir();
    if(valid){
      //TODO log file made successfully
    }
    else{
      //TODO log file not made successfully
    }
  }

  /**
   Creates a new File that will represent a game. createFile
   is called whenever a new game is created.
   **/
  public void createGame(){
    String[] fileNames = filesBundle.getStringArray("FileNames");
    for(String file: fileNames){
      manager.saveToFile(gameFolder+"\n"+file);
    }
  }

  /**
   Loads from an existing game directory.
   @param path The string that represents the files that must be read. This is
   going to be the path of the dir that contains everything.
   **/
  public void loadGame(String path){

  }

  /**
   Allows for an exisiting game to be updated. Rewrites the data inside
   the game to be what's inside of data.
   @param data is the data that gets passed to rewrite the file. Must be the entire new data of a file
   @param path is the path of the Game that is being re-written. This is
   going to be the path of the dir that contains everything.
   **/
  public void saveGame(String data, String path){

  }
}
