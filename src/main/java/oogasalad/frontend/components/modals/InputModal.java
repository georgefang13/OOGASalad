package oogasalad.frontend.components.modals;

import java.io.InputStream;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import java.util.Properties;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class InputModal extends Modal {

    final public static int GAP = 10;
    final public static int INSET_TOP = 20;
    final public static int INSET_RIGHT = 150;
    final public static int INSET_BOTTOM = 10;
    final public static int INSET_LEFT = 10;

    private Map<String, String> myPropertiesMap;

    /**
     * Constructor for InputModal
     * 
     * @param title
     */
    public InputModal(String title) {
        super(title);
        // myPropertiesMap = setPropertiesMap(title);
    }

    /**
     * Returns a map of the properties from the modal properties file using the
     * title of the modal
     * 
     * @return
     */
    protected HashMap<String, String> setPropertiesMap(String title) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("frontend/modals/Modals.properties");
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.err.println("Error: unable to load properties file");
            }
        } catch (Exception e) {
            System.out.println("Error loading properties file");
        }


        HashMap<String, String> myPropertiesMap = new HashMap<>();

        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(title)) {
                String newKey = key.substring(title.length() + 1);
                myPropertiesMap.put(newKey, properties.getProperty(key));
            }
        }
        return myPropertiesMap;

    }

    // /**
    // * Creates the actual dialog pane for the modal
    // *
    // * @return
    // */
    // @Override
    // protected DialogPane createDialogPane() {
    // DialogPane dialogPane = new DialogPane();
    // dialogPane.setContent(new Label("Enter a name for your game"));
    // TextField textField = new TextField();
    // dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    // dialogPane.setContent(textField);
    // return dialogPane;
    // }

    @Override
    protected DialogPane createDialogPane() {
        myPropertiesMap = setPropertiesMap("createGame");
        ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());
//
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        // Create the content grid
        GridPane grid = createGrid();

        ArrayList<TextField> textFields = setGridContent(grid, stringFields);

        // Enable/disable the OK button depending on whether the input fields are empty
        // Node okButton = this.getDialogPane().lookupButton(ButtonType.OK);
        // addFieldButtonListeners(okButton, textFields);

        this.getDialogPane().setContent(grid);

        return this.getDialogPane();
    }

    /**
     * Creates a text field with a prompt text
     * 
     * @param promptText
     * @return
     */
    protected TextField makeTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(150); //TODO: export to stylesheet
        return textField;
    }

    /**
     * Creates a grid pane with the appropriate spacing where the inputs will be
     * 
     * @return
     */
    protected GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(GAP);
        grid.setVgap(GAP);
        grid.setPadding(new Insets(INSET_TOP, INSET_RIGHT, INSET_BOTTOM, INSET_LEFT));

        return grid;
    }

    /**
     * Sets the content of the grid pane with the appropriate labels and text fields
     * 
     * @param grid
     * @param fields
     * @return
     */
    protected ArrayList<TextField> setGridContent(GridPane grid, ArrayList<String> fields) {
        ArrayList<TextField> textFields = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            System.out.println(fields.get(i));
            grid.add(new Label(fields.get(i)), 0, i);
            grid.add(makeTextField(fields.get(i)), 1, i);
            textFields.add(makeTextField(fields.get(i)));
        }
        return textFields;
    }

    @Override
    protected Object convertResult(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return getDialogPane().getContent().toString();
        } else {
            return null;
        }
    }

}
