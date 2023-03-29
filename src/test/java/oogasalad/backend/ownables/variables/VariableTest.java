package oogasalad.backend.ownables.variables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VariableTest {

  private Variable v;

  @BeforeEach
  void setup() {
    v = new Variable();
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
    assertEquals(null, v.get());
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
    assertTrue(v.get() instanceof Object);
  }

  @Test
  void testVariableArray() {
    v.set(new int[] {1, 2, 3});
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
    assertTrue(v.get().equals(new ArrayList<Integer>()));
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
    v.set(5);
    v.addListener(new VariableListener<Integer>() {
      @Override
      public void onChange(Integer value) {
        assertEquals(5, value);
      }
    });
    v.set(5);
  }

  @Test
  void testRemoveListener() {
    v.set(5);
    VariableListener<Integer> listener = new VariableListener<Integer>() {
      @Override
      public void onChange(Integer value) {
        assertEquals(false, true); // should not be called
      }
    };
    v.addListener(listener);
    v.removeListener(listener);
    v.set(5);
  }

  @Test
  void testNullInitial() {
    v = new Variable();
    assertEquals(null, v.get());
  }

  @Test
  void nonNullInitial() {
    v = new Variable(5);
    assertEquals(5, v.get());
  }

  @Test
  void testCanOwn() {
    assertTrue(v.canBeOwnedBy(null));
  }

}
