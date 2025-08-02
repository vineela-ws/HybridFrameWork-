package com.qa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.abstractComponents.AbstractComponents;

public class CheckOutPage extends AbstractComponents {
	 WebDriver driver;
		
		public CheckOutPage(WebDriver driver) {
			super(driver);
			this.driver = driver;	
		    PageFactory.initElements(driver, this);  // <-- Missing line added

		}
		
		@FindBy(css="[placeholder='Select Country']")
		WebElement country;
		
		@FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
		WebElement selectCountry;
		
		@FindBy(css=".action__submit")
		WebElement placeOrder;
		
		By results = By.cssSelector(".ta-results");
		
		public void selectCountry(String countryName) {
			Actions a = new Actions(driver);
		    a.click(country).sendKeys(countryName).build().perform();  // click before typing
		    waitForElementToAppear(results);
		    selectCountry.click();		
		}
		
		public ConfirmationPage placeOrder() {
			placeOrder.click();
			return new ConfirmationPage(driver);
		}
		
}
