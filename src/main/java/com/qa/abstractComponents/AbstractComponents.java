package com.qa.abstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.pageobjects.CartPage;
import com.qa.pageobjects.OrderPage;

public class AbstractComponents {

	WebDriver driver;

	public AbstractComponents(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "[routerLink*=cart]")
	WebElement cartHeader;
	
	@FindBy(css = "[routerLink*=myorders]")
	WebElement orderHeader;

	public void waitForElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForWebElementToAppear(WebElement findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}

	public void waitForElementToDisAppear(WebElement ele) throws InterruptedException {
		  WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
		  wait.until(ExpectedConditions.invisibilityOf(ele));
		 
	}

	public CartPage goToCartPage() throws InterruptedException {
		Thread.sleep(1000);
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrderPage goToOrdersPage() throws InterruptedException {
		Thread.sleep(1000);
		orderHeader.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}

}
