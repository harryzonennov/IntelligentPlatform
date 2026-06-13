# Overview

## Configuration of Panel Process Buttons

This article explains how the process buttons on the Bottom Panel are configured and rendered.

The Vue component `ControlPortletHead` serves the Bottom Panel portlet header area, where the process buttons are located.

Within the `ControlPortletHead` template, the process buttons are managed by the `PanelProcessButtonArray` subcomponent.

The `PanelProcessButtonArray` component is responsible for rendering the process buttons in the Bottom Panel's portlet header. 
It includes predefined button configurations for various use cases, such as `EDIT_SAVE` and `EXECUTE_CONFIRM`. Users can display these predefined button configurations by setting the `usecase` property.

Additionally, users can customize the button configurations by setting the `buttonArray` property. 
The `buttonArray` is an input property of the `PanelProcessButtonArray` component, allowing for a tailored button layout.