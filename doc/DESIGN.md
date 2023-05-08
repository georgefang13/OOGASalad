# DESIGN Document for oogasalad
## NAMES
* Rodrigo Bassi Guerreiro (rb419)
* Michael Bryant (mcb115)
* Joao Carvalho (jc870)
* George Fang (gyf2)
* Ethan Horowitz (ejh55)
* Aryan Kothari (ak616)
* Yegor Kursakov (ek210)
* Owen MacKenzie (opm7)
* Max Meister (mem148)
* Connor Wells-Weiner (cjw84)
* Han Zhang (hz244)


## Role(s)

- Rodrigo Bassi Guerreiro
    - Authentication, File Managers
- Michael Bryant
    - Backend visual editor, ID Manager
- Joao Carvalho
    - Node Editor, Modals
- George Fang
    - User Interface, Design, Controllers
- Ethan Horowitz
    - Interpreter, FSM, Online Multiplayer
- Aryan Kothari
    - Visual Editor, Components
- Owen MacKenzie
    - Controllers, Scenes, Windows
- Max Meister
    - Back-end visual editor, API calls, Testing, Logger
- Connor Wells-Weiner
    - Node Editor, Controllers
- Han Zhang
    - Visual Editor, Component, Controllers
- Yegor Kursakov
    - Nothing


## Design Goals
* Encapsulated:
  * The internal logic of each component such as the game object library, environment panel, header menu, properties panel, and scene editor, should be encapsulated within View.java.
* Flexible:
  * The content of the game object library, environment panel, and properties panel can be flexible and dynamic, depending on user input and data sources.
* Features that should be able to be easily added:
  * New games (only through data files)
  * New languages (only through data files)
  * New Modals (Through Data Files and adding instance where you want it to be)
  * New Component Types (Extending Component and necessary converting Params)
  * New "Valley" language commands (by adding new Java classes extending OperatorToken)

## High-Level Design
### Frontend
* Editor Frontend
  * The individual screens that appear are called Windows. These Windows are the highest in the hierarchy, and each time 
  one is called, a new stage in JavaFX is created. The Windows are controlled by a WindowController. The WindowController dictates 
  what actions happen to a Window, such as closing, showing, and keeping track of all windows. The different types 
  of Windows are controlled by an Enum declared in WindowTypes, which dictates the different types of Windows that can appear. 
  Each Enum corresponds to a different window, and the concrete Windows are all created using a WindowFactory. 
  * The next level of design are Scenes. Scenes are contained within windows, and represent a new JavaFX scene. Each Scene
  is attached to a scene controller, which extends the SceneMediator interface. The SceneController servers a similar function to
  the Window Controller, by allowing for scenes to be changed. It also serves the function of passing data back and forth 
  along the controller chain of command. Each concrete Scene extends the AbstractScene, which provides basic functionality for 
  all scenes. 
  * Contained inside of Scenes are Panels. Panels are the containers within Scenes that contain components such as buttons,
  text, accordion panes, and other forms of content. Panels all are controlled via the PanelController, which is responsible
  for panel functionality, such as updating panels and creating new scenes and windows. All panels created will implement the Panel 
  interface, which gives each panel the basic functionality of the panels. Modal Panel is an interface for all panels that
  create modals from them, such as ComponentPanel and HeaderMenuPanel. To create some of their contents, panels utilize
  the ButtonFactory, which outputs standard or customized buttons for the Panel to contain. 
  * Panels can create Modals. Modals are interactive popup fields that can display information to the user and ask for inputs. 
  Modals extend the JavaFX Dialog popup, and all Modals extend the base class Modal. More specialized modals, such as AlertModal 
  and CreateNewModal, extend DisplayModal and InputModal respectively. To populate the content of the Modal, we utilize the Command 
  design pattern and provide a command, dictated by the ModalAction, to create a field. The fields all extend the base class
  Field, and through the use of reflection we can read which one of those fields should populate the modal. 
  * Nodes control the Logic Editor. Nodes are all controlled using the NodeController, which allows for saving and loading of nodes to occur. 
  Nodes. This is assisted with the JsonNodeParser, which takes those inputs from Json and inputs and outputs them. To create a Node
  a node, everything about a node can be represented using a Command, which implements the Command pattern, and the physical class 
  is a Java Record. There are different types of Nodes, with all extending AbstractNode. This node extends the DraggableNode interface, which allows for 
  Nodes to be moved around. The ControlNode interface allows for nesting to occur. For Nodes, they can be represented with a Record called NodeData. 
  * The final section of the frontend are Components. Components are created using Modals. Components are all visual elements that the user wants to add to a game.
  The base of every component is the Component interface, which is the base building block that allows for Components to interact with the user. 
  AbstractComponent builds off this interface, and every other Component extends AbstractComponent. The two fundamentally differnet 
  components are GameObjectComponent and DropzoneComponent. These two are the two building blocks for all games. To create Components, 
  we use a Component Factory, which can create components from a String input, Arraylist, or a Map input using reflection. To properly convert
  from string to other inputs, a Parameter Strategy is created, which uses a ParamFactory to create the necessary ConversionContext, that 
  executes the strategy. Dropzones implement a Subscriber Publisher design pattern to communicate when positions and connections hae been changed. 
* GameRunner Frontend
  * In the game library window, users enter this scene first seeing all games in the games directory
  under data. These games are created from a list of all games in that directory, and a card for each 
  game is created with the image sourcing from the directory, as well as the game name from general.json
  When the user clicks on any of the hyper links in the left panel such as grid game, card game, board game,
  etc. they are filtering the list of games that appear in the game grid to the right. This occurs by sending data 
  to the game grid panel (in the form of a string: ex) "card game", and then it calls refresh on that
  panel, so now all games are repopulated into the grid, but only games with the tag "card game", or
  what ever the specified string is, in the general.json tag: portion, are populated in the grid.
  * The game runner controller is the meat behind the game actually running. The game runner controller 
  is responsible for controlling the flow of the game. It handles the creation and initialization of 
  game elements, such as the game engine, player objects, and the game board. It also handles the 
  processing of user input and updating the game state accordingly. Additionally, it manages the 
  interactions between game objects and handles game events such as scoring, winning, or losing. 
  Overall, the game runner controller is the central hub for managing the game logic and mechanics.
  * The game runner objects extend the same component classes used for the game editor. Pieces and drop zones both extend the 
    same game object class and must implement methods to make them playable or unplayable and update their visuals.
    Setting playable and unplayable refers to making the objects highlighted or not and able to be dragged or have pieces dragged on top of them
  * The ability to make pieces highlighted or un-highlighted is made possible by the SelectableVisual interface, abstract class and extending classes.
    Each game object visual is designed to be able to switch between two nodes: one representing when the piece is active, and one when it is inactive.

### Backend
* Game editor
  * 
* Game runner
* Shared dependences

### Controller
* BackendObject Controller is the controller that allows for API calls to the backend to happen on gameObjects. 
The BackendObjectStrategy can help determine how the API should be called, depending on the class of the component. 
* FileController converts Components into the proper files by calling on the FileManager. In order ot properly save and load files, the fileStrategy is used
to determine the proper file location/instance of FileManager that should be called up. 
## Assumptions or Simplifications
* The game is a game that can be played on a table. As such, it doesn't contain an individual timeline or physics-like actions
* Each player has the same turn structure. Each turn will consist of the same turn actions in order 
for the finite state machine to work properly. 
* Each game when opened up is initialized the same, with the same starting configuration. 

## Changes from the Plan
* __Creation and organization of the nodes (dropzones) in the game:__
  * Originally, the nodes in the game would be represented by the BoardGraph class, which would be generated in the backend by the BoardCreator class
  * Ultimately, we decided against having a BoardGraph in order to make DropZones extend from the GameObject class, more easily inserting them into the Owner/Ownable structure
  * In this sense, the DropZone instances would now be able to keep track of their own connections, interacting more easily and directly with the other objects in the game
* __Configuration File Structure:__ 
  * The original idea was to have all the details necessary for loading in the game in the frontend and the backend to be the same
  * However, due to the information required by each end becoming increasingly more separated, we decided to parse the original file structure mainly in the backend, and add a new set of configuration files in a "frontend" directory for each game
  * Furthermore, the backend files were changed to be organized by IDs inside a JSON object instead of a JSON array, in order to better fit the organization introduced by the IdManager class

## How to Add New Features
* A new game:
  * To add a new game, you need to first import all the assets you need for your game. Theoretically
  you would then start in the visual editor and build the game visually, but for now you must type this
  by hand in object.json and layout.json. Then you can you the actual program to begin building out the
  logic of the game. Go to your game in the game library, and open the node editor. Start by making your first
  node and open tabs for init, onleave, onenter, and to. In these tabs, create the node chain for what happens
  in each of these steps. Repeat these steps for each stage of a turn. By doing this, you will have created
  what goes into the finite state machine which runs the game!
* Shared editor (unimplemented)
  * Like the online game play works with a replic server, shared editor could easily be achieved by entering
  a shared replic server on the game editor. This would allow people with the same room code to edit
  live together, and they could each independently save the file on their own devices.
* User password recovery (partially implemented - just not fully integrated)
  * On the frontend, either create new Modal structure (by updating the resources/frontend/properties/permanentText/Modals.properties file), or create new classes extending AbstractWindow and AbstractScene (implementing all the abstract methods) for both a "Register User" and a "Forgot Password" options
  * On the backend, the information from the input fields in these windows would then be sent to a new instance of UserManager, specifically by using the overloaded tryRegister() method containing a HashMap<String, String> as its third argument, as well as tryChangePassword() after prompting the user for their answers to the security questions
* See user's own games on game library (unimplemented)
  * On the backend, iterate over the "data/games" directory and compile a list of games whose author name matches the current username (could be done by making a new FileManager instance for each general.json file, and calling getString("author"))
