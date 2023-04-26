package oogasalad.sharedDependencies.backend.id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import oogasalad.sharedDependencies.backend.id.NumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the NumberGenerator class.
 *
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

  //Comment out this test if you want to run the test suite quickly
//  @Test
//  void testNumberGeneratorMax() {
//    for (int i = 0; i < Integer.MAX_VALUE; i++) {
//      gen.next();
//    }
//    assertEquals("", gen.next());
//  }

  @Test
  void testMultipleGenerators() {
    NumberGenerator gen2 = new NumberGenerator();
    assertEquals("", gen.next());
    assertEquals("", gen2.next());
    assertEquals("2", gen.next());
    assertEquals("2", gen2.next());
    assertEquals("3", gen.next());
    assertEquals("3", gen2.next());
  }

  @Test
  void testNumberGeneratorHasNext() {
    assertTrue(gen.hasNext());
  }

}
