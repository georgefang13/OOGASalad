package oogasalad.frontend.modals.actions;

import java.util.Map;
import oogasalad.frontend.modals.ModalController;

public interface ModalAction {
  void execute(ModalController controller, String title, Map<String, String> data);
}
