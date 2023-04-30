package oogasalad.frontend.components.textObjectComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.frontend.components.AbstractComponent;

import java.util.Map;


public class TextObject extends AbstractComponent implements TextObjectComponent {
    private String name;
    private Text text;
    private String content;
    private double fontSize;
    private Color color;
    private double rotate;

    public TextObject(String id) {
        super(id);
        instantiatePropFile("frontend.properties.Defaults.TextObject");
        //this.setDefault();
        this.followMouse();
        this.getNode();
    }

    public TextObject(String ID, Map<String, String> map){
        super(ID);
        setDraggable(true);
        setValuesfromMap(map);
        initialize();
        followMouse();
    }

    private void initialize() {
        text = new Text(content);
        text.setFill(color);
        setTextSize(fontSize);
        text.setRotate(rotate);
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
