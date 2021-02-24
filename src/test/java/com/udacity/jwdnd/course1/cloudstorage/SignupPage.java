package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement input_firstname;

    @FindBy(id = "inputLastName")
    private WebElement input_lastname;

    @FindBy(id = "inputUsername")
    private WebElement input_username;

    @FindBy(id = "inputPassword")
    private WebElement input_password;

    public SignupPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void registerUser(String firstname,String lastname,String username,String password){
        input_firstname.sendKeys(firstname);
        input_lastname.sendKeys(lastname);
        input_username.sendKeys(username);
        input_password.sendKeys(password);

        input_password.submit();
    }
}
