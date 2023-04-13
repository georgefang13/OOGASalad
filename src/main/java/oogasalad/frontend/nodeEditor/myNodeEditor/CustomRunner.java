package oogasalad.frontend.nodeEditor.myNodeEditor;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CustomRunner extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane pane1 = new Pane();
        pane1.setStyle("-fx-background-color: black");
        DraggableItem redRect = new DraggableItem(50, 50, 50, 50, "red");
        DraggableItem blueRect = new DraggableItem(50, 150, 50, 50, "blue");
        DraggableItem greenRect = new DraggableItem(50, 250, 50, 50, "green");
        pane1.getChildren().addAll(redRect, blueRect, greenRect);
        pane1.setPrefSize(100, 200);

        Pane pane2 = new Pane();
        pane2.setPrefSize(200, 200);
        pane2.setOnDragOver(event -> {
            if (event.getDragboard().hasContent(DraggableContainer.RECTANGLE_FORMAT)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });
        pane2.setOnDragDropped(event -> {
            if (event.getDragboard().hasContent(DraggableContainer.RECTANGLE_FORMAT)) {
                DraggableContainer container = (DraggableContainer) event.getDragboard().getContent(DraggableContainer.RECTANGLE_FORMAT);
                DraggableItem item = container.getItem();
                item.setX(event.getX());
                item.setY(event.getY());
                pane2.getChildren().add(new DraggableItem(item));
                event.setDropCompleted(true);
            }
            event.consume();
        });
        Scene scene = new Scene(new HBox(pane1, pane2), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        new DraggableController(redRect);
        new DraggableController(blueRect);
        new DraggableController(greenRect);
    }
}