package oogasalad;

import oogasalad.gameeditor.backend.id.IdManager;
import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

import java.util.List;
import java.util.Map;

public class FileMaker {

    public static void main(String[] args) {

        IdManager<Ownable> manager = new IdManager<>();

        saveDropZones(manager);
        saveGameObjects(manager);
        saveFSM();

    }

    static void saveDropZones(IdManager<Ownable> manager){
        FileManager fm = new FileManager();
        List<DropZone> nodes = BoardCreator.createGrid(8, 8);
        nodes.forEach(manager::addObject);
        // add board DropZones
        for (int i = 0; i < nodes.size(); i++){
            DropZone node = nodes.get(i);
            node.addClass("board");
            int x = i % 8;
            int y = i / 8;

            String id = manager.getId(node);

            fm.addContent(String.valueOf(50 + x*50), id, "position", "x");
            fm.addContent(String.valueOf(50 + y*50), id, "position", "y");
            fm.addContent(String.valueOf(50), id, "position", "width");
            fm.addContent(String.valueOf(50), id, "position", "height");

            for (Map.Entry<String, DropZone> dzEdge : node.getEdges().entrySet()) {
                String nodeId = manager.getId(dzEdge.getValue());
                fm.addContent(nodeId, id, "connections", dzEdge.getKey());
            }

            fm.addContent("board", id, "classes");
        }

        fm.saveToFile("data/games/checkers/layout.json");
    }

    static void saveGameObjects(IdManager<Ownable> manager){
        FileManager fm = new FileManager();

        int counter = 0;
        // add red pieces
        for (int y = 7; y >= 5; y--){
            for (int x = 0; x < 8; x++){
                if (y % 2 == x % 2) continue;

                String id = "redPiece" + counter++;

                int num = y*8 + x + 1;
                String location = "DropZone";
                if (num != 1) location += num;

                fm.addContent("red.png", id, "image");
                fm.addContent("0", id, "owner");
                fm.addContent("30", id, "size");

                fm.addContent(location, id, "location");
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

                fm.addContent("black.png", id, "image");
                fm.addContent("1", id, "owner");
                fm.addContent("30", id, "size");

                fm.addContent(location, id, "location");
                fm.addContent("black", id, "classes");
                fm.addContent("piece", id, "classes");

                fm.addEmptyArray(id, "owns");
            }
        }

        fm.saveToFile("data/games/checkers/objects.json");
    }

    static void saveFSM(){
        // INIT
            String INITinit = """
                    to pieceAvailable [ :dz ] [
                        make :moveset [ [ "UpRight ] [ "UpLeft ] [ "DownRight ] [ "DownLeft ] ]
                        ifelse == curplayer getplayer 0 [
                            make :moveset [ [ "UpRight ] [ "UpLeft ] ]
                        ] [
                            make :moveset [ [ "DownRight ] [ "DownLeft ] ]
                        ]
                             
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
                        make :possible pieceAvailable objdz :p
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
                    make :available_items pieceAvailable :old_dz
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
        fm.addContent(INITinit, "states", "INIT", "init");
        fm.addContent("","states",  "INIT", "leave");
        fm.addContent("","states",  "INIT", "setValue");
        fm.addContent(INITto,"states",  "INIT", "to");
        fm.addContent(CHOOSEinit, "states", "CHOOSE_PIECE", "init");
        fm.addContent("","states",  "CHOOSE_PIECE", "leave");
        fm.addContent(CHOOSEsetValue,"states",  "CHOOSE_PIECE", "setValue");
        fm.addContent(CHOOSEto,"states",  "CHOOSE_PIECE", "to");
        fm.addContent(SQUAREinit, "states", "CHOOSE_SQUARE", "init");
        fm.addContent("", "states", "CHOOSE_SQUARE", "leave");
        fm.addContent(SQUAREsetValue, "states", "CHOOSE_SQUARE", "setValue");
        fm.addContent(SQUAREto, "states", "CHOOSE_SQUARE", "to");
        fm.addContent("", "states", "DONE", "init");
        fm.addContent(DONEleave, "states", "DONE", "leave");
        fm.addContent("", "states", "DONE", "setValue");
        fm.addContent(DONEto, "states", "DONE", "to");

        fm.addEmptyArray("goals");

        fm.saveToFile("data/games/checkers/fsm.json");
    }

}
