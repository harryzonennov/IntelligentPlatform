# Overview

The HTML pages on the UI client are rendered based on Async Vue elements on different typs and different levels: it includes different Asynchronous Controls,
Asynchronous Unions, Asynchronous sections and Asynchronous page.

## InputFieldUnion

//TODO 

### Important API methods
1. **`InputFieldUnion.setFieldValueWrapper`**
  Utility method to set a value to the 'parentContent' attribute in the input field union instance or in a field element instance.
  This includes setting values to standard flat properties within the 'parentContent' attribute or to embedded sub-properties if the field name is separated by a dot.


2. **`ServiceVueUtility.batchExecuteSubRefMethod`**

The `batchExecuteSubRefMethod` is a utility method designed to batch-execute specified methods across various levels of a child component hierarchy.  
Common use cases of this method include operations such as `postUpdate`, `checkSave`, and `checkSubmitValidate`.

#### How It Works:
- Child components are identified through Vue's `$refs` attribute.  
- For this mechanism to function properly, the following conditions must be met:
  1. **Child Components in the Template:**  
     The child components must be explicitly defined within the Vue template.  

  2. **Presence of the `ref` Attribute:**  
     Each child component must have a unique `ref` property defined. This is critical because the `$refs` attribute relies on these `ref` values to locate and reference the child components.  


## Async field

### Important API methods:
1. **`AsyncField.mergeFieldConfigure`**
    Merges two lists of field metadata using the field name as the key. The merge follows these rules:
      - When two field metadata objects have the same field name, their properties are combined into one.
      - If the field metadata objects contain 'postFieldMeta', these are also merged by extending their properties.
   
2. **`AsyncField.filterFieldMetaList`**
   Filters the field meta unit from the provided field meta list, If not found within the main list, it still try to fetch 
from 'postFieldMeta' properties associated with each member in fieldMetaList.

## Async Union 

AsyncUnion - Vue component responsible for managing elements within the core area of the page section inside the <portlet-body> area.


## Async Section

AsyncSection - Vue component responsible for managing elements within the page section inside the <portlet> area.

## Async Page

AsyncPage - The AsyncPage is a Vue component responsible for managing elements throughout the entire HTML page.

### Important API methods

- `getPageBottomCompensationInstance` Get the common compensation instance for the Bottom panel of the page.
    There is one common compensation defined in `AsyncTemplateConstant.PageTabContentTemplate` serves as the shared instance for the entire page. 

- `getTabBottomCompensationInstance` Get the compensation instance for Bottom panel of the Tab.
  There is one common compensation defined in `AsyncTemplateConstant.PageTabContentTemplate` serves as the shared instance for a tab.



## Important UI Component

### PortletHeadEle

The Vue component `PortletHeadEle` is responsible for rendering the header area of a portlet within the HTML element `<div class="portlet-heading bg-lightgrey">` on standard pages. 

This component is designed to display common elements such as the title, title icon, and help text.

### ControlPortletHead

The Vue component `ControlPortletHead` handles the header area of a portlet within the HTML element `<div class="portlet-heading bg-lightgrey">` specifically in the bottom panel. 
It inherits functionality from the `PortletHeadEle` component, allowing it to maintain common features like displaying the title, title icon, and help text found across standard pages and bottom panels.

Additionally, `ControlPortletHead` introduces unique features for the bottom panel:

- **Embedded Process Buttons**: These buttons are rendered within this area using the Vue component `EmbeddedProcessButtonArea`.
- **Item Actions**: Managed and rendered within this area, item actions are facilitated by the Vue component `ItemQuickAction`.

Please note that these specific UI elements are exclusive to the bottom panel and are not displayed on standard pages.

