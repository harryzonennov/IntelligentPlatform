# Business Process: Manage Registered Product Serial IDs in Purchase Scenarios

## Overview

In documents associated with the `PurchaseContract`, such as `InboundDelivery` or `QualityInspectOrder`, 
users are prompted via a pop-up panel in the document editor UI to input a serial ID when a registered product instance lacks one.


## Backend Workflow


1. **ServiceBasicUtilityController.updateSerialIdToRegProduct**
- This API method serves as the entry point and can be invoked by various document controller classes.
- Key methods called within `updateSerialIdToRegProduct`:
- `SerialIdDocumentProxy.buildDocItemUnionListMap`: Constructs a map containing lists of `DocMatItemMaterialUnion` objects from a list of raw document material items. Items are excluded if the associated material is not set to 'Single trace' mode.
- `SerialIdDocumentProxy.updateSerialIdToRegProduct`: Processes each `SerialIdInputModel` instance within its list, which corresponds to each reference template material, and executes core logic for serial ID updates.

2. **SerialIdDocumentProxy.updateSerialIdToRegProduct**
- Core functionality is centered here.
- Steps involved:
- Calls `getInitBaseSelectConfig` to fetch customized configurations for subordinate modules (e.g., `WarehouseArea`, `MaterialRegisterUnit`).
- Executes `buildDocItemMaterialMatrixBySerialIdArray` to create the serial ID update matrix, featuring the following class attributes:
- `newSerialIdList` (List<String>): Represents the list of newly entered serial IDs from end users via the UI.
- `emptyList` (List<DocMatItemMaterialUnion>): Contains registered product instances with empty serial IDs that require entry from `newSerialIdList` individually.
- `toDeleteList` (List<DocMatItemMaterialUnion>): Includes registered product instances whose serial IDs are slated for removal.
- `keepList` (List<DocMatItemMaterialUnion>): Comprises registered product instances with serial IDs that do not need updates post-user input.

