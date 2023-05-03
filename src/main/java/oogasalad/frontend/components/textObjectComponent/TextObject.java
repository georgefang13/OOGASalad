package oogasalad.frontend.components.textObjectComponent;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import oogasalad.Controller.GameRunnerController;
import oogasalad.frontend.components.AbstractComponent;

import java.util.Map;


public class TextObject extends AbstractComponent implements TextObjectComponent {
    private String name;
    private Text text;
    private String content;
    private double fontSize;
    private Color color;
    private double rotate;
    private GameRunnerController gameRunnerController;

    public TextObject(String id, GameRunnerController gameRunnerController) {
        super(id);
        this.gameRunnerController = gameRunnerController;
        instantiatePropFile("frontend.properties.Defaults.TextObject");
        //this.setDefault();
        color = Color.BLACK;
        fontSize = 12;
        rotate = 0;
        content = "";
        initialize();
        this.followMouse();
        setClickSelection();
    }

    public TextObject(String ID, Map<String, String> map, GameRunnerController gameRunnerController){
        super(ID);
        this.gameRunnerController = gameRunnerController;
        setDraggable(true);
        setValuesfromMap(map);
        initialize();
        followMouse();
        setClickSelection();
    }

    private void setClickSelection() {
        getNode().setOnMouseClicked(e -> {
            gameRunnerController.select(ID);
        });
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
