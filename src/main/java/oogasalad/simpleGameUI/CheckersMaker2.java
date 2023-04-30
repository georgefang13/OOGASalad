package oogasalad.simpleGameUI;

import oogasalad.sharedDependencies.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

import java.util.List;
import java.util.Map;

public class CheckersMaker2 {

    public static void main(String[] args) {

        IdManager<Ownable> manager = new IdManager<>();

        String folder = "data/games/checkers/";

        saveDropZones(manager, folder);
        saveGameObjects(folder);
        saveRules(folder);
        saveFSM(folder);

    }

    static void saveDropZones(IdManager<Ownable> manager, String folder){
        FileManager fm = new FileManager();
        FileManager fefm = new FileManager();

        List<DropZone> nodes = BoardCreator.createGrid(8, 8);
        nodes.forEach(manager::addObject);
        // add board DropZones
        for (int i = 0; i < nodes.size(); i++){
            DropZone node = nodes.get(i);
            node.addClass("board");
            int x = i % 8;
            int y = i / 8;

            String id = manager.getId(node);

            fefm.addContent(String.valueOf(50 + x*50), id, "x");
            fefm.addContent(String.valueOf(50 + y*50), id, "y");
            fefm.addContent(String.valueOf(50), id, "width");
            fefm.addContent(String.valueOf(50), id, "height");
            if (i % 2 == 0) {
                fefm.addContent(true, id, "unselected", "hasImage");
                fefm.addContent("dark.png", id, "unselected", "param");
                fefm.addContent(true, id, "selected", "hasImage");
                fefm.addContent("darkselected.png", id, "selected", "param");
            }
            else {
                fefm.addContent(true, id, "unselected", "hasImage");
                fefm.addContent("light.png", id, "unselected", "param");
                fefm.addContent(true, id, "selected", "hasImage");
                fefm.addContent("lightselected.png", id, "selected", "param");
            }

            for (Map.Entry<String, DropZone> dzEdge : node.getEdges().entrySet()) {
                String nodeId = manager.getId(dzEdge.getValue());
                fm.addContent(nodeId, id, "connections", dzEdge.getKey());
            }

            fm.addContent("board", id, "classes");
            if (y == 0) fm.addContent("endzone", id, "classes");
            if (y == 7) fm.addContent("endzone", id, "classes");
        }

        fm.saveToFile(folder + "layout.json");
        fefm.saveToFile(folder + "frontend/layout.json");
    }

    static void saveGameObjects(String folder){
        FileManager fm = new FileManager();
        FileManager fefm = new FileManager();

        int counter = 0;
        // add red pieces
        for (int y = 7; y >= 5; y--){
            for (int x = 0; x < 8; x++){
                if (y % 2 == x % 2) continue;

                String id = "redPiece" + counter++;

                int num = y*8 + x + 1;
                String location = "DropZone";
                if (num != 1) location += num;

                fefm.addContent("red.png", id, "image");
                fm.addContent("0", id, "owner");
                fefm.addContent("30", id, "size");

                fm.addContent(location, id, "location");
                fefm.addContent(location, id, "location");
                fm.addContent("red", id, "classes");
                fm.addContent("piece", id, "classes");

                fm.addEmptyArray(id, "owns");
            }
        }

        counter = 0;
        // add black pieces
        for (int y = 0; y < 3; y++){
            for (int x = 0; x < 8; x++){
                if (y % 2 == x % 2) continue;

                String id = "blackPiece" + counter++;

                int num = y*8 + x + 1;
                String location = "DropZone";
                if (num != 1) location += num;

                fefm.addContent("black.png", id, "image");
                fm.addContent("1", id, "owner");
                fefm.addContent("30", id, "size");

                fm.addContent(location, id, "location");
                fefm.addContent(location, id, "location");
                fm.addContent("black", id, "classes");
                fm.addContent("piece", id, "classes");

                fm.addEmptyArray(id, "owns");
            }
        }

        fm.saveToFile(folder + "objects.json");
        fefm.saveToFile(folder + "frontend/objects.json");
    }

    static void saveRules(String folder){
        String redAvailable = """
                to available [ :dz ] [
                    make :moveset [ [ "UpRight ] [ "UpLeft ] ]
                    make :ret pieceAvailable :dz :moveset
                    return :ret
                ]
                """;
        String toKing = """
                to checkKing [ :piece ] [
                    make :dz objdz :piece
                    if hasclass :dz "endzone [ 
                        putclass "king :piece 
                        ifelse hasclass :piece "red [
                            setobjimg :piece "redKing.png
                        ] [
                            setobjimg :piece "blackKing.png
                        ]
                    ]
                ]
                """;

        String blackAvailable = """
                to available [ :dz ] [
                    make :moveset [ [ "DownRight ] [ "DownLeft ] ]
                    make :ret pieceAvailable :dz :moveset
                    return :ret
                ]
                """;
        String kingAvailable = """
                to available [ :dz ] [
                    make :moveset [ [ "DownRight ] [ "DownLeft ] [ "UpRight ] [ "UpLeft ] ]
                    make :ret pieceAvailable :dz :moveset
                    return :ret
                ]
                """;

        FileManager fm = new FileManager();
        fm.addContent(toKing, "piece", "checkKing");
        fm.addContent(redAvailable, "red", "available");
        fm.addContent(blackAvailable, "black", "available");
        fm.addContent(kingAvailable, "king", "available");
        fm.saveToFile(folder + "rules.json");
    }

    static void saveFSM(String folder){
        // INIT
            String INITinit = """
                    to pieceAvailable [ :dz :moveset ] [
                             
                        make :available [ ]
                        make :available_paths [ ]
                        
                        foreach [ :path :moveset ] [
                            make :dz1 dzfollow :dz :path
                            if == null :dz1 [ continue ]
                            
                            ifelse dzempty :dz1 [
                                additem :dz1 :available
                                additem aslist [ :path ] :available_paths
                            ] [
                                make :dz2 dzfollow :dz1 :path
                                if or == null :dz2 not dzempty :dz2 [ continue ]
                                if  != curplayer owner item 0 dzitems :dz1 [
                                    additem :dz2 :available
                                    additem aslist [ :path :path ] :available_paths
                                ]
                            ]
                            
                        ]
                        
                        make :ret aslist [ :available :available_paths ]
                                     
                        return :ret
                    ]
                        """;
            String INITto = "make :game_state_output \"CHOOSE_PIECE";

        // CHOOSE_PIECE
            String CHOOSEinit = """
                    foreach [ :p fromplayer curplayer "piece  ] [
                        make :possible getrule :p "available [ objdz :p ]
                        if != 0 len item 0 :possible [
                            makeavailable :p
                        ]
                    ]
                    """;
            String CHOOSEsetValue = "make :piece_selected fromgame :game_state_input\n" +
                    "make :old_dz objdz :piece_selected";
            String CHOOSEto = "make :game_state_output \"CHOOSE_SQUARE";

        // CHOOSE_SQUARE
            String SQUAREinit = """
                    make :available_items getrule :piece_selected "available [ :old_dz ]
                    make :available item 0 :available_items
                    
                    if >= len :available 0 [
                        make :available_paths item 1 :available_items
                        makeallavailable :available
                    ]
                    """;
            String SQUAREsetValue = """            
                    make :dz fromgame :game_state_input
                    make :choice_num index :dz :available
                    make :chosen_path item :choice_num :available_paths
                    movepiece :piece_selected :dz
                    
                    getrule :piece_selected "checkKing [ :piece_selected ]
                                        
                    if == 2 len :chosen_path [
                        make :dir item 0 :chosen_path
                        make :dz_cross dzfollow :old_dz :dir
                        make :piece item 0 dzitems :dz_cross
                        if != null :piece [
                            removepiece :piece
                        ]
                    ]
                    """;
            String SQUAREto = "make :game_state_output \"DONE";

        // DONE
            String DONEleave = "gotonextplayer";
            String DONEto = "make :game_state_output \"INIT";

        // build the FSM
        FileManager fm = new FileManager();
        fm.addContent(INITinit.replace("\n", " "), "states", "INIT", "init");
        fm.addContent("","states",  "INIT", "leave");
        fm.addContent("","states",  "INIT", "setValue");
        fm.addContent(INITto.replace("\n", " "),"states",  "INIT", "to");
        fm.addContent(CHOOSEinit.replace("\n", " "), "states", "CHOOSE_PIECE", "init");
        fm.addContent("","states",  "CHOOSE_PIECE", "leave");
        fm.addContent(CHOOSEsetValue.replace("\n", " "),"states",  "CHOOSE_PIECE", "setValue");
        fm.addContent(CHOOSEto,"states",  "CHOOSE_PIECE", "to");
        fm.addContent(SQUAREinit.replace("\n", " "), "states", "CHOOSE_SQUARE", "init");
        fm.addContent("", "states", "CHOOSE_SQUARE", "leave");
        fm.addContent(SQUAREsetValue.replace("\n", " "), "states", "CHOOSE_SQUARE", "setValue");
        fm.addContent(SQUAREto.replace("\n", " "), "states", "CHOOSE_SQUARE", "to");
        fm.addContent("", "states", "DONE", "init");
        fm.addContent(DONEleave.replace("\n", " "), "states", "DONE", "leave");
        fm.addContent("", "states", "DONE", "setValue");
        fm.addContent(DONEto.replace("\n", " "), "states", "DONE", "to");

        fm.addEmptyArray("goals");

        fm.saveToFile(folder + "fsm.json");
    }

}
