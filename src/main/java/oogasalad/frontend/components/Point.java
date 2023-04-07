package oogasalad.frontend.components;

public class Point {
  double x;
  double y;
  double z;
  public Point(double xCoord, double yCoord){
    x = xCoord;
    y = yCoord;
  }
  public Point(double xCoord, double yCoord, double zCoord){
    z = zCoord;
  }

  public void setZ(double zIndex) {
    z = zIndex;
  }

  public Point scale(int scalingFactor) {
    return new Point(x*scalingFactor, y*scalingFactor);
  }
}
