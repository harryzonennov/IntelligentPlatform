# Overview
The Watch Handler mechanism within Async Control introduces dynamic watch and check functions. 
This configuration is integrated with the dynamic page meta configuration which is managed by Async Control, 
allowing the end user to customize these functions within the page meta configuration.

There are server basic handler methods which have been provided.

- **setAutoValue**: This method enables the field value to be dynamically updated based on various conditions at runtime.
- **updateFieldConfig**: Offers dynamic updates to the field meta configuration, adapting to runtime conditions, such as URLs for retrieving selections.
- **watchCheckValue**: Dynamically checks the validity of a field's value under different runtime conditions by observing other fields.
- **submitCheckValue**: Ensures the validity of a field's value by monitoring other fields before submission, under various runtime conditions.


## Working process

The watch handler mechanism is divided into two phases: `Registration` and `Execution`.

- **Registration Phase**: Once the data is initially loaded, the watch handler methods are generated and registered to the appropriate field meta.
- **Execution Phase**: If any field value changes during runtime, the corresponding watch handler will be executed.

### Registration Phase

#### `AsyncField.postUpdate` -> `ServiceFieldMetaUtility.processFieldMetaWatchList`

- The `AsyncField.postUpdate` method (within Vue component: `AsyncField`) handles the standard lifecycle of processing each group of fields post data loading. It serves as the entry point for processing the field watch handler by invoking `ServiceFieldMetaUtility.processFieldMetaWatchList`.

#### `ServiceFieldMetaUtility.registerFieldMetaWatchCore`

- This method maps the internal `handler category` according to the different `handler methods`.

#### `ServiceFieldMetaUtility.generateWatchHandlerWrapper`

- This process begins with invoking the method `ServiceFieldMetaUtility.generateWatchHandler` to generate a handler method body, assigned to the `watchHandler` property as a `callback`.
- Subsequently, it gathers the `watchFieldList` for the `watchHandler`. This list comprises field meta instances, where the watch handler is registered using the `FieldMeta.addHandler` method.

#### `ServiceFieldMetaUtility.generateWatchHandler`

- This method is tasked with generating the body of the handler method.

#### `ServiceFieldMetaUtility.parseCallbackTemplate`

- Invoked by `ServiceFieldMetaUtility.generateWatchHandler`, this method parses the user-defined template format, replacing placeholders with actual content values at runtime.

### Execution Phase

The Vue watch field `fieldValue` in the Vue Component `AbsInput` is the entry point for executing the value change handler. It calls the method `AbsInput.valueChangeHandler` within the standard Vue watch field `fieldValue`.

#### `ServiceFieldMetaUtility.executeWatchHandlerUnion`

- This method implements the core logic to execute the watch handler during runtime when watch field values change.

This improved version refines the explanation by ensuring clarity and coherence, emphasizing the sequential processes and functionality provided by the watch handler mechanism in Async Control.


#### `ServiceFieldMetaUtility.postHandleCallback`

The `postHandleCallback` method serves as a utility function to process the results of executing a watch handler by executing
the `ServiceFieldMetaUtility.executeWatchHandlerUnion` method. This method efficiently manages outcomes across various scenarios:

- **Remote HTTP Calls:** When a watch handler needs to initiate remote HTTP requests, `postHandleCallback` is employed in the HTTP request's `postHandler` 
- callback to effectively handle responses or in the `errorHandle` callback in case an HTTP request raises an exception.

- **Direct Invocation:** This method can also be called directly by `ServiceFieldMetaUtility.executeWatchHandlerUnion`.

Within the `ServiceFieldMetaUtility.postHandleCallback`, three distinct scenarios outline how the watch handler's results are processed:

1. **setValue Scenario:** If the operation involves setting a value, it invokes `watchHandler.targetField.setValue` to assign the new value to the target field.

2. **updateConfig Scenario:** For configuration updates, `watchHandler.targetField.updateConfig` is called to modify the configuration settings of the target field.

3. **validate Scenario:** When validation is needed, `ServiceValidatorHelper.defaultValidateCheckArray` is utilized to perform validation checks on the corresponding UI elements.


