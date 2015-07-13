package com.sample.tests;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import android.os.RemoteException;

public class Utils
{
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
	
	public void open_app(String app_name) throws RemoteException, UiObjectNotFoundException
	{
		this.device.wakeUp();
		
		this.device.pressHome();
		
		// wait for the app list to show up
		UiObject menu = new UiObject(new UiSelector().descriptionMatches("Apps"));
		
		menu.clickAndWaitForNewWindow();
		
		UiScrollable scroll = new UiScrollable(new UiSelector()
											   .className("android.view.View")
											   .scrollable(true));
		
		scroll.setAsHorizontalList();
		
		UiObject app = new UiObject(new UiSelector().text(app_name));
		
		boolean found = false;
		boolean can_scroll_forward = true;
		boolean can_scroll_backward = true;
		
		while (!found && (can_scroll_forward || can_scroll_backward))
		{
			if (app.waitForExists(5000))
			{
				found = true;
			}
			else
			{				
				// if forward scrolling is permitted, try it
				if (can_scroll_forward)
				{
					scroll.scrollForward();
					
					String page_count = new UiObject(new UiSelector().descriptionContains("Apps page")).getContentDescription();
					
					String[] string_elems = page_count.split(" ");
					
					can_scroll_forward = !string_elems[2].equals(string_elems[4]);
				}
				// if backward scrolling is permitted, try it
				else if (can_scroll_backward)
				{
					scroll.scrollBackward();
					
					can_scroll_backward = !new UiObject(new UiSelector().descriptionContains("Apps page 1")).exists();
				}
			}
		}
		
		if (found)
		{
			app.click();
		}
		else
		{
			throw new UiObjectNotFoundException(String.format("'%s' is not listed on the app list", app_name));
		}
	}
	
	public void open_settings(String setting_title) throws RemoteException, UiObjectNotFoundException
	{
		this.open_app("Settings");
		
		new UiObject(new UiSelector().packageName("com.android.settings").text("Settings")).waitForExists(5000);
		
		UiScrollable settings_list = new UiScrollable(new UiSelector());
		
		if (settings_list.scrollTextIntoView(setting_title))
		{
			new UiObject(new UiSelector().text(setting_title)).click();
		}
		else
		{
			throw new UiObjectNotFoundException(String.format("The %s setting does not exist!", setting_title));
		}
	}
	
	public void install_from_store(String pkg)
	{
		
	}
}
