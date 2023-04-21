package oogasalad.gameeditor.backend.ownables.gameobjects;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardGraphTest {

  private List<DropZone> g;

  private DropZone getNodeWithId(String id) {
    for (DropZone node : g) {
      if (node.getId().equals(id)) {
        return node;
      }
    }
    return null;
  }


  // create new instance of test object before each test is run
  @BeforeEach
  void setup() {
    g = new ArrayList<>();
  }

  ArrayList<Map.Entry<String, DropZone>> getSortedEdges(DropZone node) {
    ArrayList<Map.Entry<String, DropZone>> sortedEdges = new ArrayList<>(
        node.getEdges().entrySet());
    sortedEdges.sort(Comparator.comparing(e -> e.getValue().getId()));
    return sortedEdges;
  }

  @Test
  void addConnections() {
    DropZone a = new DropZone("A");
    DropZone b = new DropZone("B");
    DropZone c = new DropZone("C");

    a.addOutgoingConnection(b, "AB");
    b.addOutgoingConnection(c, "BC");
    c.addOutgoingConnection(a, "CA");

    assertEquals("B", getSortedEdges(a).get(0).getValue().getId());
    assertEquals("C", getSortedEdges(b).get(0).getValue().getId());
    assertEquals("A", getSortedEdges(c).get(0).getValue().getId());

    assertEquals("AB", getSortedEdges(a).get(0).getKey());
    assertEquals("BC", getSortedEdges(b).get(0).getKey());
    assertEquals("CA", getSortedEdges(c).get(0).getKey());
  }

  @Test
  void addNullAndRedundantConnections() {
    DropZone a = new DropZone("A");
    DropZone b = new DropZone("B");
    DropZone c = new DropZone("C");

    try {
      a.addOutgoingConnection(null, "AB");
    } catch (IllegalArgumentException e) {
      assertEquals("Cannot add null node to graph.", e.getMessage());
    }
    try {
      a.addOutgoingConnection(b, null);
    } catch (IllegalArgumentException e) {
      assertEquals("Edge name cannot be empty.", e.getMessage());
    }
    try {
      a.addOutgoingConnection(b, "");
    } catch (IllegalArgumentException e) {
      assertEquals("Edge name cannot be empty.", e.getMessage());
    }

    a.addOutgoingConnection(b, "AB");
    b.addOutgoingConnection(c, "BC");
    c.addOutgoingConnection(a, "CA");

    assertFalse(a.addOutgoingConnection(b, "AB"));
    assertFalse(b.addOutgoingConnection(c, "BC"));
    assertFalse(c.addOutgoingConnection(a, "CA"));

    assertTrue(b.addOutgoingConnection(a, "BA"));
  }

  @Test
  void testEquals() {
    DropZone a = new DropZone("A");
    assertFalse(a.equals(true));
    assertEquals(a, a);

    DropZone b = new DropZone("B");
    DropZone a2 = new DropZone("A");

    assertEquals(a, a2);
    assertNotEquals(a, b);
  }

  @Test
  void testNodeToString() {
    DropZone a = new DropZone("A");
    DropZone b = new DropZone("B");
    DropZone c = new DropZone("Blobfish Tails");

    assertEquals("A", a.toString());
    assertEquals("B", b.toString());
    assertEquals("Blobfish Tails", c.toString());
  }

  @Test
  void testNodeHashcode() {
    DropZone a = new DropZone("A");
    DropZone b = new DropZone("B");

    assertEquals("A".hashCode(), a.hashCode());
    assertEquals("B".hashCode(), b.hashCode());
  }

  void makeConnectedSquare() {
    DropZone a = new DropZone("A");
    DropZone b = new DropZone("B");
    DropZone c = new DropZone("C");
    DropZone d = new DropZone("D");

    a.addOutgoingConnection(b, "F");
    b.addOutgoingConnection(c, "F");
    c.addOutgoingConnection(d, "F");
    d.addOutgoingConnection(a, "F");

    a.addOutgoingConnection(c, "DIAG");
    b.addOutgoingConnection(d, "DIAG");
    c.addOutgoingConnection(a, "DIAG");
    d.addOutgoingConnection(b, "DIAG");

    g.add(a);
    g.add(b);
    g.add(c);
    g.add(d);
  }

  @Test
  void testCreateGrid() {
    int numRows = 3;
    int numCols = 7;
    g = BoardCreator.createGrid(numRows, numCols);
    assertEquals(numRows * numCols, g.size());
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        String id = String.format("%d,%d", i, j);
        assertNotNull(getNodeWithId(id));
        // test edges
        if (i > 0) {
          assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("Up"));
        }
        if (i < numRows - 1) {
          try {
            assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("Down"));
          } catch (AssertionError e) {
            System.out.println(id);
            System.out.println(Objects.requireNonNull(getNodeWithId(id)).getEdges());
            throw e;
          }

        }
        if (j > 0) {
          assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("Left"));
        }
        if (j < numCols - 1) {
          assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("Right"));
        }
        if (i > 0 && j > 0) {
          assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("UpLeft"));
        }
        if (i > 0 && j < numCols - 1) {
          assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("UpRight"));
        }
        if (i < numRows - 1 && j > 0) {
          assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("DownLeft"));
        }
        if (i < numRows - 1 && j < numCols - 1) {
          assertTrue(Objects.requireNonNull(getNodeWithId(id)).getEdges().containsKey("DownRight"));
        }
      }
    }
  }

  @Test
  void followPath() {
    makeConnectedSquare();

        /*
        B → C
        ↑ ╳ ↓
        A ← D
        AC and BD are diagonals
         */
//

    List<String> path1 = new ArrayList<>(List.of("F"));
    assertEquals("B", Objects.requireNonNull(getNodeWithId("A")).followPath(path1).getId());

    List<String> path2 = new ArrayList<>(Arrays.asList("F", "F"));
    assertEquals("C", Objects.requireNonNull(getNodeWithId("A")).followPath(path2).getId());

    List<String> path3 = new ArrayList<>(Arrays.asList("F", "F", "F"));
    assertEquals("D", Objects.requireNonNull(getNodeWithId("A")).followPath(path3).getId());

    List<String> path4 = new ArrayList<>(Arrays.asList("F", "F", "F", "F"));
    assertEquals("A", Objects.requireNonNull(getNodeWithId("A")).followPath(path4).getId());

    assertEquals("B", Objects.requireNonNull(getNodeWithId("B")).followPath(path4).getId());

    List<String> path6 = new ArrayList<>(Arrays.asList("F", "F", "DIAG"));
    assertEquals("A", Objects.requireNonNull(getNodeWithId("A")).followPath(path6).getId());

    // go along a nonexistent edge
    List<String> path7 = new ArrayList<>(Arrays.asList("F", "F", "D"));
    assertNull(Objects.requireNonNull(getNodeWithId("A")).followPath(path7));
  }

  @Test
  void isPathBlocked() {
    makeConnectedSquare();

        /*
        B → C
        ↑ ╳ ↓
        A ← D
        AC and BD are diagonals
         */

    Objects.requireNonNull(getNodeWithId("C")).putObject("Obj", 1);

    List<String> path1 = new ArrayList<>(List.of("F"));
    assertFalse(Objects.requireNonNull(getNodeWithId("A"))
        .isPathBlocked(path1, node -> node.hasObject("Obj")));

    List<String> path2 = new ArrayList<>(Arrays.asList("F", "F"));
    assertTrue(Objects.requireNonNull(getNodeWithId("A"))
        .isPathBlocked(path2, node -> node.hasObject("Obj")));

    List<String> path3 = new ArrayList<>(Arrays.asList("F", "F", "F"));
    assertTrue(Objects.requireNonNull(getNodeWithId("A"))
        .isPathBlocked(path3, node -> node.hasObject("Obj")));
    assertTrue(Objects.requireNonNull(getNodeWithId("B"))
        .isPathBlocked(path3, node -> node.hasObject("Obj")));
    assertFalse(Objects.requireNonNull(getNodeWithId("C"))
        .isPathBlocked(path3, node -> node.hasObject("Obj")));

    List<String> path4 = new ArrayList<>(Arrays.asList("F", "F", "F", "F"));
    assertTrue(Objects.requireNonNull(getNodeWithId("C"))
        .isPathBlocked(path4, node -> node.hasObject("Obj")));

    List<String> path6 = new ArrayList<>(Arrays.asList("F", "DIAG", "F"));
    assertFalse(Objects.requireNonNull(getNodeWithId("A"))
        .isPathBlocked(path6, node -> node.hasObject("Obj")));

    // go along a nonexistent edge
    List<String> path7 = new ArrayList<>(Arrays.asList("F", "D"));
    assertTrue(Objects.requireNonNull(getNodeWithId("A"))
        .isPathBlocked(path7, node -> node.hasObject("Obj")));
  }

  @Test
  void testFindAllSpotsUntilBlocked() {
    g = BoardCreator.createGrid(8, 8);

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

    Objects.requireNonNull(getNodeWithId("2,1")).putObject("Obj", 1);
    Objects.requireNonNull(getNodeWithId("1,6")).putObject("Obj", 1);
    Objects.requireNonNull(getNodeWithId("5,4")).putObject("Obj", 1);

    DropZone start = getNodeWithId("4,3");

    List<List<String>> paths = new ArrayList<>();
    paths.add(new ArrayList<>(List.of("UpRight")));
    paths.add(new ArrayList<>(List.of("UpLeft")));
    paths.add(new ArrayList<>(List.of("DownRight")));
    paths.add(new ArrayList<>(List.of("DownLeft")));
    List<DropZone> available = new ArrayList<>();
    List<String> expected = new ArrayList<>(List.of("3,4", "2,5", "3,2", "5,2", "6,1", "7,0"));

    for (List<String> path : paths) {
      assert start != null;
      available.addAll(start.findSpotsUntilBlocked(path, node -> node.hasObject("Obj")));
    }
    List<String> dzids = new ArrayList<>();
    for (DropZone dz : available) {
      dzids.add(dz.getId());
    }

    assertEquals(expected, dzids);

    List<String> newPath = new ArrayList<>(List.of("Up", "UpRight"));
    List<String> expected2 = new ArrayList<>(List.of("2,4", "0,5"));
    List<DropZone> available2 = start.findSpotsUntilBlocked(newPath, node -> node.hasObject("Obj"));
    dzids = new ArrayList<>();
    for (DropZone dz : available2) {
      dzids.add(dz.getId());
    }
    assertEquals(expected2, dzids);
  }


  @Test
  void testNodePutAndRemoveObject() {
    g = BoardCreator.createGrid(4, 4);
    Objects.requireNonNull(getNodeWithId("0,0")).putObject("Obj", 1);
    assertTrue(Objects.requireNonNull(getNodeWithId("0,0")).hasObject("Obj"));
    assertEquals(1, Objects.requireNonNull(getNodeWithId("0,0")).getObject("Obj"));
    Objects.requireNonNull(getNodeWithId("0,0")).removeObject("Obj");
    assertFalse(Objects.requireNonNull(getNodeWithId("0,0")).hasObject("Obj"));
    assertNull(Objects.requireNonNull(getNodeWithId("0,0")).getObject("Obj"));
  }

  @Test
  void testSquareLoop() {
    g = BoardCreator.createSquareLoop(4, 4);
    assertEquals(12, g.size());

    List<String> path = new ArrayList<>(List.of("Counterclockwise"));
    List<String> expected = new ArrayList<>(
        List.of("9", "10", "11", "0", "1", "2", "3", "4", "5", "6", "7", "8"));
    List<DropZone> dzExpected = new ArrayList<>();
    for (String id : expected) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    List<DropZone> available = g.get(8).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    assertEquals(dzExpected, available);

    g.get(8).putObject("Obj", 1);
    List<String> expected2 = new ArrayList<>(List.of("1", "2", "3", "4", "5", "6", "7"));
    List<DropZone> available2 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (String id : expected2) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    assertEquals(dzExpected, available2);

    g.get(8).removeObject("Obj");

    path = new ArrayList<>(List.of("Clockwise"));
    List<String> expected3 = new ArrayList<>(
        List.of("3", "2", "1", "0", "11", "10", "9", "8", "7", "6", "5", "4"));
    List<DropZone> available3 = g.get(4).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (String id : expected3) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    assertEquals(dzExpected, available3);

    g.get(4).putObject("Obj", 1);
    List<String> expected4 = new ArrayList<>(List.of("11", "10", "9", "8", "7", "6", "5"));
    List<DropZone> available4 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (String id : expected4) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    assertEquals(dzExpected, available4);

  }

  @Test
  void testCreate1DLoop() {
    g = BoardCreator.create1DLoop(4);
    assertEquals(4, g.size());

    List<String> path = new ArrayList<>(List.of("Forward"));
    List<String> expected = new ArrayList<>(List.of("3", "0", "1", "2"));
    List<DropZone> available = g.get(2).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    ArrayList<DropZone> dzExpected = new ArrayList<>();
    for (String id : expected) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    assertEquals(dzExpected, available);

    g.get(2).putObject("Obj", 1);
    List<String> expected2 = new ArrayList<>(List.of("1"));
    List<DropZone> available2 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (String id : expected2) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    assertEquals(dzExpected, available2);

    g.get(2).removeObject("Obj");

    path = new ArrayList<>(List.of("Backward"));
    List<String> expected3 = new ArrayList<>(List.of("1", "0", "3", "2"));
    List<DropZone> available3 = g.get(2).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (String id : expected3) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    assertEquals(dzExpected, available3);

    g.get(1).putObject("Obj", 1);
    List<String> expected4 = new ArrayList<>(List.of("3", "2"));
    List<DropZone> available4 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (String id : expected4) { dzExpected.add(Objects.requireNonNull(getNodeWithId(id))); }
    assertEquals(dzExpected, available4);
  }

}


