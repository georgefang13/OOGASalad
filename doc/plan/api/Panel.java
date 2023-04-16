public interface Panel {

  /**
   * Updates the contents of the current panel
   **/
  public void updatePanel();

  /**
   * Sets the pannel content need to figure out how to pass the content in a data file type way
   *
   * @param some object that will contain the content for the panel
   **/
  public void setPanelContent(Content content);


}