package oogasalad.frontend.components.modals.SubInputModals;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import oogasalad.frontend.components.modals.InputModal;

public class CreateGameModal extends InputModal {

    private Map<String, String> myPropertiesMap;

    /**
     * Constructor for the CreateGameModal dialog
     */
    public CreateGameModal() {
        super("createGame");
    }

    /**
     * Creates the dialog pane for the CreateGameModal dialog
     * 
     * @return
     */
    @Override
    protected DialogPane createDialogPane() {
        myPropertiesMap = super.getPropertiesMap();

        this.getDialogPane().setHeaderText(myPropertiesMap.get("title"));
        myPropertiesMap.remove("title");

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
        grid.add(makeImagePicker(myPropertiesMap.get("SetImage")), 1, 4);
        
        this.getDialogPane().setContent(grid);
    }

}