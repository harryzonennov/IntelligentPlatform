## Document Model Design

This article introduce technical information about Document Design in the model level


## Overview
The `Document` concept stands for each business transaction order. There are many kinds of business documents, such as Sales Contracts, Purchase Contracts, and Inbound Deliveries.

Each type of document has a unified structure.

Every document has a unique root node, which contains basic unique information, such as the document ID, name, status, and priority.

Each document also has multiple involved parties. For example, a Sales Contract includes at least two involved parties: 
a customer who buys the product (sold-to customer), and the organization responsible for the sale.

Furthermore, each document contains multiple item nodes. Each item represents individual material item information within a document. 
For example, a Sales Contract can contain one or multiple material items.

### Business Document
Some documents have a specific business background and business purpose, which are called Business Documents.
One good example is a "Sales Contract." Its business background is in the Sales Scenario, and the business purpose is for
the contract between the Sales organization and the customer to whom the products are sold, involving multiple sold materials as sub-items.

Another example is a "Purchase Contract." It is designed to work in the Procurement Area, and the business purpose is for
the contract between the Purchase organization and the Supplier, involving multiple purchased materials as sub-items.

### Non-Business Document
Meanwhile, some documents do not have a specific business background and business purpose, which are called Non-Business Documents.

One good example is an "Inbound Delivery." Its purpose is to manage warehouse inbound information, without a special business background.
Many business documents could generate this Inbound Delivery, such as a Purchase Contract or a Production Order.

Another example is a "Quality Check Order." Its purpose is to manage quality check information in the QE process, without a special business background.
Many business documents could generate this Quality Check Order; for example, a Purchase Contract or a Sales Contract can generate a Quality Check Order to manage the quality information in the QE process.

### Document flow
To be continued

### Document Root Node Model
`DocumentContent`, with the full name `platform.foundation.Model.DocumentContent`, is the Java model class that represents the unique root node of the `Document`.

For detailed design information about `DocumentContent`, please refer to the following document:

- [DocumentContent](DocumentContentModel.md)

### Document Item Node Model
`DocumentItem`, full name: `platform.foundation.Model.DocMatItemNode`, is the Java model class that represents the basic model of a document item.

For detailed design information about `DocumentItem`, please refer to the following document:

- [DocumentItem](DocumentItemModel.md)