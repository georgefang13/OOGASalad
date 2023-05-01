package oogasalad.Controller;

import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Button;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.DropZoneFE;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameRunnerControllerTest extends ApplicationTest{
    private static GameRunnerController gameRunnerController;
    private final String gameName = "checkers";
    private final String directory = "data/games/checkers/";
    private final ArrayList<String> gameTypeData = new ArrayList<>();
    private final String redTest = "data/games/checkers/".substring(0, "data/games/checkers/".lastIndexOf("/")) + "/assets/red.png";
    private final String lightTest = "data/games/checkers/".substring(0, "data/games/checkers/".lastIndexOf("/")) + "/assets/light.png";
    @BeforeEach
    void setUp() {
        JFXPanel jfxPanel = new JFXPanel();
        gameTypeData.add("local");
        gameRunnerController = new GameRunnerController(gameName, gameTypeData,2);

    }
    @Test
    void select() {
        String pieceId = "redPiece10";
        String nextPossibleDrop = "DropZone36";
        gameRunnerController.select(pieceId);
        assertTrue(gameRunnerController.isObjectPlayable(nextPossibleDrop));
    }

    @Test
    void addDropZone() {
        AbstractSelectableVisual.SelectableVisualParams u = new AbstractSelectableVisual.SelectableVisualParams(true,lightTest);
        AbstractSelectableVisual.SelectableVisualParams s = new AbstractSelectableVisual.SelectableVisualParams(true,lightTest);
        GameController.DropZoneParameters params = new GameController.DropZoneParameters("test",u,s,500,500,10,10,DropZoneFE.DropZoneDistribution.HORIZONTAL);
        gameRunnerController.addDropZone(params);
        assertFalse(gameRunnerController.isObjectPlayable("test"));
    }

    @Test
    void addPiece() {
        gameRunnerController.addPiece("test",redTest,"DropZone36",true,redTest,10,10);
        assertFalse(gameRunnerController.isObjectPlayable("test"));
    }

    @Test
    void setClickable() {
        List<String> ids = new ArrayList<>();
        assertFalse(gameRunnerController.isObjectPlayable("redPiece1"));
        ids.add("redPiece1");
        gameRunnerController.setClickable(ids);
        assertTrue(gameRunnerController.isObjectPlayable("redPiece1"));
    }

    @Test
    void movePiece() {
        ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
        Node lastNode = gameObjectVisuals.get(gameObjectVisuals.size()-1);
        double startX = lastNode.getTranslateX();
        double startY = lastNode.getTranslateY();
        gameRunnerController.movePiece("blackPiece11", "DropZone36");
        double endX = lastNode.getTranslateX();
        double endY = lastNode.getTranslateY();
        assertNotEquals(startX,endX);
        assertNotEquals(startY,endY);
    }

    @Test
    void removePiece() {
        ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
        Node lastNode = gameObjectVisuals.get(gameObjectVisuals.size()-1);
        gameRunnerController.removePiece("blackPiece11");
        assertFalse(gameObjectVisuals.contains(lastNode));
    }

    @Test
    void setObjectImage() {
        ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
        Node lastNode = gameObjectVisuals.get(gameObjectVisuals.size()-1);
        gameRunnerController.setObjectImage("blackPiece11",redTest);
        assertFalse(gameObjectVisuals.contains(lastNode));
    }

    @Test
    void isObjectPlayable() {
        String pieceId = "redPiece10";
        String nextPossibleDrop = "DropZone36";
        gameRunnerController.select(pieceId);
        assertTrue(gameRunnerController.isObjectPlayable(nextPossibleDrop));
    }

    @Test
    void getGameObjectVisuals() {
        ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
        assertNotNull(gameRunnerController.getGameObjectVisuals());
    }
    @Test
    void testAssignUndoButtonAction() {
        Button undoButton = new Button("Undo");
        gameRunnerController.assignUndoButtonAction(undoButton);
        assertTrue(gameRunnerController.isObjectPlayable("redPiece10"));
        gameRunnerController.select("redPiece10");
        assertFalse(gameRunnerController.isObjectPlayable("redPiece10"));
        undoButton.fire();
        assertTrue(gameRunnerController.isObjectPlayable("redPiece10"));
    }
}