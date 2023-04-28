package oogasalad.frontend.components.textObjectComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.frontend.components.AbstractComponent;

import java.util.Map;
import java.util.ResourceBundle;


public class TextObject extends AbstractComponent implements TextObjectComponent {
    private Text text;
    String content;
    double x;
    double y;
    String colorString;
    double fontSize;
    Color color;

    public TextObject(String ID, Map<String, String> map){
        super(ID);
        instantiatePropFile("frontend.properties.Defaults.TextObject");
        setValuesfromMap(map);
        this.followMouse();
        text = new Text(x, y, content);
    }

    @Override
    public void setText(String content) {
        text.setText(content);
    }

    @Override
    public void setTextPosition(double x, double y) {
        text.setX(x);
        text.setY(y);
    }

    @Override
    public void setTextColor(Color color) {
        text.setFill(color);
    }

    @Override
    public void setTextSize(double fontSize) {
        text.setFont(Font.font(fontSize));
    }

    @Override
    public Text getNode() {
        return text;
    }

}
