# UIAutomatorTutorial

UIAutomator is a test framework created by Google that enables the creation of automated tests for Android applications. These tests simulate the interaction of an actual user with the system UI.

The basic workflow looks like this:
- Finding an UI element by using a combination of its attributes: id, text, content description, and so on.
- Interacting with this element: tapping, dragging, waiting for it to disappear, etc.

As the framework bases its interactions on visible UI elements, there is no need to have the source code in order to test an app, which is a huge advantage.
It is needed, however for the target element to have some attribute that can differentiate it from the others, such as id, text, content description, hierarchy position, etc.

## *Setup*:
- Download and install the [Android SDK](https://developer.android.com/sdk/index.html)
- Using the SDK Manager, install the _Android Testing Support Library_
- Create a simple Java project, and add the following jar files to the build path:
  * _android-sdk/platforms/\<api-version\>/android.jar_
  * _android-sdk/platforms/\<api-version\>/uiautomator.jar_
  * _[junit](http://junit.org/)_
- Run `android-sdk/tools/android create uitest-project -n <project_name> -t 1 -p <project_path>`
- Run `ant build`
- Run `adb push <project_name>.jar /data/local/tmp/`
- Run `adb shell uiautomator runtest <project_name>.jar -c <totally_qualified_test_class>`

## *UIAutomatorViewer*

UIAutomatorViewer is a fundamental tool to UIAutomator development, as it enables inspecting of the system UI widgets (its ids, texts, types, etc). With this information, the developer can search for the widgets and then simulate the user interaction with them using the UIAutomator API.  
The tool can be found under _android-sdk/tools/uiautomatorviewer_. 

![screenshot do uiautomatorviewer sendo usado no 'Calendar'](https://i.imgur.com/YshxKe5.png)
![screenshot do uiautomatorviewer sendo usado no 'iFood'](https://i.imgur.com/bNSgTVm.png)

## Pros and Cons

#### Pros
- Interaction with all the apps and system UI, instead of just the AUT (Aplication Under Test).
- Provided and maintained by Google, the same company that maintains Android  .                                   
- Clear and simple - but powerful - API.

#### Cons
- It's only available on devices with API level 16 or newer.
- The classic release o UIAutomator (the one that this tutorial refers to) did not provide good IDE integration (see the setup steps). The newer release, though (launched on March), integrates well with Android Studio, and the final result is a apk, and not a jar file.
- If the UI element attributes change (text, id, content description) changes, the test has to be updated.

## *API*

The official API documentation can be found [here](https://developer.android.com/reference/android/support/test/uiautomator/package-summary.html).

## *Sample project*

The project that can be found in this repository utilizes most of UIAutomator features, and is thoroughly commented to help in the learning process.

## *Sources*:
  - https://developer.android.com/
  - http://www.vogella.com/tutorials/AndroidTesting/article.html
  - http://testdroid.com/news/the-pros-and-cons-of-different-android-testing-methods
