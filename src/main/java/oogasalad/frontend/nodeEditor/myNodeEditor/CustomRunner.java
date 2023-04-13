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
        DraggableItem redRect = new DraggableItem(50, 50, 50, 50, "red");
        DraggableItem blueRect = new DraggableItem(50, 150, 50, 50, "blue");
        pane1.getChildren().addAll(redRect, blueRect);
        pane1.setPrefWidth(150);

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
                item.setX(event.getX() - container.getDragX());
                item.setY(event.getY() - container.getDragY());
                pane2.getChildren().add(item);
                event.setDropCompleted(true);
            }
            event.consume();
        });

        Scene scene = new Scene(new HBox(pane1, pane2), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        new DraggableController(redRect);
        new DraggableController(blueRect);
    }
}