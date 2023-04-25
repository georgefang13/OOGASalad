package oogasalad.sharedDependencies.backend.rules;

import java.util.HashMap;
import java.util.Map;
import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.owners.GameWorld;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

import oogasalad.sharedDependencies.backend.ownables.Ownable;


public class RuleManagerTest {

  private RuleManager ruleManager;
  private Ownable ownable1;
  private Ownable ownable2;

  @BeforeEach
  public void setUp() {
    ruleManager = new RuleManager();
    GameWorld gameWorld = new GameWorld();
    ownable1 = new GameObject(gameWorld);
    ownable2 = new GameObject(gameWorld);
  }

  @Test
  public void testAddAndGetRule() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    assertEquals("value1", ruleManager.getRuleFromObject(ownable1, "rule1"));
  }

  @Test
  public void testModifyRule() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.modifyRule(ownable1, "rule1", "value2");
    assertEquals("value2", ruleManager.getRuleFromObject(ownable1, "rule1"));
  }

  @Test
  public void testRemoveRule() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.removeRule(ownable1, "rule1");
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule1"));
  }

  @Test
  public void testClearOwnable() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable2, "rule2", "value2");
    ruleManager.clearOwnable(ownable1);
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule1"));
    assertNotNull(ruleManager.getRuleFromObject(ownable2, "rule2"));
  }

  @Test
  public void testClearRule() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable2, "rule1", "value2");
    ruleManager.addRule(ownable1, "rule2", "value3");
    ruleManager.addRule(ownable2, "rule2", "value4");
    ruleManager.clearRule("rule1");
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule1"));
    assertNull(ruleManager.getRuleFromObject(ownable2, "rule1"));
    assertNotNull(ruleManager.getRuleFromObject(ownable1, "rule2"));
    assertNotNull(ruleManager.getRuleFromObject(ownable2, "rule2"));
  }

  @Test
  public void testClearOwnableRule() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable2, "rule1", "value2");
    ruleManager.clearOwnableRule(ownable1, "rule1");
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule1"));
    assertNotNull(ruleManager.getRuleFromObject(ownable2, "rule1"));
  }

  @Test
  public void testClear() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable2, "rule2", "value2");
    ruleManager.clear();
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule1"));
    assertNull(ruleManager.getRuleFromObject(ownable2, "rule2"));
  }

  @Test
  public void testGetTrackedOwnables() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable2, "rule2", "value2");
    ruleManager.addRule(ownable2, "rule3", "value3");
    assertEquals(2, ruleManager.getTrackedOwnables().size());
  }

  @Test
  public void testToString() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable2, "rule2", "value2");
    ruleManager.addRule(ownable2, "rule3", "value3");
    String result = ruleManager.toString();
    System.out.println(result);
    assertTrue(result.contains("rule1"));
    assertTrue(result.contains("rule2"));
    assertTrue(result.contains("value1"));
    assertTrue(result.contains("value2"));
  }

  @Test
  public void testAddRule() {
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable2, "rule2", "value2");
    assertEquals("value1", ruleManager.getRuleFromObject(ownable1, "rule1"));
    assertEquals("value2", ruleManager.getRuleFromObject(ownable2, "rule2"));
  }

  @Test
  public void testComplex() {
    // set up the RuleManager
    ruleManager.addRule(ownable1, "rule1", "value1");
    ruleManager.addRule(ownable1, "rule2", "value2");
    ruleManager.addRule(ownable2, "rule1", "value3");
    ruleManager.addRule(ownable2, "rule2", "value4");

    // modify a rule
    ruleManager.modifyRule(ownable1, "rule1", "value5");
    assertEquals("value5", ruleManager.getRuleFromObject(ownable1, "rule1"));

    // clear a rule for an ownable
    ruleManager.clearOwnableRule(ownable2, "rule1");
    assertNull(ruleManager.getRuleFromObject(ownable2, "rule1"));

    // add new rules to an existing ownable
    ruleManager.addRule(ownable1, "rule3", "value6");
    ruleManager.addRule(ownable1, "rule4", "value7");
    assertEquals("value6", ruleManager.getRuleFromObject(ownable1, "rule3"));
    assertEquals("value7", ruleManager.getRuleFromObject(ownable1, "rule4"));

    // clear an ownable and check that its rules are removed
    ruleManager.clearOwnable(ownable2);
    assertNull(ruleManager.getRuleFromObject(ownable2, "rule2"));

    // clear all rules with a specific name
    ruleManager.addRule(ownable1, "rule1", "value8");
    ruleManager.clearRule("rule1");
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule1"));
    assertNull(ruleManager.getRuleFromObject(ownable2, "rule1"));

    // clear all rules and check that they are removed
    ruleManager.clear();
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule2"));
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule3"));
    assertNull(ruleManager.getRuleFromObject(ownable1, "rule4"));
    assertNull(ruleManager.getRuleFromObject(ownable2, "rule2"));
  }




}
