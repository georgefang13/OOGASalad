package oogasalad.sharedDependencies.backend.rules;

import oogasalad.sharedDependencies.backend.ownables.Ownable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class is responsible for managing the rules of the game.
 * It maps the rules to the objects that they are associated with.
 * An example structure:
 *
 * {
 * 	"GameObject@7a4ccb53": {
 * 		"available": "...code segment 1..."
 *    },
 * 	"GameObject@309e345f": {
 * 		"available": "...code segment 1...",
 * 		"otherRule": "...code segment 2..."
 *  },
 *  "GameObject@643544af": {
 *  * 		"available": "...code segment 3...",
 *  *  }
 * }
 *
 * Using the toString method produces this string representation of the rules.
 *
 * @author Michael Bryant
 */
public class RuleManager {

  /**
   * All class names are mapped to a map of rules that they are associated with.
   * The rules are mapped to their names.
   */
  private final List<RuleClass> rules = new ArrayList<>();

  private RuleClass getRuleClass(String str){
    for(RuleClass rc : rules){
      if(rc.getName().equals(str)){
        return rc;
      }
    }
    return null;
  }

  /**
   * Adds a rule to the manager.
   * @param cls The class that the rule is associated with.
   * @param ruleName The name of the rule.
   * @param rule The rule itself.
   */
  public void addRule(String cls, String ruleName, String rule) {
    // if the class is already in the rules, then add the rule to the existing rule class
    RuleClass rc = getRuleClass(cls);
    if (rc == null){
      String[] classes = cls.split("\\.");
      RuleClass newRule = new RuleClass(classes);
      newRule.setRule(ruleName, rule);
      rules.add(newRule);
    }
    else {
      rc.setRule(ruleName, rule);
    }
  }

  /**
   * Adds a rule to the manager.
   * @param ruleList a map of multiple class names to the rules associated with them
   */
  public void addRules(List<RuleClass> ruleList) {
    //if the class is already in the rules, then add the rule to the existing map
    for (RuleClass rc : ruleList) {
      if (rules.contains(rc)){
        RuleClass r = getRuleClass(rc.getName());
        r.copyRules(rc);
      }
      else {
        rules.add(rc);
      }
    }
  }

  /**
   * Gets a rule from class cls with name ruleName
   * @param cls the class name
   * @param ruleName the rule name
   * @return the rule
   */
  public String getRule(String cls, String ruleName){
    RuleClass rc = getRuleClass(cls);
    if (rc == null) return null;
    return rc.get(ruleName);
  }

  /**
   * Returns the rule associated with the given class and rule name.
   * @param obj The object that the rule is associated with.
   * @param ruleName The name of the rule.
   * @return The rule itself.
   */
  public String getRuleFromObject(Ownable obj, String ruleName) {
    for (int i = rules.size() - 1; i >= 0; i--){
      RuleClass rc = rules.get(i);
      if (rc.containsRule(ruleName) &&  rc.applies(obj)){
        return rc.get(ruleName);
      }
    }
    return null;
  }

  /**
   * Overwrites the rule associated with the given object and rule name.
   * @param cls The class that the rule is associated with.
   * @param ruleName The name of the rule.
   * @param rule The rule itself.
   */
  public void modifyRule(String cls, String ruleName, String rule) {
    RuleClass rc = getRuleClass(cls);
    if (rc == null) return;
    if (rc.containsRule(ruleName)){
      rc.setRule(ruleName, rule);
    }
  }

  /**
   * Removes the rule associated with the given class and rule name.
   * @param cls The class that the rule is associated with.
   * @param ruleName The name of the rule.
   */
  public void removeRule(String cls, String ruleName) {
    RuleClass rc = getRuleClass(cls);
    if (rc == null) return;
    if (rc.containsRule(ruleName)){
      rc.removeRule(ruleName);
    }
  }

  /**
   * Removes all rules associated with the given class.
   * @param cls The class that the rules are associated with.
   */
  public void clearClass(String cls) {
    RuleClass rc = getRuleClass(cls);
    if (rc != null) rc.clear();
  }

  /**
   * Removes all rules associated with the given rule name.
   * Works for all objects.
   * @param ruleName The name of the rule.
   */
  public void clearRule(String ruleName) {
    //remove all rules associated with the given rule name
    for (RuleClass rc : rules){
      rc.removeRule(ruleName);
    }
  }

  /**
   * Removes all rules from the manager.
   */
  public void clear() {
    rules.clear();
  }

  /**
   * Returns a set of all objects that have rules associated with them.
   * @return A set of all objects that have rules associated with them.
   */
  public Set<String> getTrackedClasses() {
    return new HashSet<>(rules.stream().map(RuleClass::getName).toList());
  }

  /**
   * Converts the manager to a string for saving and debugging purposes.
   * @return The string representation of the manager.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("RuleManager {\n");
    for (RuleClass rc : rules){
      result.append(rc.toString()).append("\n");
    }
    result.append("}");
    return result.toString();
  }

  public List<RuleClass> getRules() {
    return rules;
  }
}
