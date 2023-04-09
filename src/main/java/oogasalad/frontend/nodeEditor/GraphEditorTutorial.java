package oogasalad.frontend.nodeEditor;

import io.github.eckig.grapheditor.Commands;
import io.github.eckig.grapheditor.GNodeSkin;
import io.github.eckig.grapheditor.model.GConnector;
import io.github.eckig.grapheditor.model.GModel;
import io.github.eckig.grapheditor.model.GNode;
import io.github.eckig.grapheditor.model.GraphFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import io.github.eckig.grapheditor.GraphEditor;
import io.github.eckig.grapheditor.core.DefaultGraphEditor;
import oogasalad.frontend.nodeEditor.skins.tree.TreeConnectionSkin;
import oogasalad.frontend.nodeEditor.skins.tree.TreeConnectorSkin;
import oogasalad.frontend.nodeEditor.skins.tree.TreeNodeSkin;
import oogasalad.frontend.nodeEditor.skins.tree.TreeTailSkin;
import oogasalad.frontend.nodeEditor.skins.tree.TreeSkinConstants;
public class GraphEditorTutorial extends Application
{

    public static void main(final String[] args)
    {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        GraphEditor graphEditor = new DefaultGraphEditor();


        graphEditor.setNodeSkinFactory(node -> new TreeNodeSkin(node));

        // Register GConnectorSkin implementation(s)
        graphEditor.setConnectorSkinFactory(connector -> new TreeConnectorSkin(connector));

        // Register GConnectionSkin implementation(s)
        graphEditor.setConnectionSkinFactory(connection -> new TreeConnectionSkin(connection));

        // Register GTailSkin implementation(s)
        graphEditor.setTailSkinFactory(tail -> new TreeTailSkin(tail));

        // Register GNodeSkin implementation(s)

        Scene scene = new Scene(graphEditor.getView(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        GModel model = GraphFactory.eINSTANCE.createGModel();
        graphEditor.setModel(model);
        addNodes(model);
        scene.getStylesheets().add(getClass().getResource("/frontend/css/tree-node.css").toExternalForm());
        System.out.println(scene.getStylesheets());

    }

    private GNode createNode()
    {
        GNode node = GraphFactory.eINSTANCE.createGNode();

        GConnector input = GraphFactory.eINSTANCE.createGConnector();
        GConnector output = GraphFactory.eINSTANCE.createGConnector();

        input.setType(TreeSkinConstants.TREE_INPUT_CONNECTOR);
        output.setType(TreeSkinConstants.TREE_OUTPUT_CONNECTOR);

        node.getConnectors().add(input);
        node.getConnectors().add(output);



        return node;
    }

    private void addNodes(GModel model)
    {
        GNode firstNode = createNode();
        GNode secondNode = createNode();

        firstNode.setX(150);
        firstNode.setY(150);

        secondNode.setX(400);
        secondNode.setY(200);
        secondNode.setWidth(200);
        secondNode.setHeight(150);

        Commands.addNode(model, firstNode);
        Commands.addNode(model, secondNode);
    }
}