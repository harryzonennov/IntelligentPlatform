
  # Overview

Each page in the UI client is rendered and managed by a UI Controller. There are various types of UI Controllers, such as `SerDocumentControlHelper`, `ServiceEditorControlHelper`, and `ServiceListControlHelper`. Each UI Controller is implemented as a Vue component class.

The `corePage` property is a key component of each UI Controller, responsible for managing unique pages within the controller. Pages are rendered through a hierarchy of child components.

---

## FAQ

### How is validation performed when submitting a document?

#### **Answer: Workflow Overview**

1. **High-Level Workflow**  
   - The process of validating a document submission is handled by the `validateSubmit` method within the UI Controller.  
   - Since the UI Controller contains the `corePage`, which consists of a structure of smaller child components (such as sections and fields), the responsibility for validation via `validateSubmit` is distributed across various levels of the child component hierarchy.

2. **Sequential `validateSubmit` Execution Across Vue Components**  
   - The `validateSubmit` process is initiated by the front-end UI Controller. By default, this phase is triggered in the parent controller, typically in `ServiceEditorControlHelper->validateSubmit`, whenever a standard document action is executed via the method `executeDocActionCore`.
   - Each UI Controller contains a `corePage` property. When the `validateSubmit` method is invoked, it triggers the `AsyncPage->checkValidateSubmit` method within the `corePage`.  
   - Since the page is composed of a nested hierarchy of child components (such as sections and fields), the `validateSubmit` logic is executed across these child components. This is achieved using the utility method `ServiceVueUtility.batchExecuteSubRefMethod`[Link to ServiceVueUtility.batchExecuteSubRefMethod](AsyncControlDesign.md#servicevueutilitybatchexecutesubrefmethod), which iteratively invokes the `validateSubmit` method for all child components.

3. **Validation Logic in Document Sections**  
   - Different sections within the document page execute distinct validation logic as part of the `validateSubmit` process, depending on the section type:
     - **List-Type Sections:**  
       If a list-type section is marked as `requiredSubmit` (with the `requiredSubmit` property set to `true` in the section metadata), its validation logic checks whether the list section contains a non-empty array. This is validated using the type `ServiceValidatorHelper.DEF_VALID_TYPE.NON_EMPTY_ARRAY`.  
       
     - **Customer Contact Section:**  
       For the customer contact section, if it is marked as `requiredSubmit` (`requiredSubmit` is `true` in the section metadata), the validation ensures that the section contains a non-empty customer UUID. This is validated using the type `ServiceValidatorHelper.DEF_VALID_TYPE.NON_EMPTY`.
