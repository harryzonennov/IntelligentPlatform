# Overview

This document outlines common bug fixes related to the Sub-selection UI and its associated Vue components.

## Case 1: The HTML Vue property `:disabled-flag="disableSection()"` is not working for `Customer-Contact-Union`.

### Analysis:
The `Customer-Contact-Union` control uses `async-field` components to render its fields, dynamically converting metadata into runtime HTML elements. 
This rendering occurs during the `mounted` lifecycle method of the `async-field` component.

However, the `disableSection()` method relies on the `content.UIModel.status` property to determine whether the control should be disabled or not. 
This dependency introduces a timing issue: the required data (`content.UIModel.status`) is only available after it has been fully loaded, 
whereas the fields are rendered earlier during the `mounted` lifecycle. By the time the `disableSection()` method gets the correct status, the fields have already been rendered, 
leading to the incorrect state.

Additionally, the `:disabled-flag="disableSection()"` expression only evaluates the return value of the method when it is initially called.
It does not dynamically re-adjust even if the underlying data changes.

### Solution:
To resolve the issue, adjust the rendering of the fields so it occurs after the data has been fully loaded, rather than during the `mounted` lifecycle method of the `async-field` component.


