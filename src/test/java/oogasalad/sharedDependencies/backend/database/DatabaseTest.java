package oogasalad.sharedDependencies.backend.database;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import oogasalad.gamerunner.backend.database.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
  MockDatabase db;

  @BeforeEach
  void setup() throws IOException {
    db = new MockDatabase();
  }

  @Test
  void addTest() {
    assertDoesNotThrow(() -> {
      db.addData("Hello world", "Test");
    });
  }


}
