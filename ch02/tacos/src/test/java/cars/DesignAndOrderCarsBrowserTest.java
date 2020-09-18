package cars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DesignAndOrderCarsBrowserTest {
  
  private static HtmlUnitDriver browser;
  
  @LocalServerPort
  private int port;
  
  @Autowired
  TestRestTemplate rest;
  
  @BeforeClass
  public static void setup() {
    browser = new HtmlUnitDriver();
    browser.manage().timeouts()
        .implicitlyWait(10, TimeUnit.SECONDS);
  }
  
  @AfterClass
  public static void closeBrowser() {
    browser.quit();
  }
  
  @Test
  public void testDesignACarPage_HappyPath() throws Exception {
    browser.get(homePageUrl());
    clickDesignACar();
    assertDesignPageElements();
    buildAndSubmitACar("Basic Car", "SUBW", "SEHA", "SPPL", "MAFL", "RORE");
    clickBuildAnotherCar();
    buildAndSubmitACar("Another Car", "SPPL", "RORE", "HADL", "AMPL", "SUBW");
    fillInAndSubmitOrderForm();
    assertEquals(homePageUrl(), browser.getCurrentUrl());
  }
  
  @Test
  public void testDesignACarPage_EmptyOrderInfo() throws Exception {
    browser.get(homePageUrl());
    clickDesignACar();
    assertDesignPageElements();
    buildAndSubmitACar("Another Car", "SPPL", "RORE", "HADL", "AMPL", "SUBW");
    submitEmptyOrderForm();
    fillInAndSubmitOrderForm();
    assertEquals(homePageUrl(), browser.getCurrentUrl());
  }

  @Test
  public void testDesignACarPage_InvalidOrderInfo() throws Exception {
    browser.get(homePageUrl());
    clickDesignACar();
    assertDesignPageElements();
    buildAndSubmitACar("Another Car", "SPPL", "RORE", "HADL", "AMPL", "SUBW");
    submitInvalidOrderForm();
    fillInAndSubmitOrderForm();
    assertEquals(homePageUrl(), browser.getCurrentUrl());
  }

  //
  // Browser test action methods
  //
  private void buildAndSubmitACar(String name, String... Gears) {
    assertDesignPageElements();

    for (String Gear : Gears) {
      browser.findElementByCssSelector("input[value='" + Gear + "']").click();
    }
    browser.findElementByCssSelector("input#name").sendKeys(name);
    browser.findElementByCssSelector("form").submit();
  }

  private void assertDesignPageElements() {
    assertEquals(designPageUrl(), browser.getCurrentUrl());
    List<WebElement> GearGroups = browser.findElementsByClassName("Gear-group");
    assertEquals(5, GearGroups.size());
    
    WebElement wrapGroup = browser.findElementByCssSelector("div.Gear-group#audio");
    List<WebElement> wraps = wrapGroup.findElements(By.tagName("div"));
    assertEquals(2, wraps.size());
    assertGear(wrapGroup, 0, "SUBW", "Subwoofers");
    assertGear(wrapGroup, 1, "AMPL", "Amplifiers");
    
    WebElement proteinGroup = browser.findElementByCssSelector("div.Gear-group#interior");
    List<WebElement> proteins = proteinGroup.findElements(By.tagName("div"));
    assertEquals(2, proteins.size());
    assertGear(proteinGroup, 0, "SEHA", "Seat harnesses");
    assertGear(proteinGroup, 1, "FIEX", "Fire extinguishers");

    WebElement cheeseGroup = browser.findElementByCssSelector("div.Gear-group#engine");
    List<WebElement> cheeses = proteinGroup.findElements(By.tagName("div"));
    assertEquals(2, cheeses.size());
    assertGear(cheeseGroup, 0, "SPPL", "Spark plugs");
    assertGear(cheeseGroup, 1, "MAFL", "Mass air flow");

    WebElement veggieGroup = browser.findElementByCssSelector("div.Gear-group#suspension");
    List<WebElement> veggies = proteinGroup.findElements(By.tagName("div"));
    assertEquals(2, veggies.size());
    assertGear(veggieGroup, 0, "SPRI", "Springs");
    assertGear(veggieGroup, 1, "SHAB", "Shock absorbers");

    WebElement sauceGroup = browser.findElementByCssSelector("div.Gear-group#tires");
    List<WebElement> sauces = proteinGroup.findElements(By.tagName("div"));
    assertEquals(2, sauces.size());
    assertGear(sauceGroup, 0, "RORE", "Rolling resistance");
    assertGear(sauceGroup, 1, "HADL", "Handling");
  }
  

  private void fillInAndSubmitOrderForm() {
    assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
    fillField("input#name", "Ima Hungry");
    fillField("input#street", "1234 Culinary Blvd.");
    fillField("input#city", "Foodsville");
    fillField("input#state", "CO");
    fillField("input#zip", "81019");
    fillField("input#ccNumber", "4111111111111111");
    fillField("input#ccExpiration", "10/19");
    fillField("input#ccCVV", "123");
    browser.findElementByCssSelector("form").submit();
  }

  private void submitEmptyOrderForm() {
    assertEquals(currentOrderDetailsPageUrl(), browser.getCurrentUrl());
    browser.findElementByCssSelector("form").submit();
    
    assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

    List<String> validationErrors = getValidationErrorTexts();
    assertEquals(9, validationErrors.size());
    assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
    assertTrue(validationErrors.contains("Name is required"));
    assertTrue(validationErrors.contains("Street is required"));
    assertTrue(validationErrors.contains("City is required"));
    assertTrue(validationErrors.contains("State is required"));
    assertTrue(validationErrors.contains("Zip code is required"));
    assertTrue(validationErrors.contains("Not a valid credit card number"));
    assertTrue(validationErrors.contains("Must be formatted MM/YY"));
    assertTrue(validationErrors.contains("Invalid CVV"));    
  }

  private List<String> getValidationErrorTexts() {
    List<WebElement> validationErrorElements = browser.findElementsByClassName("validationError");
    List<String> validationErrors = validationErrorElements.stream()
        .map(el -> el.getText())
        .collect(Collectors.toList());
    return validationErrors;
  }

  private void submitInvalidOrderForm() {
    assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
    fillField("input#name", "I");
    fillField("input#street", "1");
    fillField("input#city", "F");
    fillField("input#state", "C");
    fillField("input#zip", "8");
    fillField("input#ccNumber", "1234432112344322");
    fillField("input#ccExpiration", "14/91");
    fillField("input#ccCVV", "1234");
    browser.findElementByCssSelector("form").submit();
    
    assertEquals(orderDetailsPageUrl(), browser.getCurrentUrl());

    List<String> validationErrors = getValidationErrorTexts();
    assertEquals(4, validationErrors.size());
    assertTrue(validationErrors.contains("Please correct the problems below and resubmit."));
    assertTrue(validationErrors.contains("Not a valid credit card number"));
    assertTrue(validationErrors.contains("Must be formatted MM/YY"));
    assertTrue(validationErrors.contains("Invalid CVV"));    
  }

  private void fillField(String fieldName, String value) {
    WebElement field = browser.findElementByCssSelector(fieldName);
    field.clear();
    field.sendKeys(value);
  }
  
  private void assertGear(WebElement GearGroup,
                                int GearIdx, String id, String name) {
    List<WebElement> proteins = GearGroup.findElements(By.tagName("div"));
    WebElement Gear = proteins.get(GearIdx);
    assertEquals(id, 
        Gear.findElement(By.tagName("input")).getAttribute("value"));
    assertEquals(name, 
        Gear.findElement(By.tagName("span")).getText());
  }

  private void clickDesignACar() {
    assertEquals(homePageUrl(), browser.getCurrentUrl());
    browser.findElementByCssSelector("a[id='design']").click();
  }

  private void clickBuildAnotherCar() {
    assertTrue(browser.getCurrentUrl().startsWith(orderDetailsPageUrl()));
    browser.findElementByCssSelector("a[id='another']").click();
  }

 
  //
  // URL helper methods
  //
  private String designPageUrl() {
    return homePageUrl() + "design";
  }

  private String homePageUrl() {
    return "http://localhost:" + port + "/";
  }

  private String orderDetailsPageUrl() {
    return homePageUrl() + "orders";
  }

  private String currentOrderDetailsPageUrl() {
    return homePageUrl() + "orders/current";
  }

}
