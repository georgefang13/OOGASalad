package oogasalad.sharedDependencies.backend.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oogasalad.sharedDependencies.backend.ownables.Ownable;

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
   * All Ownables are mapped to a map of rules that they are associated with.
   * The rules are mapped to their names.
   */
  private final List<Map<Ownable, Map<String, String>>> rules = new ArrayList<>();

  /**
   * Adds a rule to the manager.
   * @param ownable The object that the rule is associated with.
   * @param ruleName The name of the rule.
   * @param rule The rule itself.
   */
  public void addRule(Ownable ownable, String ruleName, String rule) {
    //don't use map.of because it is immutable
    //if the ownable is already in the rules, then add the rule to the existing map
    for (Map<Ownable, Map<String, String>> existingRuleMap : rules) {
      if (existingRuleMap.containsKey(ownable)) {
        existingRuleMap.get(ownable).put(ruleName, rule);
        return;
      }
    }
    //otherwise, add the rule to the end of the list
    Map<Ownable, Map<String, String>> ruleMap = new HashMap<>();
    ruleMap.put(ownable, new HashMap<>());
    ruleMap.get(ownable).put(ruleName, rule);
    rules.add(ruleMap);
  }

  /**
   * Adds a rule to the manager.
   * @param ruleMap
   */
  public void addRule(Map<Ownable, Map<String, String>> ruleMap) {
    //if the ownable is already in the rules, then add the rule to the existing map
    for (Map<Ownable, Map<String, String>> existingRuleMap : rules) {
      if (existingRuleMap.containsKey(ruleMap.keySet().iterator().next())) {
        existingRuleMap.putAll(ruleMap);
        return;
      }
    }
    //otherwise, add the rule to the end of the list
    rules.add(ruleMap);
  }

  /**
   * Returns the rule associated with the given object and rule name.
   * @param obj The object that the rule is associated with.
   * @param ruleName The name of the rule.
   * @return The rule itself.
   */
  public String getRuleFromObject(Ownable obj, String ruleName) {
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      if (ruleMap.containsKey(obj)) {
        return ruleMap.get(obj).get(ruleName);
      }
    }
    return null;
  }

  /**
   * Overwrites the rule associated with the given object and rule name.
   * @param obj The object that the rule is associated with.
   * @param ruleName The name of the rule.
   * @param rule The rule itself.
   */
  public void modifyRule(Ownable obj, String ruleName, String rule) {
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      if (ruleMap.containsKey(obj)) {
        //set the rule to the new rule
        ruleMap.get(obj).put(ruleName, rule);
      }
    }
  }

  /**
   * Removes the rule associated with the given object and rule name.
   * @param obj The object that the rule is associated with.
   * @param ruleName The name of the rule.
   */
  public void removeRule(Ownable obj, String ruleName) {
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      if (ruleMap.containsKey(obj)) {
        ruleMap.get(obj).remove(ruleName);
        return;
      }
    }
  }

  /**
   * Removes all rules associated with the given object.
   * @param obj The object that the rules are associated with.
   */
  public void clearOwnable(Ownable obj) {
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      if (ruleMap.containsKey(obj)) {
        ruleMap.remove(obj);
        return;
      }
    }
  }

  /**
   * Removes all rules associated with the given rule name.
   * Works for all objects.
   * @param ruleName The name of the rule.
   */
  public void clearRule(String ruleName) {
    //remove all rules associated with the given rule name
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      //avoid concurrent modification exception
      List<Ownable> toRemove = new ArrayList<>();
      for (Ownable obj : ruleMap.keySet()) {
        if (ruleMap.get(obj).containsKey(ruleName)) {
          toRemove.add(obj);
        }
      }
      for (Ownable obj : toRemove) {
        ruleMap.get(obj).remove(ruleName);
      }
    }
  }

  /**
   * Removes all rules associated with the given object and rule name.
   * @param obj The object that the rule is associated with.
   * @param ruleName The name of the rule.
   */
  public void clearOwnableRule(Ownable obj, String ruleName) {
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      if (ruleMap.containsKey(obj)) {
        ruleMap.get(obj).remove(ruleName);
        return;
      }
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
  public Set<Ownable> getTrackedOwnables() {
    Set<Ownable> trackedOwnables = new HashSet<>();
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      trackedOwnables.addAll(ruleMap.keySet());
    }
    return trackedOwnables;
  }

  /**
   * Converts the manager to a string for saving and debugging purposes.
   * @return The string representation of the manager.
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("{\n");
    for (Map<Ownable, Map<String, String>> ruleMap : rules) {
      for (Ownable ownable : ruleMap.keySet()) {
        String className = ownable.toString().split("\\.")[ownable.toString().split("\\.").length-1];
        result.append("\t\"").append(className).append("\": {\n");
        Map<String, String> innerMap = ruleMap.get(ownable);
        for (String ruleName : innerMap.keySet()) {
          result.append("\t\t\"").append(ruleName).append("\": \"").append(innerMap.get(ruleName)).append("\",\n");
        }
        // remove the extra comma at the end
        result.deleteCharAt(result.length() - 2);
        result.append("\t},\n");
      }
    }
    // remove the extra comma at the end
    result.deleteCharAt(result.length() - 2);
    result.append("}");
    return result.toString();
  }

}
