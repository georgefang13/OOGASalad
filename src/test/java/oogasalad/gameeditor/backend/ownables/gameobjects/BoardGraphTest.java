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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardGraphTest {

  private List<DropZone> g;


  // create new instance of test object before each test is run
  @BeforeEach
  void setup() {
    g = new ArrayList<>();
  }

  ArrayList<Map.Entry<String, DropZone>> getSortedEdges(DropZone node) {
    ArrayList<Map.Entry<String, DropZone>> sortedEdges = new ArrayList<>(
        node.getEdges().entrySet());
    sortedEdges.sort(Comparator.comparing(e -> g.indexOf(e.getValue())));
    return sortedEdges;
  }

  @Test
  void addConnections() {
    DropZone a = new DropZone();
    DropZone b = new DropZone();
    DropZone c = new DropZone();

    a.addOutgoingConnection(b, "AB");
    b.addOutgoingConnection(c, "BC");
    c.addOutgoingConnection(a, "CA");

    assertEquals(b, getSortedEdges(a).get(0).getValue());
    assertEquals(c, getSortedEdges(b).get(0).getValue());
    assertEquals(a, getSortedEdges(c).get(0).getValue());

    assertEquals("AB", getSortedEdges(a).get(0).getKey());
    assertEquals("BC", getSortedEdges(b).get(0).getKey());
    assertEquals("CA", getSortedEdges(c).get(0).getKey());
  }

  @Test
  void addNullAndRedundantConnections() {
    DropZone a = new DropZone();
    DropZone b = new DropZone();
    DropZone c = new DropZone();

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

  void makeConnectedSquare() {
    DropZone a = new DropZone();
    DropZone b = new DropZone();
    DropZone c = new DropZone();
    DropZone d = new DropZone();

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
        int node = i * numCols + j;
        String id = String.format("%d,%d", i, j);
        assertNotNull(g.get(node));
        // test edges
        if (i > 0) {
          assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("Up"));
        }
        if (i < numRows - 1) {
          try {
            assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("Down"));
          } catch (AssertionError e) {
            System.out.println(id);
            System.out.println(Objects.requireNonNull(g.get(node)).getEdges());
            throw e;
          }

        }
        if (j > 0) {
          assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("Left"));
        }
        if (j < numCols - 1) {
          assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("Right"));
        }
        if (i > 0 && j > 0) {
          assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("UpLeft"));
        }
        if (i > 0 && j < numCols - 1) {
          assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("UpRight"));
        }
        if (i < numRows - 1 && j > 0) {
          assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("DownLeft"));
        }
        if (i < numRows - 1 && j < numCols - 1) {
          assertTrue(Objects.requireNonNull(g.get(node)).getEdges().containsKey("DownRight"));
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

    DropZone a = g.get(0);
    DropZone b = g.get(1);
    DropZone c = g.get(2);
    DropZone d = g.get(3);

    List<String> path1 = new ArrayList<>(List.of("F"));
    assertEquals(b, Objects.requireNonNull(a).followPath(path1));

    List<String> path2 = new ArrayList<>(Arrays.asList("F", "F"));
    assertEquals(c, a.followPath(path2));

    List<String> path3 = new ArrayList<>(Arrays.asList("F", "F", "F"));
    assertEquals(d, a.followPath(path3));

    List<String> path4 = new ArrayList<>(Arrays.asList("F", "F", "F", "F"));
    assertEquals(a, a.followPath(path4));

    assertEquals(b, b.followPath(path4));

    List<String> path6 = new ArrayList<>(Arrays.asList("F", "F", "DIAG"));
    assertEquals(a, a.followPath(path6));

    // go along a nonexistent edge
    List<String> path7 = new ArrayList<>(Arrays.asList("F", "F", "D"));
    assertNull(a.followPath(path7));
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

    DropZone a = g.get(0);
    DropZone b = g.get(1);
    DropZone c = g.get(2);
    DropZone d = g.get(3);

    c.putObject("Obj", 1);

    List<String> path1 = new ArrayList<>(List.of("F"));
    assertFalse(a.isPathBlocked(path1, node -> node.hasObject("Obj")));

    List<String> path2 = new ArrayList<>(Arrays.asList("F", "F"));
    assertTrue(a.isPathBlocked(path2, node -> node.hasObject("Obj")));

    List<String> path3 = new ArrayList<>(Arrays.asList("F", "F", "F"));
    assertTrue(a.isPathBlocked(path3, node -> node.hasObject("Obj")));
    assertTrue(b.isPathBlocked(path3, node -> node.hasObject("Obj")));
    assertFalse(c.isPathBlocked(path3, node -> node.hasObject("Obj")));

    List<String> path4 = new ArrayList<>(Arrays.asList("F", "F", "F", "F"));
    assertTrue(Objects.requireNonNull(c.isPathBlocked(path4, node -> node.hasObject("Obj"))));

    List<String> path6 = new ArrayList<>(Arrays.asList("F", "DIAG", "F"));
    assertFalse(a.isPathBlocked(path6, node -> node.hasObject("Obj")));

    // go along a nonexistent edge
    List<String> path7 = new ArrayList<>(Arrays.asList("F", "D"));
    assertTrue(a.isPathBlocked(path7, node -> node.hasObject("Obj")));
  }

  private DropZone get8x8Zone(int x, int y) {
    return g.get(y * 8 + x);
  }
  private DropZone get4x4Zone(int x, int y) {
    return g.get(y * 4 + x);
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

    Objects.requireNonNull(get8x8Zone(2,1)).putObject("Obj", 1);
    Objects.requireNonNull(get8x8Zone(1,6)).putObject("Obj", 1);
    Objects.requireNonNull(get8x8Zone(5,4)).putObject("Obj", 1);

    DropZone start = get8x8Zone(4,3);

    List<List<String>> paths = new ArrayList<>();
    paths.add(new ArrayList<>(List.of("UpRight")));
    paths.add(new ArrayList<>(List.of("UpLeft")));
    paths.add(new ArrayList<>(List.of("DownRight")));
    paths.add(new ArrayList<>(List.of("DownLeft")));
    List<DropZone> available = new ArrayList<>();
    List<String> expected = new ArrayList<>(List.of("3,4", "2,5", "3,2", "5,2", "6,1", "7,0"));
    List<DropZone> expectedDZs = new ArrayList<>();
    for (String dzid : expected) {
      String[] coords = dzid.split(",");
      expectedDZs.add(get8x8Zone(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
    }

    for (List<String> path : paths) {
      available.addAll(start.findSpotsUntilBlocked(path, node -> node.hasObject("Obj")));
    }
    List<DropZone> dzids = new ArrayList<>(available);

//    assertEquals(new HashSet<>(expectedDZs), new HashSet<>(dzids));

    start = get8x8Zone(3,4);

    List<String> newPath = new ArrayList<>(List.of("Up", "UpRight"));
    List<String> expected2 = new ArrayList<>(List.of("4,2", "5,0"));
    expectedDZs = new ArrayList<>();
    for (String dzid : expected2) {
      String[] coords = dzid.split(",");
      expectedDZs.add(get8x8Zone(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])));
    }
    List<DropZone> available2 = start.findSpotsUntilBlocked(newPath, node -> node.hasObject("Obj"));
    dzids = new ArrayList<>(available2);

    assertEquals(expectedDZs, dzids);
  }


  @Test
  void testNodePutAndRemoveObject() {
    g = BoardCreator.createGrid(4, 4);
    get4x4Zone(0,0).putObject("Obj", 1);
    assertTrue(get4x4Zone(0,0).hasObject("Obj"));
    assertEquals(1, get4x4Zone(0,0).getObject("Obj"));
    Objects.requireNonNull(get4x4Zone(0,0)).removeObject("Obj");
    assertFalse(get4x4Zone(0,0).hasObject("Obj"));
    assertNull(get4x4Zone(0,0).getObject("Obj"));
  }

  @Test
  void testSquareLoop() {
    g = BoardCreator.createSquareLoop(4, 4);
    assertEquals(12, g.size());

    List<String> path = new ArrayList<>(List.of("Counterclockwise"));
    List<Integer> expected = new ArrayList<>(
        List.of(9, 10, 11, 0, 1, 2, 3, 4, 5, 6, 7, 8));
    List<DropZone> dzExpected = new ArrayList<>();
    for (int id : expected) {
      dzExpected.add(g.get(id));
    }
    List<DropZone> available = g.get(8).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    assertEquals(dzExpected, available);

    g.get(8).putObject("Obj", 1);
    List<Integer> expected2 = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7));
    List<DropZone> available2 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (int id : expected2) {
      dzExpected.add(g.get(id));
    }
    assertEquals(dzExpected, available2);

    g.get(8).removeObject("Obj");

    path = new ArrayList<>(List.of("Clockwise"));
    List<Integer> expected3 = new ArrayList<>(
        List.of(3, 2, 1, 0, 11, 10, 9, 8, 7, 6, 5, 4));
    List<DropZone> available3 = g.get(4).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (int id : expected3) { dzExpected.add(g.get(id)); }
    assertEquals(dzExpected, available3);

    g.get(4).putObject("Obj", 1);
    List<Integer> expected4 = new ArrayList<>(List.of(11, 10, 9, 8, 7, 6, 5));
    List<DropZone> available4 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    dzExpected = new ArrayList<>();
    for (int id : expected4) { dzExpected.add(g.get(id)); }
    assertEquals(dzExpected, available4);

  }

  @Test
  void testCreate1DLoop() {
    g = BoardCreator.create1DLoop(4);
    assertEquals(4, g.size());

    DropZone d0 = g.get(0);
    DropZone d1 = g.get(1);
    DropZone d2 = g.get(2);
    DropZone d3 = g.get(3);

    List<String> path = new ArrayList<>(List.of("Forward"));
    List<DropZone> expected = new ArrayList<>(List.of(d3, d0, d1, d2));
    List<DropZone> available = g.get(2).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    assertEquals(expected, available);

    g.get(2).putObject("Obj", 1);
    List<DropZone> expected2 = new ArrayList<>(List.of(d1));
    List<DropZone> available2 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    assertEquals(expected2, available2);

    g.get(2).removeObject("Obj");

    path = new ArrayList<>(List.of("Backward"));
    List<DropZone> expected3 = new ArrayList<>(List.of(d1, d0, d3, d2));
    List<DropZone> available3 = g.get(2).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    assertEquals(expected3, available3);

    g.get(1).putObject("Obj", 1);
    List<DropZone> expected4 = new ArrayList<>(List.of(d3, d2));
    List<DropZone> available4 = g.get(0).findSpotsUntilBlocked(path, node -> node.hasObject("Obj"));
    assertEquals(expected4, available4);
  }

  @Test
  void testGetObjectKey(){
    g = BoardCreator.createGrid(4, 4);
    get4x4Zone(0,0).putObject("Obj", 1);
    assertEquals("Obj", g.get(0).getKey(1));
  }

}


