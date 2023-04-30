package oogasalad.frontend.components.rectangleObjectComponent;
import oogasalad.frontend.components.Component;
import javafx.scene.paint.Color;
;

public interface RectangleObjectComponent extends Component {
    void setWidth(double width);
    double getWidth(double width);
    void setHeight(double height);
    double getHeight(double height);
    void setFill(Color fill);
    Color getFill();
}
