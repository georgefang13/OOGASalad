package oogasalad.gameeditor.backend.ownables.gameobjects;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.DropZone;

public class BoardCreator {

  // TODO: put text in properties file

  /**
   * Creates a grid graph with the given number of rows and columns. Edges are Up, Down, Left,
   * Right, UpLeft, UpRight, DownLeft, DownRight. Node IDs start with 0,0 at the top left and
   * increase to the right and down, with the format "y,x"
   * <br>
   * example:
   * <br><br>
   * grid with 3 rows, 5 columns:
   * <table>
   *      <tr>
   *          <td>0,0</td> <td>↔</td> <td>0,1</td> <td>↔</td> <td>0,2</td> <td>↔</td> <td>0,3</td> <td>↔</td> <td>0,4</td>
   *      </tr>
   *      <tr>
   *          <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td></td>
   *      </tr>
   *      <tr>
   *          <td>1,0</td> <td>↔</td> <td>1,1</td> <td>↔</td> <td>1,2</td> <td>↔</td> <td>1,3</td> <td>↔</td> <td>1,4</td>
   *      </tr>
   *      <tr>
   *          <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td>╳</td> <td>↕</td> <td></td>
   *      </tr>
   *      <tr>
   *          <td>2,0</td> <td>↔</td> <td>2,1</td> <td>↔</td> <td>2,2</td> <td>↔</td> <td>2,3</td> <td>↔</td> <td>2,4</td>
   *      </tr>
   * </table>
   *
   * @param rows the number of rows
   * @param cols the number of columns
   * @return a grid graph with the given number of rows and columns
   */
  public static List<DropZone> createGrid(int rows, int cols) {
    DropZone[][] nodes = new DropZone[rows][cols];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        nodes[i][j] = new DropZone();
      }
    }

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        DropZone z = nodes[i][j];
          if (i > 0) {
              z.addOutgoingConnection(nodes[i - 1][j], "Up");
          }
          if (i < rows - 1) {
              z.addOutgoingConnection(nodes[i + 1][j], "Down");
          }
          if (j > 0) {
              z.addOutgoingConnection(nodes[i][j - 1], "Left");
          }
          if (j < cols - 1) {
              z.addOutgoingConnection(nodes[i][j + 1], "Right");
          }
          if (i > 0 && j > 0) {
              z.addOutgoingConnection(nodes[i - 1][j - 1], "UpLeft");
          }
          if (i > 0 && j < cols - 1) {
              z.addOutgoingConnection(nodes[i - 1][j + 1], "UpRight");
          }
          if (i < rows - 1 && j > 0) {
              z.addOutgoingConnection(nodes[i + 1][j - 1], "DownLeft");
          }
          if (i < rows - 1 && j < cols - 1) {
              z.addOutgoingConnection(nodes[i + 1][j + 1], "DownRight");
          }
      }
    }

    List<DropZone> graphList = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      graphList.addAll(Arrays.asList(nodes[i]).subList(0, cols));
    }

    return graphList;
  }

//    private static int[] getRowColDir(int rows, int cols, int row, int col){
//        int[] rowColDir = new int[2];
//
//        if (row == 0 && col != cols - 1) rowColDir[1] = 1;
//        if (row == rows - 1 && col != 0) rowColDir[1] = -1;
//        if (col == 0 && row != 0) rowColDir[0] = -1;
//        if (col == cols - 1 && row != rows - 1) rowColDir[0] = 1;
//
//        return rowColDir;
//    }

  // TODO: put text in properties file

  /**
   * Creates a square loop graph with the given number of rows and columns. Edges are named
   * Clockwise or Counterclockwise. Nodes are named from 0 to the length of the perimeter.
   *
   * @param rows the number of rows in the square
   * @param cols the number of columns in the square
   * @return a looped graph with the given number of rows and columns
   */
  public static List<DropZone> createSquareLoop(int rows, int cols) {

    String pathForward = "Counterclockwise";
    String pathBackward = "Clockwise";

    int perimeter = 2 * (rows + cols) - 4;

    return create1DLoop(perimeter, pathForward, pathBackward);
  }

  // TODO: put text in properties file

  /**
   * Creates a 1D loop graph with the given length. Edges are named Forward or Backward.
   *
   * @param length the length of the loop
   * @return a list of nodes representing the looped graph
   */
  public static List<DropZone> create1DLoop(int length) {
    return create1DLoop(length, "Forward", "Backward");
  }

  /**
   * Creates a 1D loop graph with the given length. Edges are named Forward or Backward.
   *
   * @param length the length of the loop
   * @return a list of nodes representing the looped graph
   */
  public static List<DropZone> create1DLoop(int length, String forward, String backward) {
    List<DropZone> nodes = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      nodes.add(new DropZone());
    }
    for (int i = 0; i < length; i++) {
      DropZone next = nodes.get((i + 1) % length);
      nodes.get(i).addOutgoingConnection(next, forward);
      next.addOutgoingConnection(nodes.get(i), backward);
    }
    return nodes;
  }

}
