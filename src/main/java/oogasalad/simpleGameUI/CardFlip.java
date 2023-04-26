package oogasalad.simpleGameUI;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;

import java.util.List;
import java.util.Map;

public class CardFlip {

    public static void main(String[] args) {

        IdManager<Ownable> manager = new IdManager<>();

        String folder = "data/games/cardflip/";

        saveDropZones(manager, folder);
        saveGameObjects(folder);
        saveRules(folder);
        saveVariables(folder);
        saveFSM(folder);

    }

    static void saveDropZones(IdManager<Ownable> manager, String folder){
        FileManager fm = new FileManager();

        fm.addContent(String.valueOf(100), "dz1", "position", "x");
        fm.addContent(String.valueOf(100), "dz1", "position", "y");
        fm.addContent(String.valueOf(50), "dz1", "position", "width");
        fm.addContent(String.valueOf(100), "dz1", "position", "height");
        fm.addEmptyObject("dz1", "connections");
        fm.addContent("board", "dz1", "classes");

        fm.addContent(String.valueOf(250), "dz2", "position", "x");
        fm.addContent(String.valueOf(100), "dz2", "position", "y");
        fm.addContent(String.valueOf(50), "dz2", "position", "width");
        fm.addContent(String.valueOf(100), "dz2", "position", "height");
        fm.addEmptyObject("dz2", "connections");
        fm.addContent("board", "dz2", "classes");

        fm.saveToFile(folder + "layout.json");
    }

    static void saveGameObjects(String folder){
        FileManager fm = new FileManager();

        fm.addContent("9heart.png", "g1", "image");
        fm.addContent("0", "g1", "owner");
        fm.addContent("50", "g1", "size");
        fm.addContent("dz1", "g1", "location");
        fm.addContent("card", "g1", "classes");
        fm.addContent("img1", "g1", "owns");

        fm.addContent("acespade.png", "g2", "image");
        fm.addContent("0", "g2", "owner");
        fm.addContent("50", "g2", "size");
        fm.addContent("dz2", "g2", "location");
        fm.addContent("card", "g2", "classes");
        fm.addContent("img2", "g2", "owns");

        fm.saveToFile(folder + "objects.json");
    }

    static void saveVariables(String folder){
        FileManager fm = new FileManager();

        fm.addContent("g1", "img1", "owner");
        String v1 = "9heart.png";
        fm.addContent(v1, "img1", "value");
        fm.addContent(String.class.getName(), "img1", "type");
        fm.addContent("img", "img1", "classes");

        fm.addContent("g2", "img2", "owner");
        String v2 = "acespade.png";
        fm.addContent(v2, "img2", "value");
        fm.addContent(String.class.getName(), "img2", "type");
        fm.addContent("img", "img2", "classes");

        fm.saveToFile(folder + "variables.json");
    }

    static void saveRules(String folder){
        String flip = """
                to flip [ :piece ] [
                    ifelse hasclass :piece "flipped [
                        removeclass "flipped :piece
                        make :vars objchildren :piece "img
                        make :img item 0 :vars
                        setobjimg :piece :img
                    ] [
                        setobjimg :piece "cardback.png
                        putclass "flipped :piece
                    ]
                ]
                """;

        FileManager fm = new FileManager();
        fm.addContent(flip, "card", "flip");
        fm.saveToFile(folder + "rules.json");
    }

    static void saveFSM(String folder){
        // INIT
            String INITto = "make :game_state_output \"CHOOSE_PIECE";

        // CHOOSE_PIECE
            String CHOOSEinit = """
                    makeallavailable fromgameclass "card
                    """;
            String CHOOSEsetValue = "make :piece_selected fromgame :game_state_input getrule :piece_selected \"flip aslist [ :piece_selected ]";
            String CHOOSEto = "make :game_state_output \"DONE";

        // DONE
            String DONEleave = "gotonextplayer";
            String DONEto = "make :game_state_output \"INIT";

        // build the FSM
        FileManager fm = new FileManager();
        fm.addContent("", "states", "INIT", "init");
        fm.addContent("","states",  "INIT", "leave");
        fm.addContent("","states",  "INIT", "setValue");
        fm.addContent(INITto,"states",  "INIT", "to");
        fm.addContent(CHOOSEinit, "states", "CHOOSE_PIECE", "init");
        fm.addContent("","states",  "CHOOSE_PIECE", "leave");
        fm.addContent(CHOOSEsetValue,"states",  "CHOOSE_PIECE", "setValue");
        fm.addContent(CHOOSEto,"states",  "CHOOSE_PIECE", "to");
        fm.addContent("", "states", "DONE", "init");
        fm.addContent(DONEleave.replace("\n", " "), "states", "DONE", "leave");
        fm.addContent("", "states", "DONE", "setValue");
        fm.addContent(DONEto.replace("\n", " "), "states", "DONE", "to");

        fm.addEmptyArray("goals");

        fm.saveToFile(folder + "fsm.json");
    }

}
