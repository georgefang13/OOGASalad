package oogasalad.frontend.modals.subInputModals;

import java.util.ArrayList;
import java.util.Map;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import oogasalad.frontend.modals.InputModal;

public class CreateGameModal extends InputModal {
//    private static final ResourceBundle MODAL_ID_BUNDLE = ResourceBundle.getBundle("frontend/modals/ModalStylingID");
    private static final String IMAGE_PICKER_ID = "ImagePickerID";
    private Map<String, String> myPropertiesMap;

    private String myTitle;

    /**
     * Constructor for the CreateGameModal dialog
     */
    public CreateGameModal() {
        super("Create_Game_Modal");
        myTitle = "Create_Game_Modal";
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

        for (Map.Entry entry: myPropertiesMap.entrySet()) {

            int start = entry.getKey().toString().indexOf("*") + 1;

//            System.out.println(entry.getKey().toString().substring(start) + " " + entry.getValue());

        }

        this.getDialogPane().setHeaderText(myTitle);
        System.out.println(this.getDialogPane().getHeaderText());

        ArrayList<String> stringFields = new ArrayList<>(myPropertiesMap.values());

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        makeFields(stringFields);

        return this.getDialogPane();
    }



    /**
     * Makes the appropriate types of input fields for the CreateGameModal dialog
     * 
     * @param stringFields
     * @return
     */
    protected void makeFields(ArrayList<String> stringFields) {
        GridPane grid = createGrid();

        grid.add(new Label(myPropertiesMap.get("Name")), 0, 0);
        grid.add(makeTextField(myPropertiesMap.get("Name")),1, 0);
        grid.add(new Label(myPropertiesMap.get("Description")), 0, 1);
        grid.add(makeTextField(myPropertiesMap.get("Description")), 1, 1);
        grid.add(new Label(myPropertiesMap.get("Tags")), 0, 2);
        grid.add(makeRadioButtons(myPropertiesMap.get("Tags")), 1, 2);
        grid.add(new Label(myPropertiesMap.get("Template")), 0, 3);
        grid.add(makeDropdown(myPropertiesMap.get("Template")), 1, 3);
        grid.add(new Label(myPropertiesMap.get("SetImage")), 0, 4);
//        grid.add(makeImagePicker(myPropertiesMap.get("SetImage")), 1, 4);
        Button imagePicker = makeImagePicker(myPropertiesMap.get("SetImage"));
//        imagePicker.setId(MODAL_ID_BUNDLE.getString(IMAGE_PICKER_ID));
        grid.add(imagePicker, 1, 4);


        this.getDialogPane().setContent(grid);
    } // TODO: make classes for each of these elements and use java reflection to create them. for
    // TODO: styling use the last .NAME in the properties file to get the styling id

}