# Overview

This article describes the process of executing standard document actions from UI client.

## Standard Document Buttons rendering

Buttons for executing standard document actions are dynamically rendered based on the configuration of these actions. 

These buttons are located within the `Process Buttons` section.


## Trigger the Standard Document Action by Modal

### Default handler method for standard document action in `SerDocumentControlHelper`

`SerDocumentControlHelper` serves as the primary Vue controller class for the Document page UI.
- When a user clicks an action button on this page, the method `SerDocumentControlHelper.executeDocActionCore` is invoked as the default mechanism for handling standard document actions.
- This method subsequently calls `DocActionModel.initLoad` to launch the modal for the document action.
- Before this, `SerDocumentControlHelper.genActionNodeInitConfigure` generates the necessary configuration, supplying it as input parameters to `DocActionModel.initLoad`.
- These parameters encompass essential properties required for rendering the standard document action modal, such as `actionCode`, `warnTitle`, `warnText`, and `actionCodeIconMap`,
which determines the icon rendered based on the specific `actionCode`.

### The method `SerDocumentControlHelper.genActionNodeInitConfigure`

- This method is tasked with generating the configuration required for rendering the standard document action modal.
- The configuration includes elements like `actionCode`, `warnTitle`, `warnText`, and `actionCodeIconMap`.
- The `actionCodeIconMap` is sourced from the method `SerDocumentControlHelper.getActionCodeIconMap`,
implemented within each child Vue controller class rather than the `SerDocumentControlHelper` superclass.

