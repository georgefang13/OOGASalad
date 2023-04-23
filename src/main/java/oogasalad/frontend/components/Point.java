package oogasalad.frontend.components;

public class Point {

  private double x;
  private double y;
  private double z;

  public Point(double xCoord, double yCoord) {
    x = checkPositive(xCoord);
    y = checkPositive(yCoord);
  }

  private double checkPositive(double value) {
    if (value < 0) {
      return 0;
    }
    return value;
  }

  public Point(double xCoord, double yCoord, double zCoord) {
    x = checkPositive(xCoord);
    y = checkPositive(yCoord);
    z = zCoord;
  }

  public void setZ(double zIndex) {
    z = zIndex;
  }

  public Point scale(int scalingFactor) {
    return new Point(x * scalingFactor, y * scalingFactor, z);
  }

  public double getX() {

    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }
}
