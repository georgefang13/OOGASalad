package oogasalad.sharedDependencies.backend.ownables.variables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Variable class.
 *
 * @author Michael Bryant
 */
public class VariableTest {

  private Variable v;

  @BeforeEach
  void setup() {
    v = new Variable(null);
  }

  @Test
  void testVariable() {
    v.set(5);
    assertEquals(5, v.get());
  }

  @Test
  void testVariableChange() {
    v.set(5);
    v.set(6);
    assertEquals(6, v.get());
  }

  @Test
  void testVariableNull() {
    v.set(null);
    assertNull(v.get());
  }

  @Test
  void testVariableString() {
    v.set("Hello");
    assertEquals("Hello", v.get());
  }

  @Test
  void testVariableDouble() {
    v.set(5.5);
    assertEquals(5.5, v.get());
  }

  @Test
  void testVariableBoolean() {
    v.set(true);
    assertEquals(true, v.get());
  }

  @Test
  void testVariableObject() {
    v.set(new Object());
    assertTrue(v.get() != null);
  }

  @Test
  void testVariableArray() {
    v.set(new int[]{1, 2, 3});
    assertTrue(v.get() instanceof int[]);
    int[] array = (int[]) v.get();
    assertEquals(1, array[0]);
    assertEquals(2, array[1]);
    assertEquals(3, array[2]);
  }

  @Test
  void testVariableArrayList() {
    v.set(new ArrayList<Integer>());
    assertTrue(v.get() instanceof ArrayList<?>);
    assertEquals(v.get(), new ArrayList<Integer>());
  }

  @Test
  void testVariableList() {
    v.set(new ArrayList<Integer>());
    assertEquals(new ArrayList<Integer>(), v.get());
  }

  @Test
  void testChangeType() {
    v.set(5);
    v.set("Hello");
    assertEquals("Hello", v.get());
  }

  @Test
  void testVariableListener() {
    AtomicInteger testVar = new AtomicInteger(0);
    v.addListener(value -> testVar.set((int) value));
    v.set(5);
    assertEquals(5, testVar.get());
  }

  @Test
  void testClearListener() {
    AtomicInteger testVar = new AtomicInteger(0);
    v.addListener(value -> testVar.set((int) value));
    v.clearListeners();
    v.set(5);
    assertEquals(0, testVar.get());
  }

  @Test
  void getListeners() {
    v.addListener(value -> {
    });
    assertEquals(1, v.getListeners().size());
  }

  @Test
  void testRemoveListener() {
    AtomicInteger testVar = new AtomicInteger(0);
    VariableListener listener = value -> testVar.set((int) value);
    v.addListener(listener);
    v.removeListener(listener);
    v.set(5);
    assertEquals(0, testVar.get());
  }

  @Test
  void testNullInitial() {
    v = new Variable(null);
    assertNull(v.get());
  }

  @Test
  void nonNullInitial() {
    v = new Variable(5, null);
    assertEquals(5, v.get());
  }

}

