package com.sample.tests;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.util.Log;

public class BaseTest extends UiAutomatorTestCase
{
	public final static String TAG = IFoodTests.class.getSimpleName();
	
	private UiDevice device;
	
	public BaseTest()
	{
		super();
		
		this.device = UiDevice.getInstance();
	}	
	
	public UiDevice getDevice() {
		return device;
	}

	public void setDevice(UiDevice device) {
		this.device = device;
	}

	public void setUp()
	{
		Log.d(TAG, "setUp!");
	}
	
	public void tearDown()
	{
		Log.d(TAG, "tearDown!");
	}
}