package oogasalad.frontend.components.modals;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.StageStyle;


public class Modal<T> extends Dialog<T> {
    protected static final String PROPERTIES_FILE = "frontend/modals/Modals";
    private Map<String, String> myPropertiesMap;
    private String myTitle;


    /**
     * Constructor for the modal dialog
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
     * @return
     */
    protected DialogPane createDialogPane(){
        // DialogPane dialogPane = new DialogPane();
        this.getDialogPane().setHeaderText(myPropertiesMap.get("title"));
        myPropertiesMap.remove("title");
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());
        setContent(stringFields);
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

    protected void setContent(ArrayList<String> content) {
        

        VBox vBox = new VBox();
        for (String s : content) {
            vBox.getChildren().add(new Label(s));
        }

        this.getDialogPane().setContent(vBox);
    }


    /**
     * Converts the button type to the result
     * @param buttonType
     * @return
     */
    protected T convertResult(ButtonType buttonType){
        if (buttonType == ButtonType.OK){
            return getResult();
        }
        return null;
    }


}
