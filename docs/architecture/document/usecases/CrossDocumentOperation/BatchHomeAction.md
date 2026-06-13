# Overview

The Batch Execution of a Home Action for a specific document refers to the execution of a batch process designed to handle selected items within that document. 
Subsequently, if all items satisfy the expected conditions, the corresponding action is also executed on the parent node.

### Key Steps

1. **Validate the parent node**: Ensure the parent node meets the required conditions based on the provided action code and configuration.
2. **Process selected items**: Iterate through each selected item, perform the necessary actions, and update its state in the database.
3. **Validate item status**: Verify that the status of all items for checking it all the item meet the expectation.
4. **Trigger parent node action**: If all items meet the expectation, execute the document action on the parent node.

---

## FAQ

### How does an Outbound Delivery trigger the `Delivery Done` Action?

#### **Answer**: The `Delivery Done` Action for an Outbound Delivery is triggered through the Batch Home Action process.

#### Key Steps:

1. **Adherence to the Standard Batch Home Action Process**
   - The `Delivery Done` Action for an Outbound Delivery follows the standard Batch Home Action process as its foundational framework.

2. **Custom Document Item Action for Outbound Delivery**
   - During the Batch Home Action process, a **customized document item action** can be configured for different document types. This action is specifically customized for `Outbound Delivery`.
   - The process involves invoking the `checkUpdateOutboundItem` method, which:
      - Validates whether the related warehouse store item is available for outbound delivery.
      - Updates the item's status to `Delivered` if the validation is successful.