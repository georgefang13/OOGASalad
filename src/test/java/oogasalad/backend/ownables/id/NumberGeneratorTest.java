package oogasalad.backend.ownables.id;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the NumberGenerator class.
 * @author Michael Bryant
 */
public class NumberGeneratorTest {

  private NumberGenerator gen;

  @BeforeEach
  void setup() {
    gen = new NumberGenerator();
  }

  @Test
  void testNumberGeneratorFirst() {
    assertEquals("", gen.next());
  }

  @Test
  void testNumberGeneratorSecond() {
    gen.next();
    assertEquals("2", gen.next());
  }

  @Test
  void testNumberGeneratorThird() {
    gen.next();
    gen.next();
    assertEquals("3", gen.next());
  }

  @Test
  void testNumberGeneratorLarge() {
    for (int i = 0; i < 10000; i++) {
      gen.next();
    }
    assertEquals("10001", gen.next());
  }

  @Test
  void testNumberGeneratorMax() {
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      gen.next();
    }
    assertEquals("", gen.next());
  }

}