package oogasalad.sharedDependencies.backend.database;

import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserManagerTest {
  private static final String COLLECTION = "Login Information";
  private static final String ENTRY = "Users";

  private static Database db;
  private static UserManager userManager;

  @BeforeAll
  static void setup() {
    db = MockDatabase.getInstance();
    userManager = new UserManager(db);
  }

  @BeforeEach
  void initializeUsers() {
    db.deleteData(COLLECTION, ENTRY, "rodrigo");
    db.deleteData(COLLECTION, ENTRY, "hotrod");
    db.deleteData(COLLECTION, ENTRY, "ethan");
    db.deleteData(COLLECTION, ENTRY, "theman");
  }

  @Test
  void loginAndRegisterTest() throws InterruptedException {
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
}
