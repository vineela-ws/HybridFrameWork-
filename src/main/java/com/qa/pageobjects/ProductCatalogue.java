package com.qa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.abstractComponents.AbstractComponents;

public class ProductCatalogue extends AbstractComponents {
    WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	//PageFactory
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCart  = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}
	
    public WebElement getProductByname(String productName) {
    	WebElement prod = getProductList().stream().filter(product->
    	product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
    	return prod;
    }
    
    public void addProductToCart(String productName) {
    	WebElement prod = getProductByname(productName);
        if (prod == null) {
            throw new RuntimeException("Product not found: " + productName);
        }

        prod.findElement(addToCart).click();
        
        waitForElementToAppear(toastMessage);  // success message
        //waitForElementToDisAppear(spinner);    // loading spinner disappears
    
    }
}
