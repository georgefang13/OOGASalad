package oogasalad.sharedDependencies.backend.database;

import org.junit.jupiter.api.BeforeAll;

public class UserManagerTest {
  private static Database db;

  @BeforeAll
  static void setup() {
    db = new MockDatabase();
  }

}
