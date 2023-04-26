package oogasalad.sharedDependencies.backend.rules;

import oogasalad.sharedDependencies.backend.id.IdManageable;

import java.util.*;

/**
 * Defines a set of instructions that check whether some condition in the game has been met,
 * establishing some outcome (some player wins or loses)
 *
 * @author Ethan Horowitz
 * @author Rodrigo Bassi Guerreiro
 */
public class RuleClass {

  private final List<String> myClasses;
  private final Map<String, String> rules;

  public RuleClass(String... cls){
    myClasses = new ArrayList<>(Arrays.asList(cls));
    rules = new HashMap<>();
  }

  /**
   * Get the class name associated with the rules
   * @return the class name
   */
  public String getName(){
    return String.join(".", myClasses);
  }

  /**
   * Get the rule associated with the rule name
   * @param ruleName the name of the rule
   * @return the rule
   */
  public String get(String ruleName){
    return rules.get(ruleName);
  }

  /**
   * Check if the rule class contains a rule
   * @param ruleName the name of the rule
   * @return true if the rule class contains the rule, false otherwise
   */
  public boolean containsRule(String ruleName){
    return rules.containsKey(ruleName);
  }

  /**
   * Get all the rules associated with the rule class
   * @return the rules
   */
  public Map<String, String> getRules(){
    // create unmodifiable map
    return Collections.unmodifiableMap(rules);
  }

  /**
   * Adds a rule to the rule class. If that rule exists, it updates its value.
   * @param name the name of the rule
   * @param rule the rule
   * @return the previous rule associated with the name
   */
  public String setRule(String name, String rule){
    return rules.put(name, rule);
  }

  /**
   * Copy rules form one RuleClass to another RuleClass
   * @param other the other RuleClass
   */
  public void copyRules(RuleClass other){
    rules.putAll(other.getRules());
  }

  /**
   * remove a rule from the rule class
   * @param name the name of the rule
   */
  public void removeRule(String name){
    rules.remove(name);
  }

  /**
   * Clear all the rules from the rule class
   */
  public void clear(){
    rules.clear();
  }

  /**
   * Check if the rule class applies to a given GameObject or variable
   * @param obj the GameObject or variable
   * @return true if the rule class applies, false otherwise
   */
  public boolean applies(IdManageable obj){
    for (String cls : myClasses){
      if (!obj.usesClass(cls)){
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object o){
    if (o instanceof RuleClass rc){
      return getName().equals(rc.getName());
    }
    return false;
  }

  @Override
  public String toString(){
    StringBuilder sb = new StringBuilder();
    sb.append("<RuleClass name=\"").append(getName()).append("\">\n");
    for (String ruleName : rules.keySet()){
      sb.append("<Rule name=\"").append(ruleName).append("\">").append(rules.get(ruleName)).append("</Rule>\n");
    }
    sb.append("</RuleClass>\n");
    return sb.toString();
  }

}
