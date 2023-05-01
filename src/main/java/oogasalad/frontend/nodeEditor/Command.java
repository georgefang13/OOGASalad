package oogasalad.frontend.nodeEditor;

import java.util.List;

/**
 * This class represents a command that can be used to create a node as a record
 */
public record Command(
    String name,
    String folder,
    String description,
    List<String> innerBlocks,
    String parseStr,
    List<String> inputs
) {

}