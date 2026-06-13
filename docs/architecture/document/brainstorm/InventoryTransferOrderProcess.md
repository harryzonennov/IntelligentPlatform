
# [Business]: Inventory Transfer Order Process

This article outlines the design and workflow of the **Inventory Transfer Order Process**.

---

## 1. Creation of the Inventory Transfer Order

### Standard Creation Process
The creation of a new Inventory Transfer Order is based on a selected warehouse store item. The newly created order will be appended as the next document in line for the selected warehouse store item.

### Filtering for Available Quantity
To streamline the creation process, a filter toggle should be available in the creation dialog. This toggle will allow users to transfer only the quantity that is freely available in the warehouse.

---

## 2. Execution of the `Delivery` Process

When executing the `Delivery` action for an Inventory Transfer Order, the system will perform the following steps:

### Step 1: Creating an Outbound Delivery
- Generate a new outbound delivery document based on the selected warehouse store item.

### Step 2: Linking the Outbound Delivery
- Associate the newly created outbound delivery as the **preceding document** for the Inventory Transfer Order.

### Step 3: Creating an Inbound Delivery
- Generate a new inbound delivery document based on the corresponding Inventory Transfer Order as its next document.

---

## FAQ

### Question:
How can we establish a `prev-next` relationship between the newly created outbound delivery and the inventory transfer order?

### Answer:
The outbound delivery is initially created based on the selected warehouse store item, and the inventory transfer order is also linked to the same warehouse store item.  
To establish the `prev-next` relationship, this should be implemented programmatically by invoking the appropriate API.

---

### Question:
If we are only transferring `freely available` warehouse store items, do we still need to reserve them through the inventory transfer order?

### Answer:
No, there is no need to reserve freely available warehouse store items. If these items are reserved through a transfer order, they cannot be marked as "freely available" once the transfer order is completed.  
Additionally, when the store items are used in a transfer order, the system will automatically reduce the available quantity, ensuring proper inventory management without requiring manual reservation.
