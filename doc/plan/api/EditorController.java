public interface EditorController {

  /**
   * Creates a new File that will represent a game. createFile is called whenever a new game is
   * created.
   **/
  void createGame();

  /**
   * Loads from an existing game directory.
   *
   * @path The string that represents the files that must be read. This is going to be the path of
   * the dir that contains everything.
   **/
  void loadGame(String path);

  /**
   * Allows for an exisiting game to be updated. Rewrites the data inside the game to be what's
   * inside of data.
   *
   * @data is the data that gets passed to rewrite the file. Must be the entire new data of a file
   * @path is the path of the Game that is being re-written. This is going to be the path of the dir
   * that contains everything.
   **/
  void saveGame(String data, String path);

  /**
   * Allow for Themes of a View to change. Themes includes color layout
   *
   * @theme is the name of the file that needs modification
   * @window is the ModalView being changed
   **/
  void changeTheme(String theme, View window);

  /**
   * Allow for langauge of a View to change. Language dictates the language as seen by the user for
   * each element visually displayed that's part of the editor
   *
   * @language is the name of the file that contains the map between keywords and the language
   * @window is the ModalView being changed
   **/
  void changeLanguage(String language, View window);

  /**
   * Allow for layout of a View to change. Layout is how the View or window is arranged
   *
   * @layout the name of the properties file that containes the layout of the View
   * @window is the ModalView being changed
   **/
  void changeLayout(String layout, View window);

  /**
   * Allow for a new Window to be created
   *
   * @layout the name of the properties file that containes the layout of the View
   * @type Whether the Window is a game or editor screen
   * @game The folder name of the game to locate data
   * @Language The language the window will be opened in
   **/
  View createWindow(String layout, String type, String game, String language);

  /**
   * When exiting another window, if one Window deserves priority, such as the editor to a closed
   * game, this method can be called to bring a window to the front
   **/
  void backtoWindow();

  /**
   * Method is called in order to send information about a newly constructed   object that was made
   * in the front end sent to the backend. The controller sends to the backend for the backend to
   * input these into a file
   *
   * @Type The class the object belongs to
   * @Params The params of the object
   **/
  void sendObject(String type, String params)

  /**
   * Method is called to update information about a modified object in teh front end. The controller
   * sends updates to the Backend by giving the type and params for identification
   *
   * @type The class the object belongs to
   * @Params The updated params of the object
   **/
  void updateObjectProperties(String type, String params)

  /**
   * Method is called in order to send a request to the backend to delete an object.
   *
   * @Type The class the object belongs to
   * @Params The params of the object
   **/
  void deleteObject(String type, String params)

  /**
   * Method is called to send an error to the Modal
   *
   * @e The error thrown to send to Modal for display
   **/
  void sendError(Error e)
}