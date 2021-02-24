package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HomePage {
    @FindBy(id = "logoutDiv")
    WebElement logout_div;

    @FindBy(id = "nav-files-tab")
    WebElement nav_files_tab;

    @FindBy(id = "fileTable")
    WebElement file_table;

    @FindBy(id = "nav-notes-tab")
    WebElement nav_notes_tab;

    @FindBy(id = "noteTable")
    WebElement note_table;

    @FindBy(id = "nav-credentials-tab")
    WebElement nav_credentials_tab;

    @FindBy(id = "credentialTable")
    WebElement credential_table;

    @FindBy(id = "fileUpload")
    WebElement upload_file_button;

    @FindBy(id = "noteUpload")
    WebElement upload_note_button;

    @FindBy(id = "note-title")
    WebElement input_note_title;

    @FindBy(id = "note-description")
    WebElement input_note_description;

    @FindBy(id = "credentialUpload")
    WebElement upload_credential_button;

    @FindBy(id = "credential-url")
    WebElement input_credential_url;

    @FindBy(id = "credential-username")
    WebElement input_credential_username;

    @FindBy(id = "credential-password")
    WebElement input_credential_password;

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }
    public void logout(){
        this.logout_div.findElement(By.tagName("button")).click();
    }

    public void uploadNote(String note_title, String note_description) throws InterruptedException {
        this.nav_notes_tab.click();
        Thread.sleep(2000);
        this.upload_note_button.click();
        Thread.sleep(2000);
        this.input_note_title.sendKeys(note_title);
        this.input_note_description.sendKeys(note_description);

        this.input_note_description.submit();
    }

    public void editNote(String note_title, String note_description) throws InterruptedException {
        this.nav_notes_tab.click();
        Thread.sleep(2000);
        this.note_table.findElement(By.tagName("button")).click();
        Thread.sleep(2000);
        this.input_note_title.clear();
        this.input_note_description.clear();
        this.input_note_title.sendKeys(note_title);
        this.input_note_description.sendKeys(note_description);

        this.input_note_description.submit();
    }
    public void deleteNote() throws InterruptedException {
        this.nav_notes_tab.click();
        Thread.sleep(2000);
        this.note_table.findElement(By.tagName("a")).click();
    }

    public List<String> getTitleInElementTable(WebElement table, String title){
        List<WebElement> rows = table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        System.out.println(rows);
        return rows.stream()
                .map(tr -> tr.findElements(By.tagName("th")).get(0).getText())
                .filter(titles -> titles.equals(title))
                //.allMatch( noteTitle -> noteTitle.equals(title));
                .collect(Collectors.toList());
    }
    public List<String> getColumnTrInElementTable(WebElement table, int column){
        List<WebElement> rows = table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        System.out.println(rows);
        return rows.stream()
                .map(tr -> tr.findElements(By.tagName("td")).get(column).getText())
                //.allMatch( noteTitle -> noteTitle.equals(title));
                .collect(Collectors.toList());
    }
    public void uploadCredential(String credential_url, String credential_username, String credential_password) throws InterruptedException {
        this.nav_credentials_tab.click();
        Thread.sleep(2000);
        this.upload_credential_button.click();
        Thread.sleep(2000);
        this.input_credential_url.sendKeys(credential_url);
        this.input_credential_username.sendKeys(credential_username);
        this.input_credential_password.sendKeys(credential_password);

        this.input_credential_password.submit();
    }
    public String viewCredential() throws InterruptedException {
        this.nav_credentials_tab.click();
        Thread.sleep(2000);
        this.credential_table.findElement(By.tagName("button")).click();
        Thread.sleep(3000);
        return this.input_credential_password.getAttribute("value");
    }
    public void editCredential(String credential_url, String credential_username, String credential_password) throws InterruptedException {
        this.input_credential_url.clear();
        this.input_credential_username.clear();
        this.input_credential_password.clear();
        this.input_credential_url.sendKeys(credential_url);
        this.input_credential_username.sendKeys(credential_username);
        this.input_credential_password.sendKeys(credential_password);
        Thread.sleep(2000);

        this.input_credential_password.submit();
    }

    public void deleteCredential() throws InterruptedException {
        this.nav_credentials_tab.click();
        Thread.sleep(2000);
        this.credential_table.findElement(By.tagName("a")).click();
    }
    //public void upload
}
