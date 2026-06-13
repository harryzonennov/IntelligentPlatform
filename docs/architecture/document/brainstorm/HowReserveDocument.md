## BrainStorm: How to reserve document

This article is brain storm: how and when should we reserve document 
***

##Business case for reserve document
Batch creating target document by selecting warehouse store item, such as: 
1. Creating purchase return order from selecting store item.
2. Creating waste process order from selecting store item.
3. Creating sales contract from selecting store item.

These action from UI, needs the following actions: 
1. create target doc item (such as sales contract, waste process) with prev-next relationship to warehouse store item.
2. reserve the store item, it means current store item should be reserved for other business requirements.

##Business case requirement for clean the previous reserve relationship
In the below case for batch creating document, it is also needed to clean existed reserve information, in the following scenario:

A. When creating outbound delivery from these target (reserve) document,should do the following things:
1. Creating target document: outbound item from source document warehouse store item. 
2. Making reserved document (like: waste, salse) should be the new next document from outbound delivery
3. The previous reserve relationship to store item should be cleaned.

##Business case for multiple reserve function
When standard document reserve store item, it is in some cases
***

Plan to do the following implementation to 


1. Common API for bind reserve Info:reserveStoreItem

2. Common API for free reserve Info:freeStoreItem, also need to pass reservedDocMatItem

3. Reserved Status:
3.1. FREE: totally free; 
3.2. PARTlY-FREE;  
3.3. OWN: owned by current doc mat item, 
3.4. OTHER: locked by other doc mat item. 
3.5. SHARED, owned but shared by others


5. Common API to checking reserved status, Common API to group valid reserve status

6. Common API to clear reserve Info by reserving doc mat item.

7. Common API in each documentContentSpecifier to return each mat item's request amount. 

8. Add new parameter: reserveSourceFlag in createDocAPI UI Method to reserve source mat item in backend.

9. When createDocReserved method is invoked, should firstly process the source mat item list with reserved relationship to current reserving doc mat.
When bind source document to new target mat item, the previous reserve relationship from source mat item to current reserving doc mat item will be cleaned.

