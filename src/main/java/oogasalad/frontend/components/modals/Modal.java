package oogasalad.frontend.components.modals;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public abstract class Modal<T> extends Dialog<T> {

    /**
     * Constructor for the modal dialog
     * @param title
     */
    public Modal(String title) {
        setTitle(title);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        setDialogPane(createDialogPane());
        setResultConverter(this::convertResult);
    }

    public Modal() {
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
    protected abstract DialogPane createDialogPane();

    /**
     * Converts the button type to the result
     * @param buttonType
     * @return
     */
    protected abstract T convertResult(ButtonType buttonType);
}
