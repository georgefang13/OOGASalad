package oogasalad.frontend.components.modals;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Insets;

public class CreateGameModal extends Modal {

    final public static int GAP = 10;
    final public static int INSET_TOP = 20;
    final public static int INSET_RIGHT = 150;
    final public static int INSET_BOTTOM = 10;
    final public static int INSET_LEFT = 10;

    private TextField nameField;
    private TextField imageField;

    private ArrayList<String> stringFields = new ArrayList<>();

    /**
     * Constructor for the CreateGameModal dialog
     */
    public CreateGameModal() {
        setTitle("New Game");
        setHeaderText("Create a new game");

        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

        // setOnShowing(event -> nameField.requestFocus());
    }

    @Override
    public DialogPane createDialogPane() {
        stringFields = new ArrayList<>(Arrays.asList("Name:", "Image URL:"));

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        // Create the content grid
        GridPane grid = createGrid();

        ArrayList<TextField> textFields = setGridContent(grid, stringFields);

        // Enable/disable the OK button depending on whether the input fields are empty
        Node okButton = this.getDialogPane().lookupButton(ButtonType.OK);
        addFieldButtonListeners(okButton, textFields);

        this.getDialogPane().setContent(grid);

        return this.getDialogPane();
    }

    private TextField makeTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(150);
        return textField;
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(GAP);
        grid.setVgap(GAP);
        grid.setPadding(new Insets(INSET_TOP, INSET_RIGHT, INSET_BOTTOM, INSET_LEFT));

        return grid;
    }

    private ArrayList<TextField> setGridContent(GridPane grid, ArrayList<String> fields) {
        ArrayList<TextField> textFields = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            System.out.println(fields.get(i));
            grid.add(new Label(fields.get(i)), 0, i);
            grid.add(makeTextField(fields.get(i)), 1, i);
            textFields.add(makeTextField(fields.get(i)));
        }
        return textFields;
    }

    private void addFieldButtonListeners(Node btn, ArrayList<TextField> fields) {
        btn.setDisable(true);
        for (TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                btn.setDisable(newValue.trim().isEmpty() || field.getText().trim().isEmpty());
            });
        }

    }

    @Override
    protected Object convertResult(ButtonType buttonType) {
        return null;
    }

    protected void onResult(ArrayList<TextField> results) {
        String output = "";
        for (TextField textField : results) {
            output += textField.getPromptText() + ": ";
            output += textField.getText() + " | ";
        }
        System.out.println(output);
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
