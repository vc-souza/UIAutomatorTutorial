package com.sample.tests.utils;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import android.os.RemoteException;

/***
 * 
 * A class that implements some useful UI-related functions.
 *
 */
public class Utils
{
	// The UiDevice object provides meta-information about the device,
	// as well as user interaction simulation and utilities
	private UiDevice device;
	
	public Utils(UiDevice device)
	{
		this.device = device;
	}

	public UiDevice get_device() {
		return device;
	}

	public void set_device(UiDevice device) {
		this.device = device;
	}
	
	// ----------------------------------------
	
	/***
	 * Opens an app that is in the app list.
	 * 
	 * @param app_name, the name of the app to be opened
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void open_app(String app_name) throws RemoteException, UiObjectNotFoundException
	{
		// wakes up the device in case it's screen is off
		this.device.wakeUp();
		
		// simulating the user interaction of pressing the home button
		this.device.pressHome();
		
		// wait for the app list to show up
		UiObject menu = new UiObject(new UiSelector().descriptionMatches("Apps"));
		
		// waiting for a window that's different than the current one
		menu.clickAndWaitForNewWindow();
		
		// An UiScrollable object represents a scrollable widget, which means
		// that an user should be able to scroll it to show additional content
		UiScrollable scroll = new UiScrollable(new UiSelector()
											   .className("android.view.View")
											   .scrollable(true));
		
		// the default for scrollables is being a vertical list
		scroll.setAsHorizontalList();

		// here we create an UiObject that has the given app name as its text attribute
		// we use UiSelector in order to specify the widget's attributes in order to find it
		// on the screen
		UiObject app = new UiObject(new UiSelector().text(app_name));
		
		boolean found = false;
		boolean can_scroll_forward = true;
		boolean can_scroll_backward = true;
		
		// Scrolling through the pages of the app list until we can find the
		// app with the given name or run out of pages to scroll
		while (!found && (can_scroll_forward || can_scroll_backward))
		{
			// waiting 5 seconds (the value is given in milliseconds)
			// for the specified app to show up on the screen
			if (app.waitForExists(5000))
			{
				found = true;
			}
			// if the app was not found in the current screen, scroll
			else
			{				
				// if forward scrolling is permitted, try it
				if (can_scroll_forward)
				{
					// scroll one page forward
					scroll.scrollForward();
					
					// If by now we reached the 'Apps page N of N'
					// then we ran out of forward scrolls
					String page_count = new UiObject(new UiSelector().descriptionContains("Apps page")).getContentDescription();
					
					String[] string_elems = page_count.split(" ");
					
					can_scroll_forward = !string_elems[2].equals(string_elems[4]);
				}
				// if backward scrolling is permitted, try it
				else if (can_scroll_backward)
				{
					// scroll one page backward
					scroll.scrollBackward();
					
					// If by now we reached the 'Apps page 1 of N'
					// then we ran out of backward scrolls					
					can_scroll_backward = !new UiObject(new UiSelector().descriptionContains("Apps page 1")).exists();
				}
			}
		}
		
		// if the app widget was found, simulate a click event
		if (found)
		{
			app.clickAndWaitForNewWindow();
		}
		else
		{
			throw new UiObjectNotFoundException(String.format("'%s' is not listed on the app list", app_name));
		}
	}
	
	/***
	 * Open the given System Settings.
	 * 
	 * @param setting_title, the type of system settings that should
	 * be opened, such as 'Display', or 'Bluetooth'
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void open_settings(String setting_title) throws RemoteException, UiObjectNotFoundException
	{
		// using the already implemented 'open_app' function
		this.open_app("Settings");
		
		// using other widget attributes to locate the objects on the screen
		// in this case, we wait for the 'Settings' activity, from package
		// 'com.android.settings' to show up
		new UiObject(new UiSelector().packageName("com.android.settings").text("Settings")).waitForExists(5000);
		
		UiScrollable settings_list = new UiScrollable(new UiSelector());
		
		// we use the UiScrollable object to scroll until we get the given setting title
		if (settings_list.scrollTextIntoView(setting_title))
		{
			// with the UiObject in hands, we can simulate the click event on it
			new UiObject(new UiSelector().text(setting_title)).clickAndWaitForNewWindow();
		}
		else
		{
			throw new UiObjectNotFoundException(String.format("The %s setting does not exist!", setting_title));
		}
	}
	
	public String get_screen_timeout() throws RemoteException, UiObjectNotFoundException
	{	
		UiScrollable settings_list = new UiScrollable(new UiSelector());
		UiObject sleep_widget = new UiObject(new UiSelector().text("Sleep").resourceId("android:id/title"));
		
		// Opening the Display settings
		this.open_settings("Display");
		
		// Scrolling to the 'Sleep' setting
		settings_list.scrollIntoView(sleep_widget);
		
		String summary_string = sleep_widget.getFromParent(new UiSelector().resourceId("android:id/summary")).getText();
		
		String[] tokens = summary_string.split(" ");
		
		return String.format("%s %s", tokens[1], tokens[2]);
	}
	
	/**
	 * Sets the screen timeout to the given value.
	 * 
	 * @param new_value, the new value of the screen timeout, in the 'N minutes/seconds' format
	 * @throws UiObjectNotFoundException 
	 * @throws RemoteException 
	 */
	public void set_screen_timeout(String new_value) throws RemoteException, UiObjectNotFoundException
	{
		UiScrollable settings_list = new UiScrollable(new UiSelector());
		UiObject sleep_widget = new UiObject(new UiSelector().text("Sleep").resourceId("android:id/title"));
		UiObject options_popup = new UiObject(new UiSelector().text("Sleep").resourceId("android:id/alertTitle"));
		UiObject new_timeout = new UiObject(new UiSelector().text(new_value));
		
		// Opening the Display settings
		this.open_settings("Display");
		
		// Scrolling to the 'Sleep' setting
		settings_list.scrollIntoView(sleep_widget);
		
		sleep_widget.click();
		
		options_popup.waitForExists(5000);
		
		// setting the timeout to the new value by using the pop-up
		if (new_timeout.exists())
		{
			new_timeout.click();
			
			sleep_widget.waitForExists(3000);
			
			// From the current widget, we can access its siblings or children
			// by using 'getFromParent'
			// this is useful when there are more than one widget
			// with the same information, and we want to use the one that is
			// a sibling or children of another known widget.
			// In this example, there are more than one 'summary' widgets just
			// like the one describing the current screen timeout
			// we can then access the right element by searching for it taking
			// sleep_widget as a starting point because we know that the target element
			// is a sibling of sleep_widget.
			String current_timeout_text = sleep_widget.getFromParent(new UiSelector().resourceId("android:id/summary")).getText();
			
			if(!current_timeout_text.equals(String.format("After %s of inactivity", new_value)))
			{
				throw new RuntimeException(String.format("Could not change the screen timeout to '%s'", new_value));
			}
		}
		else
		{
			throw new RuntimeException(String.format("'%s' is not a valid value for the screen timeout", new_value));
		}
	}
}
