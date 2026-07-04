package com.tichi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final int EXPLICIT_WAIT_SECONDS = 15;

    // ── Locators ────────────────────────────────────────────────────────────

    private final By emailField = By.cssSelector("input[type='email']");

    private final By continueButton = By.xpath(
            "//button[normalize-space(text())='Continue'] | " +
            "//button[contains(@class,'continue') and not(contains(translate(text(),'GOOGLE','google'),'google'))] | " +
            "//button[@type='submit' and not(contains(translate(normalize-space(.),'GOOGLE','google'),'google'))]"
    );

    private final By continueWithGoogleButton =
            By.xpath("//*[contains(.,'Google')]");

    private final By emailValidationMessage =
            By.xpath("//*[contains(text(),'valid') or contains(text(),'email') or contains(text(),'required')]");

    private final By pageHeading = By.xpath(
            "//h1 | //h2 | //*[contains(@class,'title') or contains(@class,'heading') or contains(@class,'logo')]"
    );
    private final By passwordField = By.cssSelector("input[type='password']");
//    private final By emailemptymeassage =
//            By.xpath("//*[contains(text(),'Email is required')]");

    // ── Constructor ─────────────────────────────────────────────────────────

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT_SECONDS));
    }

    // ── Actions ─────────────────────────────────────────────────────────────

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(emailField),
                    ExpectedConditions.visibilityOfElementLocated(pageHeading)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailFieldDisplayed() {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
            return field.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isContinueButtonDisplayed() {
        try {
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(continueButton));
            return button.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isContinueWithGoogleButtonDisplayed() {
        try {
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(continueWithGoogleButton));
            return button.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        field.clear();
        field.sendKeys(email);
    }

    public void clickContinue() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        button.click();
    }

    public void submitEmptyEmail() {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        field.clear();
        clickContinue();
    }

    public String getEmailValidationMessage() {
        try {
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(emailValidationMessage));
            return message.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isValidationMessageDisplayed() {
        try {
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(emailValidationMessage));
            return message.isDisplayed() && !message.getText().trim().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isNavigatedAwayFromLogin() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("signup"),
                    ExpectedConditions.urlContains("register"),
                    ExpectedConditions.urlContains("sign-up"),
                    ExpectedConditions.not(ExpectedConditions.urlToBe(driver.getCurrentUrl()))
            ));
            String currentUrl = driver.getCurrentUrl();
            return currentUrl != null && (
                    currentUrl.contains("signup") ||
                    currentUrl.contains("register") ||
                    currentUrl.contains("sign-up") ||
                    currentUrl.contains("password") ||
                    currentUrl.contains("verify")
            );
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isPasswordFieldDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
