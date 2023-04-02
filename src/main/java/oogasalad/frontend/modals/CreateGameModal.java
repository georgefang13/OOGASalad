package oogasalad.frontend.modals;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;
import javafx.geometry.Insets;


public class CreateGameModal extends Modal {

    private TextField nameField;
    private TextField imageField;

    public CreateGameModal() {
        setTitle("New Game");
        setHeaderText("Create a new game");

        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

        setOnShowing(event -> nameField.requestFocus());
    }


    @Override
    public DialogPane createDialogPane() {
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        // Create the content grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Create the input fields
        nameField = new TextField();
        nameField.setPromptText("Name");
        imageField = new TextField();
        imageField.setPromptText("Image URL");

        // Add the input fields to the grid
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Image URL:"), 0, 1);
        grid.add(imageField, 1, 1);

        // Enable/disable the OK button depending on whether the input fields are empty
        Node okButton = this.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty() || imageField.getText().trim().isEmpty());
        });
        imageField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty() || nameField.getText().trim().isEmpty());
        });

        this.getDialogPane().setContent(grid);

       return this.getDialogPane();
    }


    @Override
    protected Object convertResult(ButtonType buttonType) {
        return null;
    }

    public static class Game {
        private final String name;
        private final String imageUrl;

        public Game(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

}
