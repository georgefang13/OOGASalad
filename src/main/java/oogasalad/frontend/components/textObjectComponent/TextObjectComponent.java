package oogasalad.frontend.components.textObjectComponent;


import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.frontend.components.Component;

public interface TextObjectComponent extends Component {
    void setText(String content);

    void setTextPosition(double x, double y);
    void setTextColor(Color color);
    void setTextSize(double fontSize);

}
