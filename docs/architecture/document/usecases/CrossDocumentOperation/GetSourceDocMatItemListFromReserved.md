
# Overview: Retrieving Source Material Document Item List from Reserved List

## Get Source Item List From Reserved Information
The method `crossDocBatchConvertReservedProxy.getSourceItemListFromReserved` is designed to retrieve a list of `sourceDocMatItem` from a specified list of `reservedDocMatItem`. 
It achieves this through several steps:

1. **Loading Source Items**: The method initially calls `loadFromToReserveDocFramework`, which employs a framework to generate the 
   appropriate `sourceDocMatItem` list based on the provided reserved document item list.

2. **Determining Selected Items**: The logic within `crossDocConvertReservedRequest` determines which instances of `sourceDocMatItem` 
   are selected based on the `reservedDocMatItem` list. This selection is executed through a callback mechanism. 
   The method uses an instance of `ISourceDocItemExecutor` to handle each `sourceDocMatItem`.

## Use Case: Warehouse Store Available Item List

One common application of the `Get Source Item List From Reserved` function is managing the Warehouse Store Available Item List. 
The function's purpose is to calculate and display available warehouse store items based on current document item requirements, including material SKUs and quantities.

### Details of the Framework Process

#### 1. Utilizing `loadFromToReserveDocFramework`
- The method `getGetALLSourceMatItemListBySelectedReserved` is used to obtain all possible source material items based on 
  the selected reserved material items. A common use case involves providing store items based on the SKU list derived from the reserved document material items.

#### 2. Iterative Processing of Reserved Items
- The method iteratively processes each reserved material item from the selected list. For each item, a `filteredSourceMatItemList` 
  is created based on the current reserved material item.

#### 3. Execution of Callback Executor
- Each source material item in the list is processed by the callback executor `sourceDocItemExecutor`. 
  This executor is an instance of `ISourceDocItemExecutor`, and it serves as an input parameter for the method `loadFromToReserveDocFramework`.

### Core Logic within `sourceDocItemExecutor`

- The executor's core task is to calculate the reserved status for a specific combination of reserved material items and source items.
- For warehouse store items, this logic is encapsulated in the method `OutboundDeliveryCrossConvertReservedRequest.setDefLoadSourceItemReserved`.
- The executor calls `outboundDeliveryWarehouseItemManager.genRequestToStoreItem` for each selected source material item. 
  It tests whether the request to reserve can be fulfilled based on the required material SKU and quantity.
- If the reserve request is feasible, the relevant information is stored in `docMatItemCreateContext`, and the loop terminates.
- If the reserve request cannot be met, pertinent details are still captured in `docMatItemCreateContext`, and the process continues to the next iteration.




