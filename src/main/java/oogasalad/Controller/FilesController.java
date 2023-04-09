package oogasalad.Controller;

/**
 * @author Han
 * This class is made to update any File information from the Front End to the backend
 */
public class FilesController {
  public FilesController(){

  }

  /**
   Creates a new File that will represent a game. createFile
   is called whenever a new game is created.
   **/
  public void createGame(){

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
