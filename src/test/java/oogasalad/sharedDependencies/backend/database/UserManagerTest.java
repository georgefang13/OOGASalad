package oogasalad.sharedDependencies.backend.database;

import java.util.HashMap;
import java.util.Map;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserManagerTest {
  private static final String COLLECTION = "Users";
  private static final String QUESTION1 = "mother";
  private static final String ANSWER1 = "jessica";
  private static final String QUESTION2 = "pet";
  private static final String ANSWER2 = "coalinha";

  private static Map<String, String> securityQuestions;
  private static Database db;
  private static UserManager userManager;

  @BeforeAll
  static void setup() {
    db = MockDatabase.getInstance();
    userManager = new UserManager(db);
    securityQuestions = new HashMap<>();
    securityQuestions.put(QUESTION1, ANSWER1);
    securityQuestions.put(QUESTION2, ANSWER2);
  }

  @BeforeEach
  void initializeUsers() {
    db.deleteEntry(COLLECTION, "rodrigo");
    db.deleteEntry(COLLECTION,"hotrod");
    db.deleteEntry(COLLECTION, "theman");
  }

  @Test
  void loginAndRegisterTest() throws InterruptedException {
    assertTrue(userManager.tryRegister("rodrigo", "ilovets13", securityQuestions)); // register user
    assertTrue(userManager.tryLogin("rodrigo", "ilovets13")); // correct login
    assertFalse(userManager.tryLogin("rodrigo", "incorrect")); // incorrect login
    assertFalse(userManager.tryRegister("rodrigo", "literallyanything", securityQuestions)); // register existing user
    assertFalse(userManager.tryLogin("rodrigo", "literallyanything")); // ensure password didn't change
    assertTrue(userManager.tryLogin("rodrigo", "ilovets13")); // login again (correct password)
    assertTrue(userManager.tryRegister("theman", "loverrr", securityQuestions)); // register new user
    assertTrue(userManager.tryLogin("theman", "loverrr")); // correct login
    assertTrue(userManager.tryLogin("rodrigo", "ilovets13")); // correct login previous user
    assertFalse(userManager.tryLogin("", "password")); // empty username
  }
}
