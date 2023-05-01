Write at least six Use Cases per team member that describe specific features each person expects to
complete — these will serve as the project's initial Backlog. Focus on features you plan to complete
during the first two Sprints (i.e., the project's highest priority features). Note, they can be
similar to those given in the assignment specification, but should include more detail specific to
your project's goals. It may help to draw a diagram of how your classes collaborate or even role
play the objects for each case.


---

opm7

Creating a new game:

* User clicks on “New Game” button in top right to create new blank game (not one of preset options)

* Button triggers controller event to create a new window for the game

* Brings up default window view with box “Create new game”

* User types game name (ex “tic-tac-toe”) in textbox and selects template option (ex. “mxn grid”)
  from template dropdown which also becomes tagname.

* String tic-tac-toe is saved as the game name / ID for reference later and to name data files

* Mxn grid tag adds it to mxn game lists where all mxn game datafiles can be loaded as a preset when
  selecting the game in previous window

* User is provided with a blank editor window with side panel options to add components to the game

Adding a mxn board to the game (example tic-tac-toe to illustrate):

* User clicks on side panel button “add board” to bring up a window where they select the board type

* Button click triggers controller event addboard which tells controller to load board options from
  backend

* Backend also lists the parameters necessary for each board to ask front end to retrieve

* give these options to frontend to present in the dropdown selection for board templates in pop up
  box (ex mxn, monopoly…)

* User selects mxn from drop down which causes an update to the window (or a new window view) that
  then asks for the height and width

* Selection triggers controller to look at the necessary parameters list that backend sent along
  with the board (height and width)

* Tells abstract front end class that takes a desired parameters list and creates a variable number
  of text boxes to display variable name and box for response

* User enters 3 and 3 into those boxes to create a tic tac toe board

* These strings are passed into controller which converts them to desired variable there or passes
  it as strings to backend

* Backend creates the board which is linked with a board ID so frontend and backend know which board
  they are talking about

* User clicks done to save and return to main screen

Adding players to the game (example tic-tac-toe to illustrate):

* User clicks on button to “add players”

* This brings up window with prompt for entering players info

* User prompted to enter name for players type (some games have players with different roles and
  abilities) - user enters tic tac toe player (doesn’t matter just for ID)

* User prompted to enter in the minimum number of players and maximum number of players

* This makes it possible that when running the game a variable number of players can play the gave
  provided within these bounds

* There will be logic ways to get the number of players created, and if the piece belongs to myself
  or another player

* User selects 2 min and 2 max

* Optional selection to choose colors for each player

* So that pieces can be colored later, to be associated with a player

* This option is provided when creating a piece

* User clicks done to save and return to main screen

Adding pieces to the game (example tic-tac-toe to illustrate):

* User clicks on add pieces

* Controller is triggered which pulls player info from backend

* This is given to frontend to create a window to enter in info

* User prompted to enter the category name of the piece for identification (this should not be
  specific to the picture of the piece but encapsulates all pieces with the same logic) ex. Boot =
  car in monopoly (“move tokens”), “knight” is different then “rook” in chess but can have different
  colors for white and black - user enters “tokens” or “X and O”

* User selects from dropdown what “players” type already selected the piece belongs to or if its
  shared and belongs to the game - user chooses “tic tac toe players”

* User clicks on upload button to upload image piece options that can be chosen by each player -
  they upload “X.png”. After upload this is listed below.

* User clicks on upload button to upload image piece options that can be chosen by each player -
  they upload “O.png”. After upload this is listed below.

* User clicks “add logic” to define movement of piece which brings up logic editor

Adding logic blocks to define piece path:

* In the logic editor screen for a specific piece


* User selects “define path” tab at the top to define the path
* This opens a new tab view which has the “start path” block at the top of the view
* User can drag in blocks such as forward, back, up, left, right, down, diagonal up right…
    * Once clicked the block image is copied and moved in parallel with the mouse
    * Once released the block image stops moving and appears on the screen
* These blocks appear on the screen when dragged in and connection arrows can be drawn by clicking
  bottom of block and clicking on the top of another block
* Each of these blocks are correlated by block name to a function in backend that creates a path
  object and are linked together in sequence based on the arrows to make a path

Changing tabs/windows and going back to piece logic path editor:

* User previously created a diagram of the path logic for a piece
    * The location of all the block objects is stored in the view scene
    * When changing scene, this old scene is saved in the frontend with an ID mapping linked list (
      piecename ID - path logic tab)
* User changed tabs to either add a new piece or different component and now wants to edit the same
  piece logic
    * It would be inefficient to redownload logic orientation from saved data file
* User clicks on the desired piece shown in the left panel by its name
    * This triggers an event to look in the scene linked list by the ID key
* This brings up the piece logic homescreen. The user selects the path logic tab.
* This displays the same scene shown before exactly the same way it was left.

Drag and drop or click and place:

Click logicblock or game piece or board piece… object you want to drag in

Button for object is selected - needs to be sent to controller

User moves mouse

User clicks or releases event detected and triggers controller function

FRONTEND detects event mouseClicked() ( or released() ) in the window clicked on and selected

* Mouseclick calls controller function to get mouse coordinates
    * Inputs = mouse coordinates in window, object type from previously selected / dragged
    * Controller calls on backend to create a new object of that type at that location specified by
      the coordinates
    * It also needs to initialize the imageview of that object centered at the coordinates given
    * There will be some translation to convert coordinates of image view and backend logic
    * The imageview will be added by the controller to the appropriate frontend panel

InitializeObjectOnClick(record double {mouseX,mouseY}, string objectName -> properties to get
object):


---

cjw84

Users Changes the Language and Theme of the Game Editor / Game Player:

* User accesses the settings dropdown in the menu bar in the game editor/player and selects User
  Preferences
    * Message sent to controller to open UserPreferenceModal
* Modal displays available language options (English, Spanish, etc.) and themes (Light, Dark) to
  user in two dropdowns
* User selects their desired language and/or theme
    * Message sent to controller to change theme and language of all views
* User confirms the new language and theme and exits the settings menu

User Adds Custom Card:

* User accesses the game editor and click on add Piece
    * Message sent to controller to open GameObjectModal and retrieves player info
* Modal displays and prompts user to enter the card name, card image, quantity, and ownership of
  player or game
    * Message sent to controller to save the custom cards to the deck and displays a confirmation
      message.
* User clicks add logic to define rules of card which displays the logic editor

User Changes State of Grid Cell

* User accesses the game editor adds grid board to environment area
* User clicks on square in grid
    * Message sent to controller to open GridSpaceModal
* Modal displays and prompts user to enter the state type (e.g. for monopoly a properties space,
  utility space, special space, and out-of-bounds space) and color of the cell

User Opens Game Editor or Game Player

* Controller opens the splash screen on start-up which prompts the user to edit game or play game
    * Message sent to controller to open GameEditorView or GamePlayerView with call to openView()

User Attempts to Import Incompatible Game File

* User access the game editor and clicks on import game
    * Message sent file controller to open file handler
* User uploads game file
    * Controller file handler checks if the game file is correct format; if not, sends error message
      to error controller
    * Error controller open ErrorModal
* User is prompted with error message and how to address error

User Finds Help

* User accesses the help dropdown in the menu bar in the game editor/player and selects
  Documentation
    * Message sent to controller to open DocumentationModal
* User is prompted with documentation about how to use the game editor and game player, including
  examples

---

Modify one of the properties attached to a GameObject

Modify


---

Jc870

Load an existing game file into the editor

* Home opens and the user selects the edit button on one of the existing games.
* Button click gets heard by the front-end and handled by the controller and uses the relevant data
  to create a new view that has a stage and scene.
* Controller accesses the data files associated with the selected game and passes them to the
  backend so that it can parse everything and then communicate back through the controller to
  initialize the frontend elements of the editor.

Set how the user defines the goals of the game (i.e. How to win)

* Home opens and the user selects the edit button on one of the existing games.
* Button click gets heard by the front-end and handled by the controller and uses the relevant data
  to create a new view that has a stage and scene.
* Controller accesses the data files associated with the selected game and passes them to the
  backend so that it can parse everything and then communicate back through the controller to
  initialize the frontend elements of the editor.
* User adds a player using the scene editor panel, a modal pops up requesting the parameters needed
  to create a new player. Once the player is created, the user can use them in the node editors.
* User clicks the add Goal button on the scene editor panel and then uses the node editor to set a
  condition for the goal and then sets what happens on the achievement of the goal.

User attempts to create an empty player

* Home opens and the user selects the edit button on one of the existing games.
    * {EditorFrontend.java : getActiveComponent()}
* Button click gets heard by the front-end and handled by the controller and uses the relevant data
  to create a new view that has a stage and scene.
    * EditorController.java : loadGame(String path)
    * EditorBackendAPI.java : void loadGame(String name);
* Controller accesses the data files associated with the selected game and passes them to the
  backend so that it can parse everything and then communicate back through the controller to
  initialize the frontend elements of the editor.
* User adds a player using the scene editor panel, a modal pops up requesting the parameters needed
  to create a new player. However, the user leaves all of the parameters blank and presses submit.
    * {EditorFrontend.java : getActiveComponent()}
    * void sendObject(String type, String params)
* An error modal pops up and tells the user why the initialization didn’t work
    * void sendError(Error e)

User defines turn logic

* Home opens and the user selects the edit button on one of the existing games.
* Button click gets heard by the front-end and handled by the controller and uses the relevant data
  to create a new view that has a stage and scene.
* Controller accesses the data files associated with the selected game and passes them to the
  backend so that it can parse everything and then communicate back through the controller to
  initialize the frontend elements of the editor.
* User adds a player using the scene editor panel, a modal pops up requesting the parameters needed
  to create a new player. Once the player is created, the user can use them in the node editors.
* User clicks the add TurnRule button on the scene editor panel and then uses the node editor to set
  the conditions that constitute a player’s turn. This includes any optional and required actions
  that the user must complete.

User sets the background image

* Home opens and the user selects the edit button on one of the existing games.
    * {EditorFrontend.java : getActiveComponent()}
* Button click gets heard by the front-end and handled by the controller and uses the relevant data
  to create a new view that has a stage and scene.
    * {EditorFrontend.java : getActiveComponent()}
    * EditorController.java : createGame()
    * EditorBackendAPI.java : void createGame(String name);
* Controller accesses the data files associated with the selected game and passes them to the
  backend so that it can parse everything and then communicate back through the controller to
  initialize the frontend elements of the editor.
    * EditorController : loadGame()
    * EditorController.java : createWindow(String layout, String type, String game, String language)
* Using the scene editor, the user can add a board/grid and then once it is made, they can select it
  and the properties panel will appear and they can change the properties of it and this includes
  the background image/color of the game and then it can be changed.
    * {EditorFrontend.java : getActiveComponent()}
    * EditorController.java : sendObject(String type, String params)
    * EditorBackendAPI.java : Ownable addOwnable(String name, Map&lt;String, String> parameters);
    * EditorBackendAPI.java : void editOwnable(String id, OwnableModifier modifier);

User changes the owner of a gameObject

* Home opens and the user selects the edit button on one of the existing games.
    * {EditorFrontend.java : getActiveComponent()}
* Button click gets heard by the front-end and handled by the controller and uses the relevant data
  to load the view that has a stage and scene.
    * {EditorFrontend.java : getActiveComponent()}
* Controller accesses the data files associated with the selected game and passes them to the
  backend so that it can parse everything and then communicate back through the controller to
  initialize the frontend elements of the editor.
    * EditorController.java : void loadGame(String path)
    * EditorBackendAPI.java : void loadGame(String name);
* User adds a player using the scene editor panel, a modal pops up requesting the parameters needed
  to create a new player. Once the player is created, the user can use them in the node editors.
    * {EditorFrontend.java : getActiveComponent()}
    * EditorController.java : void sendObject(String type, String params)
    * EditorBackendAPI.java : Ownable addOwnable(String name, Map&lt;String, String> parameters);
* Now that a player has been created, we can use the node editor of the player to create and assign
  it a gameObject.
    * {EditorFrontend.java : getActiveComponent()}
    * EditorController.java : void updateObjectProperties(String type, String params)
* If the user clicks on the gameObject, they can change the owner parameter of the gameObject.

---

gyf2

User wants to create a new game from scratch

* User starts the application
* User clicks in the top right on New Game
    * Controller handles click and populates modal
* Create game modal pops up
* User doesn’t select a template
* User clicks Create
    * Controller tells the backend and creates a data file associated with the new game in data
    * Controller also opens a new “view”
* Another “view” loads with the game editor and user is free to create games

User wants to create a new game from a game template

* User starts the application
* User clicks in the top right on New Game
    * Controller handles click and populates modal
* Create game modal pops up
* User selects the template of their choice
* User clicks Create
    * Controller tells the backend and creates a data file associated with the new game in data
    * Controller also opens a new “view”
* Another “view” loads with the game editor and template, and user is free to create games

User to edit a pre exhibiting game in the game library

* User starts the application loaded into the game library
* User finds the pre-existing game they want to edit and clicks in the bottom right on the pencil
  icon
    * Controller tells the backend and access the data file associated with the game in data and
      sends it to the editor
    * Controller also opens a new “view”
* Another “view” loads with the game editor and game fully loaded from JSON file ready to be edited

User wants to edit the size of their grid

* User starts in the editor
* User drags a grid element into the environment panel
* Once dropped, a model for initial properties pops up
    * Controller handles this creation of the pop-up on click
* User enters n and m number of rows and columns
* User presses done
    * Controller sends that information to the backend for the grid cells count to be updated in the
      JSON file
* User realizes they wanted the grid to be taller
    * User clicks the grid
    * User clicks transparent arrow on the top of the grid once to add one row to the top
* User repeats last step until satisfied

User wants to resize an object (namely the grid)

* User adds a grid to the environment
* User sets the n by m number of rows and columns
* User pressed done
* User realizes the cells are too small
    * User hovers their mouse over a corner of the grid
    * User clicks and holds while dragging to resize the grid
    * Controller sends that information to the backend for the grid size to be updated in the JSON
      file
* User repeats last step until satisfied

User wants to set the moveable direction from cell to cell between two cells

* User creates a grid object by dragging it in

* Controller sends the grid to the JSON file in the backend to be reflected

* User clicks on a cell

* Controller handles onHighlightedCell and shows arrows in the 8 directions to other cells
  unselected

* User selects an arrow

* Controller handles that click making the arrow now filled in or selected

* Controller sends that info to the backend that those two cells have traverasability

---

Hz244

User drags a game component out into the editing zone

* User selects a GameObject from the TabPanel by clicking

* User attempts to place the game component outside the bounds of the VisualPanel adds

* User releases mouse

* User is greeted with ErrorModal popup that tells the user “Please place the GameObject inside of
  the screen”

* User clicks on OK button in ErrorModal, and returns to editing screen

User wants to import a image

* User clicks on Files

* User is greeted by the MenuBar popup that lists all of the options. One of the options is “import
  image”

* User clicks on “Import Image”

* User is greeted with a FileChooser window to select a image to import

* User saves image to import

* Image is stored in internal directory inside Games to be used

User wants to use a shortcut to rotate a GameObject

* User clicks on given GameOBject that they want to rotate {EditorFrontend.java :
  getActiveComponent()}

* User clicks on Crtl + R {updateObjectProperties(String type, String params)}

* GameObject is rotated

* This update is sent to the backend through the controller using updateObjectProperties()

User wants to change the layout of the Editor

* User clicks on Settings

* User clicks on Change Layout

* User selects on one of the objects that pops up from a UserPreferenceModalchild

* The options is given to the Window controller, and based on the layout selected, a property files
  is pulled up and it initializes the current Window based on selected window

* User goes back to the new Window as they are satisfied

User wants to stop the Game from Running due to recursive creation of Game Objects

* User runs a Game from the Editor

* User accidently creates a Game Object recursively because the User is bad at game design

* User enters Crtl+C

* A request is sent back to GameEngine to kill all operations

* Window is closed

User wants to make a GameObject the child of another GameObject

* User clicks on to be Parent GameObject

* User clicks on the properties tab on the right side of the Screen. This represents all the
  properties of the currently selected GameObject

* User clicks on the Children Tab

* User clicks a + button

* User selects the option for existing Object

* User selects the intended child

---

ak616

User changes the winning goal for the game

* User lands on home page and then clicks the ‘edit’ button for a game

* Upon clicking the ‘edit’ button, the front-end informs the controller which transitions the stage
  and scene to the editor screen

* User navigates to Tools → Goals, at which point data is processed from the backend and through the
  model to populate the screen with the existing goal of the game.

* User edits the game goal using a TextEditor panel which updates the data file of the Game and
  saves it in the file

User changes the order of player turns

* User goes from homepage and then is navigated to the editor upon clicking the “edit” button for a
  game

* User clicks on edit → player, which opens up another screen in the editor that allows to change
  properties about the players

* The user can toggle player turn through a drop down menu option that allows them to change the
  position of the player on the “table”

User wants to delete a player from the game

* User goes from homepage and then is navigated to the editor upon clicking the “edit” button for a
  game

* EditorFrontend.java.getActiveComponent()

* EditorController.java : createGame()

* EditorController.java : createWindow(String layout, String type, String game, String language)

* EditorBackendAPI.java : void createGame(String name);

* In the editor screen, user navigates to the Side Panel that indicates number of defined players

* EditorFrontend.java : getActiveComponent()

* Panel.java : updatePanel()

* User clicks “–” to remove player from game

* This action informs the controller to delete player (and sub objects belonging to it) to get
  removed from the data file

* {EditorController.deleteObject(String type, String params)}

* {EditorBackend.removeOwnable(String id)}

User wants to add a description to the game

* User goes from homepage and then is navigated to the editor upon clicking the “edit” button for a
  game

* User navigates to the menu bar, where they click edit → game info

* This prompts a Modal to open, which is populated with the existing data (“game name”, description,
  type, etc.)

* User can edit the fields in the modal and then click save to update the data file for the game

User wants to increase the size of the grid for an existing game

* User goes from homepage and then is navigated to the editor upon clicking the “edit” button for a
  game

* User can click the arrows on all sides of the grid to increase or decrease the size accordingly

* This indicates to the Controller to change the values of the saved row and column size

* If there are existing GameObjects on the grids that get removed, they get deleted from the data
  file permanently

---

mem148

User adds rule

* Home opens and the user selects the edit button on one of the existing games.

* Button click gets heard by the front-end and handled by the controller and uses the relevant data
  to create a new view that has a stage and scene.

* Controller accesses the data files associated with the selected game and passes them to the
  backend so that it can parse everything and then communicate back through the controller to
  initialize the frontend elements of the editor.

* User adds a player using the scene editor panel, a modal pops up requesting the parameters needed
  to create a new player. Once the player is created, the user can use them in the node editors.

* User clicks the add rule button on the scene editor panel and then uses the node editor to set a
  condition of the rule

User adds deck of cards

* User accesses the game editor and click on add Piece

* Message sent to controller to open GameObjectModal and retrieves player info

* Modal displays and user clicks preset generic card deck

User adds gamevariable

* User clicks on add variables

* Controller is triggered which pulls player info from backend

* This is given to frontend to create a window to enter in info

* User prompted to enter the category name of the variable for identification

* User selects from dropdown what “players” type already selected the variable belongs to or if its
  shared and belongs to the game

* User clicks “add logic” to define which brings up logic editor for variable

User sets gameobjects in dropzone to start

* User clicks and drags gameobject into a highlighted dropzone on the grid

* User enables initial dropzone option in the GameObjectModal

User adds cheat codes

* An example of a cheat code is copying gameobjects:

* User clicks on gameobject and pressed ctrl + c

* User presses ctrl + v and it will paste a duplicate version of the gameobject with a new id and
  name

User saves game in editor

* User clicks save game in editor and a data file is generated for both frontend and backend in
  order for game to be run by game player

---

rb419

User changes the title of game

*

User changes the order of player turns

*

User wants to delete a player from the game

User wants to add a description to the game

*

User wants to increase the size of the grid

* User scrolls back to the top of Grid Panel

* User changes field that determines grid size from template to desired size

* User clicks button to generate grid from presets, overwriting the currently existing layout

User is done editing game and clicks the play button

* User clicks save button and all information is validated and saved into data files

* Controller closes the editor view and opens the main view, from which the user can select the
  option to run a game

* New window shows up and allows user to select game and proceed to play

---

ejh55

User adds a player-dependent type

* User clicks on the player properties tab

* User clicks on “Add Player-Dependent Type”

* Modal appears with text boxes for type, and then one text box per max number of players

* User presses submit button, that data is sent through the controller into the back end

* Player-dependent type is stored in the GlobalStore class

* Backend pushes changes back to controller

* Controller updates front end if necessary

User test-runs game in editor

* A new window opens

* The Game class is run inside that new window

User adds rule to apply to specific GameObject Class

* Rule logic is sent to controller along with name and box numbers

* Controller packages data for the addOwnable backend API, with the name “Rule” and an Map<String,
  String> of box names and specifiers to be parsed later by the backend

* Backend receives the rule and passes it to an OwnableFactory

* OwnableFactory generates a rule object

* Rule object is passed to the GlobalStore object

* GlobalStore object stores the Rule in a HashMap

* Controller is updated with information on the rule if necessary

User clicks on their bishop while playing chess

* Click event is sent to the controller, which sends the id of the clicked item to the back end
* GameObject links to rule to find available options to click on
* interpreter runs the rule and returns available spots
* the IDs of the available GameObjects to click on are sent to the controller
* controller communicates to the front end which visual elements to highlight
* view highlights the GameObject visuals that the user can click on

User maps a chat code to an action

* User clicks on rules tab and launches space to edit a rule for a specific GameObject ID or class
* User drags the onCheatCode block
* User drags other logic blocks to manipulate items on the board
* User presses “Save Rule”
* Front end passes blocks to the controller
* Controller packages data for the addOwnable backend API, with the name “Rule” and an Map<String,
  String> of box names and specifiers to be parsed later by the backend

* Backend receives the rule and passes it to an OwnableFactory

* OwnableFactory generates a rule object

* Rule object is passed to the GlobalStore object

* GlobalStore object stores the Rule in a HashMap

* Controller is updated with information on the rule if necessary

A player finishes their turn in game

* Backend detects that there are no more moves to do
* Backend loads any player-specific assets for the next player
* Backend communicates to the controller objects to remove
* Backend passes assets to the controller for the view to render
* Controller communicates with view to remove GameObjects specified that are to be removed
* Controller communicates with view to draw new objects to the screen based on what needs to be
  rendered
* View responds and deletes and adds objects as necessary

---

mcb115

User adds a Variable to a GameObject

* The backend IdManager assigns an ID to this Ownable, and it immediately gets its Owner based on
  how it was created
* This change is reflected on the frontend as the Variable is now visible and its owner can be seen

User adds another Player

* User selects the frontend option to add another Player.
* User then can add GameObjects and Variables to it and reference these from elsewhere
* User can be seen in frontend and backend and auto populates in turn order
* Can be removed at will

During gameplay, a win condition is reached

* The “cloud” being the goals observing on the game notice that the desired game state is reached
* The Game is noted as concluded and a message is shown on the frontend

A Variable is displayed on the screen

* The Variable listener is used to update this value when appropriate
* Variable is updated elsewhere, including the Rules and Goals

User creates GameObject class

* User can refer to this class in general in the Rules and Goals
* When creating a Player, user can add Ownables based on class
* All Ownables of the same class are treated the same

User creates custom GameObject

* User is prompted to provide name, decide if class should be created, and a custom image
* Later on, the user can add Variables associated with it

---

ek210

Implementing Logging

Add MainLogger class to handle logging throughout the project
* Add logging to different parts of the system, including frontend, backend, and controller.
* Use better differentiation among log levels: trace, debug, informational (progress), warning, and error messages.
* Use logger instances to ensure thread safety and prevent resource contention.
* Use log files to store log data, and rotate logs periodically to prevent data loss.
* Add contextual information to logs, such as class names and method names, to help with debugging.
* Use log messages to track user actions and system events, such as adding rules, saving games, and modifying player properties.
* Use log messages to track errors and exceptions, including stack traces and error codes, to aid in diagnosing and resolving issues.
* Use log messages to track performance metrics, such as processing times and memory usage, to optimize system performance.
* Use log analysis tools, such as ELK stack, to visualize and analyze log data, and identify patterns and trends.


