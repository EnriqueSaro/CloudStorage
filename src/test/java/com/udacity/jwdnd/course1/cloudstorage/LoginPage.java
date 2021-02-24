package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement input_username;

    @FindBy(id = "inputPassword")
    private WebElement input_password;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void loginUser(String username, String password){
        input_username.sendKeys(username);
        input_password.sendKeys(password);

        input_password.submit();
    }
}
