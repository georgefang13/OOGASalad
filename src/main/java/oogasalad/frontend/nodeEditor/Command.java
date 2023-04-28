package oogasalad.frontend.nodeEditor;

import java.util.List;

public record Command(
    String name,
    String description,
    List<String> innerBlocks,
    List<String> outputTypes,
    String parseStr,
    List<String> inputs
) {}