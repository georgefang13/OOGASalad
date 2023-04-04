package oogasalad.frontend.components.modals;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class ModalFactory extends Modal {

  @Override
  protected DialogPane createDialogPane() {
    return null;
  }

  @Override
  protected Object convertResult(ButtonType buttonType) {
    return null;
  }
}
