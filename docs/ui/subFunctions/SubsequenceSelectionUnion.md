# Overview

The Subsequence Select Union, it is used for this use case: when user select a base module, it dynamically adjusts sub-module selections based on a chosen base module.
The primary purpose is to build this subsequence selection by ensuring that once a base module (like a warehouse or material) is selected, the corresponding sub-module options (such as warehouse areas or material units) are automatically rendered to reflect that selection.


# Process of Loading Base Selection and Triggering Sub-Selection

## 1) Triggering Base Selection
- The `SubSelectUnion.postUpdate` lifecycle method runs automatically after data has been loaded from the backend into the UI. 
- It orchestrates loading base selections and preparing sub-selections by invoking helper methods.
- Methods invoked within `postUpdate`:
   - `SubSelectUnion.initBaseSelectMetaSettings`: Initializes the base selection configuration using processed settings.
   - Batch-executes child components’ `postUpdate` lifecycle method for base select field components (type: select-2). Those components then:
      - Call `updateConfig` to register change/select event handlers.
      - Call `loadMetaData` to loading base selection data.

## 2) Triggering Sub-Selection
- The `SubSelectUnion.fnSetBaseModel` method is triggered automatically after a base selection is triggered, as defined in the base selection configuration.
- Within `fnSetBaseModel`, the component:
   - Generates all sub-selection configurations.
   - Calls the `updateConfig` lifecycle method to update the select field configuration and load the sub-selections.
- The `SubSelectUnion.mergeSelectFieldMetaSettingsCore` method merges the processed selection settings into each sub-selection field, then calls `updateConfig` to apply the configuration and load the sub-selections.

### the Configurable Selection Field in the `SubSelectUnion` Control

#### **Background Introduction**
The `SubSelectUnion` Control class, along with its subclasses like `WarehouseAreaSelection` and `MaterialSKUUnitSelection`, 
is designed to streamline configurable selection field functionality within user interfaces. 
These classes derive from a base Selection field (or even sub selection fields) to enable dynamic field rendering based on either an ID or a NAME configuration.

For instance:
- If the base selection field is configured to use an ID, the dropdown list will display choices by ID, while the corresponding NAME will appear in a read-only text field. 
The system will refresh the NAME value once an ID is selected.
- Conversely, if the control is configured to use a NAME, then the dropdown list will present choices by NAME, 
while the corresponding ID will appear in a read-only text field. The ID value will update accordingly upon NAME selection completion.

#### **How the fields are rendered by configured metadata**
The configurable selection field operates through the following steps:

1. **Default Selection field Configuration in the Superclass:**
   - The superclass `SubSelectUnion` defines a meta variable, `meta.selectFieldName`, which acts as the default configuration for selection fields.
   - By default, this meta variable is set to `NAME`, making NAME-based selection the default behavior across implementations.

2. **Default Selection field Configuration in Subclasses:**
   - Each subclass of `SubSelectUnion`—such as `WarehouseAreaSelection` and `MaterialSKUUnitSelection`—can re-define the default `meta.selectFieldName` configuration.

3. **Parameter Overrides:**
   - Other UI Classes consuming `SubSelectUnion` or its subclasses in UI implementations have the flexibility to provide an overriding parameter, `selectFieldName`.
   - This parameter allows the consuming UI to overwrite the default selection field configuration as defined by the superclass or its subclasses.

4. **Field Metadata Generation:**
   - Subclasses like `WarehouseAreaSelection` and `MaterialSKUUnitSelection` include a method called `getSelectFieldMetaList`.
   - This method retrieves the selection field metadata list based on the active configuration (either NAME or ID).
   - The metadata list serves as the basis for rendering selection choices, ensuring the appropriate field type (NAME or ID) is used.

#### **How to use `mapTo` function in `refControl` UI Control

1. ** Background of requirement **
- the `mapToField` function is used in using the refControl UI Control, such as this `SubSectionUnion` UI Control.
- There are some standard fields provided  from the refControl, which are equipped with some standard functions. these fields are listed in the `fieldMeta` list inside the refControl.
- At the same time, in the parent page, There are other fields configured in fieldMeta list, which are used to configure the fields on the parent page. 
- If you want to bind the `mapTo` relation ship from custom fields defined in the parent page to some standard fields from refControl, 
which utilize the refControl functions for these standards fields for your custom fields, you should use this function.

2. ** How to use it **
- Define your custom fields in the parent page, in the fieldMeta list as usual.
- The `mapTo` relation is defined in the `paras` properties in the refControl. in the `paras` properties, 
you should define the fieldMeta with key property: fieldName which is point to the standard fields from the refControl, 
at other hand, using the property: mapToFieldName which is used to point to the custom fields defined in the parent page. 
Meanwhile, some addition properties can also be defined to overwrite the standard properties from standard field from refControl. 
for example: the hiddenDisplay are recommend to value true, as it is not necessary to display the standard fields from refControl.

3. ** How does this function works in SubSelection **
- In field initialize phase, which is invoked by the method, if the field meta, the property: `mapTofieldName` is used to filter the target field meta object by the field name, 
and the target field meta object will be registered as mapTo properties in each control’s standard field.
The mapTo point to the custom field meta object which are defined in parent page.
- When baseSelection is done, the handler method: `fnBaseModule` is triggered.
trying to get the subSelectField Vue component instance and if mapTo fieldMeta is configure, also trying to get the field Vue component instances 
which stands for the target field defined in parent page, after that.  
- Using updateConfig of these 2 fields assign the selection setting the to these 2 fields , and trigger selection for subSelectField as well as mapTo target field.

### How to use `mapTo` with the `refControl` UI control

#### 1) Background
- `refControl`-based controls (for example, `SubSectionUnion`) expose a set of standard fields with built-in behaviors and helper functions. 
These are defined in the control’s internal `fieldMeta` list.
- The parent page also defines its own fields in its `fieldMeta` list to render and manage data on the page.
- If you want a parent-page field to reuse the behavior of a standard field inside `refControl`, map your custom field to that standard field 
via the `mapTo` relationship. This lets your custom field benefit from the `refControl`’s built-in logic while hiding the internal standard field.

#### 2) How to configure it
- Define your custom fields in the parent page’s `fieldMeta` as usual.
- In the `refControl` configuration, use `paras` section to declare the mapping entry:
    - `fieldName`: the name of the standard field inside `refControl` you want to reuse.
    - `mapToFieldName`: the name of the custom field on the parent page that should receive the standard behavior.
    - Optionally override properties from the standard field for this mapping. 
  For example, set `hiddenDisplay: true` so the internal standard field is not shown (the parent-page field will be displayed instead).

##### Example (illustrative)
```json
{
  "parentFieldMeta": [
    {
      "fieldName": "sampleAmount",
      "label": "sampleAmount",
      "type": "select-2",
      "required": true,
      postFieldMeta: [{
        fieldName: 'sampleUnitUUID'
      }]
    }
  ],
  "refControl": {
    "type": "SubSectionUnion",
    "paras": {
      "fieldMeta": [
        {
          fieldName:'amount2', // Standard field inside refControl
          mapToFieldName:'sampleAmount', // Custom field on parent page.
          hiddenDisplay: true,
          postFieldMeta: [{
            fieldName: 'refUnitUUID2', // Standard post field inside refControl
            mapToFieldName:'sampleUnitUUID'// Custom post field on parent page.
          }]
        }
      ]
    }
  }
}
```
Notes:

Property names are case-sensitive; use mapToFieldName exactly.  You can add more overrides in this mapping entry if needed.

#### 3) How it works in SubSectionUnion (sub-selection flow)
- Initialization:
    - During field initialization, the control scans mapping entries where `mapToFieldName` is provided.
    - For each mapping entry, it locates the corresponding standard field (by `fieldName`) inside `refControl` and registers a `mapTo` reference pointing to the target field meta on the parent page.

- Selection handling:
    - After the base selection completes, the handler `fnBaseModule` runs.
    - It retrieves:
        - The `subSelectField` Vue component instance inside the `refControl`, and
        - The Vue component instance for the mapped target field on the parent page (if configured).
    - Using `updateConfig`, it applies the same selection data/settings to both:
        - Triggers selection on the internal `subSelectField`.
        - Propagates the same selection to the mapped parent field (`mapToFieldName`), keeping both in sync.

## important functions

1. **SubSelectUnion.processSubSelectConfig**

- This method processes and outputs all four sub-model selection configurations, utilizing `SubSelectUnion.processSubSelectConfig` or similar methods.
- It assigns the `keyFieldName` for each selection configuration.
- Default properties, including callbacks such as `fnSetKey` and `fnSetInitKey`, are assigned during the execution of the `processInitSelectConfigCore` method.

2. **SubSelectUnion.processInputParas**

- This method handles the `paras` properties, which are input from a parent Vue component.
- Its primary focus is to merge field metadata lists and associate target field metadata with the 'mapTo' attribute of the current field metadata, based on the `mapToFieldName` attribute.

3. **getSubKeyFieldName**

- This method, along with similar methods such as `getSub2KeyFieldName`, `getSub3KeyFieldName`, and `getSub4KeyFieldName`, returns the key field name for sub-selection models.
- The sub-selection model key field name is used to retrieve and assign initial values to selection fields. It also assigns new values to the corresponding fields when a selection is triggered.

4. **fnSetBaseModule**
- This method is one of the base selection model configuration: `fnSetModel`, which will be invoked as callback method base model selection is triggered and the base model is loaded.
- It triggers loading for all four sub-modules selections.

5. **setValueToFieldMeta**
- This utility method is used to assign a value to a specified field meta, as well as to its mapped field, provided that the mapTo properties are bound to a mapped field.
- It is specifically designed for setting a value to a selected field within the SubSelectionUnion Vue component.

## Case Analysis

### Case 1: Automatic Assignment of the `failRefUnitUUID` Field Value During Initialization on the `QualityInspectMatControl` Page

- The `failRefUnitUUID` field on the `QualityInspectMatControl` page is managed by the `MaterialRegisterUnit` sub-component, which is a child Vue component inherits from the `SubSelectionUnion` Super Vue component.

- The `paras` property within the `refControl:materialRegisterUnit` control definition on the `QualityInspectMatControl` page specifies the configuration and mapping information for field metadata from the `MaterialRegisterUnit` sub-component to its parent page: `QualityInspectMatControl`.

- The field metadata for `refUnitUUID3` is defined with the property `mapToFieldName: 'failRefUnitUUID'`.

- During the initialization phase, the method `SubSelectUnion.processInputParas` is called by API method: `initLoad`, which initializes the `refUnitUUID3` field metadata and sets its `mapTo` property. This property references the field metadata instance of `failRefUnitUUID` on the `QualityInspectMatControl` page at runtime.

- In the `postUpdate` lifecycle method, the base model selection is activated, triggering the invocation of `SubSelectUnion.initBaseSelectMetaSettings` to register the base selection settings. 
Subsequently, the base model's callback method `fnSetBaseModule` is executed, enabling selection loading for all four sub-modules.

- Within the `fnSetBaseModule` handler, `updateSubFieldIns` method is used to generate the correct selection settings for each sub-selection module, based on the selected base module value.

- The sub-selection model's standard callback `fnSetInitKey` is called following the completion of the selection loading. 
For the `MaterialRegisterUnit` sub-control, the `refUnitUUID3` field, identified as the key field name by `getSub3KeyFieldName`, is automatically assigned an initial value.

- The value assignment for the field `refUnitUUID3` occurs through the invocation of the `setValueToFieldMeta` method. Within this method, values are also assigned to the `mapTo` target field.

### Case 2: How MaterialStockKeepUnit and RegisteredProduct work

- Business background
    - A `MaterialSKU` represents a specific material type and is the minimal stock-keeping unit.
    - For certain high-value SKUs, each physical instance carries a unique serial ID for traceability. These serialized 
  instances are modeled as `RegisteredProduct`, which is a subclass of `MaterialStockKeepUnit`.

- Java model design
    - The `DocumentItemUIModel` can reference either a `MaterialStockKeepUnit` (the SKU template) or a specific `RegisteredProduct` via the key reference field:`refMaterialSKUUUID`.
    - In `RegisteredProduct`, the field `refMaterialSKUUUID` points to its parent `MaterialStockKeepUnit` instance (the SKU template).
    - In `DocumentItemUIModel`:
        - The field `refMaterialSKUUUID` stores the UUID of the selected target, which can be either a `RegisteredProduct` or a `MaterialStockKeepUnit`.
        - If the document item references a `RegisteredProduct`, the field `refMaterialTemplateUUID` also stores the UUID of its parent `MaterialStockKeepUnit`.
        - If the document item references a `MaterialStockKeepUnit`, both `refMaterialSKUUUID` and `refMaterialTemplateUUID` hold the same UUID (the SKU template’s UUID).
    - Both `RegisteredProduct` and `MaterialStockKeepUnit` have associated material units. 
  On the backend, these units are always managed under the UUID of the `MaterialStockKeepUnit` (the SKU template), even when a `RegisteredProduct` is selected.

- How it works in the MaterialRegisteredUnit UI control (subclass of `SubSelectionUIControl`)
    - The control supports document items that reference either a `RegisteredProduct` or a `MaterialStockKeepUnit` as the base selection.
    - Sub-selection of material units is always anchored to the corresponding `MaterialStockKeepUnit` (SKU template).
    - Base select key logic:
        - When the document item references a `RegisteredProduct`, use `refMaterialTemplateUUID` (the parent SKU’s UUID) as the base key to trigger sub-selection for material units.
        - When the document item references a `MaterialStockKeepUnit`, use `refMaterialSKUUUID` (the SKU template’s UUID) as the base key to trigger sub-selection for material units.

This design ensures consistent unit selection and backend data anchoring to the SKU template, regardless of whether the base selection is a `RegisteredProduct` or a `MaterialStockKeepUnit`.

## FAQ

### How do I assign `idField` and `textField` properties in a sub-selection?

#### Answer 1
- Base selection is typically model-driven, so both `idField` and `textField` are required. 
In almost all cases, `idField` should be a UUID. The resolution logic for both fields resides in method: `initBaseSelectMetaSettings`.

- `idField` assignment (base selection):
    - Resolution order:
        1) baseSelectionSettings.idField (if provided as module-level configuration from the subclass)
        2) uuid (default value in global sub-selection union)

- `textField` assignment (base selection):
    - Resolution order:
        1) Input parameter (if supplied to the sub-selection union and its sub Vue classes initializer)
        2) baseSelectionSettings.textField (to be provided by each SubSelectionUnion subclass as module-level configuration)
        3) Constant default from SubSelectionUnion (e.g., vm.constants, as default value in global sub-selection union)


#### Answer 2
- For Sub selection is either model-driven or simple selection, so that:  `idField` and `textField` are not required, 
if `idField` or `textField` not provided, then sub selection is a simple selection.

- `idField` assignment (base selection):
    - Resolution order:
        1) subSelectionSettings.idField (if provided as module-level configuration from the subclass)

- `textField` assignment (base selection):
    - Resolution order:
        1) Input parameter (if supplied to the sub-selection union and its sub Vue classes initializer)
        2) subSelectionSettings.textField (to be provided by each SubSelectionUnion subclass as module-level configuration)

### What does keyFieldName mean (for example, the value returned by `getBaseKeyFieldName` or `getSubKeyFieldName`)?

#### Answer
- `keyFieldName` is the name of the field in the parent content that stores the selection key.

- Base keyFieldName (`getBaseKeyFieldName`): identifies the primary selection.
    - Example: In MaterialRegisteredProductUnion, the value should be: `refMaterialUUID`, which stores the UUID of the selected material SKU.

- Sub keyFieldName (`getSubKeyFieldName`): identifies a secondary or related selection.
    - Example: `refUnitUUID` stores the UUID of the selected unit.

How it’s used:
- Read: Use these field names to retrieve the current selection from the parent content (e.g., the material SKU UUID and the unit UUID).
- Write: After a selection is made, assign the chosen values back to the parent content by setting the corresponding fields 
(e.g., set refMaterialUUID to the selected material SKU UUID and refUnitUUID to the selected unit UUID).

### How is the Title Icon and Section Title Rendered for the Customer and Contact Section?

#### **Answer 1:**  
The title icon for the Customer and Contact section is generated dynamically using the method `CustomerContactUnion.genDefaultIconTitle`, which determines the icon based on the customer type.  

#### Key Steps:

1. **Component Overview:**  
   The `AsyncCustomerContactSection` represents the Customer and Contact section on the standard page. It inherits from the parent section `AsyncSection` and incorporates the `CustomerContactUnion` component as its core subcomponent to handle key functionality.

2. **Title Icon Implementation:**  
   The title icon for the `AsyncCustomerContactSection` is implemented in the `portlet-head-ele` component, which is used across all standard sections. However, the icon value is determined by a Vue computed property, `AsyncCustomerContactSection.comTitleIcon`, which is then passed to `portlet-head-ele`.

3. **Icon Generation Logic:**  
   The icon value is calculated inside the `AsyncCustomerContactSection.genDefaultIconTitle` method. This method relies on `CustomerContactUnion.genDefaultIconTitle` to handle the core logic for generating appropriate icons based on customer type.

---

#### **Answer 2:**  
The section title for the Customer and Contact section is generated dynamically using the method `CustomerContactUnion.genDefaultIconTitle`, which determines the title based on the customer type.

#### Key Steps:

1. **Component Overview:**  
   The `AsyncCustomerContactSection` acts as a standard page section that derives from `AsyncSection`. It uses the `CustomerContactUnion` component as its core subcomponent, which provides the essential functionality needed to compute and set the section title.

2. **Title Label Configuration:**  
   The title of the `AsyncCustomerContactSection` is calculated dynamically using an input parameter, `titleLabelKey`. In the document editor, the label for a customer node is typically represented by a subnode: `docInvolveParty`. This subnode is associated with the standard customer contact and includes default properties and values, such as `soldFromOrg`, `soldToCustomer`, `purchaseToOrg`, and `purchaseFromSupplier`.

3. **Runtime Behavior:**  
   During runtime, these default values for the section title are configured based on their respective `titleLabelKey` values, which are defined in the `pageMeta` configuration. This ensures the title dynamically reflects the correct customer context.


