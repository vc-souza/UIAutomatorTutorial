package com.sample.tests;

import com.android.uiautomator.core.UiObjectNotFoundException;

import android.os.RemoteException;
import android.util.Log;

public class IFoodTests extends AppTest
{
	public final static String TAG = IFoodTests.class.getSimpleName();
	
	public IFoodTests()
	{
		super("iFood");
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
}
