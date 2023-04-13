package oogasalad.frontend.modals.subInputModals;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import oogasalad.frontend.modals.InputModal;

public class CreateNewModal extends InputModal {
//    private static final ResourceBundle MODAL_ID_BUNDLE = ResourceBundle.getBundle("frontend/modals/ModalStylingID");
    private static final String IMAGE_PICKER_ID = "ImagePickerID";
    private Map<String, String> myPropertiesMap;

    private String myTitle;

    /**
     * Constructor for the CreateGameModal dialog
     */
    public CreateNewModal(String title) {
        super(title);
        myTitle = super.getMyTitle();
//        myPropertiesMap = super.setPropertiesMap(myTitle
    }

    /**
     * Creates the dialog pane for the CreateGameModal dialog
     * 
     * @return
     */
    @Override
    protected DialogPane createDialogPane() {
        myPropertiesMap = super.getPropertiesMap();

        this.getDialogPane().setHeaderText(super.getMyTitle());

        ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        try {
            makeFields(myPropertiesMap);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return this.getDialogPane();
    }



    /**
     * Makes the appropriate types of input fields for the CreateGameModal dialog
     * 
     * @param
     * @return
     */
    protected void makeFields(Map<String, String> map) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        GridPane grid = createGrid();
        int rowIndex = 0;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String propertyName = entry.getKey();
            String fieldType = propertyName.split("\\.")[propertyName.split("\\.").length - 1];
            String propertyValue = entry.getValue();

            int start = entry.getKey().toString().indexOf("*") + 1;

            String labelName = entry.getKey().toString().substring(start);
            labelName = labelName.split("\\.")[0];

            // Get the field class corresponding to the property name using reflection
            Class<?> fieldClass = Class.forName("oogasalad.frontend.modals.fields." + fieldType + "Component");


            // Create a new instance of the field class using reflection
            Constructor<?> constructor = fieldClass.getDeclaredConstructor(String.class, String.class);
            Object field = constructor.newInstance(labelName, propertyValue);

            // Invoke the createField() method on the field instance using reflection
            Method createFieldMethod = fieldClass.getDeclaredMethod("createField");
            HBox fieldHBox = (HBox) createFieldMethod.invoke(field);

            // Add the field to the grid
            grid.add(fieldHBox, 0, rowIndex);

            // Increment the row index
            rowIndex++;
        }



        this.getDialogPane().setContent(grid);
    } // TODO: make classes for each of these elements and use java reflection to create them. for
    // TODO: styling use the last .NAME in the properties file to get the styling id

}