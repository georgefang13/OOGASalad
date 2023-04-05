package oogasalad.frontend.components.modals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.geometry.Insets;

public class Modal<T> extends Dialog<T> {
    protected static final String PROPERTIES_FILE = "frontend/modals/Modals";
    final public static int GAP = 10;
    final public static int INSET_TOP = 20;
    final public static int INSET_RIGHT = 150;
    final public static int INSET_BOTTOM = 10;
    final public static int INSET_LEFT = 10;

    private Map<String, String> myPropertiesMap;
    private String myTitle;

    /**
     * Constructor for the modal dialog with a title parameter
     * 
     * @param title
     */
    public Modal(String title) {
        myTitle = title;
        myPropertiesMap = setPropertiesMap(title);
        setTitle(title);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        setDialogPane(createDialogPane());
        setResultConverter(this::convertResult);
    }

    /**
     * Default Constructor for the modal dialog
     * 
     */
    public Modal() {
        myTitle = "Default";
        myPropertiesMap = setPropertiesMap(myTitle);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        setDialogPane(createDialogPane());
        setResultConverter(this::convertResult);
    }

    /**
     * Creates the dialog pane for the modal
     * 
     * @return
     */
    protected DialogPane createDialogPane() {
        // DialogPane dialogPane = new DialogPane();
        this.getDialogPane().setHeaderText(myPropertiesMap.get("title"));
        myPropertiesMap.remove("title");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());
        setContentAsLabels(stringFields);
        // setContentAsTextField(stringFields);
        return this.getDialogPane();
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
            InputStream inputStream = getClass().getClassLoader()
                    .getResourceAsStream("frontend/modals/Modals.properties");
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

    /**
     * Uses the array list of strings to create labels and add them to the dialog
     * pane
     * 
     * @return
     */
    protected void setContentAsLabels(ArrayList<String> content) {

        VBox vBox = new VBox();
        for (String s : content) {
            vBox.getChildren().add(makeLabel(s));
        }

        this.getDialogPane().setContent(vBox);
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
        textField.setPrefWidth(150); // TODO: export to stylesheet
        return textField;
    }

    /**
     * Creates a label with a prompt text
     * 
     * @param promptText
     * @return
     */
    protected Label makeLabel(String promptText) {
        Label label = new Label(promptText);
        label.setPrefWidth(600); 
        label.wrapTextProperty().setValue(true);
        return label;
    }


    /**
     * Returns an unmodifiable version of the map of properties
     * 
     * @return
     */
    protected Map<String, String> getPropertiesMap() {
        return new HashMap<>(myPropertiesMap);
    }
    


    

    /**
     * Converts the button type to the result
     * 
     * @param buttonType
     * @return
     */
    protected T convertResult(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return getResult();
        }
        return null;
    }

}
