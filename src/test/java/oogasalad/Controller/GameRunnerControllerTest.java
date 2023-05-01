package oogasalad.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Button;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.GameRunnerObject;
import oogasalad.frontend.components.gameObjectComponent.GameRunner.gameObjectVisuals.AbstractSelectableVisual;
import oogasalad.frontend.managers.NodeRemovedListener;
import oogasalad.gamerunner.backend.Game;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameRunnerControllerTest {
    private static GameRunnerController gameRunnerController;
    private final String gameName = "checkers";
    private final String directory = "data/games/checkers/";
    private final ArrayList<String> gameTypeData = new ArrayList<>();
    private final String redTest = "data/games/checkers/".substring(0, "data/games/checkers/".lastIndexOf("/")) + "/assets/red.png";
    private final String lightTest = "data/games/checkers/".substring(0, "data/games/checkers/".lastIndexOf("/")) + "/assets/light.png";
    @BeforeEach
    void setUp() {
        gameTypeData.add("local");
        gameRunnerController = new GameRunnerController(gameName, gameTypeData);
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
        GameController.DropZoneParameters params = new GameController.DropZoneParameters("test",u,s,500,500,10,10);
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
        //ObservableList<Node> gameObjectVisuals = gameRunnerController.getGameObjectVisuals();
        //Node lastNode = gameObjectVisuals.get(gameObjectVisuals.size()-1);
        //System.out.println(lastNode.getTranslateX());
        //System.out.println(lastNode.getTranslateY());
        //gameRunnerController.movePiece("blackPiece11", "DropZone36");

    }

    @Test
    void removePiece() {
    }

    @Test
    void setObjectImage() {
    }

    @Test
    void isObjectPlayable() {
    }

    @Test
    void getGameObjectVisuals() {
    }

    @Test
    void passGameId() {
    }

    @Test
    void addTextObject() {
    }

    @Test
    void updateTextObject() {
    }

    @Test
    void testAssignUndoButtonAction() {
    }

    @Test
    void testSelect() {
    }

    @Test
    void testAddDropZone() {
    }

    @Test
    void testAddPiece() {
    }

    @Test
    void testSetClickable() {
    }

    @Test
    void testMovePiece() {
    }

    @Test
    void testRemovePiece() {
    }

    @Test
    void testSetObjectImage() {
    }

    @Test
    void testIsObjectPlayable() {
    }

    @Test
    void testGetGameObjectVisuals() {
    }

    @Test
    void testPassGameId() {
    }

    @Test
    void testAddTextObject() {
    }

    @Test
    void testUpdateTextObject() {
    }
}