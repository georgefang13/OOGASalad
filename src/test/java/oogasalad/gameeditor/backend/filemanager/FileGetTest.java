package oogasalad.gameeditor.backend.filemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import oogasalad.sharedDependencies.backend.filemanagers.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Class for testing FileManager functionalities of getting information from configuration files
 *
 * @author Rodrigo Bassi Guerreiro
 */
public class FileGetTest {
  FileManager fileManager;

  @BeforeEach
  void setup() {
    fileManager = new FileManager();
  }
}
