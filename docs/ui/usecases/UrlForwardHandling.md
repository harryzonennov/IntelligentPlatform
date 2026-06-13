
# URL Forwarding Function

The URL Forwarding function on the UI client ensures navigation to the correct target page. 
A typical use case is when the login session is lost due to session timeout. The URL forwarding mechanism can navigate the user to the `index` page, 
prompting them to provide login information to resume the session, and subsequently redirect them to the appropriate target page upon successful login.


# How It Works

## Navigation to the `Index` Page with Parameters

When a login failure occurs, such as a session timeout, the URL Forwarding function navigates to the `Index` page with parameters storing the current page information. 
This process involves calling the following key methods:

### `ServiceHttpRequestHelper.handleErrorByErrorCode`

During a login failure, such as a session timeout, the Java backend raises a `LogonInfoException` to the UI client. 
The backend response contains information: `errorCode` = `HttpStatus.SC_UNAUTHORIZED (401)` and `subErrorCode` = `HttpSubStatus.SC_SUBERROR_LOGONFAILED`. 
If the response meets these conditions, the method `ServiceHttpRequestHelper.handleLogonFailed` is invoked.

### `ServiceHttpRequestHelper.handleLogonFailed`

This method performs the following actions:

- **Retrieve Current Page Information:** It first retrieves important information about the current page, such as the `pathName`, which indicates the path of the current page.
- **Retrieve Current Page Parameters:** It obtains the current page's URL parameters via the method `ServiceHttpRequestHelper.getUrlVars`, returning them as an object.
- **Merge Page Parameters:** Current page parameters are merged with new parameters such as `errorCode`, `subErrorCode`, and `pathName`, 
prioritizing new parameters, using the method `ServiceHttpRequestHelper.mergeUrlParas`.
- **Navigate to Index Page:** The page navigates to the Index page with the newly merged parameters.


## Navigation to the Target Page from the `Index` Page

In `Login.js`, which acts as the controller for the `Index` page, the `forwardToTarget` method retrieves parameters from the URL using `ServiceHttpRequestHelper.getUrlVars`. 
Importantly, the `pathName` parameter, representing the target page URL, is utilized to navigate to the target page along with other parameters.



