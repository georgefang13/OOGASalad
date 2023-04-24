package oogasalad;

import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

import java.util.List;
import java.util.Map;

public class FileMaker {
    public static void main(String[] args) {

        saveDropZones();
        saveGameObjects();

    }

    static void saveDropZones(){
        FileManager fm = new FileManager();
        List<DropZone> nodes = BoardCreator.createGrid(3, 3);
        // add board DropZones
        for (DropZone node : nodes) {
            node.addClass("board");
            String id = node.getId();
            String[] parts = id.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);

            fm.addContent(String.valueOf(x), id, "position", "x");
            fm.addContent(String.valueOf(y), id, "position", "y");
            fm.addContent(String.valueOf(100), id, "position", "width");
            fm.addContent(String.valueOf(100), id, "position", "height");

            for (Map.Entry<String, DropZone> dzEdge : node.getEdges().entrySet()) {
                String nodeId = dzEdge.getValue().getId();
                fm.addContent(nodeId, id, "connections", dzEdge.getKey());
            }

            fm.addContent("board", id, "classes");

        }

        // create x and o dropzones
        fm.addContent("200", "XDropZone", "position", "x");
        fm.addContent("200", "XDropZone", "position", "y");
        fm.addContent("100", "XDropZone", "position", "width");
        fm.addContent("300", "XDropZone", "position", "height");

        fm.addEmptyObject("XDropZone", "connections");
        fm.addEmptyArray("XDropZone", "classes");


        fm.addContent("200", "ODropZone", "position", "x");
        fm.addContent("500", "ODropZone", "position", "y");
        fm.addContent("100", "ODropZone", "position", "width");
        fm.addContent("300", "ODropZone", "position", "height");

        fm.addEmptyObject("ODropZone", "connections");
        fm.addEmptyArray("ODropZone", "classes");

        fm.saveToFile("data/tictactoe/layout.json");
    }

    static void saveGameObjects(){
        FileManager fm = new FileManager();

        // X pieces for player 0
        for (int i = 0; i < 5; i++){
            String id = "pieceX" + i;
            fm.addContent("assets/X.png", id, "image");
            fm.addContent("0", id, "owner");
            fm.addContent("0.55", id, "size");

            fm.addContent("XDropZone", id, "location");
            fm.addContent("X", id, "classes");
            fm.addContent("piece", id, "classes");

            fm.addEmptyArray(id, "owns");
        }

        // O pieces for player 1
        for (int i = 0; i < 5; i++){
            String id = "pieceO" + i;
            fm.addContent("assets/O.png", id, "image");
            fm.addContent("0", id, "owner");
            fm.addContent("0.55", id, "size");

            fm.addContent("ODropZone", id, "location");
            fm.addContent("O", id, "classes");
            fm.addContent("piece", id, "classes");

            fm.addEmptyArray(id, "owns");
        }

        fm.saveToFile("data/games/tictactoe/objects.json");
    }

    static void saveFSM(){
        // INIT
            // init
            String INITinit = """
                    to getAvailablePieces [ ] [ 
                        make :pieces fromplayer curplayer \"piece 
                        foreach [ :x :pieces ] [ 
                            make :dz objdz :x
                            if and != null :dz not hasclass :dz \"board [ 
                                makeavailable :x 
                            ] 
                        ] 
                    ] 
                    
                    to getAvailableSpots [ ] [ 
                        make :spots fromgameclass \"board 
                        foreach [ :spot :spots ] [ 
                            if == 0 len dzitems :spot [ 
                                makeavailable :spot 
                            ] 
                        ] 
                    ]
                    """;

    }

}
