# Overview

The Store Available Item Unit UI Control is an embedded component primarily used within Document Material Item UIs. 
Its purpose is to calculate, display, and manage the available warehouse store items in the format of a store item list, 
based on the current document item's required material SKU and quantity.


## Rendering the Store Available Item List Table

The Store Available Item Unit UI Control is presented as a table that shows the list of available store items in the warehouse, 
based on the required material SKU and quantity of the current document item.

Initialization is conducted via the method `StoreAvailableItemList.getAvailableStoreItemList`, which is invoked in the `postUpdate` lifecycle method. 
This timing ensures the method is executed after the parent Controller's data is retrieved.

Upon activation, this method interacts with the backend API to fetch the list of available store items aligned with the document item's material SKU and quantity requirements. 
The resulting data is used to render the Store Available Item List table.


### Reserved Status

Each store item in the table includes a `reservedStatus` field indicating its reservation status, which may take one of five values:

- `FREE`: The item is fully available for reservation, with no other document having reserved it.
- `PARTLY_FREE`: The item is partially reserved by another document, but a portion remains available for the current document item.
- `OWN`: The item is exclusively reserved by the current document material item.
- `SHARED`: The item is reserved by both the current document material item and other document material items.
- `OTHER`: Indicates a different status, potentially undefined or exceptional.


## Reserving a Store Item

To reserve a specific store item, users can click the `Reserve` button within the available warehouse store item list table. 
The button appears when the `reservedStatus` is either `FREE` or `PARTLY_FREE`.

Clicking the `Reserve` button initiates the `StoreAvailableItemList.reserveStoreItem` method, which communicates with the 
backend API `warehouseStoreItem/reserveStoreItem` to reserve the chosen item for the current document material item.

## Freeing a Store Item

To release an item from reservation by the current document, users can click the `Free` button within the table. 
This option becomes accessible when the `reservedStatus` is either `OWN` or `SHARED`.

When the `Free` button is clicked, the `StoreAvailableItemList.freeStoreItem` method is activated, calling the backend API `warehouseStoreItem/freeStoreItem` 
to unreserve the store item for the current document material item.
