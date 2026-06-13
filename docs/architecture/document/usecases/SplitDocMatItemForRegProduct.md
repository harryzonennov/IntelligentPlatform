# Special Business Process: Splitting Document Material Items for Traced Materials

## Background

This section describes a unique business process involving the splitting of document material items for single traced materials. 
In certain documents, such as purchase contracts, a material item may contain multiple units of a single traced material. 
Once the purchase contract has completed the approval process and is converted into the subsequent document, each unit of this traced material must be split into separate document material items. 
Each of these new items will contain one registered product that is derived from the traced material and assigned a unique serial ID.

## Working Process

Currently, this splitting process is only triggered by one document type: the purchase contract document.

### Process in the UI Client

Within the default document action matrix, which is accessible via the Purchase Contract Editor UI, the standard document action 'deliverDone' initiates the selection of multiple items and facilitates the generation of the subsequent document, such as an inbound delivery.

### Process in the Backend

The 'deliverDone' action also triggers a backend API method: `PurchaseContractEditorController->generateNextDocBatch`. This API call invokes the method `sourceDocActionProxy.crossCreateDocumentBatch`, specifically via `PurchaseContractActionProxy.crossCreateDocumentBatch` since the source document type is a Purchase Contract. 
Within `crossCreateDocumentBatch`, a method `DocumentSpecifier.checkAndSplitDocMatItemListForRegProduct` is called.

### Core Logic in `SplitMatItemProxy.splitDocMatForMaterial`

The `crossCreateDocumentBatch` method eventually calls `SplitMatItemProxy.splitDocMatForMaterial`, where the primary logic for splitting document material items resides. 
If a document material item contains a single traced material with a quantity greater than one, the process generates multiple `Registered Product` instances. 
The quantity of generated `Registered Products` corresponds to the amount of the single traced material. Each generated `Registered Product` leads to the creation of multiple new document material items, ensuring that each item contains only one instance of a `Registered Product`, each with its unique serial ID.