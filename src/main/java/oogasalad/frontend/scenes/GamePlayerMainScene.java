package oogasalad.frontend.scenes;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import oogasalad.Controller.GameController;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.managers.NodeRemovedListener;

import java.util.ArrayList;

/**
 * @author Connor Wells
 * @author Owen MacKenzie
 */

public class GamePlayerMainScene extends AbstractScene {
  public GamePlayerMainScene(SceneMediator sceneController) {
    super(sceneController);
  }

  @Override
  public Scene makeScene() {
    BorderPane root = new BorderPane();
    String numPlayers = panelController.getSceneController().getData().toString();
    int players;
    if (numPlayers.isEmpty()) players = 2;
    else players = Integer.parseInt(numPlayers);
    String gameName = panelController.getSceneController().getWindowController().getData().toString();
    String gameType = panelController.getSceneController().getData().toString();
    ArrayList<String> gameTypeData = new ArrayList<>();
    gameTypeData.add(gameType);
    if (gameType.equals("join")) {
      String code = panelController.getSceneController().getData().toString();
      gameTypeData.add(code);
    }

    GameController gameRunnerController = new GameRunnerController(gameName,gameTypeData,players);

    ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
    gameObjectVisuals.addListener(new NodeRemovedListener(root));
    root.getChildren().addAll(gameObjectVisuals);

    ObjectProperty<Boolean> endGameStatus = gameRunnerController.getEndGameStatus();
    endGameStatus.addListener((obs, oldValue, newValue) -> {
      System.out.println("Value changed from " + oldValue + " to " + newValue);
      //PUT YOUR FUNCTION HERE
    });

    Button undoButton = new Button("Undo");
    gameRunnerController.assignUndoButtonAction(undoButton);
    root.setTop(undoButton);

    Scene scene = new Scene(root);
    setScene(scene);
    setText();
    setTheme();
    return getScene();
  }

  @Override
  public void setText() {
  }
}
