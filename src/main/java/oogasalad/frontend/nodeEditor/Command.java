package oogasalad.frontend.nodeEditor;

import java.util.List;

public record Command(
    String name,
    String folder,
    String description,
    List<String> innerBlocks,
    String parseStr,
    List<String> inputs
) {

}