
# Overview

This article explains how to establish document relationships, such as `prev-next` relationships, using the APIs provided by the framework.
The most common types of document relationships are:

- `prev-next` relationships
- `prev-prof-next` relationships
- `reservation` relationships

## API: PrevNextDocItemProxy.addPrevByNext

This API enables the creation of a `prev-next` relationship between two documents. Specifically, it establishes the connection between a `prev` document and a `next` document's material item.

### Process Steps

To ensure proper implementation, follow the steps below:

1. **Apply a Write Lock**:  
   Before making edits to the `prev` and `next` material items, a write lock should be applied to these items. This prevents concurrent editing issues.

2. **Update the UUID Fields**:  
   The UUID fields—`prevDocMatItemUUID` and `nextDocMatItemUUID`—store the primary `prev-next` relationship. If these fields are empty, populate them with the respective UUID values for the `prev` and `next` material items.

3. **Update UUID Array Fields**:  
   Add new UUIDs to the `nextUUIDs` and `prevUUIDs` arrays in the `prev` and `next` material items. These arrays allow for multiple `prev-next` relationships to coexist.

4. **Persist Changes and Release Write Lock**:  
   Save all updated values to the database, and then release the write lock.

---


### Case Study: Setting a Manual `prev-next` Relationship between Outbound Delivery and Inventory Transfer Order

#### Requirement

When creating a Transfer Order:
- The Transfer Order should reference specific store items and establish a `prev-next` relationship with them.
- The Transfer Order acts as the `next` document for these store items.

When triggering an Outbound Delivery from the Transfer Order:
- The Outbound Delivery is generated based on the referenced store items and must establish a `prev-next` relationship with these items.
- The Outbound Delivery acts as the `next` document for these store items.

Finally, the Outbound Delivery should manually establish a `prev-next` relationship with the Inventory Transfer Order, setting the Inventory Transfer Order as its `next` document.

#### Implementation Steps

1. **Automatic Relationship Between Outbound Delivery and Store Items**:  
   When creating the Outbound Delivery, automatically establish the `prev-next` relationship between the Outbound Delivery and the associated store items.

2. **Clean Existing UUID Values**:  
   Before establishing a new relationship, clear the `prev-next` relationship between the Transfer Order and the store items. 
This ensures the UUID fields—`prevDocMatItemUUID` and `nextDocMatItemUUID`—can be updated appropriately. 
If these fields already contain values, only the UUID arrays (`nextUUIDs` and `prevUUIDs`) are updated, which is not sufficient for this scenario.

3. **Create Relationship Between Outbound Delivery and Transfer Order**:  
   Finally, manually build the `prev-next` relationship between the Outbound Delivery and the Transfer Order, 
ensuring the Transfer Order is marked as the `prev` document in this relationship.

