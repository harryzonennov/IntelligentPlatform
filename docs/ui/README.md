
# Overview 

The product is designed as a Browser/Server (B/S) software product. On the client side, the UI client is based on the Vue.js and JavaScript platform.
This section introduces the design of the Browser UI client layer.

---

## UI Controller

Each page in the UI client is rendered and managed by a UI Controller. There are several types of UI Controllers, such as `SerDocumentControlHelper` ,`ServiceEditorControlHelper`, `ServiceListControlHelper`. Each UI Controller is implemented as a Vue component class.  

For more detailed information, please refer to the [Service UI Controller](ServiceUIController.md) documentation.

---

## Async Control and Fields

Each UI Controller utilizes the `corePage` property to manage unique pages within the controller. Pages are rendered using a hierarchy of child components. 
For example: 

1. A page consists of multiple sections.  
2. Each section contains fields.

These sections and fields are managed by `Async Control`, which are also implemented as Vue component classes.  

For further details about the hierarchy and design of the different levels of control, please refer to the [Async Control Design](AsyncControlDesign.md) documentation.

---

## Helpful UI Controls

Additionally, the UI client provides several reusable and efficient UI control components. These include:  

- `ProcessButtonArea`  
- `Multiple Document Selection`  
- `Material SKU Selection`  
- `Warehouse Area Selection`  
- `Customer Contact Selection`  

These components aim to enhance UI development efficiency and productivity.

---

