package oogasalad.frontend.components.modals;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

import java.util.Map;
import java.util.ArrayList;

public class InputModal extends Modal {

    final public static int GAP = 10;
    final public static int INSET_TOP = 20;
    final public static int INSET_RIGHT = 150;
    final public static int INSET_BOTTOM = 10;
    final public static int INSET_LEFT = 10;

    private Map<String, String> myPropertiesMap;
    private String myTitle;

    /**
     * Constructor for InputModal
     * 
     * @param title
     */
    public InputModal(String title) {
        super(title);
        myTitle = title;
    }

    /**
     * Default constructor for InputModal
     */
    public InputModal() {
        super();
    }

    /**
     * Creates the dialog pane for the modal and gives it the appropriate content
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

        setContentAsTextField(stringFields);

        return this.getDialogPane();
    }

    /**
     * Uses the array list of strings to create text fields as part of a grid and
     * add them to the dialog pane
     * 
     * @return
     */
    protected void setContentAsTextField(ArrayList<String> fields) {
        GridPane grid = createGrid();

        ArrayList<TextField> textFields = new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            System.out.println(fields.get(i));
            grid.add(new Label(fields.get(i)), 0, i);
            grid.add(makeTextField(fields.get(i)), 1, i);
            textFields.add(makeTextField(fields.get(i)));
        }

        this.getDialogPane().setContent(grid);
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

    @Override
    protected Object convertResult(ButtonType buttonType) {
        if (buttonType == ButtonType.OK) {
            return getDialogPane().getContent().toString();
        } else {
            return null;
        }
    }

}
