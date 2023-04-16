package oogasalad.gameeditor.frontend;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.frontend.components.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PointTest {

  private Point point;
  private double dummyX;
  private double dummyY;

  @BeforeEach
  public void setUp() {
    dummyX = 10;
    dummyY = 10;
    point = new Point(dummyX, dummyY);
  }

  @Test
  public void pointCreation() {
    assertEquals(point.getY(), dummyX);
    assertEquals(point.getX(), dummyY);
    assertEquals(point.getZ(), 0);
    point = new Point(5, 5, 5);
    assertEquals(point.getY(), 5);
    assertEquals(point.getX(), 5);
    assertEquals(point.getZ(), 5);
  }

  @Test
  public void positiveChecking() {
    point = new Point(-10, -10, -10);
    assertEquals(point.getX(), 0);
    assertEquals(point.getY(), 0);
    assertEquals(point.getZ(), -10);
  }

  @Test
  public void setZ() {
    point.setZ(10);
    assertEquals(point.getZ(), 10);
  }

  @Test
  public void scale() {
    Point p = point.scale(2);
    assertEquals(p.getX(), dummyX * 2);
    assertEquals(p.getY(), dummyY * 2);
    assertEquals(p.getZ(), 0);
  }
}
