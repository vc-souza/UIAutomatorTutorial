package com.sample.tests;

import android.util.Log;

public class CrackedTests extends BaseTest
{
	public final static String TAG = IFoodTests.class.getSimpleName();
	
	public CrackedTests()
	{
		super();
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