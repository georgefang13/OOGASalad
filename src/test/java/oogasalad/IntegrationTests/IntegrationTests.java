package oogasalad.IntegrationTests;

import java.awt.Button;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oogasalad.frontend.components.ComponentsFactory;
import oogasalad.frontend.modals.subInputModals.CreateNewModal;
import oogasalad.frontend.windows.WindowController;
import oogasalad.frontend.windows.WindowMediator;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class IntegrationTests extends DukeApplicationTest {

  @Override
  public void start(Stage stage) {
    WindowMediator mediator = new WindowController();
  }

  @Test
  public void SendComponent(){
//    CreateNewModal modal = new CreateNewModal();
  }
}
