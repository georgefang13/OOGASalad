package oogasalad.frontend.nodeEditor.nodes;

import io.github.eckig.grapheditor.model.GNode;
import oogasalad.frontend.nodeEditor.skins.tree.TreeNodeSkin;

public class VariableNode extends ooglaNode {

  /**
   * Creates a new {@link TreeNodeSkin} instance.
   *
   * @param node the {link GNode} this skin is representing
   */
  public VariableNode(GNode node) {
    super(node);
  }
}
