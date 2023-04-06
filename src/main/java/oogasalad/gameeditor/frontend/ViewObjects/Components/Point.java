package oogasalad.gameeditor.frontend.ViewObjects.Components;

public class Point {
  double x;
  double y;
  double z;
  public Point(int xCoord, int yCoord){
    x = xCoord;
    y = yCoord;
  }
  public Point(int xCoord, int yCoord, int zCoord){
    z = zCoord;
  }

  public void setZ(int zIndex) {
    z = zIndex;
  }
}
