
# Overview

## Table Process Button Area Operations

Within the embedded table page section, the standard embedded process buttons for processing the table can be created and initiated through the method: convertEmbedProcessButtonMeta.

The methods `asyncTableSection.genDefSectionFunctionMatrix` and `AsyncPage.generateSubNodeFunctionMatrix` are in charge of creating the standard process function matrix. 
This matrix encompasses the following standard process functions:

- `editSubNode` function: Enables editing of the sub-node in a detailed model format, viewable in the detailed editor page.
- `editSubNodeQuick` function: Allows adjustments to the sub-node in a quick model format, accessible from the side panel.
- `deleteSubNode` function: Facilitates removal of a sub-node.
- `createSubNodeQuick` function: Aids in creating a sub-node instance within the quick model format, accessible from the side panel.
- `createSubNode` function: Helps in the creation of a sub-node instance in the detailed model format, presented in the detailed editor page.