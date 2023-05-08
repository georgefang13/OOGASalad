package oogasalad.frontend;


import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import oogasalad.frontend.windows.WindowController;
import oogasalad.frontend.windows.WindowMediator;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Iterator;
import java.util.Set;

public class MultiplayerTest extends DukeApplicationTest {

    private Set<TextField> userNamePassword;
    private TextField username;

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
        Set<ImageView> gameButtons = lookup(".game-box-image").queryAll();
        Iterator<ImageView> iterator = gameButtons.iterator();
        ImageView gameButton = iterator.next();
        for (int i =0; i < 3; i++){
            gameButton = iterator.next();
        }
        clickOn(gameButton);
        sleep(750);
        Robot robot = new Robot();
        mouseMoveQuick(400, 190, robot);
        mouseClick(robot);

        sleep(2000);

        String[] words = getDialogMessage().split(" ");
        String code = words[words.length - 1];
        System.out.println("GOT CODE " + code);


        mouseMoveQuick(500, 142, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(500, 500, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        clickOn(gameButton);
        mouseMoveQuick(650, 190, robot);
        mouseClick(robot);

        for (char c : code.toCharArray()) {
            robot.keyPress(c);
            robot.keyRelease(c);
            System.out.println(c);
        }

        mouseMoveQuick(750, 190, robot);
        mouseClick(robot);

        sleep(1000);

        mouseMoveQuick(400, 1000, robot);
        mouseClick(robot);

        int red1x = 520;
        int red1y = 998;

        int topwindowx = 400;
        int topwindowy = 200;

        int yellow1x = 575;
        int yellow1y = 675;

        int bottomwindowx = 400;
        int bottomwindowy = 1000;

        mouseMoveQuick(red1x, red1y, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(red1x - 10, red1y - 100, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        mouseMoveQuick(topwindowx, topwindowy, robot);
        mouseClick(robot);

        mouseMoveQuick(yellow1x, yellow1y, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(yellow1x - 40, yellow1y - 150, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        mouseMoveQuick(bottomwindowx, bottomwindowy, robot);
        mouseClick(robot);

        mouseMoveQuick(red1x, red1y, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(red1x + 40, red1y - 100, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        mouseMoveQuick(topwindowx, topwindowy, robot);
        mouseClick(robot);

        mouseMoveQuick(yellow1x, yellow1y, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(yellow1x + 10, yellow1y - 150, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        mouseMoveQuick(bottomwindowx, bottomwindowy, robot);
        mouseClick(robot);

        mouseMoveQuick(red1x, red1y, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(red1x + 90, red1y - 100, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        mouseMoveQuick(topwindowx, topwindowy, robot);
        mouseClick(robot);

        mouseMoveQuick(yellow1x, yellow1y, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(yellow1x + 60, yellow1y - 150, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        mouseMoveQuick(bottomwindowx, bottomwindowy, robot);
        mouseClick(robot);

        mouseMoveQuick(red1x, red1y, robot);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        moveMouseAnimated(red1x + 140, red1y - 100, robot);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    @Test
    public void playMonopoly() throws AWTException {
        userNamePassword = lookup(".username-field").queryAll();
        System.out.println(userNamePassword);
        for(TextField tf: userNamePassword){
            clickOn(tf).write("Han");
        }
        clickOn(".login");
        sleep(200);
        Set<ImageView> gameButtons = lookup(".game-box-image").queryAll();
        Iterator<ImageView> it = gameButtons.iterator();
        for (int i = 0; i < 6; i++) it.next();
        ImageView gameButton = it.next();
        clickOn(gameButton);
        sleep(750);
        Robot robot = new Robot();
        moveMouseAnimated(300, 185, robot);
        mouseClick(robot);
        moveMouseAnimated(630, 645, robot);

        for (int i = 0; i < 5; i++) {
            moveMouseAnimated(630, 645, robot);
            // drag dice
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            moveMouseAnimated(650, 630, robot);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            // move to buy
            mouseClick(robot);
            moveMouseAnimated(540, 735, robot);
            mouseClick(robot);
            sleep(500);

            // click trade
            mouseClick(robot);
            sleep(500);
        }
    }

    /**
     * This method moves the mouse cursor to the target location in small steps.
     *
     * @param x     Target x coordinate
     * @param y     Target y coordinate
     * @param robot Robot instance
    */
    private void moveMouseAnimated(int x, int y, Robot robot) {
        Point currentLocation = java.awt.MouseInfo.getPointerInfo().getLocation();

        // calculate the distance to move the mouse cursor
        int dx = x - currentLocation.x;
        int dy = y - currentLocation.y;

        // calculate the number of steps to take
        int steps = 100;
        double stepX = (double) dx / steps;
        double stepY = (double) dy / steps;

        // move the mouse cursor in small steps
        for (int i = 0; i < steps; i++) {
            robot.mouseMove((int) (currentLocation.x + i * stepX), (int) (currentLocation.y + i * stepY));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // handle exception
            }
        }
    }
    private void mouseMoveQuick(int x, int y, Robot robot) {
        robot.mouseMove(x, y);
    }

    private void mouseClick(Robot robot) {
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
