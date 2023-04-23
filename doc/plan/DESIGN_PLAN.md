# DESIGN PLAN Document for oogasalad_team2 (duVALLEY Boiz)

## Rodrigo Bassi Guerreiro (rb419), Michael Bryant (mcb115), Joao Mocelin Carvalho (jc870), George Fang (gyf2), Ethan Horowitz (ejh55), Aryan Kothari (ak616), Yegor Kursakov (ek210), Owen MacKenzie (opm7), Max Meister (mem148), Connor Wells-Weiner (cjw84), Han Zhang (hz244)

## Introduction

* Design Goals
    * Encapsulated:
        * The internal logic of each component such as the game object library, environment panel,
          header menu, properties panel, and scene editor, should be encapsulated within View.java.
    * Flexible:
        * The content of the game object library, environment panel, and properties panel can be
          flexible and dynamic, depending on user input and data sources.

## Configuration File Format

The data files used to generate and run different games will be JSON files, separated into
categories based on how they are created in the frontend, and the classes that use this data in the
backend

### General information

```json
{
  “name”: name of game
“author”: author of game
“description”: general description for game
“players”: {
“min”: minimum number of players
“max”: maximum number of players
}
“board-image”: path to image that’s fixed in game background
}
```

### Layout of nodes

```json
{
  “nodes”: (array of objects representing dropzones) [
{
“id”: ID of dropzone
“position”: {
“x”: “y”: }
“connections”: [
{
“edgeId”: ID of connection
“nodeId”: ID of connected dropzone
}, …
]
}, …
]
}
``` 

### Game Objects

```json
{
  “ownedByGame”: [
{
“id”: ID of object
“image”: path of image
“initialDropbox”: ID of dropbox in which piece should be placed (OPTIONAL - if not present, object is initialized as inactive)
}, …
]
“ownedByPlayer”: same array structure as described above
}
```

### Game Variables

```json
{
  “ownedByGame”: [
{
“id”: ID of variable
“default”: default value
}, …
]
“ownedByPlayer”: same array structure as described above
}
```

### Rules

```json
{
[
  “id”: ID of rule
“type”: type of rule (mapping to class that can be accessed by Interpreter)
“arguments”: [] (list of arguments)
]
}
```

## Design Overview

![Backend Class Interactions](\doc\plan\dependencies\backend_classes.png)
![Frontend Class Interactions](\doc\plan\dependencies\frontend_classes.png)

## Design Details

### Game Editor Frontend

### Game Runner Frontend

### Game Editor Backend

* __IDManager__
* Manages queries to all elements
* Manages ownership of elements
* __GlobalStore__
    * Stores and saves variables that are necessary for multiple parts of the design process
        * Player-dependent types (white/black chess, monopoly piece, etc.)
        * Rules and Goals
* __OwnableStore__
    * Stores and saves of the GameObjects and Variables
* __BoardCreator__
    * Creates template boards

### Game Runner Backend

* __Game__
    * Represents the overall structure of the game that will be running, containing Players,
      GameWorld, Rules, and Goals
    * The idea is for this to encapsulate most of the functionalities of the game, interfacing
      directly with the Controller in order to manage active players, enforce rules, check whether a
      goal has been achieved (and thus end the game), and access the information owned by the
      GameWorld
* __GameWorld__
* Represents an abstract entity that controls all the GameObjects and GameVariables that are
  relevant to the game but are not tied to a specific player
* Updates GameObject state and GameVariable values as the game runs
* __Player__
    * Represents a player in the game, which owns specific GameObjects and GameVariables
    * Can be associated with specific rules that can apply to itself or to the entities that it owns
* __BoardGraph__
    * Stores the board drop zones as a graph with named nodes and edges
* Contains static methods to create template grids
* Communicates node contents, layout, node and edge names for front end to use
* Detects blocked paths
* Finds all available spots for a player based on paths supplied
  *__Interpreter__
    * Takes in data and converts into concrete actions that can be applied to elements in the game
    * NOTE: most of it will have a very similar structure ot the interpreter implemented by SLogo
      Team 5
* __Rule__
    * Specific set of actions that limit and/or specify what a player can do in its turn, or what
      the game is responsible for doing after each player’s turn
    * Can be extended in order to have specific outputs based on result of running this fixed
      sequence
* __Goal__
    * Extends rule, such that it has a fixed sequence of actions, but this sequence has an output,
      which returns whether certain player won or loss the game
* __GameVariable__
    * Represents a specific value in the game (of any type, often a String or an Integer), which can
      be updated by Rules
      *__GameObject__
    * Represents any sort of “piece” in a game
    * Can have an active/inactive status, move around the grid, and interact with other GameObjects

## Design Considerations

* Grid will be owned by GameWorld class, but should it be a type of GameObject or its own separate
  entity?
    * We had this discussion in order to define exactly how the GameWorld should be handing the
      Grid, as in, whether it should be taken as a unique object (likely in the form of an instance
      variable) or as just another GameObject (essentially being handled at the same level as the
      “pieces” themselves)
    * The first approach would make the game more structured, in the sense that it would have one
      single board that could be handled in a simpler way, actively interacting with the GameObjects
      to allow for the game to run. The main tradeoff of this approach is that it removes
      flexibility, in the sense that there can only ever be one board per game, and there must be
      specific methods to interact with the board inside the GameWorld
    * The advantage of the second approach is that it provides interfacing with the game more
      flexible and general, given that, in this situation, the Grid itself could be treated as any
      other object inside the game. The disadvantage is that this might make it harder to access
      some more specific functionalities that the board might have, adding complexity to the code,
      or potentially breaking the abstraction altogether
    * This debate has not yet been settled as of the planning deadline - as a team, we expect to try
      the first approach initially, and, if it is feasible, refactor the code in order to adapt it
      to the more abstract organization described by the second approach


* Actions, Rules, and Goals
  We had this discussion to determine what Rules are, what governs them, and what their scope is.
    * Option 1: Everything is a rule
      This makes design a little simpler - rules are just programs to be added. Conditions specified
      by a specific turn, like a chance card in Monopoly, are rules that also edit items on the
      board. Goals are special rules that call the end of the game. Rules are also called on during
      turns to verify if a move is valid. Rules are synonymous with interpreted logic flow from an
      action.

* Option 2: Actions are one-time “programs”, Rules are persistent “programs”, and Goals are win
  conditions
  Using this approach gives the program more structure. Goals are given somewhat of a priority,
  rules are always checked, and actions don’t need to be cared about until a piece runs it. This
  means that the game runner can optimize for each case, and adding logic isn’t as abstract.

## Role(s)

* Frontend:
    * George Fang (gyf2)
    * Joao Carvalho (jc870)
    * Connor Wells-Weiner (cjw84)
    * Aryan Kothari (ak616)

* Controller
    * Han Zhang (hz244)
    * Owen MacKenzie (opm7)

* Backend
    * Ethan Horowitz (ejh55)
    * Michael Bryant (mcb115)
    * Rodrigo Guerreiro (rb419)
    * Max Meister (mem148)
    * Yegor Kursakov (ek210)

Hardcoded:
Hardcoded endgame displays (probably modals)
If we have some hard coded displays we can 
