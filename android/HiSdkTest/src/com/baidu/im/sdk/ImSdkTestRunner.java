package com.baidu.im.sdk;

import junit.framework.TestSuite;
import android.test.InstrumentationTestRunner;
import android.test.suitebuilder.TestSuiteBuilder;

import com.baidu.im.inapp.transaction.session.processor.AppLoginProsessorTest;

public class ImSdkTestRunner extends InstrumentationTestRunner {
	@Override
	public TestSuite getAllTests() {
		return new TestSuiteBuilder(AppLoginProsessorTest.class)
				.includeAllPackagesUnderHere().build();
	}
}
