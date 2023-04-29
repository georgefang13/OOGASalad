package oogasalad.frontend.components.lineObjectComponent;

import javafx.scene.paint.Color;
import oogasalad.frontend.components.Component;

public interface LineObjectComponent extends Component {
    
    double getStartX();
    void setStartX(double startX);
    double getStartY();
    void setStartY(double startY);
    double getEndX();
    void setEndX(double endX);
    double getEndY();
    void setEndY(double endY);
    public Color getColor();
    void setColor(Color color);
    double getStrokeWidth();
    void setStrokeWidth(double strokeWidth);
    
}
