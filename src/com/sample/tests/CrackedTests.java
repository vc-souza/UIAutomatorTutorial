package com.sample.tests;

import com.android.uiautomator.core.UiObjectNotFoundException;

import android.os.RemoteException;
import android.util.Log;

public class CrackedTests extends AppTest
{
	public final static String TAG = CrackedTests.class.getSimpleName();
	
	public CrackedTests()
	{
		super("Cracked Lite");
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