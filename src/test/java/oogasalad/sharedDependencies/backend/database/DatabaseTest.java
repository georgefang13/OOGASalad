package oogasalad.sharedDependencies.backend.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
  static Database db;

  @BeforeAll
  static void setup() throws IOException {
    db = MockDatabase.getInstance();
  }

  @Test
  void simpleDatabaseTest() throws InterruptedException {
    String collection = "Login Information";
    String entry = "Users";
    String field = "Rodrigo";
    String data = "Slay!!!!!";
    db.addData(collection, entry, field, data);
    assertEquals("Slay!!!!!", db.getData(collection, entry, field));

    Long moreData = 42L;
    db.addData(collection, entry, field, moreData);
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
