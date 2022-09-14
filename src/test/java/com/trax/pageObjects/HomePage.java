package com.trax.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage  {

	WebDriver ldriver;

	public HomePage(WebDriver rdriver) {
		ldriver = rdriver;
		PageFactory.initElements(rdriver, this);
	}

	// objects of the

	@FindBy(how = How.XPATH, using = "//a[normalize-space()='About Us']")
	WebElement tabMyBankTab;
	
	public void setUserName(String x){
		tabMyBankTab.click();		
	}

	

}
