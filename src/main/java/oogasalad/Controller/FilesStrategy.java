package oogasalad.Controller;

import oogasalad.frontend.components.Component;
import oogasalad.frontend.components.dropzoneComponent.Dropzone;
import oogasalad.frontend.components.gridObjectComponent.GridObject;
import oogasalad.sharedDependencies.backend.filemanagers.FileManager;

public class FilesStrategy {

  private FileManager layout;
  private FileManager object;
  public FilesStrategy(FileManager lay, FileManager obj){
    layout = lay;
    object = obj;
  }

  public FileManager getFileLocation(Component c){
    if(c.getClass() == Dropzone.class){
      return layout;
    }
    if(c.getClass() == GridObject.class){
      return layout;
    }
    else{
      return object;
    }
  }
}
