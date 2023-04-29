package oogasalad.sharedDependencies.backend.database;

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
    db = new MockDatabase();
    userManager = new UserManager(db);
  }

  @BeforeEach
  void initializeUsers() {
    db.addData(COLLECTION, ENTRY, "rodrigo", "ilovets13");
    db.addData(COLLECTION, ENTRY, "hotrod", "dropaslay");
    db.addData(COLLECTION, ENTRY, "ethan", "gormandized");
    db.deleteData(COLLECTION, ENTRY, "theman");
  }

  @Test
  void loginAndRegisterTest() throws InterruptedException {
    assertTrue(userManager.tryLogin("rodrigo", "ilovets13"));
    Thread.sleep(100);
    assertFalse(userManager.tryLogin("rodrigo", "badpassword"));
    Thread.sleep(100);
    assertTrue(userManager.tryRegister("theman", "loverrr"));
    Thread.sleep(100);
    assertFalse(userManager.tryRegister("rodrigo", "literallyanything"));
    Thread.sleep(100);
    assertTrue(userManager.tryLogin("theman", "loverrr"));
  }
}
