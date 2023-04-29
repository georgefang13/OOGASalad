package oogasalad.Controller;

import oogasalad.frontend.components.dropzoneComponent.Dropzone;

public class DropZoneController {

  private Dropzone previous;
  private Dropzone current;

  public DropZoneController(){

  }

  /**
   * Keep track of previous dropzone to create
   * @param clicked
   */
  public void storePrevious(Dropzone clicked){
    previous = clicked;
  }

  /**
   * storeCurrent keeps track of clicked drop zone to create where the arrow should point
   * @param current
   */
  public void storeCurrent(Dropzone current){

  }

}
