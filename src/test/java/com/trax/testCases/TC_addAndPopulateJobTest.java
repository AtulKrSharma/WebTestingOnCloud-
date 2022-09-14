package com.trax.testCases;

import java.io.IOException;

import org.testng.annotations.Test;

import com.trax.pageObjects.HomePage;
import com.trax.pageObjects.BaseClass;

public class TC_addAndPopulateJobTest extends BaseClass {

	@Test()
	public void addAndPopulateJobTest() throws IOException, InterruptedException {

		// Objects of the pages needed by the this workflow

		HomePage hp = new HomePage(driver);
		hp.setUserName("Atul");
		System.out.println("Successfully executed Test->'");
	}
}
