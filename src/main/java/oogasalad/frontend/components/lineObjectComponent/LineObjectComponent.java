package oogasalad.frontend.components.lineObjectComponent;

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
    public String getColor();
    void setColor(String color);
    double getStrokeWidth();
    void setStrokeWidth(double strokeWidth);
    
}
