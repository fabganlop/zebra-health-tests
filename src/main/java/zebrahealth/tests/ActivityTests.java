package zebrahealth.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import zebrahealth.pageObjects.ActivityPage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ActivityTests {

    private static IOSDriver driver;
    private static AppiumDriverLocalService appiumLocalService;
    private static ActivityPage activityPage;

    @BeforeAll
    public static void beforeClass() throws MalformedURLException {
        // Creates local Appium server instance
        appiumLocalService = new AppiumServiceBuilder().usingPort(4723).build();
        appiumLocalService.start();

        // Capabilities define environment (phone, app, session)
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
        capabilities.setCapability(MobileCapabilityType.UDID, "00000000-0000000000000000");
        capabilities.setCapability(MobileCapabilityType.APP, "/Path/To/An/Application.ipa");

        // Driver is the proxy between our script and the phone
        driver = new IOSDriver(appiumLocalService, capabilities);

        // Page Object
        activityPage = new ActivityPage(driver);
    }

    @AfterAll
    public static void afterClass() {
        // Close Driver connection
        driver.quit();
        // End Appium local server run (to free port that was being used)
        appiumLocalService.close();
    }

    @Test
    public void TestAddNewActivity() throws InterruptedException {
        // Add a new activity
        activityPage.clickOnAddActivityButton();
        activityPage.clickOnNumberOfTrotsTextField();
        activityPage.setTextToNumberOfTrotsTextField("400");
        activityPage.clickOnAddButton();
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));

        // Verify new added activity has correct value
        activityPage.clickOnActivity(8);
        assertThat(activityPage.getCurrentValueText(), containsString("400"));
    }
}
