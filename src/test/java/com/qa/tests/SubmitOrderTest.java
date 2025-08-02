package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.pageobjects.CartPage;
import com.qa.pageobjects.CheckOutPage;
import com.qa.pageobjects.ConfirmationPage;
import com.qa.pageobjects.LandingPage;
import com.qa.pageobjects.OrderPage;
import com.qa.pageobjects.ProductCatalogue;
import com.qa.testcomponents.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {
	
	String productName = "ZARA COAT 3";
			
    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {
    
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));
        
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
	
        CartPage cartPage = productCatalogue.goToCartPage();
        
        Boolean match = cartPage.verifyProductDisplay(input.get("product"));
        Assert.assertTrue(match);
        
        CheckOutPage CheckOutPage = cartPage.goToCheckOut();
        CheckOutPage.selectCountry("india");
        ConfirmationPage confirmationPage = CheckOutPage.placeOrder();
        
        String confirmationMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	
    }
    
    @Test(dataProvider = "getData",dependsOnMethods = {"submitOrder"})
    public void orderHistoryTest(HashMap<String,String> input) throws InterruptedException {
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));
        OrderPage  OrderPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(OrderPage.verifyOrderDisplay(input.get(productName)));
    }
    
    
    //OtherWay Using Json
    @DataProvider
    public Object[][] getData() throws Exception {
    	List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\com\\qa\\data\\PurchaseOrder.json");
    	
    	return new Object [] [] {{data.get(0)}, {data.get(1)}};
    }
}
