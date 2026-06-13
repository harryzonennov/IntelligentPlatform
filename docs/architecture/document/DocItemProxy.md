# Utility Class: DocItemProxy

This article introduces the Utility Class DocItemProxy, which provides utility functions for Document items.
***

## Methods

### getDuplicateItemList

Checks for duplicates of the given reference item among existing items that might point to the same target reference. 
It provides a widely used function that allows consumers to specify the scope of the item list to be checked. If duplicates are found, it returns the list; otherwise, it returns null.

Here is the improved JavaDoc for the method:


---

### exclusiveExeSelectItemList

Executes an exclusive action on the selected item list, while performing another action on the rest (non-selected) items. 
A typical use case is to set one selected item’s status to `active`, while setting all non-selected items to `archived`.

Here is the improved JavaDoc for the method:


---
