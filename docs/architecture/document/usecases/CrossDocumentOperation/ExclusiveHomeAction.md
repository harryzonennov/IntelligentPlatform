# Overview

Exclusive execution of a document action on a specific set of documents refers to performing an exclusive action on the selected items 
while performing another action on the remaining non-selected items. A typical use case is to apply the active action to the 
selected documents (including the root document when applicable) and apply the archived action to all non-selected documents.

### Key Steps

1. Validate the parent node: Ensure the parent node meets the required conditions based on the provided action code and configuration.
2. Process selected items: Iterate through each selected item, perform the necessary actions, and update its state in the database.
3. Process the secondary action for non-selected items: Apply the archived document action to all non-selected items.
4. Trigger the parent/root node action: Execute the document action on the root (parent) node.
