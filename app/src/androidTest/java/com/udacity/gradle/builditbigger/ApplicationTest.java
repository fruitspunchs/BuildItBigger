package com.udacity.gradle.builditbigger;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ApplicationTest() {
        super(MainActivity.class);
    }

    private String mJoke;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mJoke = new EndpointsAsyncTask().execute(getActivity()).get();
    }

    @MediumTest
    public void testEndpointsAsyncTask() {
        assertNotNull(mJoke);
        assertNotSame("", mJoke);
    }
}