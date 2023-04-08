package oogasalad.gameeditor.backend.id;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import oogasalad.gameeditor.backend.ownables.gameobjects.EmptyGameObject;
import oogasalad.sharedDependencies.backend.ownables.Ownable;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.ownables.variables.Variable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the IdManager class.
 * @author Michael Bryant
 */
public class IdManagerTest {

  private IdManager<Ownable> manager;
  private Variable variable1;
  private Variable variable2;
  private Variable variable3;
  private GameObject object1;
  private GameObject object2;
  private GameObject object3;

  @BeforeEach
  public void setUp() {
    manager = new IdManager<>();
    variable1 = new Variable(manager, null);
    variable2 = new Variable(manager, null);
    variable3 = new Variable(manager, null);
    object1 = new EmptyGameObject(null);
    object2 = new EmptyGameObject(null);
    object3 = new EmptyGameObject(null);
  }

  @Test
  public void testIsIdInUse() {
    assertFalse(manager.isIdInUse("Variable"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
    assertThrows(IllegalArgumentException.class, () -> manager.getObject("Variable2"));
    assertThrows(IllegalArgumentException.class, () -> manager.getId(variable2));
  }

  @Test
  public void testMultipleIsIdInUse() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable1"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.addObject(variable1);
    manager.addObject(variable2);
    manager.addObject(variable3);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.isIdInUse("Variable3"));
  }

  @Test
  public void testIsIdInUseGameObject() {
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    manager.addObject(object1);
    assertTrue(manager.isIdInUse("EmptyGameObject"));
  }

  @Test
  public void testMultipleIsIdInUseGameObject() {
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject1"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    manager.addObject(object1);
    manager.addObject(object2);
    manager.addObject(object3);
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.isIdInUse("EmptyGameObject3"));
  }

  @Test
  public void testMultipleDifferentOrders() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable1"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject1"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    manager.addObject(variable3);
    manager.addObject(object1);
    manager.addObject(variable2);
    manager.addObject(object3);
    manager.addObject(variable1);
    manager.addObject(object2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.isIdInUse("Variable3"));
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.isIdInUse("EmptyGameObject3"));
  }

  @Test
  public void testMultipleAdd() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.addObject(variable1);
    manager.addObject(variable2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
  }

  @Test
  public void testRepeatAdd() {
    assertFalse(manager.isIdInUse("Variable"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
    assertThrows(IllegalArgumentException.class, () -> manager.addObject(variable1));
    assertTrue(manager.isIdInUse("Variable"));
    //check that only one variable is added
    assertTrue(manager.getSimpleIds().size() == 1);
  }

  @Test
  public void testRemove() {
    assertFalse(manager.isIdInUse("Variable"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
    manager.removeObject(variable1);
    assertFalse(manager.isIdInUse("Variable"));
  }

  @Test
  public void testRemoveMultiple() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable1"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject1"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    manager.addObject(variable3);
    manager.addObject(object1);
    manager.addObject(variable2);
    manager.addObject(object3);
    manager.addObject(variable1);
    manager.addObject(object2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.isIdInUse("Variable3"));
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.isIdInUse("EmptyGameObject3"));
    manager.removeObject(variable1);
    manager.removeObject(object2);
    manager.removeObject(variable3);
    manager.removeObject(object1);
    manager.removeObject(variable2);
    manager.removeObject(object3);
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertFalse(manager.isIdInUse("Variable3"));
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    assertFalse(manager.isIdInUse("EmptyGameObject3"));
  }

  @Test
  public void testGetObject() {
    assertFalse(manager.isIdInUse("Variable"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.getObject("Variable") == variable1);
  }

  @Test
  public void testGetMultipleSimpleIds() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.addObject(variable1);
    manager.addObject(variable2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.getObject("Variable") == variable1);
    assertTrue(manager.getObject("Variable2") == variable2);
  }

  @Test
  public void testGetSimpleIds() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.addObject(variable1);
    manager.addObject(variable2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    Map<String, Ownable> ids = manager.getSimpleIds();
    assertTrue(ids.size() == 2);
    assertTrue(ids.get("Variable") == variable1);
    assertTrue(ids.get("Variable2") == variable2);
  }

  @Test
  public void testMultipleGetSimpleIds() {
    //Order of adding/getting is intentionally arbitrary
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable1"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject1"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    manager.addObject(variable3);
    manager.addObject(object1);
    manager.addObject(variable2);
    manager.addObject(object3);
    manager.addObject(variable1);
    manager.addObject(object2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.isIdInUse("Variable3"));
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.isIdInUse("EmptyGameObject3"));
    Map<String, Ownable> ids = manager.getSimpleIds();
    assertTrue(ids.size() == 6);
    assertTrue(ids.get("EmptyGameObject") == object1);
    assertTrue(ids.get("Variable") == variable3);
    assertTrue(ids.get("Variable2") == variable2);
    assertTrue(ids.get("Variable3") == variable1);
    assertTrue(ids.get("EmptyGameObject2") == object3);
    assertTrue(ids.get("EmptyGameObject3") == object2);
  }

  @Test
  public void testAddParent() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject1"));
    manager.addObject(object1);
    manager.addObject(object2, object1);
    manager.addObject(variable1, object2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.getObject("Variable") == variable1);
    assertTrue(manager.getObject("EmptyGameObject") == object1);
    assertTrue(manager.getObject("EmptyGameObject2") == object2);
    assertEquals(manager.getId(object1), "EmptyGameObject");
    assertEquals(manager.getId(object2), "EmptyGameObject.EmptyGameObject2");
    assertEquals(manager.getId(variable1), "EmptyGameObject.EmptyGameObject2.Variable");
  }

  @Test
  public void testMultipleChildren() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable1"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject1"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    manager.addObject(object1);
    manager.addObject(object2, object1);
    manager.addObject(variable1, object2);
    manager.addObject(variable2, object2);
    manager.addObject(object3, object1);
    manager.addObject(variable3, object3);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.isIdInUse("Variable3"));
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.isIdInUse("EmptyGameObject3"));
    assertTrue(manager.getObject("Variable") == variable1);
    assertTrue(manager.getObject("Variable2") == variable2);
    assertTrue(manager.getObject("Variable3") == variable3);
    assertTrue(manager.getObject("EmptyGameObject") == object1);
    assertTrue(manager.getObject("EmptyGameObject2") == object2);
    assertTrue(manager.getObject("EmptyGameObject3") == object3);
    assertEquals(manager.getId(object1), "EmptyGameObject");
    assertEquals(manager.getId(object2), "EmptyGameObject.EmptyGameObject2");
    assertEquals(manager.getId(object3), "EmptyGameObject.EmptyGameObject3");
    assertEquals(manager.getId(variable1), "EmptyGameObject.EmptyGameObject2.Variable");
    assertEquals(manager.getId(variable2), "EmptyGameObject.EmptyGameObject2.Variable2");
    assertEquals(manager.getId(variable3), "EmptyGameObject.EmptyGameObject3.Variable3");
  }

  @Test
  public void testRemoveObject() {
    assertFalse(manager.isIdInUse("Variable"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
    manager.removeObject(variable1);
    assertFalse(manager.isIdInUse("Variable"));
    assertTrue(manager.getSimpleIds().size() == 0);
  }

  @Test
  public void testRemoveNonExisting() {
    assertFalse(manager.isIdInUse("Variable"));
    assertThrows(IllegalArgumentException.class, () -> manager.removeObject(variable1));
  }

  @Test
  public void testRemoveMiddleChild() {
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject1"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    manager.addObject(object1);
    manager.addObject(object2, object1);
    manager.addObject(object3, object2);
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.isIdInUse("EmptyGameObject3"));
    manager.removeObject(object2);
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    assertFalse(manager.isIdInUse("EmptyGameObject3"));
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.getSimpleIds().size() == 1);
  }

  @Test
  public void testRenameObject() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.changeId("Variable", "Variable2");
    assertFalse(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.getSimpleIds().size() == 1);
  }

  @Test
  public void testRenameToFutureName() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.changeId("Variable", "Variable2");
    assertFalse(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.getSimpleIds().size() == 1);
    manager.addObject(variable2);
    assertTrue(manager.isIdInUse("Variable3"));
  }

  @Test
  public void testRenameToExisting() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    manager.addObject(variable1);
    manager.addObject(variable2);
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertThrows(IllegalArgumentException.class, () -> manager.changeId("Variable", "Variable2"));
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.getSimpleIds().size() == 2);
  }

  @Test
  public void testRenameNonExisting() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertThrows(IllegalArgumentException.class, () -> manager.changeId("Variable", "Variable2"));
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertTrue(manager.getSimpleIds().size() == 0);
  }

  @Test
  public void testRenameAndGetId() {
    manager.addObject(object1);
    manager.addObject(object2, object1);
    manager.addObject(object3, object2);
    assertEquals(manager.getId(object1), "EmptyGameObject");
    assertEquals(manager.getId(object2), "EmptyGameObject.EmptyGameObject2");
    assertEquals(manager.getId(object3), "EmptyGameObject.EmptyGameObject2.EmptyGameObject3");
    manager.changeId("EmptyGameObject2", "EmptyGameObject2Renamed");
    assertEquals(manager.getId(object1), "EmptyGameObject");
    assertEquals(manager.getId(object2), "EmptyGameObject.EmptyGameObject2Renamed");
    assertEquals(manager.getId(object3), "EmptyGameObject.EmptyGameObject2Renamed.EmptyGameObject3");
  }

  @Test
  public void testClear() {
    //add a bunch of objects
    manager.addObject(object1);
    manager.addObject(object2, object1);
    manager.addObject(object3, object2);
    manager.addObject(variable1, object1);
    manager.addObject(variable2, object2);
    manager.addObject(variable3, object3);
    //make sure they are all there
    assertTrue(manager.isIdInUse("EmptyGameObject"));
    assertTrue(manager.isIdInUse("EmptyGameObject2"));
    assertTrue(manager.isIdInUse("EmptyGameObject3"));
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.isIdInUse("Variable3"));
    //clear the manager
    manager.clear();
    //make sure they are all gone
    assertFalse(manager.isIdInUse("EmptyGameObject"));
    assertFalse(manager.isIdInUse("EmptyGameObject2"));
    assertFalse(manager.isIdInUse("EmptyGameObject3"));
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("Variable2"));
    assertFalse(manager.isIdInUse("Variable3"));
    assertTrue(manager.getSimpleIds().size() == 0);
  }

  @Test
  public void testIterator() {
    manager.addObject(object1);
    manager.addObject(object2, object1);
    manager.addObject(object3, object2);
    manager.addObject(variable1, object1);
    manager.addObject(variable2, object2);
    manager.addObject(variable3, object3);
    int num = 0;
    for(Map.Entry<String, Ownable> entry : manager) {
      num++;
      //check that it is one of the objects
      assertTrue(entry.getValue() == object1 || entry.getValue() == object2 || entry.getValue() == object3 || entry.getValue() == variable1 || entry.getValue() == variable2 || entry.getValue() == variable3);
    }
    assertTrue(num == 6);
  }

  @Test
  public void testAddWithId() {
    assertFalse(manager.isIdInUse("Variable"));
    assertFalse(manager.isIdInUse("random"));
    manager.addObject(variable1, "Variable");
    manager.addObject(variable2, "random");
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("random"));
    assertTrue(manager.getSimpleIds().size() == 2);
  }

  @Test
  public void testAddWithIdAndParent() {
    assertFalse(manager.isIdInUse("this_is_a_test"));
    assertFalse(manager.isIdInUse("this_is_a_test_random"));
    manager.addObject(variable1, "this_is_a_test");
    manager.addObject(variable2, "this_is_a_test_random", variable1);
    assertTrue(manager.isIdInUse("this_is_a_test"));
    assertTrue(manager.isIdInUse("this_is_a_test_random"));
  }

  @Test
  public void testAddWithParentId() {
    assertFalse(manager.isIdInUse("this_is_a_test"));
    assertFalse(manager.isIdInUse("this_is_a_test_random"));
    manager.addObject(variable1, "this_is_a_test");
    manager.addObject(variable2, "this_is_a_test_random", "this_is_a_test");
    assertTrue(manager.isIdInUse("this_is_a_test"));
    assertTrue(manager.isIdInUse("this_is_a_test_random"));
    //make sure the parent is correct
    assertEquals(manager.getId(variable2), "this_is_a_test.this_is_a_test_random");
  }

  @Test
  public void testCannotContainDot() {
    assertFalse(manager.isIdInUse("this.is.a.test"));
    assertThrows(IllegalArgumentException.class, () -> manager.addObject(variable1, "this.is.a.test"));
    assertFalse(manager.isIdInUse("this.is.a.test"));
    assertThrows(IllegalArgumentException.class, () -> manager.addObject(variable1, "this.is.a.test", "this.is.a.test"));
    assertFalse(manager.isIdInUse("this.is.a.test"));
    assertThrows(IllegalArgumentException.class, () -> manager.addObject(variable1, "this.is.a.test", variable1));
    assertFalse(manager.isIdInUse("this.is.a.test"));
    manager.addObject(variable1);
    assertTrue(manager.isIdInUse("Variable"));
  }

  @Test
  public void addManyInDifferentWays() {
    manager.addObject(variable1);
    manager.addObject(variable2);
    manager.addObject(variable3, variable1);
    manager.addObject(object1, "x");
    manager.addObject(object2, "amazingObject", "x");
    manager.addObject(object3, "amazingObject2", "amazingObject");
    assertTrue(manager.isIdInUse("Variable"));
    assertTrue(manager.isIdInUse("Variable2"));
    assertTrue(manager.isIdInUse("Variable3"));
    assertTrue(manager.isIdInUse("x"));
    assertTrue(manager.isIdInUse("amazingObject"));
    assertTrue(manager.isIdInUse("amazingObject2"));
    assertTrue(manager.getSimpleIds().size() == 6);
    //test that the parents are correct
    assertEquals(manager.getId(variable1), "Variable");
    assertEquals(manager.getId(variable2), "Variable2");
    assertEquals(manager.getId(variable3), "Variable.Variable3");
    assertEquals(manager.getId(object1), "x");
    assertEquals(manager.getId(object2), "x.amazingObject");
    assertEquals(manager.getId(object3), "x.amazingObject.amazingObject2");
  }

  @Test
  public void testCheckClasses() {
    variable1.addClass("test");
    variable1.addClass("test2");
    variable1.addClass("test3");
    variable2.addClass("test");
    manager.addObject(variable1);
    manager.addObject(variable2, variable1);
    assertTrue(manager.getIdsOfObjectsOfClass("test").size() == 2);
    assertTrue(manager.getIdsOfObjectsOfClass("test2").size() == 1);
    assertTrue(manager.getIdsOfObjectsOfClass("test3").size() == 1);
    assertTrue(manager.getIdsOfObjectsOfClass("test4").size() == 0);
    assertTrue(manager.getIdsOfObjectsOfClass("test").contains("Variable"));
    assertTrue(manager.getIdsOfObjectsOfClass("test").contains("Variable.Variable2"));
    assertFalse(manager.getIdsOfObjectsOfClass("invalid").contains("Variable"));

  }

}

