<!-----

Yay, no errors, warnings, or alerts!

Conversion time: 0.526 seconds.


Using this Markdown file:

1. Paste this output into your source file.
2. See the notes and action items below regarding this conversion run.
3. Check the rendered output (headings, lists, code blocks, tables) for proper
   formatting and use a linkchecker before you publish this page.

Conversion notes:

* Docs to Markdown version 1.0β34
* Fri Mar 31 2023 23:52:28 GMT-0700 (PDT)
* Source doc: Test Plan
----->


Description of planning strategies + examples

**2 strategies:**

_Describe at least two specific strategies your team discussed to make your APIs more easily
testable (such as useful parameters and return values, using smaller classes or methods, throwing
Exceptions, etc.)._

One specific strategy we used to make classes more testable is splitting the Controller into smaller
classes. The classes of Controller are split into FilesControllers, WindowsControllers,
GameItemsController, ErrorController. By splitting Controller into multiple parts, it’s easier to
isolate specific methods and integrations tests that we need to conduct. For example, if we had to
tests errors being sent back between the frontend and backend, we just have to include the
ErrorController class, instead of having to add a larger controller. This allows for earlier testing
and more modular testing, both which result in faster debugging.

Another specific strategy we can use, especially for the backend, is to make mock classes that
extend the standard classes in the backend API which make the internal details of the class more
easily accessible. As such, it would be much easier to test the implementation of classes with
several dependencies, given that these could be extracted and compared more efficiently.
Furthermore, it would be useful to override the equals() method in these backend classes, internally
specifying what needs to be compared in order to ensure that all the states and properties of a
certain instance have the expected values.

**3 test scenarios per team member:**

Describe at least three test scenarios per team member for a project feature they plan to be
responsible for (at least one of which is negative/sad/error producing), the expected outcome, and
how your design supports testing for it (such as methods you can call, specific values you can use
as parameters and return values to check, specific expected UI displays or changes that could be
checked, etc.). A test scenario describes the expected results from a user's action to initiate a
feature in your program (whether "happy" or "sad") and the steps that lead to that result.


---

cjw84

* Test Scenario: Test User Add Game Objects
    * By creating a GameObjectModal, we can easily test the ability of the user to add game objects
      including pieces and boards.
* Test Scenario: Test User Upload Incompatible Game File
    * By creating a file handler controller and error modal we can test by intentionally uploading a
      incompatible game file and if the appropriate error message is displayed in the error modal
* Test Scenario: Test User Upload Compatible Game File
    * By creating a file handler controller we can test by intentionally uploading a compatible game
      file and if the game is loaded into the game player with the appropriate game objects in the
      appropriate locations\

---

opm7

* Test Scenario: Test User Adds Players to the game
    * By creating a PlayerList object that holds all the player instances we can compare the length
      of this object to make sure that players were in fact added and are within the min and max
      range
* Test Scenario: Test User Uploads Too Few Players
    * As above the PlayerList object will hold all the player instances that are created. Then when
      the user clicks continue the controller should communicate with the backend to error check
      that the length of this PlayerList is within the viable range. This should return below
      minimum so raise an error window “too few players added”. This will be an object that can be
      tested for in testing.
* Test Scenario: Test user removes a player's class in editor but that player type was assigned game
  objects.
    * Removing a player should use the variable in the players interface “owned pieces” to check if
      that player interface has any game objects assigned. All game objects including pieces are
      very player specific so these pieces will become irrelevant. This should lead to an error
      message prompt that can be tested for in testing.

---

Jc870

* Test Scenario: Test the User creates a new game
    * When the user attempts to create a game, we can check the /Games directory to check if a game
      of the same name with empty data files has been created.
* Test Scenario: Test Changing player properties
    * When the user attempts to change a player’s properties using the properties panel, the test
      should change it, then save the file with the changes, and use the parser to read that file
      and see if the changes were applied
* Test Scenario: User clicks to create a new game but closes the modal without submitting
    * When the user attempts to create a game, we can check the /Games directory to check if a game
      of the same name with empty data files has been created.
        * Since it wasn’t submitted, the file shouldn’t exist.

---

Gyf2

* Test Scenario: Test the user connection between two cells was created
    * When the user clicks an arrow connecting two cells traversability, we can check the backend
      JSON file for the name of the connection. By default we check the name is “Cell(#)Cell(#)” for
      example “Cell1Cell2” or if the user names it “forward’ make sure that's reflected in the file
* Test Scenario: Front end test that on click the properties file opens for the right object
    * When the user selects an object and clicks the properties tab on the right, the tab should
      open and the title of it should be the object. On default check that the properties allows the
      user to edit the environment.
* Test Scenario: User increase the number of cells in a grid by clicking the arrows when grid
  selected
    * When the user clicks the arrow to increase number of cells by one, check the grid in the
      backend file has reflected this change.
        * Get the value from the file first, then have mouse click up for +1 row, then check to make
          sure the number in the file for rows has increased by 1

---

mem148

* Test Scenario: Test User save game
    * By saving a game, we can test the backend and frontend files to ensure that they have stored
      the correct information
* Test Scenario: Test User add rule
    * When creating a rule, we can test the ability of the user to add rules by checking to see if
      conditions stay true
* Test Scenario: Test User add goal
    * By creating a goal, we can easily test the ability of the user to add goals including action
      taken by goal

---

ak616

* Test Scenario: Test User imports a game
    * By comparing the imported file to a default file, we can see if the file is being correctly
      translated to Objects in the game.
* Test Scenario: Test User updates size of the grid
    * Updating the size of the grid can be tested by comparing _m _and _n _values saved to the data
      file and compare it to the value it should actually be
* Test Scenario: Test User changes theme of the window
    * Changing the theme of the window, from light to dark mode for instance, can be tested by
      comparing a default css file path to the one being actively used by the default program

---

mcb115

* Test Scenario: User adds Variable to GameObject
    * When this happens, a Variable should be added to the GameObject object and a dropdown should
      appear under the GameObject representing this Variable
* Test Scenario: User renames the name (ID) of an Ownable to an ID already in use
    * The backend rejects this change and pushes an exception to the frontend where a warning shows
* Test Scenario: User removes a Player
    * The player and all its corresponding Ownables are then destroyed and cannot be referenced or
      seen on the frontend or backend

---

Hz244

* Test Scenario: User wants to use a shortcut to rotate a GameObject
    * After simulating the pressing of the two keys using TestFx, we can check in the backend if the
      param of the GameObject has set a rotation
* Test Scenario: User drags a game component out into the editing zone
    * When the error pops up, first check for the pane to exist, and then check that message about
      error message, and then check if you can exist.
* Test Scenario: User wants to import a image
    * Once the image importing is simulating through TestFx, we can check by going through the
      folders of the project and see if the image exists in the right folder. 