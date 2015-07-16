package com.sample.tests;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;

import android.os.RemoteException;
import android.util.Log;
import junit.framework.Assert;

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
	
	public void tearDown() throws RemoteException, UiObjectNotFoundException
	{
		super.tearDown();
		
		Log.d(TAG, "tearDown!");
	}
	
	// Tests start here --------------------------------
	
	/**
	 * Tests if an user can view the featured Cracked article.
	 * 
	 * @throws UiObjectNotFoundException
	 */
	public void testFeaturedArticleLink() throws UiObjectNotFoundException
	{
		UiScrollable scrollable = new UiScrollable(new UiSelector());
		UiObject articles = new UiObject(new UiSelector().resourceId("com.cracked.android.lite:id/tvContentType").text("Articles"));
		UiObject featured_article = new UiObject(new UiSelector().resourceId("com.cracked.android.lite:id/feature_title"));
		
		// scrolling to the 'Articles' widget
		// we scroll instead of just finding it and clicking
		// because of devices with smaller screens
		scrollable.scrollIntoView(articles);
		
		articles.clickAndWaitForNewWindow();
		
		String featured_title = featured_article.getText();
		
		featured_article.clickAndWaitForNewWindow(5000);
		
		// UiCollection objects represent a widget that has children, which can be accessed
		// (mostly searched) by UiSelectors
		// In this example, we will just take the first element of this collection, though
		UiCollection webview = new UiCollection(new UiSelector().className("android.webkit.WebView").packageName("com.cracked.android.lite").scrollable(true));
		
		UiObject actual_title = webview.getChild(new UiSelector().index(0));
		
		// if the current article does not have the same title as the
		// one advertised, the test has failed
		if (!actual_title.getContentDescription().equals(featured_title))
		{
			Assert.fail("Could not go to the featured article!");
		}
	}
}