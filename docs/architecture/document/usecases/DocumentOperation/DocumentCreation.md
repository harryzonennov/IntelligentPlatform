
# Overview

The Standalone Document Creation process involves creating a new Document in the system independently, without interacting with other types of documents. 
This process not only creates a new instance of a core entity from the root node but also generates items for sub-nodes.

## Create Standalone Document Working Process

The entire Standalone Document Creation is implemented by the Java method `serviceBasicUtilityController.newModuleServiceDefTemplate`, which can be invoked directly by each editor controller.

The entire creation process is as follows:
### Core Entity Creation:
Depending on the input value of `parenNodeUUID` or `parentNodeName`:
- Initializes the core service entity instance (root node) by calling the `serviceEntityManager.newRootEntityNode` method, or
- Initializes other sub-nodes by calling the `serviceEntityManager.newEntityNode` method.

### Get the Initialization Configuration
Retrieve the initialization configuration by calling the `serviceDocInitConfigureManager.getServiceDocInitConfigureMetaResult` method. This method executes the following logic:
- Fetches the Document node initialization configuration by comparing the default configuration (defined in each Document specifier) and the custom configuration (defined in the DB: `ServiceDocInitConfigure`).
- It then parses the final result into the format of `ServiceDocInitConfigureMetaParseResult`.

### Initialize the Document Content with Initialization Configuration
- Initializes the document content by the generated Initialization Configuration is implemented within the `serviceBasicUtilityController.newModuleServiceDefTemplate` method.
- The method: `serviceDocInitConfigureManager.initServiceModuleWithMeta(ServiceModule...)` is used for initializing the document content in the compound format (with sub nodes) with the map type configuration.
- The method: `serviceDocInitConfigureManager.initServiceEntityWithMeta(ServiceEntityNode...)` is used for initializing the core service entity instance in the simple format with the list type configuration.

## Default Initialization Configuration
Each document can provide a default initialization configuration to initialize the document content when the document is created. This configuration is defined in each Document Specifier class, which is a subclass of `DocumentContentSpecifier`.

## Custom Initialization Configuration
Each document can also provide a custom initialization configuration, which is stored in the DB: `ServiceDocInitConfigure`. Customers can save custom configurations here to initialize document content in a self-configured way.

### Core Entity Initialization Process
**Method:** `serviceDocInitConfigureManager.initServiceEntityWithMeta(ServiceEntityNode...)`

#### Overview
The initialization process focuses on assigning values to the fields of a core service entity. 
This is achieved by iterating through the metadata in the `coreNodeMetaList` to extract field-specific information such as:
- Field names (`fieldName` and `inputFieldName`).
- Input values (`inputFieldValue`).

The method ensures dynamic field value configuration, leveraging various strategies based on metadata and request handling.

---

#### Detailed Steps

1. **Case 1: Field values determined by `inputFieldValue` from `ServiceDocInitConfigureMeta`**

    - **System Variable Resolution**
        - If the metadata indicates the field value is a `System Variable`, resolve its value using the `standardSystemVariableProxy`. 
        - The resolved value is then applied to the corresponding field in the service entity.

    - **Constant Value Configuration**
        - If the field value specified is not a system variable, treat it as a constant. The constant value is directly assigned to the field.

    - **Communication Object Mappings**
        - Handle specialized scenarios where the field requires mappings for communication objects—perform custom configuration as necessary.

---

2. **Case 2: Field values retrieved from UI requests (`inputRequest`)**

    - Extract the field value provided through the UI client request (`inputRequest`) using the `inputFieldName`.
    - Apply the value dynamically to the corresponding field in the Service Entity.

---

## Key Actions Summary

- **Resolve system variables** using runtime information (`logonInfo`).
- **Apply constant values** directly to fields when no system variable resolution is needed.
- **Fetch values from `inputRequest`** for uninitialized fields based on metadata configuration.

This process ensures dynamic and flexible initialization of the `ServiceEntityNode` based on metadata-driven configurations.