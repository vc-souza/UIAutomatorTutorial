package com.sample.tests;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

import android.os.RemoteException;
import android.util.Log;

public class Tests extends UiAutomatorTestCase
{
	public final String TAG = Tests.class.getSimpleName();
	
	private UiDevice device;
	
	private Utils utils;
	
	public void setUp()
	{
		Log.d(TAG, "setUp");
		
		this.device = UiDevice.getInstance();
		this.utils = new Utils(this.device);
	}
	
	public void tearDown()
	{
		Log.d(TAG, "tearDown");
	}
	
	public void test1() throws RemoteException
	{
		this.device.wakeUp();
	}
}
