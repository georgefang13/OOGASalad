package oogasalad.backend.ownables.gameobjects.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BoardGraphTest {

    private BoardGraph g;

    // create new instance of test object before each test is run
    @BeforeEach
    void setup () {
        g = new BoardGraph();
    }

    @Test
    void addMultipleNodes(){
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");

        assertEquals("A", g.getNode("A").getId());
        assertEquals("B", g.getNode("B").getId());
        assertEquals("C", g.getNode("C").getId());
        assertEquals(3, g.getNodes().size());
    }

    @Test
    void addExistingNodes(){
        assertTrue(g.addNode("A"));
        assertTrue(g.addNode("B"));
        assertTrue(g.addNode("C"));

        assertFalse(g.addNode("A"));
        assertFalse(g.addNode("B"));
        assertFalse(g.addNode("C"));

        assertEquals("A", g.getNode("A").getId());
        assertEquals("B", g.getNode("B").getId());
        assertEquals("C", g.getNode("C").getId());
        assertEquals(3, g.getNodes().size());
    }

    ArrayList<Map.Entry<String, BoardGraph.BoardGraphNode>> getSortedEdges(BoardGraph.BoardGraphNode node){
        ArrayList<Map.Entry<String, BoardGraph.BoardGraphNode>> sortedEdges = new ArrayList<>(node.getEdges().entrySet());
        sortedEdges.sort(Comparator.comparing(e -> e.getValue().getId()));
        return sortedEdges;
    }

    @Test
    void addConnections(){
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");

        assertTrue(g.addConnection("A", "B", "AB"));
        assertTrue(g.addConnection("B", "C", "BC"));
        assertTrue(g.addConnection("C", "A", "CA"));

        assertEquals("A", g.getNode("A").getId());
        assertEquals("B", g.getNode("B").getId());
        assertEquals("C", g.getNode("C").getId());
        assertEquals(3, g.getNodes().size());

        assertEquals("B", getSortedEdges(g.getNode("A")).get(0).getValue().getId());
        assertEquals("C", getSortedEdges(g.getNode("B")).get(0).getValue().getId());
        assertEquals("A", getSortedEdges(g.getNode("C")).get(0).getValue().getId());

        assertEquals("AB", getSortedEdges(g.getNode("A")).get(0).getKey());
        assertEquals("BC", getSortedEdges(g.getNode("B")).get(0).getKey());
        assertEquals("CA", getSortedEdges(g.getNode("C")).get(0).getKey());
    }

    @Test
    void addNullNode(){
        try {
            g.addNode(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Node name cannot be null", e.getMessage());
        }

        try {
            g.addNode("");
        }
        catch (IllegalArgumentException e){
            assertEquals("Node name cannot be empty", e.getMessage());
        }

    }

    @Test
    void addNullConnections(){
        try {
            g.addNode(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Node name cannot be null", e.getMessage());
        }

        g.addNode("A");
        g.addNode("B");
        g.addNode("C");

        try { g.addConnection(null, "B", "AB"); }
        catch (IllegalArgumentException e){
            assertEquals("Node name cannot be null", e.getMessage());
        }
        try { g.addConnection("A", null, "AB"); }
        catch (IllegalArgumentException e) {
            assertEquals("Node name cannot be null", e.getMessage());
        }
        try { g.addConnection("A", "B", null); }
        catch (IllegalArgumentException e){
            assertEquals("Edge name cannot be null", e.getMessage());
        }

        g.addConnection("A", "B", "AB");
        g.addConnection("B", "C", "BC");
        g.addConnection("C", "A", "CA");

        assertFalse(g.addConnection("A", "B", "AB"));
        assertFalse(g.addConnection("B", "C", "BC"));
        assertFalse(g.addConnection("C", "A", "CA"));

        assertTrue(g.addConnection("B", "A", "BA"));
    }

    @Test
    void addRedundantEdges(){
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");

        g.addConnection("A", "B", "AB");
        g.addConnection("B", "C", "BC");
        g.addConnection("C", "A", "CA");

        assertFalse(g.addConnection("A", "B", "AB"));
        assertFalse(g.addConnection("B", "C", "BC"));
        assertFalse(g.addConnection("C", "A", "CA"));

        assertFalse(g.addConnection("A", "B", "AB2"));
        assertFalse(g.addConnection("B", "C", "BC2"));
        assertFalse(g.addConnection("C", "A", "CA2"));

        assertTrue(g.addConnection("B", "A", "BA"));
    }

    @Test
    void testEquals(){
        g.addNode("A");
        g.addNode("B");
        Boolean x = true;
        assertNotEquals(g.getNode("A"), x);
        assertNotEquals(null, g.getNode("B"));
        assertNotEquals(g.getNode("A"), g.getNode("B"));
        assertEquals(g.getNode("A"), g.getNode("A"));

        BoardGraph.BoardGraphNode a = new BoardGraph.BoardGraphNode("A");
        BoardGraph.BoardGraphNode b = new BoardGraph.BoardGraphNode("B");
        BoardGraph.BoardGraphNode a2 = new BoardGraph.BoardGraphNode("A");

        assertEquals(a, a2);
        assertNotEquals(a, b);
    }

    @Test
    void testNodeToString(){
        BoardGraph.BoardGraphNode a = new BoardGraph.BoardGraphNode("A");
        BoardGraph.BoardGraphNode b = new BoardGraph.BoardGraphNode("B");
        BoardGraph.BoardGraphNode c = new BoardGraph.BoardGraphNode("Blobfish Tails");

        assertEquals("A", a.toString());
        assertEquals("B", b.toString());
        assertEquals("Blobfish Tails", c.toString());
    }

    @Test
    void testNodeHashcode(){
        BoardGraph.BoardGraphNode a = new BoardGraph.BoardGraphNode("A");
        BoardGraph.BoardGraphNode b = new BoardGraph.BoardGraphNode("B");

        assertEquals("A".hashCode(), a.hashCode());
        assertEquals("B".hashCode(), b.hashCode());
    }

    void makeConnectedSquare(){
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");

        g.addConnection("A", "B", "F");
        g.addConnection("B", "C", "F");
        g.addConnection("C", "D", "F");
        g.addConnection("D", "A", "F");
        g.addConnection("A", "C", "DIAG");
        g.addConnection("C", "A", "DIAG");
        g.addConnection("B", "D", "DIAG");
        g.addConnection("D", "B", "DIAG");
    }

    @Test
    void testCreateGrid(){
        int numRows = 3;
        int numCols = 7;
        g = BoardGraph.createGrid(numRows, numCols);
        assertEquals(numRows * numCols, g.getNodes().size());
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                String id = String.format("%d,%d", i, j);
                assertNotNull(g.getNode(id));
                // test edges
                if (i > 0){
                    assertTrue(g.getNode(id).getEdges().containsKey("Up"));
                }
                if (i < numRows-1){
                    try{
                        assertTrue(g.getNode(id).getEdges().containsKey("Down"));
                    }
                    catch (AssertionError e){
                        System.out.println(id);
                        System.out.println(g.getNode(id).getEdges());
                        throw e;
                    }

                }
                if (j > 0){
                    assertTrue(g.getNode(id).getEdges().containsKey("Left"));
                }
                if (j < numCols-1){
                    assertTrue(g.getNode(id).getEdges().containsKey("Right"));
                }
                if (i > 0 && j > 0){
                    assertTrue(g.getNode(id).getEdges().containsKey("UpLeft"));
                }
                if (i > 0 && j < numCols-1){
                    assertTrue(g.getNode(id).getEdges().containsKey("UpRight"));
                }
                if (i < numRows-1 && j > 0){
                    assertTrue(g.getNode(id).getEdges().containsKey("DownLeft"));
                }
                if (i < numRows-1 && j < numCols-1){
                    assertTrue(g.getNode(id).getEdges().containsKey("DownRight"));
                }
            }
        }
    }

    @Test
    void followPath(){
        makeConnectedSquare();

        /*
        B → C
        ↑ ╳ ↓
        A ← D
        AC and BD are diagonals
         */

        List<String> path1 = new ArrayList<>(List.of("F"));
        assertEquals("B", g.followPath("A", path1).getId());

        List<String> path2 = new ArrayList<>(Arrays.asList("F", "F"));
        assertEquals("C", g.followPath("A", path2).getId());

        List<String> path3 = new ArrayList<>(Arrays.asList("F", "F", "F"));
        assertEquals("D", g.followPath("A", path3).getId());

        List<String> path4 = new ArrayList<>(Arrays.asList("F", "F", "F", "F"));
        assertEquals("A", g.followPath("A", path4).getId());

        assertEquals("B", g.followPath("B", path4).getId());

        List<String> path6 = new ArrayList<>(Arrays.asList("F", "F", "DIAG"));
        assertEquals("A", g.followPath("A", path6).getId());

        // go along a nonexistent edge
        List<String> path7 = new ArrayList<>(Arrays.asList("F", "F", "D"));
        assertNull(g.followPath("A", path7));
    }

    @Test
    void isPathBlocked(){
        makeConnectedSquare();

        /*
        B → C
        ↑ ╳ ↓
        A ← D
        AC and BD are diagonals
         */

        g.getNode("C").putObject("Obj", 1);

        List<String> path1 = new ArrayList<>(List.of("F"));
        assertFalse(g.isPathBlocked("A", path1, node -> node.hasObject("Obj")));

        List<String> path2 = new ArrayList<>(Arrays.asList("F", "F"));
        assertTrue(g.isPathBlocked("A", path2, node -> node.hasObject("Obj")));

        List<String> path3 = new ArrayList<>(Arrays.asList("F", "F", "F"));
        assertTrue(g.isPathBlocked("A", path3 , node -> node.hasObject("Obj")));
        assertTrue(g.isPathBlocked("A", path3 , node -> node.hasObject("Obj")));
        assertTrue(g.isPathBlocked("B", path3 , node -> node.hasObject("Obj")));
        assertFalse(g.isPathBlocked("C", path3 , node -> node.hasObject("Obj")));

        List<String> path4 = new ArrayList<>(Arrays.asList("F", "F", "F", "F"));
        assertTrue(g.isPathBlocked("C", path4 , node -> node.hasObject("Obj")));


        List<String> path6 = new ArrayList<>(Arrays.asList("F", "DIAG", "F"));
        assertFalse(g.isPathBlocked("A", path6, node -> node.hasObject("Obj")));

        // go along a nonexistent edge
        List<String> path7 = new ArrayList<>(Arrays.asList("F", "D"));
        assertTrue(g.isPathBlocked("A", path7, node -> node.hasObject("Obj")));
    }

    @Test
    void testFindAllSpotsUntilBlocked(){
        g = BoardGraph.createGrid(8, 8);

        /*
        X is a bishop
        8 is any other piece

        0 0 1 2 3 4 5 6 7
        0 - - - - - - - -
        1 - - - - - - 8 -
        2 - 8 - - - - - -
        3 - - - - - - - -
        4 - - - X - - - -
        5 - - - - 8 - - -
        6 - - - - - - - -
        7 - - - - - - - -
         */


        g.getNode("2,1").putObject("Obj", 1);
        g.getNode("1,6").putObject("Obj", 1);
        g.getNode("5,4").putObject("Obj", 1);

        String start = "4,3";

        List<List<String>> paths = new ArrayList<>();
        paths.add(new ArrayList<>(List.of("UpRight")));
        paths.add(new ArrayList<>(List.of("UpLeft")));
        paths.add(new ArrayList<>(List.of("DownRight")));
        paths.add(new ArrayList<>(List.of("DownLeft")));
        List<String> available = new ArrayList<>();
        List<String> expected = new ArrayList<>(List.of("3,4", "2,5", "3,2", "5,2", "6,1", "7,0"));

        for (List<String> path : paths){
            available.addAll(g.findSpotsUntilBlocked(start, path, node -> node.hasObject("Obj")));
        }

        assertEquals(expected, available);

        List<String> newPath = new ArrayList<>(List.of("Up", "UpRight"));
        List<String> expected2 = new ArrayList<>(List.of("2,4", "0,5"));
        List<String> available2 = g.findSpotsUntilBlocked(start, newPath, node -> node.hasObject("Obj"));
        assertEquals(expected2, available2);
    }

    @Test
    void testNodePutAndRemoveObject(){
        g = BoardGraph.createGrid(4, 4);
        g.getNode("0,0").putObject("Obj", 1);
        assertTrue(g.getNode("0,0").hasObject("Obj"));
        assertEquals(1, g.getNode("0,0").getObject("Obj"));
        g.getNode("0,0").removeObject("Obj");
        assertFalse(g.getNode("0,0").hasObject("Obj"));
        assertNull(g.getNode("0,0").getObject("Obj"));
    }

}
