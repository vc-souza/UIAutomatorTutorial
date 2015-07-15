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
			app.click();
		}
		else
		{
			throw new UiObjectNotFoundException(String.format("'%s' is not listed on the app list", app_name));
		}
	}
	
	/***
	 * Open the given System Settings
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
			new UiObject(new UiSelector().text(setting_title)).click();
		}
		else
		{
			throw new UiObjectNotFoundException(String.format("The %s setting does not exist!", setting_title));
		}
	}
}
