package oogasalad.Controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Set;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import oogasalad.frontend.windows.WindowController;
import oogasalad.frontend.windows.WindowMediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class EmptyTest extends DukeApplicationTest {

  private Set<TextField> userNamePassword;
  @Override
  public void start(Stage stage) throws Exception {
    WindowMediator mediator = new WindowController();
  }
  @Test
  public void playGametoCompleteion() throws AWTException {
    userNamePassword = lookup(".username-field").queryAll();
    System.out.println(userNamePassword);
    for(TextField tf: userNamePassword){
      clickOn(tf).write("Han");
    }
    clickOn(".login");
    sleep(200);
    Set<Button> button = lookup(".icon-button").queryAll();
    Robot robot = new Robot();
    for(Button btn:button){
      clickOn(btn);
      mouseClick(robot);
    }
    sleep(300);
    for(Button btn:button){
      clickOn(btn);
    }
  }

  private void mouseClick(Robot robot) {
    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
  }
}
