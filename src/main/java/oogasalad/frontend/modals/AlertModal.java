package oogasalad.frontend.modals;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class AlertModal extends Modal<ButtonType> {

    private String message;
    private ButtonType[] buttonTypes;

    public AlertModal(String title, String message, ButtonType... buttonTypes) {
        super(title);
        this.message = message;
        this.buttonTypes = buttonTypes;
    }

    @Override
    protected DialogPane createDialogPane() {
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(message);
        dialogPane.getButtonTypes().addAll(buttonTypes);
        return dialogPane;
    }

    @Override
    protected ButtonType convertResult(ButtonType buttonType) {
        return buttonType;
    }
}
