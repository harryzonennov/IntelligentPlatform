
# Overview

Creating a new target document from reserved documents by identifying the related source document and establishing
a relationship between the source and target documents in the document flow. 

Specifically, this use case focuses on:
- Based on the selected reserved document and specific target type, Creating a new target document (including the target document material item list).
- Find the related source documents to be reserved by the specific target type.
- Find the related source document material list as resources to `reserve`.
- Generating a target document material item list based on the material items from the source and reserved documents.

## The working flow:

### Batch Creation of Target Document From Reserved Document

- **The entrance method:**: This use case begins with the method `DocActionExecutionProxy.crossCreateBatchDocReserved`.
- **Calculate the Source Document Type**: It is crucial to determine the source document type based on the provided target document type. This is done using the method `getDefSourceDocTypeReserved`, which retrieves the source-target mapping information from a predefined map. For example, if the target document type is `OutboundDelivery`, the source type should be `WarehouseStore`. 
- **Handle Inbound Cross-Creation Requests and Invoke Core Logic**: the method:`targetDocActionProxy.handleInboundCrossCreateDocReserved` will call `crossDocBatchConvertReservedProxy.createTargetBatchDocFromReservedDoc`. which contains the core logic for creating target documents from reserved documents in batch. For detailed information, refer to [Core Logic for Batch Creation of Target Document From Reserved Document](#core-logic-for-batch-creation-of-target-document-from-reserved-document)

- **Post Actions Triggered on Source Document:** In some cases, when a target document is created, post actions should be performed on the source document type. For example, when an outbound delivery is created, the warehouse store item should be marked as `Delivered`. 

### Core Logic for Batch Creation of Target Document From Reserved Document

- **The entrance method:**: The core logic of this use case is starting from method: `CrossDocBatchConvertReservedProxy.createTargetBatchDocFromReservedDoc`.
- **Call framework method to prepare the resource document material item list**: The entrance method call the method: `loadFromToReserveDocFramework` to prepare the possible relative source document material item list by the provided reserved document material item list.
- **Loop each resource mat item and call the executor:** inside the method: `loadFromToReserveDocFramework`, it loops all possible relative source document material item, and call the callback executor: `sourceDocItemExecutor` for each item, which is the instance of `ISourceDocItemExecutor` and also a input parameter of the method: `loadFromToReserveDocFramework`.
- **Create target document in sourceDocItemExecutor:** inside the callback executor: `sourceDocItemExecutor`, target document material item is created by method: 
`genDefTargetMatItemServiceModelReserved`, the created target document material item is stored in the instance of `DocMatItemCreateContext`.
- **Update into DB:** call method `storeContext` to store the created and updated information which is stored in `DocMatItemCreateContext`, it includes the following attributes: `targetItemServiceModel`, `sourceDocMatItemNode`, `targetDocMatItemNode`, `prevProfDocMatItemNode`.


### Creating a Target Document Material Item Based on a Source Material Item.

The method `genDefTargetMatItemServiceModelReserved` is responsible for creating a target document material item for each provided source material item. 
This involves several steps:

- **Filter or Create Target Document Root Node** : Attempt to filter or retrieve the existing target document root node. 
If it does not exist, create a new target type root service model.
- **Get Core Target Document Root Node**: Retrieve the core target document root node and the cross-copy document configuration.
- **Create Target Document Material Item**: Use the method `CrossDocBatchConvertReservedProxy.initConvertToTargetMatItem` to create the target document material item and copy data from the source document and reserved material item.
- **Create Other sub Nodes**: Utilize the pre-configured cross document configuration to create other sub nodes, such as involved parties and attachments.

### Conversion Logic for Reserved Item, Source Item and Target Item.

The Core Conversion Logic for Reserved Item, Source Item and Target Item is implemented in the method `CrossDocBatchConvertReservedProxy.initConvertToTargetMatItem`. 
This is quite complicated logic, since the Reserved Item, Source Item and Target Item should be considered in the conversion.

- **Clean deprecated relationship between reserved item and source item** : In most cases, the reservation or prev-next relationship 
existed for reserved material item and source item, for example: the warehouse item should be reserved by sales contract or purchase return order before generated target out bound delivery. This logic is implemented in the method: `initPreUpdateSrcMatItemTarget.initPreUpdateSrcMatItemTarget`. it follows the following workflow: call: `prevNextDocItemProxy.cleanPrevNext` trying to clean the possible prev-next relationship, then call: `` trying to clean the possible reservation relationship.

- **Build Prev-Next relationship between source item and newly created target item**: call: `prevNextDocItemProxy.addPrevNextRelation` 
to build the prev-next relationship between source item and newly created target item, call `` to copying item information from sourceMatItemNode to newly created target item.

- **Build Prev-Next relationship between source item and newly created target item**: call: `prevNextDocItemProxy.addPrevNextRelation`
  to build the prev-next relationship between source item and newly created target item, call `` to copying item information from sourceMatItemNode to newly created target item.

## Business Process Discussion

### Should the generated target document be linked to the original reserved document via a previous-next document relationship?

### Answer: 
No. This is because in some cases, the reserved document may already be linked to a previous document. 
If we link the reserved document directly to the newly created target document as its next doc, the existing relationship 
between the reserved document and its prior document will be overwritten.

### Correct Process:
- The **previous-next document relationship** should be established using the source document that is being reserved during cross-document reserved creation.
- The correct relationships are as follows:
    1. The reserved document acts as the *previous document*.
    2. The source document serves as the *next document* for the reserved document.
    3. The newly created target document becomes the *next document* for the source document.

### Should we trigger `post action` for both reserved document and the source document ?

### Answer: 
Yes. We should trigger `post action` for both reserved document and the source document after the target document is created.

## FAQ


### Question:  
In the provided source material, how can we prioritize items listed in the `generatedTargetMaterialItem` list that are already reserved under the current `reservedDocMatUUID`?

### Answer:  
This prioritization is handled through a standard function in the document flow utility method: **`DocFlowProxy->sortDocMatItemList`**.

The method takes two input parameters:  
1. **`docMatItemList`** – The list of document material items to be sorted.  
2. **`reservedDocMatUUID`** – The UUID used to identify the already reserved document material items.  

When `reservedDocMatUUID` is provided, the `docMatItemList` will be sorted based on prioritization rules, ensuring that the items corresponding to the reserved UUID are given higher priority.  

This method is invoked by the **`loadFromToReserveDocFramework`** process.

