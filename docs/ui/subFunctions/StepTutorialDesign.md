
# Overview: Step Tutorial for End Users on the UI

## Background
The Step Tutorial is a functionality embedded within the Client UI to assist users in performing specific functions within certain contexts. 
It does so by providing notification messages and visually highlighting specific UI elements such as buttons and links. 
These visual cues aim to guide users effectively through tasks or processes.

---

## Utility Class: `ServiceStepTutorialHelper`
The core functionality of the Step Tutorial is implemented within the utility class `ServiceStepTutorialHelper`. 
This class contains two key methods: `initConfiguration` and `loadTutorialUnion`.

### Method: `initConfiguration`
This method serves as the entry point for `ServiceStepTutorialHelper`. It initializes the Step Tutorial by reading the 
configuration object provided in JSON format. This object includes essential information, such as:
- **`stepTutorialConfig`**: A JSON object that specifies conditions for activating the tutorial, the messages to be displayed, 
- and the specific UI elements (such as buttons or links) to be highlighted.
- **`labelObj`**: The label object passed from the parent UI controller.
- **`getStatus`** (optional): A function from the parent UI controller, which provides the status of different UI elements.
- **`defaultMessageContainer`**: The default container within the page UI where notification messages should be displayed. 
- This is used if no specific container is defined in the configuration.

### Method: `loadTutorialUnion`
This is the core method responsible for rendering the Step Tutorial on the UI. It includes the following sub-functions:
1. **Displaying Notification Messages**: This renders notification messages at specified locations within the UI. 
These locations can be configured within the `stepTutorialConfig` object or designated by the `defaultMessageContainer`.
2. **Highlighting Buttons**: Specific buttons can be visually highlighted based on the configuration in the `stepTutorialConfig` object.
3. **Highlighting Table Buttons**: Embedded links within table rows can also be highlighted if specified in the configuration.

---

## Business Use Cases

### Step Tutorial: Configuration for the Editor Page
The Step Tutorial can be activated on the Editor Page by setting up the `stepTutorialConfig` object within the `pageMeta` object in the UI controller.

- The **`labelObj`** is retrieved as the `vm.label` attribute from the parent UI controller.
- The **`getStatus`** function is accessed through the `vm.getStatus` method of the parent UI controller.
- The **`defaultMessageContainer`** function is retrieved by calling `vm.getDefaultMessageContainer`, 
which typically returns the page header message container. If no specific location is defined in the `stepTutorialConfig`, the notification message will default to appear in this container.

---

### Step Tutorial: Configuration for Specific Sections
To enable the Step Tutorial for specific sections that are child Vue classes of the `Async Section`, the `stepTutorialConfig` 
can be provided via the `getSectionStepTutorialConfigure` method of the `AsyncSection` or its child Vue classes.

For example, the `Store Available Store Section`, which is a child vue classes of the `Async Section`, enabled with Step Tutorial configuration. 
The following parameters are used in these specific sections:
- The **`defaultMessageContainer`** function is retrieved with the `vm.getDefaultSectionMessageContainer` method. As in other cases, 
this typically defaults to the section header message container, where notification messages are displayed if a specific location is not defined within the `stepTutorialConfig`.

---

### Step Tutorial: Document Controller Step Tutorial Config
The Step Tutorial is actived for some standard document action in the Document Controller method: `initDocumentStepTutorial`