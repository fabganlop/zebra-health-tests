package zebrahealth.pageObjects;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.devtools.v95.input.model.TouchPoint;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
public class ActivityPage {
    IOSDriver driver;
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"id_barchart_view\"])[1]")
    WebElement dailyActivityText;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"id_barchart_view\"])[2]")
    WebElement currentValueText;

    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeStaticText[@name=\"id_barchart_view\"])[3]")
    WebElement trotsText;

    @iOSXCUITFindBy(id = "id_slideover_add_activity_button")
    WebElement addButton;

    @iOSXCUITFindBy(id = "id_slideover_trots_textfield")
    WebElement numberOfTrotsTextField;

    @iOSXCUITFindBy(id = "Add Activity")
    WebElement addActivityButton;

    int activities;

    public ActivityPage(IOSDriver driver2) {
        driver = driver2;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);

        // Initial number of activities
        activities = 7;
    }
    public String getDailyActivityText() { return dailyActivityText.getText(); }
    public String getCurrentValueText() { return currentValueText.getText(); }
    public String getTrotsText() { return trotsText.getText(); }

    public void setTextToNumberOfTrotsTextField(String text) { numberOfTrotsTextField.sendKeys(text); }
    public void clickOnNumberOfTrotsTextField() { numberOfTrotsTextField.click(); }
    public void clickOnAddActivityButton() { addActivityButton.click(); }
    public void clickOnAddButton() { addButton.click(); }

    public void clickOnActivity(int noOfActivity) throws InterruptedException {
        int width  = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int x = (width / (activities + 2)) * (noOfActivity + 1);
        int y = height / 2;

        new TouchAction<>(driver).press(PointOption.point(x, y))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2))).release().perform();
    }
}
