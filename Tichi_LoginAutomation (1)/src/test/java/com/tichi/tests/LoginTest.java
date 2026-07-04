package com.tichi.tests;

import com.tichi.base.BaseTest;
import com.tichi.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    private static final String VALID_UNREGISTERED_EMAIL = "qa.testuser.tichi@mailinator.com";
    private static final String INVALID_EMAIL_MISSING_TLD = "abc@gmail";
    private static final String EXPECTED_EMPTY_EMAIL_ERROR = "Email is required";
    private static final String APP_BASE_URL = "https://tichi-app-webapp-stage.web.app/login";

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1,
          testName = "TC-LGN-001",
          description = "Verify that the Login page loads successfully")
    public void verifyLoginPageLoadsSuccessfully() {
        boolean pageLoaded = loginPage.isPageLoaded();
        String currentUrl = loginPage.getCurrentUrl();

        Assert.assertTrue(pageLoaded,
                "Login page did not load successfully. Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains(APP_BASE_URL.replace("https://", "")),
                "Browser is not on the expected Login page. Current URL: " + currentUrl);
    }

    @Test(priority = 2,
          testName = "TC-LGN-002",
          description = "Verify that the Email Address input field is displayed on the Login page")
    public void verifyEmailFieldIsDisplayed() {
        boolean isDisplayed = loginPage.isEmailFieldDisplayed();

        Assert.assertTrue(isDisplayed,
                "Email Address input field is not displayed on the Login page.");
    }

    @Test(priority = 3,
          testName = "TC-LGN-003",
          description = "Verify that the Continue button is displayed on the Login page")
    public void verifyContinueButtonIsDisplayed() {
        boolean isDisplayed = loginPage.isContinueButtonDisplayed();

        Assert.assertTrue(isDisplayed,
                "Continue button is not displayed on the Login page.");
    }

    @Test(priority = 4,
          testName = "TC-LGN-004",
          description = "Verify that the 'Continue with Google' button is displayed on the Login page")
    public void verifyContinueWithGoogleButtonIsDisplayed() {
        boolean isDisplayed = loginPage.isContinueWithGoogleButtonDisplayed();

        Assert.assertTrue(isDisplayed,
                "'Continue with Google' button is not displayed on the Login page.");
    }

    @Test(priority = 5,
          testName = "TC-LGN-005",
          description = "Verify that a validation message is displayed when the Email field is submitted empty")
    public void verifyValidationMessageForEmptyEmail() {
        loginPage.submitEmptyEmail();

        boolean validationDisplayed = loginPage.isValidationMessageDisplayed();
        String actualMessage = loginPage.getEmailValidationMessage();

        Assert.assertTrue(validationDisplayed,
                "No validation message was displayed after submitting an empty Email field.");
        Assert.assertEquals(actualMessage, EXPECTED_EMPTY_EMAIL_ERROR,
                "Validation message text does not match. Expected: [" + EXPECTED_EMPTY_EMAIL_ERROR +
                "] Actual: [" + actualMessage + "]");
    }

    @Test(priority = 6,
          testName = "TC-LGN-006",
          description = "Verify that a validation error is displayed when an invalid email format 'abc@gmail' is entered")
    public void verifyValidationMessageForInvalidEmailFormat() {
        loginPage.enterEmail(INVALID_EMAIL_MISSING_TLD);
        loginPage.clickContinue();

        boolean validationDisplayed = loginPage.isValidationMessageDisplayed();
        String actualMessage = loginPage.getEmailValidationMessage();

        Assert.assertTrue(validationDisplayed,
                "No validation message was displayed after entering invalid email: " +
                INVALID_EMAIL_MISSING_TLD);
        Assert.assertFalse(actualMessage.isEmpty(),
                "Validation message text is empty. Expected a non-empty error message for invalid email: " +
                INVALID_EMAIL_MISSING_TLD);
    }

    @Test(priority = 7,
          testName = "TC-LGN-007",
          description = "Verify that entering a valid unregistered email navigates the user to the Signup page")
    public void verifySuccessfulNavigationWithValidEmail() {
        String urlBeforeSubmit = loginPage.getCurrentUrl();

        loginPage.enterEmail(VALID_UNREGISTERED_EMAIL);
        loginPage.clickContinue();

        boolean navigatedAway = loginPage.isNavigatedAwayFromLogin();
        String urlAfterSubmit = loginPage.getCurrentUrl();

        Assert.assertTrue(navigatedAway,
                "Application did not navigate away from Login after entering a valid email. " +
                "URL before: [" + urlBeforeSubmit + "] URL after: [" + urlAfterSubmit + "]");
    }
    @Test(priority = 8,
            testName = "TC-LGN-008",
            description = "Verify password field is displayed on login page")
    public void verifyPasswordFieldIsDisplayed() {

        boolean isDisplayed = loginPage.isPasswordFieldDisplayed();

        Assert.assertTrue(isDisplayed,
                "Password field is not displayed on login page.");
    }
}
