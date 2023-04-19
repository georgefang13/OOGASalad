package oogasalad.frontend.nodeEditor.customNodeEditor.Runners;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.DraggableAbstractNode;
import oogasalad.frontend.nodeEditor.customNodeEditor.Nodes.SumNode;

/**
 * Scrolling/panning based on
 * https://stackoverflow.com/questions/61195436/javafx-pan-and-zoom-with-draggable-nodes-inside
 */

public class NodeExperiment extends Application {

  private double scaleFactor = 1.0;

  @Override
  public void start(Stage primaryStage) {
    ImageView imageView = new ImageView(
        "https://static.vecteezy.com/system/resources/previews/005/424/896/original/blueprint-background-in-blue-planning-architecture-sheet-with-grid-free-vector.jpg");
    DraggableAbstractNode sum = new SumNode(100, 100, 50, 25, "red");
    Group group = new Group(imageView, sum);
    StackPane content = new StackPane(new Group(group));
    content.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    content.setOnScroll(e -> {
      if (e.isShortcutDown() && e.getDeltaY() != 0) {
        if (e.getDeltaY() < 0) {
          group.setScaleX(Math.max(group.getScaleX() - 0.1, 0.5));
        } else {
          group.setScaleX(Math.min(group.getScaleX() + 0.1, 5.0));
        }
        group.setScaleY(group.getScaleX());
        scaleFactor = group.getScaleX(); // update scale factor
        e.consume();
      }
    });
    ScrollPane scrollPane = new ScrollPane(content);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setPannable(true);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    primaryStage.setScene(new Scene(scrollPane, 800, 600));
    primaryStage.show();
  }
}

