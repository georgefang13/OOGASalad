# OOGASalad
Description:
* A Java program using OpenJFX (without FXML), that provides a No-Code authoring environment for game designers to build
  2D board games and card games using a interactive, visual tools that require minimal programming, such as:
  * choose and place game elements (i.e., what things will be in the game, their starting positions and values, etc)
  * setup graphical elements (i.e., background, images/colors/shapes used for game elements or board spaces, etc)
  * tweak settings (i.e., point values, game timing, projectile/obstacle/objects speed or number, etc)
  * set game rules (i.e., what actions are allowed, what happens when two objects intersect, etc)
  * set interaction types and reactions (i.e., what happens when a key is pressed or the mouse is moved, without hardcoding specific       keys, and what object is controlled)
  * set goals for the game (i.e., how to advance within the game, how to win or lose the game, etc)
  * determine the order of advancement (i.e., what level or stage follows the current one)
  * set instructions, splash screen, player setup, level bonuses, etc

## duVALLEY BOYS
Presentation Link: https://drive.google.com/file/d/1MiXRb9vTVpsO2pChT_FaPKULG8UZ8VQw/view?usp=sharing

## Names:

- Rodrigo Bassi Guerreiro
- Michael Bryant
- Joao Carvalho
- George Fang
- Ethan Horowitz
- Aryan Kothari
- Yegor Kursakov
- Owen MacKenzie
- Max Meister
- Connor Wells-Weiner
- Han Zhang

---

This project implements an authoring environment and player for multiple related games.

### Timeline

* Start Date: _March 26, 2023_

* Finish Date: _April 30, 2023_

* Hours Spent: 800

### Attributions

* Resources used for learning (including AI assistance)

* Resources used directly (including AI assistance)
    * https://firebase.google.com/docs/firestore/quickstart

### Running the Program

* Main class:
     * Run src/main/Main.java
     * In splash screen, select desired language and styling
     * If user already has a registered login, fill in that information in text box and press "Log In"
     * Otherwise, add desired credentials, press "Sign Up" (if no warning is shown, then registration was successful), and then press "Log In"

* Data files needed:
   * Games
      * "gamename"/
         * fsm.json
         * display.png
         * general.json
         * layout.json
         * object.json
         * rules.json
         * variables.json
         * frontend/
            * layout.json
            * objects.json
         * assets/*

* Interesting data files:
   * fsm.json
   * layout.json
   * objects.json

* Key/Mouse inputs:
   * Dragging and dropping game pieces with mouse
   * Key inputs for logging in and signing up (typing)
   * Key inputs for modals (typing)

### Notes/Assumptions

* Assumptions or Simplifications:

* Known Bugs:

* Features implemented:

* Features unimplemented:

* Noteworthy Features:
   * Login/sign up
   * Spanish/English
   * Dark/Light mode
   * Online multiplayer games
   * Sorting games by type by pressing the type on the left of the window in the game library

### Assignment Impressions


