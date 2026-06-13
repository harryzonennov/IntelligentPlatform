
# Overview

## The working flow:

### The Universal Working flow:

#### 1. Load Existing Target Document Root Node
- The system retrieves the existing target document's root node to verify if it can accommodate the newly created items.

#### 2. Traverse Source Document Items
- The key logic centers on traversing the material item list in the source document and executing essential actions, such as creating target document items and retrieving relevant resources.

#### 3. Create a New Target Document Root Node
- During traversal, the system creates a new target document root node if none exists and stores it in the **Document Creation Context**.

#### 4. Generate Target Document Items
- For each selected source item, the system creates a corresponding target document item. During this step, relevant resources or data transformations (e.g., mapping properties) are applied,
- and the data is stored in the **Document Creation Context**.

#### 5. Persist New and Updated Documents
- After processing all selected items, the system persists the newly created target document, along with other components such as the root node, material item nodes, and any associated data (e.g., attachments).
- Additionally, updates to the source document (if applicable) are also saved in the persistence layer.

### The Special details Working flow:
//TODO

## FAQ

### How does the source document status change after generating the target document in batch?

#### **Answer:** This functionality is implemented in the method: `DocActionExecutionProxy.crossCreateDocumentBatch` as a subsequent action in this method.

#### Key Steps:

1. **Starting Post-Trigger Source Action:**  
   - The `crossCreateDocumentBatch` method first executes the creation of the target document by invoking `targetDocActionProxy.handleInboundCrossCreateDoc`.  
   - Once the target document is successfully created, the method calls `postTriggerSourceAction` to update the status of the source document.

2. **Retrieve Cross-Copy Document Conversion Map:**  
   - Inside the `postTriggerSourceAction` method, the system retrieves the Cross-Copy Document Conversion Map using the method `getCrossCopyDocConversionConfigMap`.  
   - This map is defined in each specific document action proxy class and provides the configuration for mapping the resulting document action codes.  
   - For example: When creating a specific target document from a source document (e.g., a purchase contract), the mapping determines the action code to execute for the source document upon completion of the target document.  
     - *Case Example:* If the target document is an inbound delivery, the purchase contract's status will update with the action code `DELIVERY_DONE` after the inbound delivery is successfully generated.

3. **Execute Source Document Action Code for Selected Items:**  
   - The source document action code is executed for selected source items using the method `DocumentItemMultiSelect.batchExecSelectedItemTryExecuteParent`.  
   - In this scenario, the execution involves validating and updating the status of all selected document items in the source document.
