
# Developing a Bottom Side Panel UI

This guide provides detailed instructions on how to develop a Bottom Side Panel User Interface (UI).

## Creating New Vue Classes
To construct a Bottom Side Panel UI, you need to create two Vue classes, the examples are `SerialIdUpdateControl` and `SerialIdUpdatePanel`
- `Panel Vue Class` This class defines a single Panel and should be mixed with the Super Panel class using: `ServiceItemControlHelper.defEditorPanelMinxin` , as well as with the Control class.
  The Panel Class serves as the container for the entire Bottom Side Panel, managing standard panel actions like opening and closing the panel.
- `Control Vue Class` This class establishes a single Control class.
  The Control Class is responsible for implementing the UI elements, their layout design, and the basic logic needed for interaction.


### Overriding API Methods

Several standard methods, predefined in the standard panel, may need to be overridden.

- `loadPanel` function: This standard method is used to open the Bottom Side Panel. 
    It calls `openPanel` to open the panel and  `parentControl.loadModule` to load relevant data into the Control.

- `loadModule` function: This is standard method for loading module in the control class.
  If it is not overridden in the child class,, it will default to the logic provided in `ServicePopBottomPanelHelper.defControlMinxin.loadModule`.
  
- `getDefaultPageMeta` function: This mandatory function provides the JSON format for the UI layout and elements metadata.

- `getResourceId` function: This function provides the resource ID needed for authorization control.

- `getI18nConfig` function: This function supplies the configuration for internationalization (i18n).

