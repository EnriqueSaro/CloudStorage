package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private final String base_url = "http://localhost:";
	private final String first_name = "Firstname";
	private final String last_name = "Lastname";
	private final String username = "testUsername";
	private final String password = "testPassword";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(1)
	public void noAccessToUnauthorizedUser() throws InterruptedException {
		driver.get(base_url + port +"/home");
		Assertions.assertNotEquals("Home",driver.getTitle());

		//check that unauthorized user has no access to home page
		driver.get(base_url + port +"/home/view-file/1");
		Assertions.assertNotEquals("Home",driver.getTitle());
		Assertions.assertEquals("Login",driver.getTitle());

		//check that unauthorized user has access to signup page
		driver.get(base_url + port +"/signup");
		Thread.sleep(2000);
		Assertions.assertEquals("Sign Up",driver.getTitle());

	}

	@Test
	@Order(2)
	public void registerUserLoginAndLogout() throws InterruptedException {
		driver.get(base_url + port +"/signup");
		Assertions.assertNotEquals("Sign up",driver.getTitle());

		//register user
		SignupPage signupPage = new SignupPage(driver);
		signupPage.registerUser(first_name,last_name,username,password);
		Thread.sleep(2000);
		Assertions.assertTrue(driver.findElement(By.id("signup_success")).isDisplayed());

		//login new user
		driver.get(base_url + port +"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		Thread.sleep(2000);
		Assertions.assertEquals("Home", driver.getTitle());

		//check if new user has access to home page
		HomePage homePage = new HomePage(driver);
		homePage.logout();
		driver.get(base_url + port + "/home");
		Assertions.assertNotEquals("Home",driver.getTitle());
		Assertions.assertEquals("Login", driver.getTitle());
		Thread.sleep(2000);
	}
	@Test
	@Order(3)
	public void createNote() throws InterruptedException {
		driver.get(base_url + port +"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		Thread.sleep(2000);

		String note_title = "New note";
		String note_description = "Note description";

		//create note
		HomePage homePage = new HomePage(driver);
		homePage.uploadNote(note_title,note_description);
		Thread.sleep(2000);

		//check if note was created
		List<String> title = homePage.getTitleInElementTable( homePage.note_table , note_title);
		Assertions.assertEquals(1,title.size());
		Assertions.assertEquals(note_title, title.get(0));

	}
	@Test
	@Order(4)
	public void editNote() throws InterruptedException {
		driver.get(base_url + port +"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		Thread.sleep(2000);

		String note_title = "New note";
		String edited_note_title = "Edited note";
		String edited_note_description = "Edited description";

		HomePage homePage = new HomePage(driver);
		homePage.nav_notes_tab.click();
		Thread.sleep(2000);

		//get actual information about note
		List<String> title = homePage.getTitleInElementTable( homePage.note_table , note_title);
		Assertions.assertEquals(1,title.size());
		Assertions.assertEquals(note_title, title.get(0));

		//edit note
		homePage.editNote(edited_note_title,edited_note_description);
		Thread.sleep(2000);

		//check if note was edited
		List<String> edited_title = homePage.getTitleInElementTable( homePage.note_table , edited_note_title);
		Assertions.assertEquals(1,title.size());
		Assertions.assertEquals(edited_note_title, edited_title.get(0));
		Thread.sleep(2000);

	}
	@Test
	@Order(5)
	public void deleteNote() throws InterruptedException {
		driver.get(base_url + port +"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		Thread.sleep(2000);

		String edited_note_title = "Edited note";
		HomePage homePage = new HomePage(driver);

		//get information about note
		homePage.nav_notes_tab.click();
		Thread.sleep(2000);
		List<String> title = homePage.getTitleInElementTable( homePage.note_table , edited_note_title);
		Assertions.assertEquals(1,title.size());
		Assertions.assertEquals(edited_note_title, title.get(0));

		//delete note
		homePage.deleteNote();
		Thread.sleep(3000);

		//check if note was deleted
		List<String> edited_title = homePage.getTitleInElementTable( homePage.note_table , edited_note_title);
		Assertions.assertEquals(0,edited_title.size());
		Thread.sleep(2000);

	}

	@Test
	@Order(6)
	public void createCredential() throws InterruptedException {
		driver.get(base_url + port +"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		Thread.sleep(2000);

		String credential_url = "http://localhost:8080";
		String credential_username = "TestUsername";
		String credential_password = "SecureTestPassword1234&%";

		//create credential
		HomePage homePage = new HomePage(driver);
		homePage.uploadCredential(credential_url,credential_username,credential_password);
		Thread.sleep(2000);

		//check if credential was created
		List<String> url = homePage.getTitleInElementTable( homePage.credential_table , credential_url);
		Assertions.assertEquals(1,url.size());
		Assertions.assertEquals(credential_url, url.get(0));

		//Check if credential is encrypted on table
		List<String> encrypted_password = homePage.getColumnTrInElementTable( homePage.credential_table ,2);
		Assertions.assertEquals(1,url.size());
		Assertions.assertNotEquals(credential_password,encrypted_password.get(0));

	}
	@Test
	@Order(7)
	public void editCredential() throws InterruptedException {
		driver.get(base_url + port +"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		Thread.sleep(2000);

		String credential_url = "http://localhost:8080";
		String credential_password = "SecureTestPassword1234&%";

		String edited_credential_url = "http://facebook.com";
		String edited_credential_username = "EditedUsername";
		String edited_credential_password = "SecureTestEditedPassword1234&%";

		HomePage homePage = new HomePage(driver);
		homePage.nav_credentials_tab.click();
		Thread.sleep(2000);

		//Get credential actual information
		List<String> url = homePage.getTitleInElementTable( homePage.credential_table , credential_url);
		Assertions.assertEquals(1,url.size());
		Assertions.assertEquals(credential_url, url.get(0));

		//check if credential is unencrypted on modal
		String unencrypted_password = homePage.viewCredential();
		Assertions.assertEquals(credential_password,unencrypted_password);

		//edit credential
		homePage.editCredential(edited_credential_url,edited_credential_username,edited_credential_password);
		Thread.sleep(2000);

		//Check if credential was edited
		List<String> edited_url = homePage.getTitleInElementTable( homePage.credential_table , edited_credential_url);
		Assertions.assertEquals(1,edited_url.size());
		Assertions.assertEquals(edited_credential_url, edited_url.get(0));
		Thread.sleep(2000);

	}
	@Test
	@Order(8)
	public void deleteCredential() throws InterruptedException {
		driver.get(base_url + port +"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		Thread.sleep(2000);

		String edited_credential_url = "http://facebook.com";

		HomePage homePage = new HomePage(driver);
		homePage.nav_credentials_tab.click();
		Thread.sleep(2000);
		//get info about credential
		List<String> title = homePage.getTitleInElementTable( homePage.credential_table , edited_credential_url);
		Assertions.assertEquals(1,title.size());
		Assertions.assertEquals(edited_credential_url, title.get(0));

		//delete credential
		homePage.deleteCredential();
		Thread.sleep(3000);

		//check if credential was deleted
		List<String> edited_title = homePage.getTitleInElementTable( homePage.credential_table , edited_credential_url);
		Assertions.assertEquals(0,edited_title.size());
		Thread.sleep(2000);

	}


}
