
# Overview

## Tree Process Button Area Operations

In each Tree node, the standard process buttons for processing each tree node can be created and initiated through the method: `initProcessTreeNode`.
Similar to what we did in the table section, The utility method `AsyncPage.generateSubNodeFunctionMatrix` are in charge of creating the standard process function matrix. 
This matrix encompasses the following standard process functions:

- `editSubNode` function: Enables editing of the sub-node in a detailed model format, viewable in the detailed editor page.
- `editSubNodeQuick` function: Allows adjustments to the sub-node in a quick model format, accessible from the side panel.
- `deleteSubNode` function: Facilitates removal of a sub-node.
- `createSubNodeQuick` function: Aids in creating a sub-node instance within the quick model format, accessible from the side panel.
- `createSubNode` function: Helps in the creation of a sub-node instance in the detailed model format, presented in the detailed editor page.


## Use Case: Data List Determined Dynamic Tree Sub Nodes

Typically, a tree node's sub tree nodes are predetermined at the design stage. 
However, some use cases may require the sub tree nodes to flexibly rendered in response to a sub data list at runtime.

Consider a tree node within a NavigationItem. This might contain a second layer of sub-NavigationItems, 
wherein the sub nodes are essentially dictated by the second layer of sub-NavigationItems at runtime.

The Service Tree Framework handles this scenario in the following manner:

The tree model `ServiceTreeNode` possesses a computed property `comSubNodeList` which essentially controls the sub tree node list within the tree structure. 

In scenarios where there are no dynamic sub tree nodes, the `comSubNodeList` value is calculated by the sub tree node determined at the design stage, 
represented by the property `subNodeList`. 
However, if dynamic sub tree nodes are enabled (denoted by the computed property `comSubUIModelList` containing a non-null 
value) the `comSubNodeList` value is a sum of its own value and the value derived from the `subNodeList`.

The computed property `comSubUIModelList` is influenced by the properties `getSubDataList` and `allUiModelList`. 
If dynamic sub tree nodes are required, `getSubDataList` is defined as the callback method which calculates the dynamic sub tree node's sub data list. 
If `comSubUIModelList` returns a non-null value, a dynamic sub tree node has been effectively enabled.

Meanwhile, some properties in the tree model `ServiceTreeTemplate` are also involved in this process:
1. The `allUiModelList` property on `ServiceTreeSetting` passes all potential data list from the parent layer to the child layer.
2. The `getSubDataList` property on `ServiceTreeSetting` acts as a callback method that calculates sub data list for child nodes, 
when this property is configured, dynamic sub tree nodes are enabled.
3. The computed property `comAllUIModelList` maintains all possible UI Model lists for the current service entity node at the respective layer.
4. The computed property `comUIModelList` hosts the current data list at the given layer. This property is instrumental in rendering the 
following 'service-tree-node' list beneath the current template node. If `getSubDataList` isn't configured, suggesting no dynamic sub tree node, 
the value of `comAllUIModelList` is returned. Otherwise, the computed value by the `getSubDataList` method is returned.