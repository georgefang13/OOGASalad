package oogasalad.sharedDependencies.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import java.util.Map.Entry;
import oogasalad.gameeditor.backend.GameInator;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.ObjectType;
import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.sharedDependencies.backend.id.OwnableSearchStream;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import oogasalad.sharedDependencies.backend.owners.Player;
import oogasalad.sharedDependencies.backend.rules.RuleManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ObjectFactoryTest {

  private IdManager idManager;
  private ArrayList<Player> players;
  private GameWorld world;
  private GameInator game;
  private RuleManager ruleManager;

  private String gameName = "testGame";
  private String gameDirectory = "data/games/" + gameName + "/";
  private String layoutFile = gameDirectory + "layout.json";
  private String generalFile = gameDirectory + "general.json";
  private String variablesFile = gameDirectory + "variables.json";
  private String objectsFile = gameDirectory + "objects.json";

  @BeforeEach
  void setup() {
    game = new GameInator(gameName);
    world = game.getGameWorld();
    game.addPlayer(new Player());
    game.addPlayer(new Player());
    game.addPlayer(new Player());
    idManager = game.getOwnableIdManager();
    ruleManager = game.getRuleManager();
    players = new ArrayList<>(game.getPlayers());
  }

  @Test
  public void testCreteVariables() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
    Map<Object, Object> constructorParams = new HashMap<>();
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    // Variable with nothing
    game.sendObject(type, params);
    // Variable with Constructor Value
    constructorParams.put(ObjectParameter.VALUE, 64);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    game.sendObject(type, params);
    Variable var = (Variable) game.getOwnable("Variable2");
    assertEquals(64, var.get());
    // Variable with ID
    params.put(ObjectParameter.ID, "myId");
    game.sendObject(type, params);
    // Variable with owner
    params.put(ObjectParameter.OWNER, "2");
    game.sendObject(type, params);
    // Variable with parent ownable
    params.put(ObjectParameter.PARENT_OWNABLE_ID, "myId");
    game.sendObject(type, params);
    assertEquals(5, idManager.getSimpleIds().size());
    checkFileContents(layoutFile, "{}");
    checkFileContents(objectsFile, "{}");
    checkFileContents(generalFile, "{\n"
        + "  \"name\": \"testGame\",\n"
        + "  \"author\": \"\",\n"
        + "  \"description\": \"\",\n"
        + "  \"tags\": \"all\",\n"
        + "  \"players\": {\n"
        + "    \"min\": \"2\",\n"
        + "    \"max\": 3\n"
        + "  }\n"
        + "}");
    checkFileContents(variablesFile, "{\"Variable2\":{\"owner\":\"0\",\"type\":\"java.lang.Integer\",\"value\":64},\"myId2\":{\"owner\":\"2\",\"type\":\"java.lang.Integer\",\"value\":64},\"myId\":{\"owner\":\"0\",\"type\":\"java.lang.Integer\",\"value\":64},\"myId.myId3\":{\"owner\":\"0\",\"type\":\"java.lang.Integer\",\"value\":64}}");
  }

  private void addDropZone(int num) {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "BoardCreator");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.BOARD_TYPE, "createGrid");
    constructorParams.put(ObjectParameter.BOARD_ROWS, "1");
    constructorParams.put(ObjectParameter.BOARD_COLS, ""+num);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    game.sendObject(type, params);
  }

  @Test
  public void testCreateGameObjects() {
    // Need to have a drop zone to put the GameObjects in
    int x = 1;
    addDropZone(x);
    //get first dropzone id using idManager iterator
    Iterator<Entry<String, Object>> it = idManager.getSimpleIds().entrySet().iterator();
    //check if it has next
    assertEquals(true, it.hasNext());
    Entry<String, Object> entry = it.next();
    String dzid = String.valueOf(entry.getKey());

    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "GameObject");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.DROPZONE_ID, dzid);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    // GameObject with nothing
    game.sendObject(type, params);
    // GameObject with ID
    params.put(ObjectParameter.ID, "myIdGameObject");
    game.sendObject(type, params);
    // GameObject with owner
    params.put(ObjectParameter.OWNER, "3");
    game.sendObject(type, params);
    // GameObject with parent ownable
    params.put(ObjectParameter.PARENT_OWNABLE_ID, "myIdGameObject");
    game.sendObject(type, params);
    assertEquals(x+3, idManager.getSimpleIds().size() - 1);
    checkFileContents(layoutFile, "{\"DropZone\":{\"connections\":\"\"}}");
    checkFileContents(objectsFile, "{\"myIdGameObject.myIdGameObject3\":{\"owner\":\"-1\",\"location\":\"DropZone\"},\"myIdGameObject2\":{\"owner\":\"-1\",\"location\":\"\"},\"GameObject\":{\"owner\":\"-1\",\"location\":\"\"},\"myIdGameObject\":{\"owner\":\"-1\",\"location\":\"\"}}");
    checkFileContents(generalFile, "{\n"
        + "  \"name\": \"testGame\",\n"
        + "  \"author\": \"\",\n"
        + "  \"description\": \"\",\n"
        + "  \"tags\": \"all\",\n"
        + "  \"players\": {\n"
        + "    \"min\": \"2\",\n"
        + "    \"max\": 3\n"
        + "  }\n"
        + "}");
    checkFileContents(variablesFile, "{}");
  }

  @Test
  public void testCreateGameObjectWithoutDropzone() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "GameObject");
    Map<Object, Object> constructorParams = new HashMap<>();
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    // GameObject with nothing
    assertThrows(NullPointerException.class, () -> game.sendObject(type, params));
    // GameObject with ID
    params.put(ObjectParameter.ID, "myIdGameObject");
    assertThrows(NullPointerException.class, () -> game.sendObject(type, params));
    // GameObject with owner
    params.put(ObjectParameter.OWNER, "3");
    assertThrows(NullPointerException.class, () -> game.sendObject(type, params));
    // GameObject with parent ownable
    params.put(ObjectParameter.PARENT_OWNABLE_ID, "myIdGameObject");
    assertThrows(NullPointerException.class, () -> game.sendObject(type, params));
    checkFileContents(layoutFile, "{}");
    checkFileContents(objectsFile, "{}");
    checkFileContents(generalFile, "{\n"
        + "  \"name\": \"testGame\",\n"
        + "  \"author\": \"\",\n"
        + "  \"description\": \"\",\n"
        + "  \"tags\": \"all\",\n"
        + "  \"players\": {\n"
        + "    \"min\": \"2\",\n"
        + "    \"max\": 3\n"
        + "  }\n"
        + "}");
    checkFileContents(variablesFile, "{}");
  }

  @Test
  public void testDeleteObject() {
    // Need to have a drop zone to put the GameObjects in
    addDropZone(1);
    //get first dropzone id using idManager iterator
    Iterator<Entry<String, Object>> it = idManager.getSimpleIds().entrySet().iterator();
    //check if it has next
    assertEquals(true, it.hasNext());
    Entry<String, Object> entry = it.next();
    String dzid = String.valueOf(entry.getKey());
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.VALUE, 64);
    constructorParams.put(ObjectParameter.DROPZONE_ID, dzid);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    params.put(ObjectParameter.ID, "myId");
    params.put(ObjectParameter.OWNER, "2");
    game.sendObject(type, params);
    assertEquals(1, idManager.getSimpleIds().size() - 1);
    game.deleteObject(type, params);
    assertEquals(0, idManager.getSimpleIds().size() - 1);
    checkFileContents(layoutFile, "{\"DropZone\":{\"connections\":\"\"}}");
    checkFileContents(objectsFile, "{}");
    checkFileContents(generalFile, "{\n"
        + "  \"name\": \"testGame\",\n"
        + "  \"author\": \"\",\n"
        + "  \"description\": \"\",\n"
        + "  \"tags\": \"all\",\n"
        + "  \"players\": {\n"
        + "    \"min\": \"2\",\n"
        + "    \"max\": 3\n"
        + "  }\n"
        + "}");
    checkFileContents(variablesFile, "{}");
  }

  @Test
  public void testUpdateObjectProperties() {
    // Need to have a drop zone to put the GameObjects in
    addDropZone(1);
    //get first dropzone id using idManager iterator
    Iterator<Entry<String, Object>> it = idManager.getSimpleIds().entrySet().iterator();
    //check if it has next
    assertEquals(true, it.hasNext());
    Entry<String, Object> entry = it.next();
    String dzid = String.valueOf(entry.getKey());
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "Variable");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.VALUE, 64);
    constructorParams.put(ObjectParameter.DROPZONE_ID, dzid);
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    params.put(ObjectParameter.ID, "myId");
    params.put(ObjectParameter.OWNER, "1");
    game.sendObject(type, params);
    game.sendObject(type, params);
    Map<ObjectParameter, Object> updateParams = new HashMap<>();
    Map<Object, Object> updateConstructorParams = new HashMap<>();
    updateConstructorParams.put(ObjectParameter.VALUE, 30);
    updateConstructorParams.put(ObjectParameter.DROPZONE_ID, dzid);
    updateParams.put(ObjectParameter.CONSTRUCTOR_ARGS, updateConstructorParams);
    updateParams.put(ObjectParameter.ID, "updatedId");
    updateParams.put(ObjectParameter.OWNER, "2");
    updateParams.put(ObjectParameter.PARENT_OWNABLE_ID, "myId2");
    game.updateObjectProperties("myId", type, updateParams);
    Variable var = (Variable) game.getOwnable("updatedId");
    assertEquals(30, var.get());
    assertEquals(players.get(1), var.getOwner());
    updateParams.replace(ObjectParameter.OWNER, "GameWorld");
    updateParams.remove(ObjectParameter.ID);
    updateParams.remove(ObjectParameter.PARENT_OWNABLE_ID);
    updateParams.replace(ObjectParameter.CONSTRUCTOR_ARGS, new HashMap<>());
    game.updateObjectProperties("updatedId", type, updateParams);
    assertEquals(world, game.getOwnable("updatedId").getOwner());
    checkFileContents(layoutFile, "{\"DropZone\":{\"connections\":\"\"}}");
    checkFileContents(objectsFile, "{}");
    checkFileContents(generalFile, "{\n"
        + "  \"name\": \"testGame\",\n"
        + "  \"author\": \"\",\n"
        + "  \"description\": \"\",\n"
        + "  \"tags\": \"all\",\n"
        + "  \"players\": {\n"
        + "    \"min\": \"2\",\n"
        + "    \"max\": 3\n"
        + "  }\n"
        + "}");
    checkFileContents(variablesFile, "{\"myId2\":{\"owner\":\"1\",\"type\":\"java.lang.Integer\",\"value\":64}}");
  }

  @Test
  public void testBoardCreator() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "BoardCreator");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.BOARD_TYPE, "createSquareLoop");
    constructorParams.put(ObjectParameter.BOARD_ROWS, "3");
    constructorParams.put(ObjectParameter.BOARD_COLS, "3");
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    game.sendObject(type, params);
    checkFileContents(layoutFile, "{\"DropZone7\":{\"connections\":{\"Clockwise\":\"DropZone6\",\"Counterclockwise\":\"DropZone8\"}},\"DropZone8\":{\"connections\":{\"Clockwise\":\"DropZone7\",\"Counterclockwise\":\"DropZone\"}},\"DropZone5\":{\"connections\":{\"Clockwise\":\"DropZone4\",\"Counterclockwise\":\"DropZone6\"}},\"DropZone6\":{\"connections\":{\"Clockwise\":\"DropZone5\",\"Counterclockwise\":\"DropZone7\"}},\"DropZone3\":{\"connections\":{\"Clockwise\":\"DropZone2\",\"Counterclockwise\":\"DropZone4\"}},\"DropZone4\":{\"connections\":{\"Clockwise\":\"DropZone3\",\"Counterclockwise\":\"DropZone5\"}},\"DropZone2\":{\"connections\":{\"Clockwise\":\"DropZone\",\"Counterclockwise\":\"DropZone3\"}},\"DropZone\":{\"connections\":{\"Clockwise\":\"DropZone8\",\"Counterclockwise\":\"DropZone2\"}}}");
    checkFileContents(objectsFile, "{}");
    checkFileContents(generalFile, "{\n"
        + "  \"name\": \"testGame\",\n"
        + "  \"author\": \"\",\n"
        + "  \"description\": \"\",\n"
        + "  \"tags\": \"all\",\n"
        + "  \"players\": {\n"
        + "    \"min\": \"2\",\n"
        + "    \"max\": 3\n"
        + "  }\n"
        + "}");
    checkFileContents(variablesFile, "{}");
  }

  @Test
  public void testBoardCreatorMultiple() {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "BoardCreator");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.BOARD_TYPE, "createSquareLoop");
    constructorParams.put(ObjectParameter.BOARD_ROWS, "3");
    constructorParams.put(ObjectParameter.BOARD_COLS, "3");
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    //params.put(ObjectParameter.ID, "myIdBoard");
    game.sendObject(type, params);
    game.sendObject(type, params);
    assertEquals(2*8, idManager.getSimpleIds().size());
    checkFileContents(layoutFile, "{\"DropZone11\":{\"connections\":{\"Clockwise\":\"DropZone10\",\"Counterclockwise\":\"DropZone12\"}},\"DropZone10\":{\"connections\":{\"Clockwise\":\"DropZone9\",\"Counterclockwise\":\"DropZone11\"}},\"DropZone15\":{\"connections\":{\"Clockwise\":\"DropZone14\",\"Counterclockwise\":\"DropZone16\"}},\"DropZone14\":{\"connections\":{\"Clockwise\":\"DropZone13\",\"Counterclockwise\":\"DropZone15\"}},\"DropZone13\":{\"connections\":{\"Clockwise\":\"DropZone12\",\"Counterclockwise\":\"DropZone14\"}},\"DropZone12\":{\"connections\":{\"Clockwise\":\"DropZone11\",\"Counterclockwise\":\"DropZone13\"}},\"DropZone\":{\"connections\":{\"Clockwise\":\"DropZone8\",\"Counterclockwise\":\"DropZone2\"}},\"DropZone9\":{\"connections\":{\"Clockwise\":\"DropZone16\",\"Counterclockwise\":\"DropZone10\"}},\"DropZone7\":{\"connections\":{\"Clockwise\":\"DropZone6\",\"Counterclockwise\":\"DropZone8\"}},\"DropZone8\":{\"connections\":{\"Clockwise\":\"DropZone7\",\"Counterclockwise\":\"DropZone\"}},\"DropZone5\":{\"connections\":{\"Clockwise\":\"DropZone4\",\"Counterclockwise\":\"DropZone6\"}},\"DropZone6\":{\"connections\":{\"Clockwise\":\"DropZone5\",\"Counterclockwise\":\"DropZone7\"}},\"DropZone3\":{\"connections\":{\"Clockwise\":\"DropZone2\",\"Counterclockwise\":\"DropZone4\"}},\"DropZone4\":{\"connections\":{\"Clockwise\":\"DropZone3\",\"Counterclockwise\":\"DropZone5\"}},\"DropZone2\":{\"connections\":{\"Clockwise\":\"DropZone\",\"Counterclockwise\":\"DropZone3\"}},\"DropZone16\":{\"connections\":{\"Clockwise\":\"DropZone15\",\"Counterclockwise\":\"DropZone9\"}}}");
    checkFileContents(objectsFile, "{}");
    checkFileContents(generalFile, "{\n"
        + "  \"name\": \"testGame\",\n"
        + "  \"author\": \"\",\n"
        + "  \"description\": \"\",\n"
        + "  \"tags\": \"all\",\n"
        + "  \"players\": {\n"
        + "    \"min\": \"2\",\n"
        + "    \"max\": 3\n"
        + "  }\n"
        + "}");
    checkFileContents(variablesFile, "{}");
  }

  private void checkFileContents(String filename, String expectedContents) {
    try {
      String contents = new String(Files.readAllBytes(Paths.get(filename)));
      assertEquals(expectedContents, contents);
    } catch (IOException e) {
      fail("Could not read file " + filename);
    }
  }
}


