package oogasalad;

import oogasalad.gameeditor.backend.ownables.gameobjects.BoardCreator;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

import java.util.List;
import java.util.Map;

public class TestMain {
    public static void main(String[] args) {


        // add pieces
        FileManager fm = new FileManager();

        // X pieces for player 0
        for (int i = 0; i < 5; i++){
            String id = "piece" + i;
            fm.addContent("assets/X.png", id, "image");
            fm.addContent("0", id, "owner");
            fm.addContent("100", id, "size");
            fm.addContent("100", id, "size");

            fm.addContent("XDropZone", id, "position", "location");
            fm.addContent("X", id, "classes");
            fm.addContent("peice", id, "classes");
        }

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


        fm.addContent("200", "ODropZone", "position", "x");
        fm.addContent("500", "ODropZone", "position", "y");
        fm.addContent("100", "ODropZone", "position", "width");
        fm.addContent("300", "ODropZone", "position", "height");

        fm.saveToFile("data/tictactoe/layout.json");
    }
}
