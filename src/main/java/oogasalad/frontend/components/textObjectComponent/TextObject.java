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

    public TextObject(String id) {
        super(id);
        instantiatePropFile("frontend.properties.Defaults.TextObject");
        this.setDefault();
        this.followMouse();
        this.getNode();
    }

    public TextObject(String ID, Map<String, String> map){
        super(ID);
        setValuesfromMap(map);
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

    @Override
    public void setDefault() {
        String content = getDEFAULT_BUNDLE().getString("text.content");
        double x = Double.parseDouble(getDEFAULT_BUNDLE().getString("text.x"));
        double y = Double.parseDouble(getDEFAULT_BUNDLE().getString("text.y"));
        String colorString = getDEFAULT_BUNDLE().getString("text.color");
        double fontSize = Double.parseDouble(getDEFAULT_BUNDLE().getString("text.fontSize"));
        Color color = Color.web(colorString);

        text = new Text(x, y, content);
        text.setFont(Font.font(fontSize));
        text.setFill(Color.BLACK);
    }
}
