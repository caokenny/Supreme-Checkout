package emerpuS;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kenny on 4/17/2017.
 */
public class SupremeBot {
    ChromeDriver browser;
    WebDriverWait wait;
    Calendar calendar;
    SimpleDateFormat hour = new SimpleDateFormat("hh");
    SimpleDateFormat minutes = new SimpleDateFormat("mm");
    SimpleDateFormat seconds = new SimpleDateFormat("ss");

    //Initialize XPaths for checkout page
    String[] elementXPaths = {"//*[@id=\"order_billing_name\"]",
            "//*[@id=\"order_email\"]",
            "//*[@id=\"order_tel\"]",
            "//*[@id=\"bo\"]",
            "//*[@id=\"oba3\"]",
            "//*[@id=\"order_billing_zip\"]",
            "//*[@id=\"cnb\"]",
            "//*[@id=\"vval\"]",
            "//*[@id=\"header\"]/hgroup/time/b"};

    String[] monthXPaths = {"//*[@id=\"credit_card_month\"]/option[1]",
            "//*[@id=\"credit_card_month\"]/option[2]",
            "//*[@id=\"credit_card_month\"]/option[3]",
            "//*[@id=\"credit_card_month\"]/option[4]",
            "//*[@id=\"credit_card_month\"]/option[5]",
            "//*[@id=\"credit_card_month\"]/option[6]",
            "//*[@id=\"credit_card_month\"]/option[7]",
            "//*[@id=\"credit_card_month\"]/option[8]",
            "//*[@id=\"credit_card_month\"]/option[9]",
            "//*[@id=\"credit_card_month\"]/option[10]",
            "//*[@id=\"credit_card_month\"]/option[11]",
            "//*[@id=\"credit_card_month\"]/option[12]"};

    String[] yearXPaths = {"//*[@id=\"credit_card_year\"]/option[1]",
            "//*[@id=\"credit_card_year\"]/option[2]",
            "//*[@id=\"credit_card_year\"]/option[3]",
            "//*[@id=\"credit_card_year\"]/option[4]",
            "//*[@id=\"credit_card_year\"]/option[5]",
            "//*[@id=\"credit_card_year\"]/option[6]",
            "//*[@id=\"credit_card_year\"]/option[7]",
            "//*[@id=\"credit_card_year\"]/option[8]",
            "//*[@id=\"credit_card_year\"]/option[9]",
            "//*[@id=\"credit_card_year\"]/option[10]",
            "//*[@id=\"credit_card_year\"]/option[11]"};

    int elevenAMMilliSecs = 39600000;

    public SupremeBot(String link, String name, String email, String phone, String address, String address2,
                      String zip, String ccNumber, String expMonth, String expYear, String cvv,
                      String itemName, String itemColor, String itemSize) throws InterruptedException {
        browser = new ChromeDriver();   //Open Chrome
        wait = new WebDriverWait(browser, 5);   //Create WebDriverWait object
        browser.get("http://www.supremenewyork.com/shop/all/accessories");  //Go to Supreme accessories page
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Hanes")));   //Wait until Hanes is clickable
        browser.findElementByPartialLinkText("Hanes").click();  //Click on Hanes
        browser.get(browser.getCurrentUrl());   //Go to current URL, should be the Hanes item
        wait.until(ExpectedConditions.elementToBeClickable(browser.findElementByXPath(
                "//*[@id=\"add-remove-buttons\"]/input"))); //Wait until you can click add to cart
        browser.findElementByXPath("//*[@id=\"add-remove-buttons\"]/input").click();    //Click add to cart
        wait.until(ExpectedConditions.elementToBeClickable(browser.findElementByXPath("//*[@id=\"cart\"]/a[1]")));  //Wait until view cart is clickable
        WebElement cart = browser.findElementByXPath("//*[@id=\"cart\"]/a[1]");
        Actions openInNewTab = new Actions(browser);
        openInNewTab.keyDown(Keys.CONTROL).click(cart).keyUp(Keys.CONTROL).perform();   //Open view cart in new tab
        wait.until(ExpectedConditions.elementToBeClickable(browser.findElementByXPath("//*[@id=\"cart\"]/a[2]")));  //Wait until checkout is clickable
        browser.findElementByXPath("//*[@id=\"cart\"]/a[2]").click();   //Click checkout
        browser.get(browser.getCurrentUrl());   //Go to current page, should be checkout
        //Set up elements to fill checkout page
        WebElement nameElement = browser.findElementByXPath(elementXPaths[0]);
        WebElement emailElement = browser.findElementByXPath(elementXPaths[1]);
        WebElement phoneElement = browser.findElementByXPath(elementXPaths[2]);
        WebElement addressElement = browser.findElementByXPath(elementXPaths[3]);
        WebElement address2Element = browser.findElementByXPath(elementXPaths[4]);
        WebElement zipElement = browser.findElementByXPath(elementXPaths[5]);
        WebElement ccElement = browser.findElementByXPath(elementXPaths[6]);
        //Fill in Billing Info
        nameElement.clear();
        nameElement.sendKeys(name);

        emailElement.clear();
        emailElement.sendKeys(email);

        phoneElement.clear();
        phoneElement.sendKeys(phone);

        addressElement.clear();
        addressElement.sendKeys(address);

        address2Element.clear();
        address2Element.sendKeys(address2);

        zipElement.clear();
        zipElement.sendKeys(zip);

        ccElement.clear();
        ccElement.sendKeys(ccNumber);



        int i = 0;
        while (true){
            if (browser.findElementByXPath(monthXPaths[i]).getText().equals(expMonth)){
                browser.findElementByXPath(monthXPaths[i]).click();
                break;
            }
            else i++;
        }

        i = 0;
        while (true){
            if (browser.findElementByXPath(yearXPaths[i]).getText().equals(expYear)){
                browser.findElementByXPath(yearXPaths[i]).click();
                break;
            }
            else i++;
        }

        WebElement cvvElement = browser.findElementByXPath(elementXPaths[7]);

        cvvElement.clear();
        cvvElement.sendKeys(cvv);
        cvvElement.sendKeys(Keys.TAB, Keys.SPACE);

        ArrayList<String> windowTabs = new ArrayList<>(browser.getWindowHandles()); //Get window handles as strings in array list
        lookForItem(itemName, itemColor, itemSize, link, windowTabs);   //Call lookForItem function
    }

    public void lookForItem(String itemName, String itemColor, String itemSize, String link, ArrayList<String> windowTabs) throws InterruptedException {
        int x = 0;
        browser.switchTo().window(windowTabs.get(1));   //Switch to second tab
        browser.get(browser.getCurrentUrl());   //Go to current URL, should be cart
        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("button")));  //Wait until remove is clickable
        browser.findElementByTagName("button").click(); //Click remove
        //DOUBLE CHECK REMOVE WAS ACTUALLY CLICKED
        if (browser.findElementByTagName("button").isDisplayed()) {
            browser.findElementByTagName("button").click();
        }
        browser.get("http://www.supremenewyork.com/shop/all/" + link);  //Go to the page of the item category
        try {
            Thread.sleep(getTime());    //Get time until drop and make program sleep until then
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true){
            try {
                List<WebElement> itemsOnPage = browser.findElementsByTagName("article");    //Put all items into List
                for (int i = 1; i <= itemsOnPage.size(); i++){
                    WebElement itemElement =
                            browser.findElementByXPath("//*[@id=\"container\"]/article[" + i + "]/div/h1/a");   //Search for item
                    if (itemElement.getText().contains(itemName)){  //When item is found, set x to i
                        x = i;
                        break;
                    }
                    else continue;
                }

                while (true){
                    String holder = "//*[@id=\"container\"]/article[" + x + "]/div/p/a";    //Set holder to XPath of item
                    WebElement linkColor = browser.findElementByXPath(holder);  //Check the colors, click the wanted color
                    if (linkColor.getText().contains(itemColor)){
                        linkColor.click();
                        break;
                    }
                    else x++;
                }
                break;
                //If can't find element then refresh page until found
            } catch (ElementNotVisibleException enve){
                browser.navigate().refresh();
                continue;
            } catch (ElementNotInteractableException enie){
                browser.navigate().refresh();
                continue;
            } catch (ElementNotSelectableException ense){
                browser.navigate().refresh();
                continue;
            }
        }
        checkoutItem(itemSize, windowTabs); //Call checkOutItem function
    }

    public void checkoutItem(String itemSize, ArrayList<String> windowTabs){
        browser.get(browser.getCurrentUrl());   //Get current URL, should be page of item wanted
        try {
            List<WebElement> sizes = browser.findElementsByTagName("option");   //Look for sizes
            for (int i = 1; i <= sizes.size(); i++){
                if (sizes.get(i).getText().contains(itemSize)){
                    sizes.get(i).click();   //Pick size wanted
                    break;
                }
            }
        } catch (Exception e){
            System.out.println("Sizes not available");  //If no sizes found, print no sizes
        }
        browser.findElementByName("commit").click();    //Click add to cart
        browser.switchTo().window(windowTabs.get(0));   //Switch to checkout tab
        browser.findElementByXPath(elementXPaths[0]).submit();  //Submit form
    }

    public long getTime(){
        calendar = Calendar.getInstance();  //Get time from operating system
        long returnThis = TimeUnit.HOURS.toMillis(Integer.parseInt(hour.format(calendar.getTime())));   //Get hours, convert to MILLISECONDS
        returnThis += TimeUnit.MINUTES.toMillis(Integer.parseInt(minutes.format(calendar.getTime())));  //Get minutes, convert to MILLISECONDS
        returnThis += TimeUnit.SECONDS.toMillis(Integer.parseInt(seconds.format(calendar.getTime())));  //Get seconds, convert to MILLISECONDS
        returnThis = elevenAMMilliSecs - returnThis;    //Add up all MILLISECONDS and subtract from 11 hours MILLISECONDS
        System.out.printf("Sleeping for %d minutes.", TimeUnit.MILLISECONDS.toMinutes(returnThis)); //Print how long we sleep for
        return returnThis;  //Return MILLISECONDS
    }
}