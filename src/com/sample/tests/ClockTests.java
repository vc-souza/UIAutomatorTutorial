package com.sample.tests;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import android.os.RemoteException;
import android.util.Log;
import junit.framework.Assert;

public class ClockTests extends AppTest
{
	public final static String TAG = ClockTests.class.getSimpleName();
	
	public ClockTests()
	{
		super("Clock");
	}
	
	public void setUp() throws RemoteException, UiObjectNotFoundException
	{
		super.setUp();
		
		Log.d(TAG, "setUp!");
	}
	
	public void tearDown() throws RemoteException, UiObjectNotFoundException
	{
		super.tearDown();
		
		Log.d(TAG, "tearDown!");
	}
	
	// Tests start here --------------------------------
	
	/**
	 * Tests if an user can add a city clock to the Clock app.
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testAddNewLocation() throws UiObjectNotFoundException
	{
		String test_clock = "New York";
		
		UiObject all_clocks = new UiObject(new UiSelector().resourceId("com.android.deskclock:id/fab"));
		UiObject search_btn = new UiObject(new UiSelector().resourceId("com.android.deskclock:id/search_button"));
		UiObject city_widget = new UiObject(new UiSelector().resourceId("com.android.deskclock:id/city_name").text(test_clock));
		
		all_clocks.click();
		
		search_btn.waitForExists(3000);
		
		search_btn.setText(test_clock); 
		
		UiObject test_city = new UiObject(new UiSelector().resourceId("com.android.deskclock:id/city_name"));;
		
		test_city.click();
		
		for (int i = 0; i < 2; i++)
		{
			this.device.pressBack();
		}
		
		this.device.waitForIdle(3000);
		
		UiScrollable clocks_list = new UiScrollable(new UiSelector());
		
		// if the city does not show up at the clock home screen
		if (!clocks_list.scrollIntoView(city_widget))
		{
			Assert.fail(String.format("Could not add the '%s' city to the Clock app", test_city));
		}
	}
}
