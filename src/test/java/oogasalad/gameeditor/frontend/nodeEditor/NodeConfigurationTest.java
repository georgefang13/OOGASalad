package oogasalad.gameeditor.frontend.nodeEditor;

import oogasalad.frontend.nodeEditor.configuration.NodeConfiguration;
import oogasalad.frontend.nodeEditor.configuration.NodeData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NodeConfigurationTest extends DukeApplicationTest {
    private String testFilePath;
    private NodeConfiguration nodeConfiguration;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        testFilePath = "src/main/resources/nodeCode/config.json";
        nodeConfiguration = new NodeConfiguration(testFilePath);
    }
    @Test
    public void testGetNodeData() throws IOException, ClassNotFoundException {
        List<NodeData> nodeData = nodeConfiguration.getNodeData();

        assertNotNull(nodeData, "Node data should not be null");
        assertEquals(3, nodeData.size(), "Node data size should be 2");

        System.out.println(nodeData);
    }


}
