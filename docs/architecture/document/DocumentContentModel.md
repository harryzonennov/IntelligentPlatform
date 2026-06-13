# Document Model Design

This article introduce the design of model: `DocumentContent`, which stands for the root node of a `Document`.


## Document Root Node Model
`DocumentContent`, full name: `platform.foundation.Model.DocumentContent`, is the Java model class that represents the basic model of a document.
It stands for the root node in the Document
Each specific document model should be a child class of this basic model and should extend this model class.

## Document Content Fields
This section lists all the fields from the basic Document model: `DocumentContent`.

| Field Name and Type      |              Title              |                                 Description                                 |
|:-------------------------|:-------------------------------:|:---------------------------------------------------------------------------:|
| uuid (String)            |          Internal Key           |           The UUID: The unique internal key in the system and DB            |
| id (String)              |          Business Key           | The unique business key, potentially auto-generated in a pre-defined format |
| name (String)            |          Document Name          |             The name of this document for easier identification             |
| status (int)             |         Document Status         |                       The document life-cycle status                        |
| priorityCode (int)       |        Document priority        |                      The priority code of the document                      |
| documentCategoryType (int) |     Document Category Type    |         Classifies the document (business vs. non-business, etc.)           |
| prevDocType (int)        |  Direct Previous Document type  |           The direct previous document type in the document flow            |
| prevDocUUID (String)     |  Direct Previous Document UUID  |           The direct previous document UUID in the document flow            |
| prevProfDocType (int)    | Previous Business Document type |          The previous business document type in the document flow           |
| prevProfDocUUID (String) | Previous Business Document UUID |          The previous business document UUID in the document flow           |
| nextDocType (int)        |    Direct Next Document type    |             The direct next document type in the document flow              |
| nextDocUUID (String)     |    Direct Next Document UUID    |             The direct next document UUID in the document flow              |
| nextProfDocType (int)    |   Next Business Document type   |            The next business document type in the document flow             |
| nextProfDocUUID (String) |   Next Business Document UUID   |            The next business document UUID in the document flow             |


## Special Fields Design
### The Document flow in the root node
These fields `prevDocType`, `prevDocUUID`, `prevProfDocType`, `prevProfDocUUID`, `nextDocType`, `nextDocUUID`, `nextProfDocType`, `nextProfDocUUID` manage
the document flow (previous, next) information in the document root node.

However, this is not the precise document flow information. The accurate document flow information should be recorded and managed at the material item level node. 
Detailed model level information and search data rely on the document flow information at the item level.

The document flow information in the root node is duplicated information, primarily used for displaying data in the list view of the root node. 
Storing this document flow information in the root node can significantly reduce resource consumption and improve performance.

It is important to be cautious that these document flow fields are maintained consistently in both the root node and the item level to ensure data consistency.