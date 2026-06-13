# Overview

The ServiceUtilityHelper provides the general utility methods for UI component.

## Core Functions

### Value Assignment for Select2 Element

The `ServiceUtilityHelper` contains several methods to facilitate seamless value assignment and event handling for Select2 components in the UI.

#### 1. `ServiceUtilityHelper.genDefSelect2SelectHandler`
Generates a default handler method when the `postLoadUrl` property is configured. This method operates as follows:
1. Updates a key field in the parent content object based on the selected item's ID.
2. Triggers a "post-loading" process to fetch additional data from the backend if `postLoadUrl` is specified.
3. Upon successful data loading, assigns the fetched values to specified fields using the `ServiceUtilityHelper.setSelect2ModelValue` method.

#### 2. `ServiceUtilityHelper.setSelect2ModelValue`
Updates the values in a parent content object according to a Select2 selection event by processing a list of fields provided in the `fieldList`.  
This method can perform simple field assignments or complex mappings where mappings between `sourceField` and `targetField` are specified.

#### 3. `ServiceUtilityHelper.setSelect2FieldValue`
Updates a single field value in the parent content object.
- This method accommodates both simple string-based field updates and more complex object-based mappings, using `sourceField` and `targetField` to define relationships.
- It leverages the core logic implemented in the `ServiceUtilityHelper.setFieldValueWrapper` method to set the value.

#### 4. `ServiceUtilityHelper.setFieldValueWrapper`
Sets a field's value in the `parentContent` object using Vue's `vm.$set` method. This is the core logic for assigning 
values consistently within any Vue instance, ensuring reactivity for the updated fields.

### Loading Data and Triggering Selection for Select2 Elements

#### 1. `ServiceUtilityHelper.loadModelMetaRequest`
This method is responsible for loading selection data from the backend and rendering the appropriate UI elements for selections. 
It serves as the entry point for the process, delegating the core logic to the `ServiceUtilityHelper._loadMetaRequestCore` method.

---

#### 2. `ServiceUtilityHelper._loadMetaRequestCore`

This method is responsible for loading the metadata for selection, rendering select elements with data list and trigger the selection event with initial value.

##### Key Steps Include:
- **Fetching Data via HTTP Request:**  
  Generates and sends an HTTP request using `ServiceUtilityHelper._genHttpRequestPromise` to fetch metadata from the backend.

- **Parsing Backend Response:**  
  Parses the received HTTP response using the method: `ServiceUtilityHelper._renderSelectResultList`. The parsed data is stored in a local variable `rawResultList`.

- **Rendering the Selection Element:**  
  Renders the selection element by invoking `ServiceUtilityHelper._updateSelectElementWrapper`.  
  The processed data list is passed to create UI elements, and the selection event is triggered using the initial value.

---

#### 3. `ServiceUtilityHelper._renderSelectResultList`
This method parses the Select2 HTTP response to generate a formatted result list that can be used for rendering a Select2 element.  
It supports additional options for customizing the behavior of selection elements.

##### Key Steps Include:
- **Processing Custom Select Options:**  
  If a `processSelectOptions` callback is configured, the method processes and customizes the raw data.

- **Filtering and Excluding Data:**  
  Filters or excludes metadata based on the `filteredKeyList` or `excludeKeyList` settings, when provided.

- **Setting Default Initial Value:**  
  If no initial value is configured, and a `fnSetInitKey` callback is specified, the method sets the first item in `rawResultList` as the default value and triggers the callback.

- **Adding Empty Options:**  
  When required, adds an empty value option using `emptyId` and `emptyLabel`.  
  This can be handled either via a custom callback (`processEmptyCallback`) or default logic.

---

#### 4. `ServiceUtilityHelper._updateSelectElementWrapper`
This method updates the selection element in the UI using `$(element).select2({...})`, which is the standard way of rendering Select2 elements with the provided data list.

- If an `initValue` is configured, it triggers a selection event using `ServiceUtilityHelper.triggerSelect`, which invokes the standard `select2:select` event after updating the element.

---

#### 5. `ServiceUtilityHelper.triggerSelect`
Focused specifically on raising the `select2:select` event, this method ensures that a selection event is triggered for the 
specified UI element and integrates smoothly with Select2's event handling system.


