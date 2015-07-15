package com.sample.tests;

import android.util.Log;

public class IFoodTests extends BaseTest
{
	public final static String TAG = IFoodTests.class.getSimpleName();
	
	public IFoodTests()
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
