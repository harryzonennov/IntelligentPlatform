
# Refreshing a Bottom Side Panel UI

Unlike a standard page, the height of a Bottom Side Panel is not fixed. When UI elements within the panel change, 
the height of the panel may also change—such as when dynamic fields are rendered at runtime under certain conditions.

When this happens, it is essential to notify the UI framework that the side panel has been modified. 
This ensures that other UI components associated with the Bottom Side Panel, like the floating fold/unfold button, remain aligned with the panel's new dimensions. 
Without this notification, these components could appear misaligned or disconnected from their intended positions.

## Implementation Steps

To notify the UI framework that the side panel has been updated, follow these steps:

- **Emit a Vue Event:** Use the standard Vue event `changeUI` to signal updates. 
This can be achieved by invoking the following command within the `control` Vue class: `vm.$emit('changeUI');`.

- **Handle the Vue Event:** Implement a handler method controlled by the Vue event `changeUI`. 
This can be set up in the panel's HTML template by configuring the property as: `@changeUI="changeUIHandler"`. 
The controller method `changeUIHandler` will manage the events triggered by changes to the UI.

### How It Works

The above steps set up a communication channel between the Bottom Side Panel UI and the framework. 
By emitting and handling the `changeUI` event, the application can dynamically adjust and respond to changes in panel dimensions, maintaining a seamless and consistent user interface experience.



