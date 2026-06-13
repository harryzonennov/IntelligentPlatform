# Business Process: Manage Registered Product Serial IDs in Purchase Scenarios

## Overview

For `single` trace mode material, it is essential to assign a unique serial ID to each product instance.
In the purchasing process, several convenient options are available to manage serial IDs for multiple registered product instances as a batch. 
This eliminates the need to individually input serial IDs in the `Registered product` editor UI.


### Creating Registered Product Instances with Serial IDs in New Purchase Contracts

When adding new material items to a freshly created purchase contract, for `single` trace mode materials, you can batch-enter multiple serial IDs. 
This results in the creation of multiple registered product instances according to the serial IDs provided.

### Background Process: Splitting Document Material Items for Traced Materials
A unique automated background business process is available in the `Purchase Contract`. 
If a material item consists of multiple units of a single traced material, each unit must be subdivided into separate document material items once 
the purchase contract has undergone approval and is converted into subsequent documents. 
For more information, refer to [SplitDocMatItemForRegProduct](SplitDocMatItemForRegProduct.md).


### Batch Input of Serial IDs for Registered Products with Empty Serial IDs

In downstream documents related to the `PurchaseContract`, such as `InboundDelivery` or `QualityInspectOrder`, 
if the material items contain a registered instance without a `Serial ID`, a pop-up panel in the document editor UI will prompt users to input the serial ID. 

This scenario often occurs when Document Material Items are split in the upstream document `PurchaseContract`. 
Refer to [Background Process: Splitting Document Material Items for Traced Materials] for additional details.

### Switching Registered Products for Outbound Delivery with Serial ID Changes

In the document titled `OutboundDelivery`, users have the option to select and make adjustments based on serial IDs for outbound delivery tasks. 







