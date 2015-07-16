package com.sample.tests;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.sample.tests.utils.Utils;

import android.os.RemoteException;
import android.util.Log;

/***
 * 
 * A test suite class has to extend UiAutomatorTestCase in order to use the framework.
 * 
 */
public class AppTest extends UiAutomatorTestCase
{
	public final static String TAG = AppTest.class.getSimpleName();
	
	protected UiDevice device;
	protected Utils utils;
	protected String app_name;
	protected String original_screen_timeout;
	
	public AppTest(String app_name)
	{
		super();
		
		this.device = UiDevice.getInstance();
		this.utils = new Utils(this.device);
		this.app_name = app_name;
	}	

	/**
	 * The setUp() method runs before each test and is used to
	 * prepare the environment for the test, so it can start
	 * in a known state
	 */
	public void setUp() throws RemoteException, UiObjectNotFoundException
	{
		Log.d(TAG, "setUp!");
		
		this.original_screen_timeout = this.utils.get_screen_timeout();
		
		this.utils.set_screen_timeout("30 minutes");
		
		this.utils.open_app(this.app_name);
	}
	
	/**
	 * The tearDown() method runs after each test and is used to
	 * revert changes made by the test to the environment
	 * @throws UiObjectNotFoundException 
	 * @throws RemoteException 
	 */
	public void tearDown() throws RemoteException, UiObjectNotFoundException
	{
		Log.d(TAG, "tearDown!");
		
		this.utils.set_screen_timeout(this.original_screen_timeout);
	}
}