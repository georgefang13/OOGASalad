package oogasalad.frontend.components.rectangleObjectComponent;
import oogasalad.frontend.components.Component;
import javafx.scene.paint.Color;
;

public interface RectangleObjectComponent extends Component {
    void setFill(Color fill);
    Color getFill();
    void setStrokeWidth(double strokeWidth);
    double getStrokeWidth();
    void setStrokeColor(Color strokeColor);
    Color getStrokeColor();
}
