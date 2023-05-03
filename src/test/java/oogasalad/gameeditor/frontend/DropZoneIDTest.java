package oogasalad.gameeditor.frontend;

import static org.codehaus.plexus.util.FileUtils.deleteDirectory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import oogasalad.Controller.BackendObjectController;
import oogasalad.Controller.BackendObjectStrategy;
import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.components.gridObjectComponent.GridObject;
import oogasalad.gameeditor.backend.GameInator;
import oogasalad.gameeditor.backend.ObjectParameter;
import oogasalad.gameeditor.backend.ObjectType;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class DropZoneIDTest extends DukeApplicationTest {

  ComponentsFactory factory = new ComponentsFactory();

  @Override
  public void start(Stage stage) throws Exception {

  }

  private void createBoard(GameInator game) {
    ObjectType type = ObjectType.OWNABLE;
    Map<ObjectParameter, Object> params = new HashMap<>();
    params.put(ObjectParameter.OWNABLE_TYPE, "BoardCreator");
    Map<Object, Object> constructorParams = new HashMap<>();
    constructorParams.put(ObjectParameter.BOARD_TYPE, "createSquareLoop");
    constructorParams.put(ObjectParameter.BOARD_ROWS, "3");
    constructorParams.put(ObjectParameter.BOARD_COLS, "3");
    params.put(ObjectParameter.CONSTRUCTOR_ARGS, constructorParams);
    game.sendObject(type, params);
    System.out.println(game.getOwnableIdManager().getSimpleIds());
  }

  @Test
  public void testAPI(){

    Map<String, String> params = new HashMap<>();
    params.put("width", "50");
    params.put("image", "data/games/TestGame/rick-astly.png");
    Component c = factory.create("GameObject", params);
    BackendObjectController controller = new BackendObjectController();
    GameInator game = new GameInator("TestGame");
    createBoard(game);
    controller.setGame(game);
    controller.sendOwnableObject(c);
  }

//  @AfterEach
//  public void tearDown() throws IOException {
//    //delete the test game folder
//    File file = new File("data/games/TestGame");
//    deleteDirectory(file);
//  }
}
