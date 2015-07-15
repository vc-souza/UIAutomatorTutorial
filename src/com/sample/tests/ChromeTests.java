package com.sample.tests;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import android.os.RemoteException;
import android.util.Log;
import junit.framework.Assert;

public class ChromeTests extends AppTest
{
	public final static String TAG = ChromeTests.class.getSimpleName(); 
	
	public ChromeTests()
	{
		super("Chrome");
	}
	
	public void setUp() throws RemoteException, UiObjectNotFoundException
	{
		super.setUp();
		
		Log.d(TAG, "setUp!");
	}
	
	public void tearDown()
	{
		super.tearDown();
		
		Log.d(TAG, "tearDown!");
	}
	
	// Tests start here --------------------------------
	
	/***
	 * Tests if a user can bookmark a website
	 * @throws UiObjectNotFoundException 
	 */
	public void testAddBookmark() throws UiObjectNotFoundException
	{		
		String url = "www.guildwars2.com";
		String bookmark_title = "test_bookmark";
		
		// The UiObject instances can be created anywhere, we can query if they exist in the current
		// screen by calling obj.exists()
		UiObject url_bar = new UiObject(new UiSelector().resourceId("com.android.chrome:id/url_bar"));
		UiObject more_options = new UiObject(new UiSelector().description("More options"));
		UiObject bookmark_title_input = new UiObject(new UiSelector().resourceId("com.android.chrome:id/bookmark_title_input"));
		UiObject ok = new UiObject(new UiSelector().resourceId("com.android.chrome:id/ok"));
		UiObject favorite_star = new UiObject(new UiSelector().resourceId("com.android.chrome:id/button_two"));
		UiObject add_bookmark = new UiObject(new UiSelector().text("Add bookmark"));
		UiObject bookmarks = new UiObject(new UiSelector().text("Bookmarks"));
		
		// waiting for the url bar to appear
		if (url_bar.waitForExists(5000))
		{
			// inputing the url and then going to the website
			url_bar.click();
			
			url_bar.setText(url);
			
			this.device.pressEnter();
			
			this.device.waitForIdle(5000);
			
			// bookmarking the website
			more_options.click();
			
			favorite_star.waitForExists(3000);
			
			favorite_star.click();
			
			if (add_bookmark.waitForExists(3000))
			{
				// changing the bookmark title to a known string
				
				bookmark_title_input.click();
				
				this.device.clearLastTraversedText();
				
				bookmark_title_input.setText(bookmark_title);
				
				this.device.pressBack();
				
				ok.waitForExists(2000);
				
				ok.clickAndWaitForNewWindow();
				
				more_options.click();
				
				// access the Bookmarks list				
				bookmarks.waitForExists(3000);
				
				bookmarks.clickAndWaitForNewWindow();
				
				// and test if we can scroll to the test bookmark to check if it's here
				UiScrollable bookmarks_list = new UiScrollable(new UiSelector());
				
				if (!bookmarks_list.scrollTextIntoView(bookmark_title))
				{
					Assert.fail(String.format("Could not find the '%s' bookmark in the bookmarks list", bookmark_title));
				}
			}
			else
			{
				throw new RuntimeException("The 'Add bookmark' dialog did not appear!");
			}
		}
		else
		{
			throw new UiObjectNotFoundException("The 'url_bar' widget could not be found");
		}		
	}
}