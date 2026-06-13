# Overview

The `PageHeader` control is responsible for rendering breadcrumb header links within the document material item's UI page header.

## Backend Model: `PageHeaderModel`

### Model Fields
The backend model, `PageHeaderModel`, includes the following fields:

- `nodeInstId`: The node ID associated with this page header instance. This key is crucial for identifying the specific Page Header configuration model on the UI.
- `uuid`: The universally unique identifier for the model. This is stored in the backend model and is used for generating correct links on the UI.
- `index`: Sorting index for the page header models, intended to influence the order of rendering in the breadcrumb header, though currently, it has no practical effect.
- `pageTitle`: The default page title provided by the backend. Currently, it doesn't impact the UI.
- `headerName`: The default variable title for the page rendered by the backend, which currently has no effect on the UI.
- `pageLink`: The link associated with the breadcrumb header, provided by the backend but actually rendered on the UI.

### Backend Model Rendering
- **Document Item Controller API**: The Document Item Editor controller should offer an API to handle HTTP requests.
- **Document Item Manager Method**: The manager provides the `getPageHeaderModelList` method, invoked by the controller API.
- **Framework Utility Method**: The utility class `DocPageHeaderModelProxy` offers methods for generating PageHeader models, such as `getPageHeaderModelList`, 
which operates on the parent document root node.

#### Current Backend Logic Considerations
Many properties within the Page Header rendering logic currently have no real impact on the UI side.

## UI Control: `PageHeaderUnion`

The `PageHeaderUnion` UI control is responsible for generating Page Header models on the UI side and rendering their properties to ensure correct breadcrumb header display.

### Rendering Process in `PageHeaderUnion`
- The `ServiceEditorController` begins rendering the page header using the `getPageHeaderModelList` method within the `postUpdate` UI Controller lifecycle.
- Prior to invoking `getPageHeaderModelList`, the `checkPageHeader` method assesses whether page header rendering is necessary by confirming the existence of `pageHeaderConfig` within `pageMeta`.
- `getPageHeaderModelList` triggers the `initPageHeader` method of the `PageHeaderUnion` UI Control, making an HTTP request with the URL: `getPageHeaderListUrl` to obtain the `PageHeaderModel` list from the backend.
- `PageHeaderUnion` also takes responsibility for setting basic properties for `pageHeader`, such as index and active status.
- The callback method `fnPageHeaderModel`, defined in the UI Controller, processes key properties for each `PageHeaderModel` by comparing configurations in `pageMeta`.
- Key properties like `pageTitlePath` and `pageVarPath`, among others, are generated within the `fnPageHeaderModel` callback method.

## Key Points

### Activating PageHeader Union in UI Control
The `PageHeaderConfig` must be configured and present in `pageMeta` for activation.

### Rendering the Page Title
Page titles are rendered via logic in the UI Controller's callback method `fnPageHeaderModel`, combining `pageTitlePath` and `pageVarPath`. 
Proper configuration of `pageHeaderConfig` within `PageMeta` or `SectionMeta` is essential for enabling and correctly rendering the Page Header on the UI. 


