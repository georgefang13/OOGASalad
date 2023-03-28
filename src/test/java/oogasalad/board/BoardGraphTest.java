package oogasalad.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

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

}
