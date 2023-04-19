package oogasalad.frontend.nodeEditor.customNodeEditor.Runners;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableAbstractNode;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.SumNode;
import oogasalad.frontend.scenes.AbstractScene;
import oogasalad.frontend.scenes.SceneTypes;
import oogasalad.frontend.windows.AbstractWindow;

/**
 * Scrolling/panning based on
 * https://stackoverflow.com/questions/61195436/javafx-pan-and-zoom-with-draggable-nodes-inside
 */

public class NodeExperiment extends Application {

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setResizable(false);
    primaryStage.setWidth(1200);
    primaryStage.setHeight(700);
    ImageView imageView = new ImageView(
        new Image(getClass().getResourceAsStream("/frontend/images/GameEditor/grid.png")));
    imageView.setFitWidth(3 * primaryStage.getWidth());
    imageView.setFitHeight(3 * primaryStage.getHeight());
    DraggableAbstractNode sum = new SumNode(0, 0, 75, 25, "red");
    sum.setBoundingBox(imageView.getBoundsInParent());
    Group group = new Group(imageView, sum);
    double defaultXScale = 0.5;
    double defaultYScale = 0.5;
    group.setScaleX(defaultXScale);
    group.setScaleY(defaultYScale);
    StackPane content = new StackPane(new Group(group));
    content.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    content.setOnScroll(e -> {
      if (e.isShortcutDown() && e.getDeltaY() != 0) {
        if (e.getDeltaY() < 0) {
          group.setScaleX(Math.max(group.getScaleX() - 0.1, 0.25));
        } else {
          group.setScaleX(Math.min(group.getScaleX() + 0.1, 5.0));
        }
        group.setScaleY(group.getScaleX());
        e.consume();
      }
    });
    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setPannable(true);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    primaryStage.setScene(new Scene(scrollPane));
    primaryStage.show();

  }
}