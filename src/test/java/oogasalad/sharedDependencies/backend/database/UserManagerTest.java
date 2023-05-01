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
  private static final String WRONG = "idk man";

  private static Map<String, String> securityQuestions;
  private static Map<String, String> wrongAnswers;
  private static Database db;
  private static UserManager userManager;

  @BeforeAll
  static void setup() {
    db = MockDatabase.getInstance();
    userManager = new UserManager(db);
    securityQuestions = new HashMap<>();
    securityQuestions.put(QUESTION1, ANSWER1);
    securityQuestions.put(QUESTION2, ANSWER2);
    wrongAnswers = new HashMap<>();
    wrongAnswers.put(QUESTION1, ANSWER1);
    wrongAnswers.put(QUESTION2, WRONG);
  }

  @BeforeEach
  void clearDatabase() throws InterruptedException {
    db.deleteEntry(COLLECTION, "rodrigo");
    db.deleteEntry(COLLECTION,"hotrod");
    db.deleteEntry(COLLECTION, "theman");
    Thread.sleep(100);
  }

  @Test
  void loginAndRegisterTest() {
    assertTrue(userManager.tryRegister("rodrigo", "ilovets13")); // register user
    assertTrue(userManager.tryLogin("rodrigo", "ilovets13")); // correct login
    assertFalse(userManager.tryLogin("rodrigo", "incorrect")); // incorrect login
    assertFalse(userManager.tryRegister("rodrigo", "literallyanything")); // register existing user
    assertFalse(userManager.tryLogin("rodrigo", "literallyanything")); // ensure password didn't change
    assertTrue(userManager.tryLogin("rodrigo", "ilovets13")); // login again (correct password)
    assertTrue(userManager.tryRegister("theman", "loverrr")); // register new user
    assertTrue(userManager.tryLogin("theman", "loverrr")); // correct login
    assertTrue(userManager.tryLogin("rodrigo", "ilovets13")); // correct login previous user
    assertFalse(userManager.tryLogin("", "password")); // empty username
  }

  @Test
  void changePasswordTest() {
    assertTrue(userManager.tryRegister("rodrigo", "ilovets13", securityQuestions));
    assertTrue(userManager.tryChangePassword("rodrigo", "ireallylovets13", securityQuestions));
    assertTrue(userManager.tryLogin("rodrigo", "ireallylovets13"));
    assertFalse(userManager.tryChangePassword("rodrigo", "ilovets13", wrongAnswers));
    assertFalse(userManager.tryLogin("rodrigo", "ilovets13"));
  }
}
