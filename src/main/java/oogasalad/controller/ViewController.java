package oogasalad.controller;


import java.util.ArrayList;
import java.util.List;
import javafx.stage.Stage;
import oogasalad.frontend.views.OriginView;
import oogasalad.frontend.views.View;

public class ViewController implements EmptyController {

  private List<View> views;

  public ViewController(Stage originStage) {
    OriginView originView = new OriginView(originStage, this);
    originView.open();
    views = new ArrayList<>();
    views.add(originView);
  }

  public void open(View gameChooserView){
    gameChooserView.open();
    views.add(gameChooserView);
  }

}
