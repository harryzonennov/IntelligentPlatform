## Document Model Design

This article introduce the design of model: `DocumentItem`, which stands for the material item node of a `Document`.


## Document Item Node Model
`DocumentItem`, full name: `platform.foundation.Model.DocMatItemNode`, is the Java model class that represents the basic model of a document item.
It stands for the material item node in the Document
Each specific document material item model should be a child class of this basic model and should extend this model class.

### Document Item Fields
This section lists all the fields from the base document item model: `DocMatItemNode`.

**Status**

| Field Name and Type  |       Title        |                       Description                       |
|:---------------------|:------------------:|:-------------------------------------------------------:|
| itemStatus (int)     |    Item Status     |              The item life-cycle status                 |
| materialStatus (int) |  Material Status   | Status of the material associated with the item         |
| homeDocumentType (int) | Home Document Type | The document type this item belongs to                |

**Document chain — direct links (item-level)**

| Field Name and Type              |              Title               |                            Description                             |
|:---------------------------------|:--------------------------------:|:------------------------------------------------------------------:|
| prevDocType (int)                | Direct Previous Document type    | The direct previous document type in the document flow             |
| prevDocMatItemUUID (String)      | Direct Previous Item UUID        | UUID of the direct previous item in the document flow              |
| prevDocMatItemArrayUUID (String) | Direct Previous Item UUID Array  | Array UUID of direct previous items in the document flow           |
| nextDocType (int)                | Direct Next Document type        | The direct next document type in the document flow                 |
| nextDocMatItemUUID (String)      | Direct Next Item UUID            | UUID of the direct next item in the document flow                  |
| nextDocMatItemArrayUUID (String) | Direct Next Item UUID Array      | Array UUID of direct next items in the document flow               |

**Document chain — professional/business document links (item-level)**

| Field Name and Type                  |             Title               |                           Description                            |
|:-------------------------------------|:-------------------------------:|:----------------------------------------------------------------:|
| prevProfDocType (int)                | Previous Business Document type | The previous business document type in the document flow         |
| prevProfDocMatItemUUID (String)      | Previous Business Item UUID     | UUID of the previous business item in the document flow          |
| prevProfDocMatItemArrayUUID (String) | Previous Business Item UUID Array | Array UUID of previous business items in the document flow     |
| nextProfDocType (int)                | Next Business Document type     | The next business document type in the document flow             |
| nextProfDocMatItemUUID (String)      | Next Business Item UUID         | UUID of the next business item in the document flow              |
| nextProfDocMatItemArrayUUID (String) | Next Business Item UUID Array   | Array UUID of next business items in the document flow           |

**Reservation links**

| Field Name and Type                       |            Title              |                        Description                          |
|:------------------------------------------|:-----------------------------:|:-----------------------------------------------------------:|
| reservedMatItemUUID (String)              | Reserved Item UUID            | UUID of the item that reserves this item's material         |
| reservedDocType (int)                     | Reserved Document type        | Document type of the reservation source                     |
| reservedDocMatItemArrayUUID (String)      | Reserved Item UUID Array      | Array UUID of reservation source items                      |
| reserveTargetMatItemUUID (String)         | Reserve Target Item UUID      | UUID of the item being reserved (target)                    |
| reserveTargetDocType (int)                | Reserve Target Document type  | Document type of the reservation target                     |
| reserveTargetDocMatItemArrayUUID (String) | Reserve Target Item UUID Array | Array UUID of reservation target items                     |

**Material and pricing**

| Field Name and Type            |       Title          |                      Description                       |
|:-------------------------------|:--------------------:|:------------------------------------------------------:|
| refMaterialSKUUUID (String)    | Material SKU UUID    | Reference UUID of the material SKU                     |
| refUnitUUID (String)           | Unit UUID            | Reference UUID of the unit of measure                  |
| amount (double)                | Amount               | Quantity of material in this item                      |
| itemPrice (double)             | Item Price           | Total price for this item line                         |
| unitPrice (double)             | Unit Price           | Price per unit                                         |
| itemPriceDisplay (double)      | Item Price Display   | Display price for this item (may differ from base)     |
| unitPriceDisplay (double)      | Unit Price Display   | Display unit price                                     |
| currencyCode (String)          | Currency Code        | Currency used for pricing                              |
| refFinMatItemUUID (String)     | Finance Item UUID    | Reference UUID of the corresponding finance item       |
| productionBatchNumber (String) | Production Batch     | Production batch number associated with this item      |
| purchaseBatchNumber (String)   | Purchase Batch       | Purchase batch number associated with this item        |
