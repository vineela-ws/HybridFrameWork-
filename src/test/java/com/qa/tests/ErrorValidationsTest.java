package com.qa.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.testcomponents.BaseTest;
import com.qa.testcomponents.Retry;

public class ErrorValidationsTest extends BaseTest {
	
	@Test(groups = {"ErrorHandling"}, retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException, InterruptedException {   	
        landingPage.loginApplication("vara@gmail.com", "Varnam@98765");
        Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());;
    }

}
