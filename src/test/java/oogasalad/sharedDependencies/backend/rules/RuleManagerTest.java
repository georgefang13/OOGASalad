package oogasalad.sharedDependencies.backend.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import oogasalad.sharedDependencies.backend.ownables.gameobjects.GameObject;
import oogasalad.sharedDependencies.backend.rules.RuleClass;
import oogasalad.sharedDependencies.backend.rules.RuleManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RuleManagerTest {

  private RuleManager ruleManager;
  private String cls1;
  private String cls2;

  @BeforeEach
  public void setUp() {
    ruleManager = new RuleManager();
    cls1 = "red";
    cls2 = "black";
  }

  @Test
  public void testAddAndGetRule() {
    ruleManager.addRule(cls1, "rule1", "value1");
    assertEquals("value1", ruleManager.getRule(cls1, "rule1"));
  }

  @Test
  public void testModifyRule() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.modifyRule(cls1, "rule1", "value2");
    assertEquals("value2", ruleManager.getRule(cls1, "rule1"));
  }

  @Test
  public void testRemoveRule() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.removeRule(cls1, "rule1");
    assertNull(ruleManager.getRule(cls1, "rule1"));
  }

  @Test
  public void testClearOwnable() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule2", "value2");
    ruleManager.clearClass(cls1);
    assertNull(ruleManager.getRule(cls1, "rule1"));
    assertNotNull(ruleManager.getRule(cls2, "rule2"));
  }

  @Test
  public void testClearRule() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule1", "value2");
    ruleManager.addRule(cls1, "rule2", "value3");
    ruleManager.addRule(cls2, "rule2", "value4");
    ruleManager.clearRule("rule1");
    assertNull(ruleManager.getRule(cls1, "rule1"));
    assertNull(ruleManager.getRule(cls2, "rule1"));
    assertNotNull(ruleManager.getRule(cls1, "rule2"));
    assertNotNull(ruleManager.getRule(cls2, "rule2"));
  }

  @Test
  public void testClear() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule2", "value2");
    ruleManager.clear();
    assertNull(ruleManager.getRule(cls1, "rule1"));
    assertNull(ruleManager.getRule(cls2, "rule2"));
  }

  @Test
  public void testGetTrackedOwnables() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule2", "value2");
    ruleManager.addRule(cls2, "rule3", "value3");
    assertEquals(2, ruleManager.getTrackedClasses().size());
  }

  @Test
  public void testToString() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule2", "value2");
    ruleManager.addRule(cls2, "rule3", "value3");
    String result = ruleManager.toString();
    System.out.println(result);
    assertTrue(result.contains("rule1"));
    assertTrue(result.contains("rule2"));
    assertTrue(result.contains("value1"));
    assertTrue(result.contains("value2"));
  }

  @Test
  public void testAddRule() {
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule2", "value2");
    assertEquals("value1", ruleManager.getRule(cls1, "rule1"));
    assertEquals("value2", ruleManager.getRule(cls2, "rule2"));
  }

  @Test
  public void testComplex() {
    // set up the RuleManager
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls1, "rule2", "value2");
    ruleManager.addRule(cls2, "rule1", "value3");
    ruleManager.addRule(cls2, "rule2", "value4");

    // modify a rule
    ruleManager.modifyRule(cls1, "rule1", "value5");
    assertEquals("value5", ruleManager.getRule(cls1, "rule1"));

    // clear a rule for an ownable
    ruleManager.removeRule(cls2, "rule1");
    assertNull(ruleManager.getRule(cls2, "rule1"));

    // add new rules to an existing ownable
    ruleManager.addRule(cls1, "rule3", "value6");
    ruleManager.addRule(cls1, "rule4", "value7");
    assertEquals("value6", ruleManager.getRule(cls1, "rule3"));
    assertEquals("value7", ruleManager.getRule(cls1, "rule4"));

    // clear an ownable and check that its rules are removed
    ruleManager.clearClass(cls2);
    assertNull(ruleManager.getRule(cls2, "rule2"));

    // clear all rules with a specific name
    ruleManager.addRule(cls1, "rule1", "value8");
    ruleManager.clearRule("rule1");
    assertNull(ruleManager.getRule(cls1, "rule1"));
    assertNull(ruleManager.getRule(cls2, "rule1"));

    // clear all rules and check that they are removed
    ruleManager.clear();
    assertNull(ruleManager.getRule(cls1, "rule2"));
    assertNull(ruleManager.getRule(cls1, "rule3"));
    assertNull(ruleManager.getRule(cls1, "rule4"));
    assertNull(ruleManager.getRule(cls2, "rule2"));
  }

  @Test
  public void copyMultipleRules(){
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls1, "rule2", "value2");
    ruleManager.addRule(cls2, "rule1", "value3");
    ruleManager.addRule(cls2, "rule2", "value4");

    List<RuleClass> classes = new ArrayList<>();
    RuleClass c1 = new RuleClass(cls1);
    c1.setRule("rule1", "value5");
    c1.setRule("rule3", "value6");
    classes.add(c1);
    RuleClass c2 = new RuleClass(cls2);
    c2.setRule("rule2", "value7");
    c2.setRule("rule4", "value8");
    classes.add(c2);
    RuleClass c3 = new RuleClass("orange");
    c3.setRule("rule1", "value9");
    classes.add(c3);

    ruleManager.addRules(classes);

    assertEquals("value5", ruleManager.getRule(cls1, "rule1"));
    assertEquals("value2", ruleManager.getRule(cls1, "rule2"));
    assertEquals("value3", ruleManager.getRule(cls2, "rule1"));
    assertEquals("value7", ruleManager.getRule(cls2, "rule2"));
    assertEquals("value9", ruleManager.getRule("orange", "rule1"));
  }

  @Test
  public void testObjectRule(){
    GameObject obj1 = new GameObject(null);
    obj1.addClass(cls1);
    GameObject obj2 = new GameObject(null);
    obj2.addClass(cls2);
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule1", "value2");
    assertEquals("value1", ruleManager.getRuleFromObject(obj1, "rule1"));
    assertEquals("value2", ruleManager.getRuleFromObject(obj2, "rule1"));
    assertNull(ruleManager.getRuleFromObject(obj1, "rule86"));
  }

  @Test
  public void testEquals(){
    RuleClass r1 = new RuleClass(cls1);
    RuleClass r2 = new RuleClass(cls1);
    RuleClass r3 = new RuleClass(cls2);
    assertTrue(r1.equals(r2));
    assertFalse(r1.equals(r3));
    assertFalse(r1.equals(5));
  }

  @Test
  public void testObjectDualClass(){
    GameObject obj1 = new GameObject(null);
    obj1.addClass(cls1);
    obj1.addClass(cls2);
    ruleManager.addRule(cls1, "rule1", "value1");
    ruleManager.addRule(cls2, "rule1", "value2");
    ruleManager.addRule(cls1 + "." + cls2, "rule1", "value3");
    assertEquals("value3", ruleManager.getRuleFromObject(obj1, "rule1"));
  }

}