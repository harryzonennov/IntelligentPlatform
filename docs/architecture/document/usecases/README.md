
# Overview 

This Section introduces some important use cases in our product, as well as how it works

## Document Operations

The Document Operations list several typical operations of Document, such as Document Creation, Document Update, Document Deletion, etc.
- **Document Creation** 
    The Document Creation is the process of creating a new Document in the system. For detailed design information, 
    please refer to the following document:[DocumentCreation](DocumentOperation/DocumentCreation.md)
- **Document Deletion**
  The Document Deletion is the process of delete a new Document in the system, as well as remove their relationship to other documents.
  For detailed design information, please refer to the following document:[DocumentDeletion](DocumentOperation/DocumentDeletion.md)

## Cross Document Operations

Cross Document operations are essential in certain scenarios, such as creating a new document based on previous documents. 
Below are two common types of Cross Document operations:
- **Document Creation from Previous Documents**
    This operation entails creating a new document from existing documents as previous one in the document flow. 
    For detailed design information, please refer to the following document::[CrossDocCreationPrevious](CrossDocumentOperation/CrossDocCreationPrevious.md)
   

- **Document Creation from Reserved Documents**
    This operation involves creating a new target document from reserved documents by identifying the related source document and establishing 
    a relationship between the source and target documents in the document flow. 
    For detailed design information, please refer to the following document:[CrossDocCreationReserve](CrossDocumentOperation/CrossDocCreationReserve.md)