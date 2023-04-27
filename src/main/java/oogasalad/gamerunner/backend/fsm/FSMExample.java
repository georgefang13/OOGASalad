package oogasalad.gamerunner.backend.fsm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.gamerunner.backend.interpreter.Interpreter;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

enum States {
  INIT, MOVE1, DONE
}

public class FSMExample {

  private final IdManager<Ownable> idManager;
  private final FSM<String> fsm;
  Variable<List<GameObject>> availableVar;
  Interpreter interpreter;

  List<String> goals = new ArrayList<>();

  public FSMExample() {
    idManager = new IdManager<>();
    interpreter = new Interpreter();
    interpreter.linkIdManager(idManager);
    fsm = new FSM<>(idManager);
    Variable<Double> turn = new Variable<>(0.);
    turn.addListener((value) -> fsm.setState("INIT"));
    Variable<Integer> numPlayers = new Variable<>(2);
    availableVar = new Variable<>(new ArrayList<>());

    readFromJson();

    List<DropZone> zones = BoardCreator.createGrid(3, 3);
    for (DropZone zone : zones) {
      zone.addClass("board");
      idManager.addObject(zone);
    }

    idManager.addObject(turn, "turn");
    idManager.addObject(numPlayers, "numPlayers");
    idManager.addObject(availableVar, "available");

    fsm.setState("INIT");
  }

  private void readFromJson(){
    String absoluteFilePath =
//            System.getProperty("user.dir") + "/src/main/resources/FSMExample.json";
            System.getProperty("user.dir") + "/src/main/resources/export.json";
    String fileContent = "";
    // Read the entire file content
    try {
      fileContent = Files.readString(Paths.get(absoluteFilePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    JsonElement json = JsonParser.parseString(fileContent);
    JsonObject obj = json.getAsJsonObject();
    JsonObject states = obj.getAsJsonObject("states");

    for (String key : states.keySet()) {
      JsonObject value = (JsonObject) states.get(key);

      String init = value.get("init").getAsString();
      String leave = value.get("leave").getAsString();
      String set = value.get("setValue").getAsString();
      String tofunc = value.get("to").getAsString();

      ProgrammableState state = new ProgrammableState(interpreter, init, leave, set);
      fsm.putState(key, state, (prevstate, data) -> {
        interpreter.interpret(tofunc);
        IdManager idManager = (IdManager) data.get("idManager");
        Variable<String> output = (Variable<String>) idManager.getObject("state_output");
        return output.get();
      });
    }

    JsonArray goalsObj = obj.getAsJsonArray("goals");
    for (JsonElement goal : goalsObj) {
      String goalString = goal.getAsString();
      goals.add(goalString);
    }
  }

  public String run(String fromFront) {
    sendUserInput(fromFront);
    return getInstruction();
  }

  public String getInstruction() {
    String response = "";
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        DropZone dropZone = (DropZone) idManager.getObject(i + "," + j);
        if (dropZone.getAllObjects().size() == 0) {
          response += "-";
        } else {
          response += dropZone.getAllObjects().get(0);
        }
      }
      response += "\n";
    }
    response = response + "Turn: " + ((Variable<Integer>) idManager.getObject("turn")).get();
    fsm.transition();

    List<GameObject> available = availableVar.get();
    if (available.size() == 0) {
      response += "\nDONE";
    }

    response += "\nAvailable spots: ";
    for (GameObject obj : available) {
      response = response + idManager.getId(obj) + " ";
    }
    response += "\nSelect a spot: ";
    return response;
  }

  private void sendUserInput(String fromFront) {
    fsm.setStateInnerValue(fromFront);
    fsm.transition();
    for (String goal : goals) {
      if (idManager.isIdInUse("state_output")) {
        idManager.removeObject("state_output");
      }
      interpreter.interpret(goal);

      if (idManager.isIdInUse("state_output")) {
        Variable<String> output = (Variable<String>) idManager.getObject("state_output");
        System.out.println(output.get() + " won!");
      }

    }
  }

  public static void main(String[] args) {
    IdManager<Ownable> idManager = new IdManager<>();

    FSM<States> fsm = new FSM<>(idManager);

    Variable<Integer> turn = new Variable<>(0);
    turn.addListener((value) -> fsm.setState(States.INIT));

    Variable<Integer> numPlayers = new Variable<>(2);
    Variable<List<GameObject>> availableVar = new Variable(new ArrayList<>());

    List<DropZone> zones = BoardCreator.createGrid(3, 3);
    for (DropZone zone : zones) {
      idManager.addObject(zone);
    }

    idManager.addObject(turn, "turn");
    idManager.addObject(numPlayers, "numPlayers");
    idManager.addObject(availableVar, "available");

    fsm.setState(States.INIT);

    Scanner in = new Scanner(System.in);

    while (true) {
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          DropZone dropZone = (DropZone) idManager.getObject(i + "," + j);
          if (dropZone.getAllObjects().size() == 0) {
            System.out.print("-");
          } else {
            System.out.print(dropZone.getAllObjects().get(0));
          }
        }
        System.out.println();
      }
      System.out.println("Turn: " + ((Variable<Integer>) idManager.getObject("turn")).get());
      fsm.transition();

      List<GameObject> available = availableVar.get();
      if (available.size() == 0) {
        break;
      }

      System.out.print("Available spots: ");
      for (GameObject obj : available) {
        System.out.print(idManager.getId(obj) + " ");
      }
      System.out.println("\nSelect a spot: ");
      String s = in.nextLine();
      fsm.setStateInnerValue(idManager.getObject(s));
      fsm.transition();
    }
  }
}
