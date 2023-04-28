package oogasalad.sharedDependencies.backend.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import oogasalad.gamerunner.backend.database.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
  static MockDatabase db;

  @BeforeAll
  static void setup() throws IOException {
    db = new MockDatabase();
  }

  @Test
  void simpleDatabaseTest() throws InterruptedException {
    String collection = "Login Information";
    String entry = "Users";
    String field = "Rodrigo";
    String data = "Slay!!!!!";
    db.addData(collection, entry, field, data);
    Thread.sleep(1000);
    assertEquals("Slay!!!!!", db.getData(collection, entry, field));

    Long moreData = 42L;
    db.addData(collection, entry, field, moreData);
    Thread.sleep(1000);
    assertEquals(42L, db.getData(collection, entry, field));
  }

  @Test
  void sadTests() {
    String nonexistent = "Taylor Swift";
    String entry = "Midnights";
    String field = "Grammy";
    assertNull(db.getData(nonexistent, entry, field));
  }
}
